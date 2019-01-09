package com.linepro.modellbahn.model.impl;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IKupplung;
import com.linepro.modellbahn.model.ILicht;
import com.linepro.modellbahn.model.IMotorTyp;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.IWahrung;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.model.util.Status;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.persistence.util.PathConverter;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Artikel.
 * An article. 
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.ARTIKEL)
@Table(name = DBNames.ARTIKEL, indexes = { @Index(columnList = DBNames.PRODUKT_ID),
        @Index(columnList = DBNames.WAHRUNG_ID),
        @Index(columnList = DBNames.STEUERUNG_ID),
        @Index(columnList = DBNames.MOTOR_TYP_ID),
        @Index(columnList = DBNames.LICHT_ID),
        @Index(columnList = DBNames.KUPPLUNG_ID),
        @Index(columnList = DBNames.DECODER_ID) })
public class Artikel extends AbstractItem implements IArtikel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8652624782179487496L;

    /** The abbildung. */
    @Pattern(regexp = "^[A-Z0-9]+$", message = "{com.linepro.modellbahn.validator.constraints.decoderId.invalid}")
    private String artikelId;

    /** The produkt. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.produkt.notnull}")
    private IProdukt produkt;

    /** The Kaufdatum. */
    @Past(message = "{com.linepro.modellbahn.validator.constraints.kaufdatum.past}")
    private LocalDate kaufdatum;

    /** The wahrung. */
    private IWahrung wahrung;

    /** The steuerung. */
    private ISteuerung steuerung;

    /** The motor typ. */
    private IMotorTyp motorTyp;

    /** The licht. */
    private ILicht licht;

    /** The kupplung. */
    private IKupplung kupplung;

    /** The decoder. */
    private IDecoder decoder;

    /** The bezeichnung. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.bezeichnung.notnull}")
    private String bezeichnung;

    /** The preis. */
    @Positive(message = "{com.linepro.modellbahn.validator.constraints.preis.positive}")
    private BigDecimal preis;

    /** The stuck. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.stuck.notnull}")
    @Positive(message = "{com.linepro.modellbahn.validator.constraints.stuck.positive}")
    private Integer stuck;

    /** The anmerkung. */
    private String anmerkung;

    /** The beladung. */
    private String beladung;

    /** The abbildung. */
    private Path abbildung;

    /** The status. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.status.notnull}")
    private Status status;

    /**
     * Instantiates a new artikel.
     */
    public Artikel() {
        super();
    }

    /**
     * Instantiates a new artikel.
     *
     * @param id the id
     * @param produkt the produkt
     * @param kaufdatum the kaufdatum
     * @param wahrung the wahrung
     * @param preis the preis
     * @param stuck the stuck
     * @param steuerung the steuerung
     * @param motorTyp the motor typ
     * @param licht the licht
     * @param kupplung the kupplung
     * @param decoder the decoder
     * @param artikelId the artikel nr
     * @param bezeichnung the bezeichnung
     * @param anmerkung the anmerkung
     * @param beladung the beladung
     * @param status the status
     * @param deleted the deleted
     */
    public Artikel(Long id, IProdukt produkt, LocalDate kaufdatum, IWahrung wahrung, BigDecimal preis, Integer stuck,
            ISteuerung steuerung, IMotorTyp motorTyp, ILicht licht, IKupplung kupplung, IDecoder decoder,
            String artikelId, String bezeichnung, String anmerkung,
            String beladung, Status status, Boolean deleted) {
        super(id, deleted);

        setArtikelId(artikelId);
        setBezeichnung(bezeichnung);
        setProdukt(produkt);
        setKaufdatum(kaufdatum);
        setWahrung(wahrung);
        setPreis(preis);
        setStuck(stuck);
        setSteuerung(steuerung);
        setMotorTyp(motorTyp);
        setLicht(licht);
        setKupplung(kupplung);
        setDecoder(decoder);
        setAnmerkung(anmerkung);
        setBeladung(beladung);
        setStatus(status);
    }

    @BusinessKey
    @Column(name = DBNames.ARTIKEL_ID, unique = true, length = 50)
    public String getArtikelId() {
      return artikelId;
    }

    @Override
    public void setArtikelId(String artikelId) {
      this.artikelId = (artikelId != null ? artikelId.toUpperCase() : artikelId);
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Produkt.class)
    @JoinColumn(name = DBNames.PRODUKT_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ARTIKEL + "_fk1"))
    public IProdukt getProdukt() {
        return produkt;
    }

    @Override
    public void setProdukt(IProdukt produkt) {
        this.produkt = produkt;
    }

    @Override
    @Column(name = DBNames.KAUFDATUM)
    public LocalDate getKaufdatum() {
        return kaufdatum;
    }

    @Override
    public void setKaufdatum(LocalDate kaufdatum) {
        this.kaufdatum = kaufdatum;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Wahrung.class)
    @JoinColumn(name = DBNames.WAHRUNG_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ARTIKEL + "_fk2"))
    public IWahrung getWahrung() {
        return wahrung;
    }

    @Override
    public void setWahrung(IWahrung wahrung) {
        this.wahrung = wahrung;
    }

    @Override
    @Column(name = DBNames.PREIS, precision = 6, scale = 2)
    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public void setPreis(BigDecimal preis) {
        this.preis = preis;
    }

    @Override
    @Column(name = DBNames.STUCK, nullable = false)
    public Integer getStuck() {
        return stuck;
    }

    @Override
    public void setStuck(Integer stuck) {
        this.stuck = stuck;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Steuerung.class)
    @JoinColumn(name = DBNames.STEUERUNG_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ARTIKEL + "_fk3"))
    public ISteuerung getSteuerung() {
        return steuerung;
    }

    @Override
    public void setSteuerung(ISteuerung steuerung) {
        this.steuerung = steuerung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MotorTyp.class)
    @JoinColumn(name = DBNames.MOTOR_TYP_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ARTIKEL + "_fk4"))
    public IMotorTyp getMotorTyp() {
        return motorTyp;
    }

    @Override
    public void setMotorTyp(IMotorTyp motorTyp) {
        this.motorTyp = motorTyp;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Licht.class)
    @JoinColumn(name = DBNames.LICHT_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ARTIKEL + "_fk5"))
    public ILicht getLicht() {
        return licht;
    }

    @Override
    public void setLicht(ILicht licht) {
        this.licht = licht;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Kupplung.class)
    @JoinColumn(name = DBNames.KUPPLUNG_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ARTIKEL + "_fk6"))
    public IKupplung getKupplung() {
        return kupplung;
    }

    @Override
    public void setKupplung(IKupplung kupplung) {
        this.kupplung = kupplung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Decoder.class)
    @JoinColumn(name = DBNames.DECODER_ID, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.ARTIKEL + "_fk7"))
    public IDecoder getDecoder() {
        return decoder;
    }

    @Override
    public void setDecoder(IDecoder decoder) {
        this.decoder = decoder;
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
    @Column(name = DBNames.ANMERKUNG, length = 100)
    public String getAnmerkung() {
        return anmerkung;
    }

    @Override
    public void setAnmerkung(String anmerkung) {
        this.anmerkung = anmerkung;
    }

    @Override
    @Column(name = DBNames.BELADUNG, length = 100)
    public String getBeladung() {
        return beladung;
    }

    @Override
    public void setBeladung(String beladung) {
        this.beladung = beladung;
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
    @Column(name = DBNames.STATUS, nullable = false)
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    @Transient
    public String getLinkId() {
        return getArtikelId();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.PRODUKT, getProdukt())
                .append(ApiNames.KAUFDATUM, getKaufdatum())
                .append(ApiNames.WAHRUNG, getWahrung())
                .append(ApiNames.PREIS, getPreis())
                .append(ApiNames.STUCK, getStuck())
                .append(ApiNames.STEUERUNG, getSteuerung())
                .append(ApiNames.MOTOR_TYP, getMotorTyp())
                .append(ApiNames.LICHT, getLicht())
                .append(ApiNames.KUPPLUNG, getKupplung())
                .append(ApiNames.DECODER, getDecoder())
                .append(ApiNames.ANMERKUNG, getAnmerkung())
                .append(ApiNames.BELADUNG, getBeladung())
                .append(ApiNames.ABBILDUNG, getAbbildung())
                .append(ApiNames.STATUS, getStatus())
                .toString();
    }
}
