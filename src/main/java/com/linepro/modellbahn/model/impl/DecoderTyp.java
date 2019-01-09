package com.linepro.modellbahn.model.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypAdress;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.IProtokoll;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.model.util.Konfiguration;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * DecoderTyp. Represents a Decoder type (manufacturer : part numer)
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.DECODER_TYP)
@Table(name = DBNames.DECODER_TYP, indexes = { @Index(columnList = DBNames.HERSTELLER_ID),
        @Index(columnList = DBNames.PROTOKOLL_ID) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { DBNames.HERSTELLER_ID, DBNames.BESTELL_NR }) })
public class DecoderTyp extends AbstractItem implements IDecoderTyp {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8503812316290492490L;

    /** The hersteller. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.hersteller.notnull}")
    private IHersteller hersteller;

    @Pattern(regexp = "^[A-Z0-9\\-.]+$", message = "{com.linepro.modellbahn.validator.constraints.bestellNr.invalid}")
    private String bestellNr;

    @NotEmpty(message = "{com.linepro.modellbahn.validator.constraints.bezeichnung.notempty}")
    private String bezeichnung;

    /** The i max. */
    //@IMax
    private BigDecimal iMax;

    /** The protokoll. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.protokoll.notnull}")
    private IProtokoll protokoll;

    /** The fahrstufe. */
    //@Fahrstufe
    private Integer fahrstufe;

    /** The sound. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.sound.notnull}")
    private Boolean sound;

    /** The konfiguration. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.konfiguration.notnull}")
    private Konfiguration konfiguration;

    /** The cv. */
    private Set<IDecoderTypAdress> adressen = new TreeSet<>();

    /** The cv. */
    private Set<IDecoderTypCV> CVs = new TreeSet<>();

    /** The funktion. */
    private Set<IDecoderTypFunktion> funktionen = new TreeSet<>();

    /**
     * Instantiates a new decoder typ.
     */
    public DecoderTyp() {
        super();
    }

