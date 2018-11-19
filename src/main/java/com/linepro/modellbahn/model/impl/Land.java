package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.linepro.modellbahn.model.ILand;
import com.linepro.modellbahn.model.IWahrung;
import com.linepro.modellbahn.model.keys.NameKey;
import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.resolver.WahrungResolver;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Land. The country of a product.
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.LAND)
@Table(name = DBNames.LAND, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
@JsonRootName(value = ApiNames.LAND)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.WAHRUNG, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED,
        ApiNames.LINKS })
public class Land extends AbstractNamedItem<NameKey> implements ILand {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5352015940349871580L;

    /** The wahrung. */
    private IWahrung wahrung;

    /**
     * Instantiates a new land.
     */
    public Land() {
        super();
    }

    public Land(String name) {
        super(name);
    }

    /**
     * Instantiates a new land.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param bezeichnung
     *            the bezeichnung
     * @param wahrung
     *            the wahrung
     * @param deleted
     *            the deleted
     */
    public Land(Long id, String name, String bezeichnung, IWahrung wahrung, Boolean deleted) {
        super(id, name, bezeichnung, deleted);

        setWahrung(wahrung);
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Wahrung.class)
    @JsonGetter(ApiNames.WAHRUNG)
    @JsonView(Views.Public.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = ApiNames.NAMEN, resolver=WahrungResolver.class)
    public IWahrung getWahrung() {
        return wahrung;
    }

    @Override
    @JsonSetter(ApiNames.WAHRUNG)
    @JsonDeserialize(as=Wahrung.class)
    public void setWahrung(IWahrung wahrung) {
        this.wahrung = wahrung;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.WAHRUNG, getWahrung())
                .toString();
    }
}