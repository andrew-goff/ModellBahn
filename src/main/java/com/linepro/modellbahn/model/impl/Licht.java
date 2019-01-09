package com.linepro.modellbahn.model.impl;

import java.nio.file.Path;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.ILicht;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.PathConverter;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Licht. The NEM 006 / Märklin light configuration for a product
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.LICHT)
@Table(name = DBNames.LICHT, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Licht extends AbstractNamedItem implements ILicht {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6288751316098975414L;

    /** The abbildung. */
    private Path abbildung;

    /**
     * Instantiates a new licht.
     */
    public Licht() {
        super();
    }

    public Licht(String name) {
        super(name);
    }

    /**
     * Instantiates a new licht.
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
    public Licht(Long id, String name, String bezeichnung, Boolean deleted) {
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