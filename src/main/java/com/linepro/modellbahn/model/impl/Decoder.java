package com.linepro.modellbahn.model.impl;

import java.net.URI;
import java.util.Set;
import java.util.TreeSet;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderAdress;
import com.linepro.modellbahn.model.IDecoderCV;
import com.linepro.modellbahn.model.IDecoderFunktion;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IProtokoll;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Decoder.
 * Represents a decoder (with its settings).
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.DECODER)
@Table(name = DBNames.DECODER, indexes = { @Index(columnList = DBNames.DECODER_ID, unique = true), @Index(columnList = DBNames.DECODER_TYP_ID),
        @Index(columnList = DBNames.PROTOKOLL_ID) }, uniqueConstraints = { @UniqueConstraint(columnNames = { DBNames.DECODER_ID }) })
public class Decoder extends AbstractItem implements IDecoder {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 44440227704021482L;

    @Pattern(regexp = "^[A-Z0-9]+$", message = "{com.linepro.modellbahn.validator.constraints.decoderId.invalid}")
    private String decoderId;

    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.bezeichnung.notnull}")
    private String bezeichnung;

    /** The typ. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.decoderTyp.notnull}")
    private IDecoderTyp decoderTyp;

    /** The protokoll. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.protokoll.notnull}")
    private IProtokoll protokoll;

    /** The fahrstufe. */
    //@Fahrstufe
    private Integer fahrstufe;

    /** The adressen. */
    private Set<IDecoderAdress> adressen = new TreeSet<>();

    /** The cvs. */
    private Set<IDecoderCV> cvs = new TreeSet<>();

    /** The funktionen. */
    private Set<IDecoderFunktion> funktionen = new TreeSet<>();

    /**
     * Instantiates a new decoder.
     */
    public Decoder() {
        super();
    }

    public Decoder(String decoderId) {
        this(null, null, null, decoderId, null, null, false);
    }

    /**
     * Instantiates a new decoder.
     *
     * @param id the id
     * @param typ the typ
     * @param protokoll the protokoll
     * @param decoderId the decoder id
     * @param bezeichnung the bezeichnung
     * @param fahrstufe the fahrstufe
     * @param deleted the deleted
     */
    public Decoder(Long id, IDecoderTyp typ, IProtokoll protokoll, String decoderId, String bezeichnung, Integer fahrstufe, Boolean deleted) {
        super(id, deleted);

        setDecoderId(decoderId);
        setBezeichnung(bezeichnung);
        setDecoderTyp(typ);
        setProtokoll(protokoll);
        setFahrstufe(fahrstufe);
    }

    @Override
    @BusinessKey
    @Column(name=DBNames.DECODER_ID, unique=true, length=50)
    public String getDecoderId() {
        return decoderId;
    }

    @Override
    public void setDecoderId(String decoderId) {
        this.decoderId = decoderId;
    }

    @Column(name = DBNames.BEZEICHNUNG, length = 100)
    public String getBezeichnung() {
      return bezeichnung;
    }

    @Override
    public void setBezeichnung(String bezeichnung) {
      this.bezeichnung = bezeichnung;
    }
    
    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTyp.class)
    @JoinColumn(name = DBNames.DECODER_TYP_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER + "_fk1"))
    public IDecoderTyp getDecoderTyp() {
        return decoderTyp;
    }

    @Override
    public void setDecoderTyp(IDecoderTyp decoderTyp) {
        this.decoderTyp = decoderTyp;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Protokoll.class)
    @JoinColumn(name = DBNames.PROTOKOLL_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER + "_fk2"))
    public IProtokoll getProtokoll() {
        return protokoll;
    }

    @Override
    public void setProtokoll(IProtokoll protokoll) {
        this.protokoll = protokoll;
    }

    @Override
    @Column(name = DBNames.FAHRSTUFE)
    public Integer getFahrstufe() {
        return fahrstufe;
    }

    @Override
    public void setFahrstufe(Integer fahrstufe) {
        this.fahrstufe = fahrstufe;
    }

    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER, targetEntity = DecoderAdress.class, orphanRemoval = true)
    public Set<IDecoderAdress> getAdressen() {
        return adressen;
    }

    @Override
    public void setAdressen(Set<IDecoderAdress> adressen) {
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

    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER, targetEntity = DecoderCV.class, orphanRemoval = true)
    public Set<IDecoderCV> getCVs() {
        return cvs;
    }

    @Override
    public void setCVs(Set<IDecoderCV> cvs) {
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

    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER, targetEntity = DecoderFunktion.class, orphanRemoval = true)
    public Set<IDecoderFunktion> getFunktionen() {
        return funktionen;
    }

    @Override
    public void setFunktionen(Set<IDecoderFunktion> funktionen) {
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
    @Transient
    public String getLinkId() {
        return getDecoderId();
    }

    @Override
    protected void addChildLinks(URI root, boolean update, boolean delete) {
        addLinks(root, getAdressen(), update, delete);
        addLinks(root, getCVs(), update, delete);
        addLinks(root, getFunktionen(), update, delete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
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