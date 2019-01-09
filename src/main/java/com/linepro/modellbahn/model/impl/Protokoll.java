package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.IProtokoll;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;

/**
 * Protokoll. The communications protocol used by a Decoder
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.PROTOKOLL)
@Table(name = DBNames.PROTOKOLL, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Protokoll extends AbstractNamedItem implements IProtokoll {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2601992994975232884L;

    /**
     * Instantiates a new protokoll.
     */
    public Protokoll() {
        super();
    }

    public Protokoll(String name) {
        super(name);
    }

    /**
     * Instantiates a new protokoll.
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
    public Protokoll(Long id, String name, String bezeichnung, Boolean deleted) {
        super(id, name, bezeichnung, deleted);
    }
}