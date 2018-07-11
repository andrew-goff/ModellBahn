package com.linepro.modellbahn.model.impl;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.linepro.modellbahn.model.IAchsfolg;
import com.linepro.modellbahn.model.IAufbau;
import com.linepro.modellbahn.model.IBahnverwaltung;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IEpoch;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.IKupplung;
import com.linepro.modellbahn.model.ILicht;
import com.linepro.modellbahn.model.IMassstab;
import com.linepro.modellbahn.model.IMotorTyp;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.IProduktTeil;
import com.linepro.modellbahn.model.ISondermodel;
import com.linepro.modellbahn.model.ISpurweite;
import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.IVorbild;
import com.linepro.modellbahn.model.util.AbstractItem;

@Entity
@Table(name = "PRODUKT", indexes = { @Index(columnList = "HERSTELLER_ID,BESTELLNR", unique = true) })
public class Produkt extends AbstractItem implements IProdukt {

    private static final long serialVersionUID = 8098838727023710484L;

    private IHersteller hersteller;

    private String bestellNr;

    private IUnterKategorie unterKategorie;

    private IMassstab massstab;

    private ISpurweite spurweite;

    private IBahnverwaltung bahnverwaltung;

    private IGattung gattung;

    private IEpoch epoch;

    private IAchsfolg achsfolge;

    private ISondermodel Sondermodel;

    private IAufbau aufbau;

    private ILicht licht;

    private IKupplung kupplung;

    private IVorbild vorbild;

    private ISteuerung steuerung;

    private IDecoderTyp decoderTyp;

    private IMotorTyp motorTyp;

    private String anmerkung;

    private String betreibsnummer;

    private Date bauzeit;

    private String anleitungen;

    private String explosionszeichnung;

    private Double lange;

    private Image abbildung;

    private List<IProduktTeil> teilen = new ArrayList<>();

    public Produkt() {
        super();
    }

    public Produkt(Long id, String name, String bezeichnung, Image abbildung, Boolean deleted) {
        super(id, deleted);
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Epoch.class)
    @JoinColumn(name = "EPOCH_ID", nullable = true, referencedColumnName = "ID")
    public IEpoch getEpoch() {
        return epoch;
    }

    @Override
    public void setEpoch(IEpoch epoch) {
        this.epoch = epoch;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Gattung.class)
    @JoinColumn(name = "GATTUNG_ID", nullable = true, referencedColumnName = "ID")
    public IGattung getGattung() {
        return gattung;
    }

    @Override
    public void setGattung(IGattung gattung) {
        this.gattung = gattung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Bahnverwaltung.class)
    @JoinColumn(name = "BAHNVERWALTUNG_ID", nullable = true, referencedColumnName = "ID")
    public IBahnverwaltung getBahnverwaltung() {
        return bahnverwaltung;
    }

    @Override
    public void setBahnverwaltung(IBahnverwaltung bahnverwaltung) {
        this.bahnverwaltung = bahnverwaltung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Achsfolg.class)
    @JoinColumn(name = "ACHSFOLG_ID", nullable = true, referencedColumnName = "ID")
    public IAchsfolg getAchsfolge() {
        return achsfolge;
    }

    @Override
    public void setAchsfolge(IAchsfolg achsfolge) {
        this.achsfolge = achsfolge;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Massstab.class)
    @JoinColumn(name = "MASSSTAB_ID", referencedColumnName = "ID")
    public IMassstab getMassstab() {
        return massstab;
    }

    @Override
    public void setMassstab(IMassstab massstab) {
        this.massstab = massstab;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Spurweite.class)
    @JoinColumn(name = "SPURWEITE_ID", referencedColumnName = "ID")
    public ISpurweite getSpurweite() {
        return spurweite;
    }

    @Override
    public void setSpurweite(ISpurweite spurweite) {
        this.spurweite = spurweite;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UnterKategorie.class)
    @JoinColumn(name = "UNTER_KATEGORIE_ID", referencedColumnName = "ID")
    public IUnterKategorie getUnterKategorie() {
        return unterKategorie;
    }

    @Override
    public void setUnterKategorie(IUnterKategorie unterKategorie) {
        this.unterKategorie = unterKategorie;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Sondermodel.class)
    @JoinColumn(name = "SONDERMODEL_ID", nullable = true, referencedColumnName = "ID")
    public ISondermodel getSondermodel() {
        return Sondermodel;
    }

    @Override
    public void setSondermodel(ISondermodel sondermodel) {
        Sondermodel = sondermodel;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Aufbau.class)
    @JoinColumn(name = "AUFBAU_ID", nullable = true, referencedColumnName = "ID")
    public IAufbau getAufbau() {
        return aufbau;
    }

    @Override
    public void setAufbau(IAufbau aufbau) {
        this.aufbau = aufbau;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Licht.class)
    @JoinColumn(name = "LICHT_ID", nullable = true, referencedColumnName = "ID")
    public ILicht getLicht() {
        return licht;
    }

    @Override
    public void setLicht(ILicht licht) {
        this.licht = licht;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Kupplung.class)
    @JoinColumn(name = "KUPPLUNG_ID", nullable = true, referencedColumnName = "ID")
    public IKupplung getKupplung() {
        return kupplung;
    }

