package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.util.AbstractNamedItem;

@Entity
@Table(name = "steuerungen", indexes = { @Index(columnList = "name", unique = true) }, 
       uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Steuerung extends AbstractNamedItem implements ISteuerung {

    private static final long serialVersionUID = 6787896300087581256L;

    public Steuerung() {
		super();
	}

	public Steuerung( Long id, String name, String bezeichnung, Boolean deleted) {
		super(id, name, bezeichnung, deleted);
	}
}