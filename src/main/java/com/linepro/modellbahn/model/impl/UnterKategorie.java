package com.linepro.modellbahn.model.impl;

import javax.persistence.AttributeOverride;
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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.linepro.modellbahn.model.IKategorie;
import com.linepro.modellbahn.model.IUnterKategorie;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * UnterKategorie.
 * The sub category for a product
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.UNTER_KATEGORIE)
@Table(name = DBNames.UNTER_KATEGORIE, indexes = { @Index(columnList = DBNames.KATEGORIE_ID +"," + DBNames.NAME, unique = true),
        @Index(columnList = DBNames.KATEGORIE_ID) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { DBNames.KATEGORIE_ID, DBNames.NAME }) })
@AttributeOverride(name = DBNames.NAME, column = @Column(name = DBNames.NAME, length = 50))
public class UnterKategorie extends AbstractNamedItem implements IUnterKategorie {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5346529720680464691L;

    /** The kategorie. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.kategorie.notnull}")
    private IKategorie kategorie;

    /**
     * Instantiates a new unter kategorie.
     */
    public UnterKategorie() {
        super();
    }

    public UnterKategorie(IKategorie kategorie, String name) {
        super(name);
       
        setKategorie(kategorie);
    }

    /**
     * Instantiates a new unter kategorie.
     *
     * @param id the id
     * @param kategorie the kategorie
     * @param name the name
     * @param bezeichnung the bezeichnung
     * @param deleted the deleted
     */
    public UnterKategorie(Long id, IKategorie kategorie, String name, String bezeichnung, Boolean deleted) {
        super(id, name, bezeichnung, deleted);

        setKategorie(kategorie);
    }

    @BusinessKey
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Kategorie.class)
    @JoinColumn(name = DBNames.KATEGORIE_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.UNTER_KATEGORIE + "_fk1"))
    public IKategorie getKategorie() {
        return kategorie;
    }

    @Override
    public void setKategorie(IKategorie kategorie) {
        this.kategorie = kategorie;
    }
    
    @Override
    @Transient
    public String getParentId() {
        return getKategorie().getLinkId();
    }

    @Override
    @Transient
    public String getLinkId() {
        return String.format(ApiPaths.UNTER_KATEGORIE_LINK, getParentId(), super.getLinkId());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getKategorie())
                .append(getName())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof UnterKategorie)) {
            return false;
        }

        UnterKategorie other = (UnterKategorie) obj;

        return new EqualsBuilder()
                .append(getKategorie(), other.getKategorie())
                .append(getName(), other.getName())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.KATEGORIE, getKategorie())
                .toString();
    }
}