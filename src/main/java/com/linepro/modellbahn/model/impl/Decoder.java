package com.linepro.modellbahn.model.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderAdress;
import com.linepro.modellbahn.model.IDecoderCV;
import com.linepro.modellbahn.model.IDecoderFunktion;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IProtokoll;
import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.rest.json.DecoderAdressSerializer;
import com.linepro.modellbahn.rest.json.DecoderCVSerializer;
import com.linepro.modellbahn.rest.json.DecoderFunktionSerializer;
import com.linepro.modellbahn.rest.json.DecoderTypSerializer;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Decoder.
 * Represents a decoder (with its settings).
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = "Decoder")
@Table(name = "Decoder", indexes = { @Index(columnList = DBNames.NAME, unique = true), @Index(columnList = DBNames.DECODER_TYP_ID),
        @Index(columnList = DBNames.PROTOKOLL_ID) }, uniqueConstraints = { @UniqueConstraint(columnNames = { DBNames.NAME }) })
@JsonRootName(value = ApiNames.DECODER)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.DECODER_ID, ApiNames.HERSTELLER, ApiNames.BESTELL_NR, ApiNames.DESCRIPTION, ApiNames.PROTOKOLL, ApiNames.FAHRSTUFE, ApiNames.ADRESSEN, ApiNames.DELETED, ApiNames.CVS, ApiNames.FUNKTIONEN, ApiNames.LINKS })
public class Decoder extends AbstractNamedItem implements IDecoder {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 44440227704021482L;

    /** The typ. */
    private IDecoderTyp decoderTyp;

    /** The protokoll. */
    private IProtokoll protokoll;

    /** The fahrstufe. */
    private Long fahrstufe;

    /** The adressen. */
    private List<IDecoderAdress> adressen = new ArrayList<>();

    /** The cvs. */
    private List<IDecoderCV> cvs = new ArrayList<>();

    /** The funktionen. */
    private List<IDecoderFunktion> funktionen = new ArrayList<>();

    /**
     * Instantiates a new decoder.
     */
    public Decoder() {
        super();
    }

    public Decoder(String decoderId) {
        super(decoderId);
    }

    /**
     * Instantiates a new decoder.
     *
     * @param id the id
     * @param typ the typ
     * @param protokoll the protokoll
     * @param decoderId the decoder id
     * @param beschreibung the beschreibung
     * @param fahrstufe the fahrstufe
     * @param deleted the deleted
     */
    public Decoder(Long id, IDecoderTyp typ, IProtokoll protokoll, String decoderId, String beschreibung, Long fahrstufe, Boolean deleted) {
        super(id, decoderId, beschreibung, deleted);

        setDecoderTyp(typ);
        setProtokoll(protokoll);
        setFahrstufe(fahrstufe);
    }

    @Override
    @JsonGetter(ApiNames.DECODER_ID)
    public String getName() {
        return super.getName();
    }

