package com.linepro.modellbahn.model.impl;

import java.nio.file.Path;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.IKupplung;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.PathConverter;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Kupplung. The coupling configuration for a product
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.KUPPLUNG)
@Table(name = DBNames.KUPPLUNG, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Kupplung extends AbstractNamedItem implements IKupplung {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3158490202101950479L;

    /** The abbildung. */
    private Path abbildung;

    /**
     * Instantiates a new kupplung.
     */
    public Kupplung() {
        super();
    }

    public Kupplung(String name) {
        super(name);
    }

    /**
     * Instantiates a new kupplung.
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
    public Kupplung(Long id, String name, String bezeichnung, Boolean deleted) {
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