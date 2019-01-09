package com.linepro.modellbahn.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.IZug;
import com.linepro.modellbahn.model.IZugConsist;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * ZugConsist. A component of a train
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.ZUG_CONSIST)
@Table(name = DBNames.ZUG_CONSIST, indexes = { @Index(columnList = DBNames.ZUG_ID),
        @Index(columnList = DBNames.ARTIKEL_ID, unique = true) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { DBNames.ZUG_ID, DBNames.POSITION }) })
public class ZugConsist extends AbstractItem implements IZugConsist {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3941436184732408563L;

    /** The zug. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.zug.notnull}")
    private IZug zug;

    /** The position. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.position.notnull}")
    @Positive(message = "{com.linepro.modellbahn.validator.constraints.position.positive}")
    private Integer position;

    /** The artikel. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.artikel.notnull}")
    private IArtikel artikel;

    /**
     * Instantiates a new zug consist.
     */
    public ZugConsist() {
        super();
    }

    /**
     * Instantiates a new zug consist.
     *
     * @param id
     *            the id
     * @param zug
     *            the zug
     * @param position
     *            the position
     * @param artikel
     *            the artikel
     * @param deleted
     *            the deleted
     */
    public ZugConsist(Long id, IZug zug, Integer position, IArtikel artikel, Boolean deleted) {
        super(id, deleted);

        setZug(zug);
        setPosition(position);
        setArtikel(artikel);
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Zug.class)
    @JoinColumn(name = DBNames.ZUG_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ZUG_CONSIST + "_fk1"))
    public IZug getZug() {
        return zug;
    }

    @Override
    public void setZug(IZug zug) {
        this.zug = zug;
    }

    @Override
    @BusinessKey
    @Column(name = DBNames.POSITION, nullable = false)
    public Integer getPosition() {
        return position;
    }

    @Override
    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Artikel.class)
    @JoinColumn(name = DBNames.ARTIKEL_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ZUG_CONSIST + "_fk2"))
    public IArtikel getArtikel() {
        return artikel;
    }

    @Override
    public void setArtikel(IArtikel artikel) {
        this.artikel = artikel;
    }

    @Override
    @Transient
    public String getParentId() {
        return getZug().getLinkId();
    }

    @Override
    @Transient
    public String getLinkId() {
        return String.format(ApiPaths.ZUG_CONSIST_LINK, getParentId(), getPosition());
    }

    @Override
    public int compareTo(IItem other) {
        if (other instanceof ZugConsist) {
            return new CompareToBuilder()
                    .append(getZug(), ((ZugConsist) other).getZug())
                    .append(getPosition(), ((ZugConsist) other).getPosition())
                    .toComparison();
        }
        
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getZug())
                .append(getPosition())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ZugConsist)) {
            return false;
        }

        ZugConsist other = (ZugConsist) obj;

        return new EqualsBuilder()
                .append(getZug(), other.getZug())
                .append(getPosition(), other.getPosition())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.ZUG, getZug())
                .append(ApiNames.POSITION, getPosition())
                .append(ApiNames.ARTIKEL, getArtikel())
                .toString();
    }
}