    @Override
    @JsonSetter(ApiNames.DECODER_ID)
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * Gets the typ.
     *
     * @return the typ
     */
    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTyp.class)
    @JoinColumn(name = DBNames.DECODER_TYP_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = "decoder_fk1"))
    @JsonUnwrapped
    @JsonGetter(ApiNames.DECODER_TYP)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(contentUsing=DecoderTypSerializer.class)
    public IDecoderTyp getDecoderTyp() {
        return decoderTyp;
    }

    /**
     * Sets the typ.
     *
     * @param typ the new typ
     */
    @Override
    @JsonSetter(ApiNames.DECODER_TYP)
    public void setDecoderTyp(IDecoderTyp decoderTyp) {
        this.decoderTyp = decoderTyp;
    }

    /**
     * Gets the protokoll.
     *
     * @return the protokoll
     */
    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Protokoll.class)
    @JoinColumn(name = DBNames.PROTOKOLL_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = "decoder_fk2"))
    @JsonGetter(ApiNames.PROTOKOLL)
    @JsonView(Views.DropDown.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = ApiNames.NAME)
    public IProtokoll getProtokoll() {
        return protokoll;
    }

    /**
     * Sets the protokoll.
     *
     * @param protokoll the new protokoll
     */
    @Override
    @JsonSetter(ApiNames.PROTOKOLL)
    public void setProtokoll(IProtokoll protokoll) {
        this.protokoll = protokoll;
    }

    /**
     * Gets the fahrstufe.
     *
     * @return the fahrstufe
     */
    @Override
    @Column(name = DBNames.FAHRSTUFE, nullable = true)
    @JsonGetter(ApiNames.FAHRSTUFE)
    @JsonView(Views.Public.class)
    public Long getFahrstufe() {
        return fahrstufe;
    }

    /**
     * Sets the fahrstufe.
     *
     * @param fahrstufe the new fahrstufe
     */
    @Override
    @JsonSetter(ApiNames.FAHRSTUFE)
    public void setFahrstufe(Long fahrstufe) {
        this.fahrstufe = fahrstufe;
    }

    /**
     * Gets the adressen.
     *
     * @return the adressen
     */
    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER, targetEntity = DecoderAdress.class, orphanRemoval = true)
    @JsonGetter(ApiNames.ADRESSEN)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentUsing=DecoderAdressSerializer.class)
    public List<IDecoderAdress> getAdressen() {
        return adressen;
    }

    /**
     * Sets the adressen.
     *
     * @param adressen the new adressen
     */
    @Override
    @JsonSetter(ApiNames.ADRESSEN)
    public void setAdressen(List<IDecoderAdress> adressen) {
        this.adressen = adressen;
    }

    @Override
    public void addAdress(IDecoderAdress adress) {
        adress.setDecoder(this);
        getAdressen().add(adress);
    }

    @Override
    public void removeAdress(IDecoderAdress adress) {
        getAdressen().remove(adress);
    }

    /**
     * Gets the cvs.
     *
     * @return the cvs
     */
    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER, targetEntity = DecoderCV.class, orphanRemoval = true)
    @JsonGetter(ApiNames.CVS)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentUsing=DecoderCVSerializer.class)
    public List<IDecoderCV> getCVs() {
        return cvs;
    }

    /**
     * Sets the cvs.
     *
     * @param cvs the new cvs
     */
    @Override
    @JsonSetter(ApiNames.CVS)
    public void setCVs(List<IDecoderCV> cvs) {
        this.cvs = cvs;
    }

    @Override
    public void addCV(IDecoderCV cv) {
        cv.setDecoder(this);
        getCVs().add(cv);
    }

    @Override
    public void removeCV(IDecoderCV cv) {
        getCVs().remove(cv);
    }

    /**
     * Gets the funktionen.
     *
     * @return the funktionen
     */
    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER, targetEntity = DecoderFunktion.class, orphanRemoval = true)
    @JsonGetter(ApiNames.FUNKTIONEN)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentUsing=DecoderFunktionSerializer.class)
    public List<IDecoderFunktion> getFunktionen() {
        return funktionen;
    }

    /**
     * Sets the funktionen.
     *
     * @param funktionen the new funktionen
     */
    @Override
    @JsonSetter(ApiNames.FUNKTIONEN)
    public void setFunktionen(List<IDecoderFunktion> funktionen) {
        this.funktionen = funktionen;
    }

    @Override
    public void addFunktion(IDecoderFunktion funktion) {
        funktion.setDecoder(this);
        getFunktionen().add(funktion);
    }

    @Override
    public void removeFunktion(IDecoderFunktion funktion) {
        getFunktionen().remove(funktion);
    }

    @Override
    protected void addChildLinks(UriInfo root) {
        addLinks(root, getAdressen(), false, false);
        addLinks(root, getCVs(), false, false);
        addLinks(root, getFunktionen(), false, false);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append(ApiNames.DECODER_TYP, getDecoderTyp())
                .append(ApiNames.PROTOKOLL, getProtokoll())
                .append(ApiNames.FAHRSTUFE, getFahrstufe())
                .append(ApiNames.ADRESSEN, getAdressen())
                .append(ApiNames.CVS, getCVs())
                .append(ApiNames.FUNKTIONEN, getFunktionen())
                .toString();
    }
}