package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.linepro.modellbahn.model.ISonderModell;
import com.linepro.modellbahn.model.keys.NameKey;
import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.rest.util.ApiNames;

/**
 * SonderModell. Special category for a product. E.g. MHI, Einmaligeserien
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = "SonderModel")
@Table(name = "SonderModel", indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
@JsonRootName(value = ApiNames.SONDERMODELL)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAME, ApiNames.DESCRIPTION, ApiNames.DELETED, ApiNames.LINKS })
public class SonderModell extends AbstractNamedItem<NameKey> implements ISonderModell {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1654429154794267608L;

    /**
     * Instantiates a new sonder modell.
     */
    public SonderModell() {
        super();
    }

    public SonderModell(String name) {
        super(name);
    }

    /**
     * Instantiates a new sonder modell.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param bezeichnung
     *            the bezeichnung
     * @param deleted
     *            the deleted
     */
    public SonderModell(Long id, String name, String bezeichnung, Boolean deleted) {
        super(id, name, bezeichnung, deleted);
    }
}