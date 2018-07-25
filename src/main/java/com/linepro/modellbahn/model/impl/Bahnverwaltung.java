package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.linepro.modellbahn.model.IBahnverwaltung;
import com.linepro.modellbahn.model.util.AbstractNamedItem;

/**
 * Bahnverwaltung.
 * Represents a Railway company. 
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = "Bahnverwaltung")
@Table(name = "bahnverwaltungen", indexes = { @Index(columnList = "name", unique = true) }, 
       uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@JsonRootName(value = "railway")
public class Bahnverwaltung extends AbstractNamedItem implements IBahnverwaltung {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1665511590535290700L;

    /**
     * Instantiates a new bahnverwaltung.
     */
    public Bahnverwaltung() {
		super();
	}

    public Bahnverwaltung(String name) {
        super(name);
    }

    /**
	 * Instantiates a new bahnverwaltung.
	 *
	 * @param id the id
	 * @param name the name
	 * @param bezeichnung the bezeichnung
	 * @param deleted the deleted
	 */
	public Bahnverwaltung( Long id, String name, String bezeichnung, Boolean deleted) {
		super(id, name, bezeichnung, deleted);
	}
}