    @Override
    public void setKupplung(IKupplung kupplung) {
        this.kupplung = kupplung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Vorbild.class)
    @JoinColumn(name = "VORBILD_ID", nullable = true, referencedColumnName = "ID")
    public IVorbild getVorbild() {
        return vorbild;
    }

    @Override
    public void setVorbild(IVorbild vorbild) {
        this.vorbild = vorbild;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Steuerung.class)
    @JoinColumn(name = "STEUERUNG_ID", nullable = true, referencedColumnName = "ID")
    public ISteuerung getSteuerung() {
        return steuerung;
    }

    @Override
    public void setSteuerung(ISteuerung steuerung) {
        this.steuerung = steuerung;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTyp.class)
    @JoinColumn(name = "DECODER_TYP_ID", nullable = true, referencedColumnName = "ID")
    public IDecoderTyp getDecoderTyp() {
        return decoderTyp;
    }

    @Override
    public void setDecoderTyp(IDecoderTyp decoderTyp) {
        this.decoderTyp = decoderTyp;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MotorTyp.class)
    @JoinColumn(name = "MOTOR_TYP_ID", nullable = true, referencedColumnName = "ID")
    public IMotorTyp getMotorTyp() {
        return motorTyp;
    }

    @Override
    public void setMotorTyp(IMotorTyp motorTyp) {
        this.motorTyp = motorTyp;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Hersteller.class)
    @JoinColumn(name = "HERSTELLER_ID", referencedColumnName = "ID")
    public IHersteller getHersteller() {
        return hersteller;
    }

    @Override
    public void setHersteller(IHersteller hersteller) {
        this.hersteller = hersteller;
    }

    @Override
    @Column(name = "BESTELLNR", length=30)
    public String getBestellNr() {
        return bestellNr;
    }

    @Override
    public void setBestellNr(String bestellNr) {
        this.bestellNr = bestellNr;
    }

    @Override
    @Column(name = "ANMERKUNG", nullable = true, length=100)
    public String getAnmerkung() {
        return anmerkung;
    }

    @Override
    public void setAnmerkung(String anmerkung) {
        this.anmerkung = anmerkung;
    }

    @Override
    @Column(name = "BETREIBSNUMMER", nullable = true, length=100)
    public String getBetreibsnummer() {
        return betreibsnummer;
    }

    @Override
    public void setBetreibsnummer(String betreibsnummer) {
        this.betreibsnummer = betreibsnummer;
    }

    @Override
    @Column(name = "BAUZEIT", nullable = true)
    @Temporal(TemporalType.DATE)
    public Date getBauzeit() {
        return bauzeit;
    }

    @Override
    public void setBauzeit(Date bauzeit) {
        this.bauzeit = bauzeit;
    }

    @Override
    @Column(name = "ANLEITUNGEN", nullable = true, length=512)
    public String getAnleitungen() {
        return anleitungen;
    }

    @Override
    public void setAnleitungen(String anleitungen) {
        this.anleitungen = anleitungen;
    }

    @Override
    @Column(name = "EXPLOSIONSZEICHNUNG", nullable = true, length=512)
    public String getExplosionszeichnung() {
        return explosionszeichnung;
    }

    @Override
    public void setExplosionszeichnung(String explosionszeichnung) {
        this.explosionszeichnung = explosionszeichnung;
    }

    @Override
    @Column(name = "LANGE", nullable = true, precision = 5, scale = 2)
    public Double getLange() {
        return lange;
    }

    @Override
    public void setLange(Double lange) {
        this.lange = lange;
    }

    @Override
    @Lob
    @Basic(optional = true, fetch = FetchType.LAZY)
    @Column(name = "ABBILDUNG", columnDefinition = "BLOB NOT NULL")
    public Image getAbbildung() {
        return abbildung;
    }

    @Override
    public void setAbbildung(Image abbildung) {
        this.abbildung = abbildung;
    }

    @Override
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "produkt", targetEntity = ProduktTeil.class)
    public List<IProduktTeil> getTeilen() {
        return teilen;
    }

    @Override
    public void setTeilen(List<IProduktTeil> teilen) {
        this.teilen.clear();
        this.teilen.addAll(teilen);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("epoch", getEpoch()).append("gattung", getGattung())
                .append("bahnverwaltung", getBahnverwaltung())
                .append("achsfolge", getAchsfolge()).append("massstab", getMassstab())
                .append("spurweite", getSpurweite()).append("unterKategorie", getUnterKategorie())
                .append("sondermodel", getSondermodel()).append("aufbau", getAufbau())
                .append("licht", getLicht()).append("kupplung", getKupplung())
                .append("vorbild", getVorbild()).append("steuerung", getSteuerung())
                .append("decoderTyp", getDecoderTyp())
                .append("motorTyp", getMotorTyp()).append("hersteller", getHersteller())
                .append("bestellNr", getBestellNr()).append("anmerkung", getAnmerkung())
                .append("betreibsnummer", getBetreibsnummer()).append("bauzeit", getBauzeit())
                .append("anleitungen", getAnleitungen())
                .append("explosionszeichnung", getExplosionszeichnung()).append("lange", getLange())
                .append("teilen", getTeilen()).toString();
    }
}