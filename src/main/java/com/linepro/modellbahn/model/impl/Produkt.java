package com.linepro.modellbahn.model.impl;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.linepro.modellbahn.model.IAchsfolg;
import com.linepro.modellbahn.model.IAufbau;
import com.linepro.modellbahn.model.IBahnverwaltung;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IEpoch;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.IKupplung;
import com.linepro.modellbahn.model.ILicht;
import com.linepro.modellbahn.model.IMassstab;
import com.linepro.modellbahn.model.IMotorTyp;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.IProduktTeil;
import com.linepro.modellbahn.model.ISonderModell;
import com.linepro.modellbahn.model.ISpurweite;
import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.IVorbild;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.persistence.util.PathConverter;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Produkt. A product.
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.PRODUKT)
@Table(name = DBNames.PRODUKT, indexes = {
        @Index(columnList = DBNames.HERSTELLER_ID),
        @Index(columnList = DBNames.EPOCH_ID),
        @Index(columnList = DBNames.GATTUNG_ID),
        @Index(columnList = DBNames.BAHNVERWALTUNG_ID),
        @Index(columnList = DBNames.ACHSFOLG_ID),
        @Index(columnList = DBNames.MASSSTAB_ID),
        @Index(columnList = DBNames.SPURWEITE_ID),
        @Index(columnList = DBNames.UNTER_KATEGORIE_ID),
        @Index(columnList = DBNames.SONDERMODELL_ID),
        @Index(columnList = DBNames.AUFBAU_ID),
        @Index(columnList = DBNames.LICHT_ID),
        @Index(columnList = DBNames.KUPPLUNG_ID),
        @Index(columnList = DBNames.VORBILD_ID),
        @Index(columnList = DBNames.STEUERUNG_ID),
        @Index(columnList = DBNames.DECODER_TYP_ID),
        @Index(columnList = DBNames.MOTOR_TYP_ID) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { DBNames.HERSTELLER_ID, DBNames.BESTELL_NR }) })
public class Produkt extends AbstractItem implements IProdukt {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8098838727023710484L;

    /** The hersteller. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.hersteller.notnull}")
    private IHersteller hersteller;

    @Pattern(regexp = "^[A-Z0-9\\-.]+$", message = "{com.linepro.modellbahn.validator.constraints.bestellNr.invalid}")
    private String bestellNr;

    @NotEmpty(message = "{com.linepro.modellbahn.validator.constraints.bezeichnung.notempty}")
    private String bezeichnung;

    /** The unter kategorie. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.unterKategorie.notnull}")
    private IUnterKategorie unterKategorie;

    /** The massstab. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.massstab.notnull}")
    private IMassstab massstab;

    /** The spurweite. */
    private ISpurweite spurweite;

    /** The bahnverwaltung. */
    private IBahnverwaltung bahnverwaltung;

    /** The gattung. */
    private IGattung gattung;

    /** The epoch. */
    private IEpoch epoch;

    /** The achsfolg. */
    private IAchsfolg achsfolg;

    /** The Sondermodel. */
    private ISonderModell sondermodell;

    /** The aufbau. */
    private IAufbau aufbau;

    /** The licht. */
    private ILicht licht;

    /** The kupplung. */
    private IKupplung kupplung;

    /** The vorbild. */
    private IVorbild vorbild;

    /** The steuerung. */
    private ISteuerung steuerung;

    /** The decoder typ. */
    private IDecoderTyp decoderTyp;

    /** The motor typ. */
    private IMotorTyp motorTyp;

    /** The anmerkung. */
    private String anmerkung;

    /** The betreibsnummer. */
    private String betreibsnummer;

    /** The bauzeit. */
    @Past(message = "{com.linepro.modellbahn.validator.constraints.bauzeit.past}")
    private LocalDate bauzeit;

    /** The anleitungen. */
    private Path anleitungen;

    /** The explosionszeichnung. */
    private Path explosionszeichnung;

    /** The lange. */
    @Positive(message = "{com.linepro.modellbahn.validator.constraints.lange.positive}")
    private BigDecimal lange;

    /** The abbildung. */
    private Path abbildung;

    /** The teilen. */
    private Set<IProduktTeil> teilen = new TreeSet<>();

    public Produkt() {
        super();
    }

