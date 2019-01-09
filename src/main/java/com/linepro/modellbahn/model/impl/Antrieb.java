package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.IAntrieb;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;

/**
 * Antrieb. Represents a drive method.
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.ANTRIEB)
@Table(name = DBNames.ANTRIEB, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Antrieb extends AbstractNamedItem implements IAntrieb {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6791703187859778429L;

    /**
     * Instantiates a new antrieb.
     */
    public Antrieb() {
        super();
    }

    public Antrieb(String name) {
        super(name);
    }

    /**
     * Instantiates a new antrieb.
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
    public Antrieb(Long id, String name, String bezeichnung, Boolean deleted) {
        super(id, name, bezeichnung, deleted);
    }
}