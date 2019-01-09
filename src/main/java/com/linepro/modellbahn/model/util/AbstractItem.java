package com.linepro.modellbahn.model.util;

import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.GET;
import static javax.ws.rs.HttpMethod.PUT;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * AbstractItem.
 * The base class for all items 
 * @author  $Author:$
 * @version $Id:$
 */
@MappedSuperclass
@Cacheable
public abstract class AbstractItem implements IItem {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 938276986391979417L;

    /** The primary key id. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.id.notnull}")
    private Long id;

	/** The soft deleted state. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.deleted.notnull}")
	private Boolean deleted;
	
	/** Set of HATEOAS links for Json serialization */
    private final Set<Link> links = new HashSet<>();

	/**
	 * Instantiates a new abstract item.
	 */
	public AbstractItem() {
	}

	/**
	 * Instantiates a new abstract item.
	 *
	 * @param id the id
	 * @param deleted the deleted
	 */
    public AbstractItem(Long id, Boolean deleted) {
		setId(id);
		setDeleted(deleted);
	}

	@Override
    @Id
	@Column(name=DBNames.ID)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@Override
    public void setId(Long id) {
		this.id = id;
	}

	@Override
    @Column(name=DBNames.DELETED, length=5)
	public Boolean getDeleted() {
		return deleted;
	}

	@Override
    public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

    @Override
    @Transient
    public Set<Link> getLinks() {
        return links;
    }

    @Override
    @Transient
    public String getParentId() {
        return null;
    }

    @Override
    @Transient
    public String getLinkId() {
        return getId().toString();
    }

    private Link makeLink(URI uri, String path, String rel, String method) {
        return Link.fromUri(UriBuilder.fromUri(uri).path(path).build()).rel(rel).type(method).build();
    }
    
    @Override
    public IItem addLinks(URI root, boolean update, boolean delete) {
        getLinks().clear();

        addParent(root);
        addSelf(root);

        if (update) {
            addUpdate(root);
        }
        
        if (delete) {
            addDelete(root);
        }

        addChildLinks(root, update, delete);

        return this;
    }
    
    protected void addChildLinks(URI root, boolean update, boolean delete) {
    }

    protected void addLinks(URI root, Collection<? extends IItem> items, boolean update, boolean delete) {
        if (!items.isEmpty()) {
            for (IItem item : items) {
                item.addLinks(root, update, delete);
            }
        }
        
    }

    private void addParent(URI root) {
        if (getParentId() != null) {
            getLinks().add(makeLink(root, getParentId(), ApiNames.PARENT, GET));
        }
    }

    private void addDelete(URI root) {
        getLinks().add(makeLink(root, getLinkId(), ApiNames.DELETE, DELETE));
    }

    private void addSelf(URI root) {
        getLinks().add(makeLink(root, getLinkId(), ApiNames.SELF, GET));
    }

    private void addUpdate(URI root) {
        getLinks().add(makeLink(root, getLinkId(), ApiNames.UPDATE, PUT));
    }

    @Override
    public int compareTo(IItem other) {
        return new CompareToBuilder()
                .append(getId(), other.getId())
                .toComparison();
    }
    
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof AbstractItem)) {
			return false;
		}

		IItem other = (IItem) obj;

		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append(ApiNames.ID, getId())
				.append(ApiNames.DELETED, getDeleted())
				.toString();
	}
}