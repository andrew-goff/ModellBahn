/*
 * Achsfolg
 * Author:  JohnG
 * Version: $id$
 */
package com.linepro.modellbahn.model.impl;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.linepro.modellbahn.model.IAchsfolg;
import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.rest.util.ApiNames;

/**
 * Achsfolg Represents Axle configuration using German nomenclature.
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = "Achsfolg")
@Table(name = "Achsfolg", indexes = { @Index(columnList = ApiNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { ApiNames.NAME }) })
@JsonRootName(value = ApiNames.ACHSFOLG)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAME, ApiNames.DESCRIPTION, ApiNames.DELETED, ApiNames.LINKS })
public class Achsfolg extends AbstractNamedItem implements IAchsfolg {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 210058293067560474L;

    /**
     * Instantiates a new achsfolg.
     */
    public Achsfolg() {
        super();
    }

    public Achsfolg(String name) {
        super(name);
    }

    /**
     * Instantiates a new achsfolg.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param bezeichnung
     *            the description
     * @param deleted
     *            if true this achsfolg is soft deleted
     */
    public Achsfolg(Long id,
            String name,
            String bezeichnung,
            Boolean deleted) {
        super(id, name, bezeichnung, deleted);
    }
}