package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.rest.util.ApiNames;

/**
 * Gattung.
 * The Gattung (or Baureihe) for product
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = "Gattung")
@Table(name = "Gattung", indexes = { @Index(columnList = ApiNames.NAME, unique = true) }, 
       uniqueConstraints = { @UniqueConstraint(columnNames = { ApiNames.NAME }) })
@JsonRootName(value = ApiNames.GATTUNG)
@JsonPropertyOrder({ApiNames.ID, ApiNames.NAME, ApiNames.DESCRIPTION, ApiNames.DELETED, ApiNames.LINKS})
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
	 * @param id the id
	 * @param name the name
	 * @param bezeichnung the bezeichnung
	 * @param deleted the deleted
	 */
	public Gattung( Long id, String name, String bezeichnung, Boolean deleted) {
		super(id, name, bezeichnung, deleted);
	}
}