    public DecoderTyp(Long id, IHersteller hersteller, IProtokoll protokoll, String bestellNr, String bezeichnung,
            Boolean sound, Konfiguration konfiguration, Boolean deleted) {
        super(id, deleted);

        setHersteller(hersteller);
        setBestellNr(bestellNr);
        setBezeichnung(bezeichnung);
        setProtokoll(protokoll);
        setSound(sound);
        setKonfiguration(konfiguration);
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Hersteller.class)
    @JoinColumn(name = DBNames.HERSTELLER_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER_TYP + "_fk2"))
    public IHersteller getHersteller() {
        return hersteller;
    }

    @Override
    public void setHersteller(IHersteller hersteller) {
        this.hersteller = hersteller;
    }

    @Override
    @BusinessKey
    public String getBestellNr() {
        return bestellNr;
    }

    @Override
    public void setBestellNr(String bestellNr) {
        this.bestellNr = bestellNr;
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
    @Column(name = DBNames.I_MAX, precision = 6, scale = 2)
    public BigDecimal getiMax() {
        return iMax;
    }

    @Override
    public void setiMax(BigDecimal iMax) {
        this.iMax = iMax;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Protokoll.class)
    @JoinColumn(name = DBNames.PROTOKOLL_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER_TYP + "_fk3"))
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
    @Column(name = DBNames.SOUND, nullable = false)
    public Boolean getSound() {
        return sound;
    }

    @Override
    public void setSound(Boolean sound) {
        this.sound = sound;
    }

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = DBNames.KONFIGURATION, nullable = false, length = 15)
    public Konfiguration getKonfiguration() {
        return konfiguration;
    }

    @Override
    public void setKonfiguration(Konfiguration konfiguration) {
        this.konfiguration = konfiguration;
    }

    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER_TYP, targetEntity = DecoderTypAdress.class, orphanRemoval = true)
    public Set<IDecoderTypAdress> getAdressen() {
        return adressen;
    }

    @Transient
    public Set<IDecoderTypAdress> getSortedAdressen() {
        return new TreeSet<>(getAdressen());
    }

    @Override
    public void setAdressen(Set<IDecoderTypAdress> adressen) {
        this.adressen = adressen;
    }

    @Override
    public void addAdress(IDecoderTypAdress adress) {
        adress.setDecoderTyp(this);
        getAdressen().add(adress);
    }

    @Override
    public void removeAdress(IDecoderTypAdress adress) {
        getAdressen().remove(adress);
    }


    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER_TYP, targetEntity = DecoderTypCV.class, orphanRemoval = true)
    public Set<IDecoderTypCV> getCVs() {
        return CVs;
    }

    @Transient
    public Set<IDecoderTypCV> getSortedCVs() {
        return new TreeSet<>(getCVs());
    }

    @Override
    public void setCVs(Set<IDecoderTypCV> CVs) {
        this.CVs = CVs;
    }

    @Override
    public void addCV(IDecoderTypCV cv) {
        cv.setDecoderTyp(this);
        CVs.add(cv);
    }

    @Override
    public void removeCV(IDecoderTypCV cv) {
        CVs.remove(cv);
    }

    @Override
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.DECODER_TYP, targetEntity = DecoderTypFunktion.class, orphanRemoval = true)
    public Set<IDecoderTypFunktion> getFunktionen() {
        return funktionen;
    }

    @Transient
    public Set<IDecoderTypFunktion> getSortedFunktionen() {
        return new TreeSet<>(getFunktionen());
    }

    @Override
    public void setFunktionen(Set<IDecoderTypFunktion> funktionen) {
        this.funktionen = funktionen;
    }

    @Override
    public void addFunktion(IDecoderTypFunktion funktion) {
        funktion.setDecoderTyp(this);
        getFunktionen().add(funktion);
    }

    @Override
    public void removeFunktion(IDecoderTypFunktion funktion) {
        getFunktionen().remove(funktion);
    }

    @Override
    @Transient
    public String getLinkId() {
        return String.format(ApiPaths.DECODER_TYP_LINK, getHersteller().getLinkId(), getBestellNr());
    }

    @Override
    protected void addChildLinks(URI root, boolean update, boolean delete) {
        addLinks(root, getAdressen(), update, delete);
        addLinks(root, getCVs(), update, delete);
        addLinks(root, getFunktionen(), update, delete);
    }
    
    @Override
    public int compareTo(IItem other) {
        if (other instanceof DecoderTyp) {
            return new CompareToBuilder()
                    .append(getHersteller(), ((DecoderTyp) other).getHersteller())
                    .append(getBestellNr(), ((DecoderTyp) other).getBestellNr())
                    .toComparison();
        }
        
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getHersteller())
                .append(getBestellNr())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (!(obj instanceof DecoderTyp)) {
            return false;
        }

        DecoderTyp other = (DecoderTyp) obj;
        
        return new EqualsBuilder()
                .append(getHersteller(), other.getHersteller())
                .append(getBestellNr(), other.getBestellNr())
                .isEquals();
    }

    @Override
    public String toString() {
      //noinspection Duplicates
      return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.HERSTELLER, getHersteller())
                .append(ApiNames.BESTELL_NR, getBestellNr())
                .append(ApiNames.BEZEICHNUNG, getBezeichnung())
                .append(ApiNames.PROTOKOLL, getProtokoll())
                .append(ApiNames.I_MAX, getiMax())
                .append(ApiNames.FAHRSTUFE, getFahrstufe())
                .append(ApiNames.GERAUSCH, getSound())
                .append(ApiNames.KONFIGURATION, getKonfiguration())
                .append(ApiNames.ADRESSEN, getAdressen())
                .append(ApiNames.CVS, getCVs())
                .append(ApiNames.FUNKTIONEN, getFunktionen())
                .toString();
    }
}