    public Produkt(Long id, IHersteller hersteller, String bestellNr, String bezeichnung, IUnterKategorie unterKategorie,
            IMassstab massstab, ISpurweite spurweite, IEpoch epoch, IBahnverwaltung bahnverwaltung, IGattung gattung,
            String betreibsnummer, LocalDate bauzeit, IVorbild vorbild, IAchsfolg achsfolg, String anmerkung,
            ISonderModell sondermodel, IAufbau aufbau, ILicht licht, IKupplung kupplung, ISteuerung steuerung,
            IDecoderTyp decoderTyp, IMotorTyp motorTyp, BigDecimal lange, Boolean deleted) {
        super(id, deleted);
        setHersteller(hersteller);
        setBestellNr(bestellNr);
        setBezeichnung(bezeichnung);
        setUnterKategorie(unterKategorie);
        setMassstab(massstab);
        setSpurweite(spurweite);
        setEpoch(epoch);
        setBahnverwaltung(bahnverwaltung);
        setGattung(gattung);
        setBetreibsnummer(betreibsnummer);
        setBauzeit(bauzeit);
        setVorbild(vorbild);
        setAchsfolg(achsfolg);
        setAnmerkung(anmerkung);
        setSondermodell(sondermodel);
        setAufbau(aufbau);
        setLicht(licht);
        setKupplung(kupplung);
        setSteuerung(steuerung);
        setDecoderTyp(decoderTyp);
        setMotorTyp(motorTyp);
        setLange(lange);
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Hersteller.class)
    @JoinColumn(name = DBNames.HERSTELLER_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk16"))
    @BusinessKey
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
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = UnterKategorie.class)
    @JoinColumn(name = DBNames.UNTER_KATEGORIE_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk7"))
    public IUnterKategorie getUnterKategorie() {
        return unterKategorie;
    }

    @Override
    public void setUnterKategorie(IUnterKategorie unterKategorie) {
        this.unterKategorie = unterKategorie;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Massstab.class)
    @JoinColumn(name = DBNames.MASSSTAB_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk5"))
    public IMassstab getMassstab() {
        return massstab;
    }

    @Override
    public void setMassstab(IMassstab massstab) {
        this.massstab = massstab;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Spurweite.class)
    @JoinColumn(name = DBNames.SPURWEITE_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk6"))
    public ISpurweite getSpurweite() {
        return spurweite;
    }

    @Override
    public void setSpurweite(ISpurweite spurweite) {
        this.spurweite = spurweite;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Epoch.class)
    @JoinColumn(name = DBNames.EPOCH_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk1"))
    public IEpoch getEpoch() {
        return epoch;
    }

    @Override
    public void setEpoch(IEpoch epoch) {
        this.epoch = epoch;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Bahnverwaltung.class)
    @JoinColumn(name = DBNames.BAHNVERWALTUNG_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk3"))
    public IBahnverwaltung getBahnverwaltung() {
        return bahnverwaltung;
    }

    @Override
    public void setBahnverwaltung(IBahnverwaltung bahnverwaltung) {
        this.bahnverwaltung = bahnverwaltung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Gattung.class)
    @JoinColumn(name = DBNames.GATTUNG_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk2"))
    public IGattung getGattung() {
        return gattung;
    }

    @Override
    public void setGattung(IGattung gattung) {
        this.gattung = gattung;
    }

    @Override
    @Column(name = DBNames.BETREIBSNUMMER, length = 100)
    public String getBetreibsnummer() {
        return betreibsnummer;
    }

    @Override
    public void setBetreibsnummer(String betreibsnummer) {
        this.betreibsnummer = betreibsnummer;
    }

    @Override
    @Column(name = DBNames.BAUZEIT)
    public LocalDate getBauzeit() {
        return bauzeit;
    }

    @Override
    public void setBauzeit(LocalDate bauzeit) {
        this.bauzeit = bauzeit;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Vorbild.class)
    @JoinColumn(name = DBNames.VORBILD_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk12"))
    public IVorbild getVorbild() {
        return vorbild;
    }

    @Override
    public void setVorbild(IVorbild vorbild) {
        this.vorbild = vorbild;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Achsfolg.class)
    @JoinColumn(name = DBNames.ACHSFOLG_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk4"))
    public IAchsfolg getAchsfolg() {
        return achsfolg;
    }

    @Override
    public void setAchsfolg(IAchsfolg achsfolg) {
        this.achsfolg = achsfolg;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SonderModell.class)
    @JoinColumn(name = DBNames.SONDERMODELL_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk8"))
    public ISonderModell getSondermodell() {
        return sondermodell;
    }

    @Override
    public void setSondermodell(ISonderModell sondermodell) {
        this.sondermodell = sondermodell;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Aufbau.class)
    @JoinColumn(name = DBNames.AUFBAU_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk9"))
    public IAufbau getAufbau() {
        return aufbau;
    }

    @Override
    public void setAufbau(IAufbau aufbau) {
        this.aufbau = aufbau;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Licht.class)
    @JoinColumn(name = DBNames.LICHT_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk10"))
    public ILicht getLicht() {
        return licht;
    }

    @Override
    public void setLicht(ILicht licht) {
        this.licht = licht;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Kupplung.class)
    @JoinColumn(name = DBNames.KUPPLUNG_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk11"))
    public IKupplung getKupplung() {
        return kupplung;
    }

    @Override
    public void setKupplung(IKupplung kupplung) {
        this.kupplung = kupplung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Steuerung.class)
    @JoinColumn(name = DBNames.STEUERUNG_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk13"))
    public ISteuerung getSteuerung() {
        return steuerung;
    }

    @Override
    public void setSteuerung(ISteuerung steuerung) {
        this.steuerung = steuerung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTyp.class)
    @JoinColumn(name = DBNames.DECODER_TYP_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk14"))
    public IDecoderTyp getDecoderTyp() {
        return decoderTyp;
    }

    @Override
    public void setDecoderTyp(IDecoderTyp decoderTyp) {
        this.decoderTyp = decoderTyp;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MotorTyp.class)
    @JoinColumn(name = DBNames.MOTOR_TYP_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.PRODUKT + "_fk15"))
    public IMotorTyp getMotorTyp() {
        return motorTyp;
    }

    @Override
    public void setMotorTyp(IMotorTyp motorTyp) {
        this.motorTyp = motorTyp;
    }

    @Override
    @Column(name = DBNames.LANGE, precision = 6, scale = 2)
    public BigDecimal getLange() {
        return lange;
    }

    @Override
    public void setLange(BigDecimal lange) {
        this.lange = lange;
    }

    @Override
    @Column(name = DBNames.ANMERKUNG, length = 100)
    public String getAnmerkung() {
        return anmerkung;
    }

    @Override
    public void setAnmerkung(String anmerkung) {
        this.anmerkung = anmerkung;
    }

    @Override
    @Column(name = DBNames.ANLEITUNGEN, length = 512)
    @Convert(converter = PathConverter.class)
    public Path getAnleitungen() {
        return anleitungen;
    }

    @Override
    public void setAnleitungen(Path anleitungen) {
        this.anleitungen = anleitungen;
    }

    @Override
    @Column(name = DBNames.EXPLOSIONSZEICHNUNG, length = 512)
    @Convert(converter = PathConverter.class)
    public Path getExplosionszeichnung() {
        return explosionszeichnung;
    }

    @Override
    public void setExplosionszeichnung(Path explosionszeichnung) {
        this.explosionszeichnung = explosionszeichnung;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = DBNames.PRODUKT, targetEntity = ProduktTeil.class, orphanRemoval = true)
    public Set<IProduktTeil> getTeilen() {
        return teilen;
    }

    @Override
    public void setTeilen(Set<IProduktTeil> teilen) {
        this.teilen = teilen;
    }

    @Override
    public void addTeil(IProduktTeil funktion) {
        funktion.setProdukt(this);
        getTeilen().add(funktion);
    }

    @Override
    public void removeTeil(IProduktTeil funktion) {
        getTeilen().remove(funktion);
    }

    @Override
    @Transient
    public String getLinkId() {
        return String.format(ApiPaths.PRODUKT_LINK, getHersteller().getLinkId(), getBestellNr());
    }

    @Override
    protected void addChildLinks(URI root, boolean update, boolean delete) {
        addLinks(root, getTeilen(), update, delete);
    }

    @Override
    public int compareTo(IItem other) {
        if (other instanceof Produkt) {
            return new CompareToBuilder()
                    .append(getHersteller(), ((Produkt) other).getHersteller())
                    .append(getBestellNr(), ((Produkt) other).getBestellNr())
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

        if (!(obj instanceof Produkt)) {
            return false;
        }

        Produkt other = (Produkt) obj;

        return new EqualsBuilder()
                .append(getHersteller(), other.getHersteller())
                .append(getBestellNr(), other.getBestellNr())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.HERSTELLER, getHersteller())
                .append(ApiNames.BESTELL_NR, getBestellNr())
                .append(ApiNames.UNTER_KATEGORIE, getUnterKategorie())
                .append(ApiNames.MASSSTAB, getMassstab())
                .append(ApiNames.SPURWEITE, getSpurweite())
                .append(ApiNames.BETREIBSNUMMER, getBetreibsnummer())
                .append(ApiNames.EPOCH, getEpoch())
                .append(ApiNames.BAHNVERWALTUNG, getBahnverwaltung())
                .append(ApiNames.GATTUNG, getGattung())
                .append(ApiNames.BAUZEIT, getBauzeit())
                .append(ApiNames.ACHSFOLG, getAchsfolg())
                .append(ApiNames.VORBILD, getVorbild())
                .append(ApiNames.ANMERKUNG, getAnmerkung())
                .append(ApiNames.SONDERMODELL, getSondermodell())
                .append(ApiNames.AUFBAU, getAufbau())
                .append(ApiNames.LICHT, getLicht())
                .append(ApiNames.KUPPLUNG, getKupplung())
                .append(ApiNames.STEUERUNG, getSteuerung())
                .append(ApiNames.DECODER_TYP, getDecoderTyp())
                .append(ApiNames.MOTOR_TYP, getMotorTyp())
                .append(ApiNames.LANGE, getLange())
                .append(ApiNames.ANLEITUNGEN, getAnleitungen())
                .append(ApiNames.EXPLOSIONSZEICHNUNG, getExplosionszeichnung())
                .append(ApiNames.ABBILDUNG, getAbbildung())
                .append(ApiNames.TEILEN, getTeilen())
                .toString();
    }
}