package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.linepro.modellbahn.model.IEpoch;
import com.linepro.modellbahn.model.util.AbstractNamedItem;

/**
 * Epoch.
 * The NEM 800 Epoch for a product
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = "Epoch")
@Table(name = "epochen", indexes = { @Index(columnList = "name", unique = true) }, 
       uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@JsonRootName(value = "era")
public class Epoch extends AbstractNamedItem implements IEpoch {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1742959707816247906L;

    /**
     * Instantiates a new epoch.
     */
    public Epoch() {
		super();
	}

    public Epoch(String name) {
        super(name);
    }

	/**
	 * Instantiates a new epoch.
	 *
	 * @param id the id
	 * @param name the name
	 * @param bezeichnung the bezeichnung
	 * @param deleted the deleted
	 */
	public Epoch( Long id, String name, String bezeichnung, Boolean deleted) {
		super(id, name, bezeichnung, deleted);
	}
}