package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.util.AbstractNamedItem;

/**
 * Steuerung.
 * The steering method for a product.
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = "Steuerung")
@Table(name = "steuerungen", indexes = { @Index(columnList = "name", unique = true) }, 
       uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Steuerung extends AbstractNamedItem implements ISteuerung {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6787896300087581256L;

    /**
     * Instantiates a new steuerung.
     */
    public Steuerung() {
		super();
	}

    public Steuerung(String name) {
        super(name);
    }

    /**
	 * Instantiates a new steuerung.
	 *
	 * @param id the id
	 * @param name the name
	 * @param bezeichnung the bezeichnung
	 * @param deleted the deleted
	 */
	public Steuerung( Long id, String name, String bezeichnung, Boolean deleted) {
		super(id, name, bezeichnung, deleted);
	}
}