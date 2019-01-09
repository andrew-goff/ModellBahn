package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.IMassstab;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;

/**
 * Massstab. The NEM 010 Maßstäb (scale) of a product.
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.MASSSTAB)
@Table(name = DBNames.MASSSTAB, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Massstab extends AbstractNamedItem implements IMassstab {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3531688695840325563L;

    /**
     * Instantiates a new massstab.
     */
    public Massstab() {
        super();
    }

    public Massstab(String name) {
        super(name);
    }

    /**
     * Instantiates a new massstab.
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
    public Massstab(Long id, String name, String bezeichnung, Boolean deleted) {
        super(id, name, bezeichnung, deleted);
    }
}