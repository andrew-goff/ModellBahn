package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.IGattung;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;

/**
 * Gattung. The Gattung (or Baureihe) for product
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.GATTUNG)
@Table(name = DBNames.GATTUNG, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Gattung extends AbstractNamedItem implements IGattung {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6746062683096738117L;

    /**
     * Instantiates a new gattung.
     */
    public Gattung() {
        super();
    }

    public Gattung(String name) {
        super(name);
    }

    /**
     * Instantiates a new gattung.
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
    public Gattung(Long id, String name, String bezeichnung, Boolean deleted) {
        super(id, name, bezeichnung, deleted);
    }
}