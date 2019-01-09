package com.linepro.modellbahn.model.impl;

import java.nio.file.Path;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.IAufbau;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.PathConverter;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Aufbau. Represents a construction method.
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.AUFBAU)
@Table(name = DBNames.AUFBAU, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Aufbau extends AbstractNamedItem implements IAufbau {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 233985591709407388L;

    /** The abbildung. */
    private Path abbildung;

    /**
     * Instantiates a new aufbau.
     */
    public Aufbau() {
        super();
    }

    public Aufbau(String name) {
        super(name);
    }

    /**
     * Instantiates a new aufbau.
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
    public Aufbau(Long id, String name, String bezeichnung, Boolean deleted) {
        super(id, name, bezeichnung, deleted);
    }

    @Override
    @Column(name = DBNames.ABBILDUNG)
    @Convert(converter = PathConverter.class)
    public Path getAbbildung() {
        return abbildung;
    }

    @Override
    public void setAbbildung(Path abbildung) {
        this.abbildung = abbildung;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(ApiNames.ABBILDUNG, getAbbildung())
                .toString();
    }
}