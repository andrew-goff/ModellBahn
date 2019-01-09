package com.linepro.modellbahn.model.impl;

import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.URLConverter;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Hersteller. The manufacturer for a product
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.HERSTELLER)
@Table(name = DBNames.HERSTELLER, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Hersteller extends AbstractNamedItem implements IHersteller {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2896366936132390553L;

    /** The url. */
    //@org.hibernate.validator.constraints.URL
    private URL url;

    /** The Telefon. */
    //@Telephone
    private String Telefon;

    /**
     * Instantiates a new hersteller.
     */
    public Hersteller() {
        super();
    }

    public Hersteller(String name) {
        super(name);
    }

    /**
     * Instantiates a new hersteller.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param bezeichnung
     *            the bezeichnung
     * @param url
     *            the url
     * @param telefon
     *            the telefon
     * @param deleted
     *            the deleted
     */
    public Hersteller(Long id, String name, String bezeichnung, URL url, String telefon, Boolean deleted) {
        super(id, name, bezeichnung, deleted);

        setUrl(url);
        setTelefon(telefon);
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    @Override
    @Column(name = DBNames.URL)
    @Convert(converter = URLConverter.class)
    public URL getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param uRL
     *            the new url
     */
    @Override
    public void setUrl(URL uRL) {
        url = uRL;
    }

    /**
     * Gets the telefon.
     *
     * @return the telefon
     */
    @Override
    @Column(name = DBNames.TELEFON, length = 20)
    public String getTelefon() {
        return Telefon;
    }

    /**
     * Sets the telefon.
     *
     * @param telefon
     *            the new telefon
     */
    @Override
    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.URL, getUrl())
                .append(ApiNames.TELEFON, getTelefon())
                .toString();
    }
}