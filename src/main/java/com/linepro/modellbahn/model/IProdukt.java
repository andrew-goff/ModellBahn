package com.linepro.modellbahn.model;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.Achsfolg;
import com.linepro.modellbahn.model.impl.Aufbau;
import com.linepro.modellbahn.model.impl.Bahnverwaltung;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.impl.Epoch;
import com.linepro.modellbahn.model.impl.Gattung;
import com.linepro.modellbahn.model.impl.Hersteller;
import com.linepro.modellbahn.model.impl.Kupplung;
import com.linepro.modellbahn.model.impl.Licht;
import com.linepro.modellbahn.model.impl.Massstab;
import com.linepro.modellbahn.model.impl.MotorTyp;
import com.linepro.modellbahn.model.impl.SonderModell;
import com.linepro.modellbahn.model.impl.Spurweite;
import com.linepro.modellbahn.model.impl.Steuerung;
import com.linepro.modellbahn.model.impl.UnterKategorie;
import com.linepro.modellbahn.model.impl.Vorbild;

import com.linepro.modellbahn.model.refs.IAchsfolgRef;
import com.linepro.modellbahn.model.refs.IAufbauRef;
import com.linepro.modellbahn.model.refs.IBahnverwaltungRef;
import com.linepro.modellbahn.model.refs.IDecoderTypRef;
import com.linepro.modellbahn.model.refs.IEpochRef;
import com.linepro.modellbahn.model.refs.IGattungRef;
import com.linepro.modellbahn.model.refs.IKupplungRef;
import com.linepro.modellbahn.model.refs.ILichtRef;
import com.linepro.modellbahn.model.refs.IMassstabRef;
import com.linepro.modellbahn.model.refs.IMotorTypRef;
import com.linepro.modellbahn.model.refs.IProduktRef;
import com.linepro.modellbahn.model.refs.IProduktTeilRef;
import com.linepro.modellbahn.model.refs.ISonderModellRef;
import com.linepro.modellbahn.model.refs.ISpurweiteRef;
import com.linepro.modellbahn.model.refs.ISteuerungRef;
import com.linepro.modellbahn.model.refs.IVorbildRef;
import com.linepro.modellbahn.rest.json.Formats;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.serialization.LocalDateDeserializer;
import com.linepro.modellbahn.rest.json.serialization.LocalDateSerializer;
import com.linepro.modellbahn.rest.json.serialization.PathSerializer;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * IProdukt.
 * 
 * @author $Author$
 * @version $Id$
 */
@JsonRootName(value = ApiNames.PRODUKT)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.HERSTELLER, ApiNames.BESTELL_NR, ApiNames.BEZEICHNUNG, ApiNames.UNTER_KATEGORIE, ApiNames.MASSSTAB,
        ApiNames.SPURWEITE, ApiNames.EPOCH, ApiNames.BAHNVERWALTUNG, ApiNames.GATTUNG, ApiNames.BETREIBSNUMMER,
        ApiNames.BAUZEIT, ApiNames.VORBILD, ApiNames.ACHSFOLG, ApiNames.ANMERKUNG, ApiNames.SONDERMODELL,
        ApiNames.AUFBAU, ApiNames.LICHT, ApiNames.KUPPLUNG, ApiNames.STEUERUNG, ApiNames.DECODER_TYP,
        ApiNames.MOTOR_TYP, ApiNames.LANGE, ApiNames.ANLEITUNGEN, ApiNames.EXPLOSIONSZEICHNUNG, ApiNames.ABBILDUNG,
        ApiNames.TEILEN, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.PRODUKT, description = "Product - template for article.")
public interface IProdukt extends IItem, IProduktRef {

    @JsonSetter(ApiNames.HERSTELLER)
    @JsonDeserialize(as = Hersteller.class)
    void setHersteller(IHersteller hersteller);

    @JsonSetter(ApiNames.BESTELL_NR)
    void setBestellNr(String bestellNr);

    void setBezeichnung(String bezeichnung);

    @JsonGetter(ApiNames.UNTER_KATEGORIE)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as = IUnterKategorie.class)
    IUnterKategorie getUnterKategorie();

    @JsonSetter(ApiNames.UNTER_KATEGORIE)
    @JsonDeserialize(as = UnterKategorie.class)
    void setUnterKategorie(IUnterKategorie unterKategorie);

    @JsonGetter(ApiNames.MASSSTAB)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IMassstabRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IMassstabRef", value = "Scale")
    IMassstab getMassstab();

    @JsonSetter(ApiNames.MASSSTAB)
    @JsonDeserialize(as = Massstab.class)
    void setMassstab(IMassstab massstab);

    @JsonGetter(ApiNames.SPURWEITE)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= ISpurweiteRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.ISpurweiteRef", value = "Track gauge")
    ISpurweite getSpurweite();

    @JsonSetter(ApiNames.SPURWEITE)
    @JsonDeserialize(as = Spurweite.class)
    void setSpurweite(ISpurweite spurweite);

    @JsonGetter(ApiNames.EPOCH)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IEpochRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IEpochRef", value = "ERA")
    IEpoch getEpoch();

    @JsonSetter(ApiNames.EPOCH)
    @JsonDeserialize(as = Epoch.class)
    void setEpoch(IEpoch epoch);

    @JsonGetter(ApiNames.BAHNVERWALTUNG)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IBahnverwaltungRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IBahnverwaltungRef", value = "Railway company")
    IBahnverwaltung getBahnverwaltung();

    @JsonSetter(ApiNames.BAHNVERWALTUNG)
    @JsonDeserialize(as = Bahnverwaltung.class)
    void setBahnverwaltung(IBahnverwaltung bahnverwaltung);

    @JsonGetter(ApiNames.GATTUNG)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IGattungRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IGattungRef", value = "Vehicle class")
    IGattung getGattung();

    @JsonSetter(ApiNames.GATTUNG)
    @JsonDeserialize(as = Gattung.class)
    void setGattung(IGattung gattung);

    @JsonGetter(ApiNames.BETREIBSNUMMER)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Service number", example = "89 006")
    String getBetreibsnummer();

    @JsonSetter(ApiNames.BETREIBSNUMMER)
    void setBetreibsnummer(String betreibsnummer);

    @JsonGetter(ApiNames.BAUZEIT)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonView(Views.Public.class)
    @JsonFormat(shape = Shape.STRING, pattern = Formats.ISO8601_DATE)
    @ApiModelProperty(dataType = "java.time.LocalDate", value = "Construction date", example = "1934-01-01")
    LocalDate getBauzeit();

    @JsonSetter(ApiNames.BAUZEIT)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    void setBauzeit(LocalDate bauzeit);

    @JsonGetter(ApiNames.VORBILD)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IVorbildRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IVorbildRef", value = "Prototype")
    IVorbild getVorbild();

    /**
     * Sets the vorbild.
     *
     * @param vorbild
     *            the new vorbild
     */
    @JsonSetter(ApiNames.VORBILD)
    @JsonDeserialize(as = Vorbild.class)
    void setVorbild(IVorbild vorbild);

    /**
     * Gets the achsfolg.
     *
     * @return the achsfolg
     */
    @JsonGetter(ApiNames.ACHSFOLG)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IAchsfolgRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IAchsfolgRef", value = "Axle configuration")
    IAchsfolg getAchsfolg();

    /**
     * Sets the achsfolg.
     *
     * @param achsfolg
     *            the new achsfolg
     */
    @JsonSetter(ApiNames.ACHSFOLG)
    @JsonDeserialize(as = Achsfolg.class)
    void setAchsfolg(IAchsfolg achsfolg);

    /**
     * Gets the sondermodell.
     *
     * @return the sondermodell
     */
    @JsonGetter(ApiNames.SONDERMODELL)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= ISonderModellRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.ISonderModellRef", value = "Special model indicator")
    ISonderModell getSondermodell();

    /**
     * Sets the sondermodell.
     *
     * @param sondermodell
     *            the new sondermodell
     */
    @JsonSetter(ApiNames.SONDERMODELL)
    @JsonDeserialize(as = SonderModell.class)
    void setSondermodell(ISonderModell sondermodell);

    /**
     * Gets the aufbau.
     *
     * @return the aufbau
     */
    @JsonGetter(ApiNames.AUFBAU)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IAufbauRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IAufbauRef", value = "Construction")
    IAufbau getAufbau();

    /**
     * Sets the aufbau.
     *
     * @param aufbau
     *            the new aufbau
     */
    @JsonSetter(ApiNames.AUFBAU)
    @JsonDeserialize(as = Aufbau.class)
    void setAufbau(IAufbau aufbau);

    /**
     * Gets the licht.
     *
     * @return the licht
     */
    @JsonGetter(ApiNames.LICHT)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= ILichtRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.ILichtRef", value = "Light configuration")
    ILicht getLicht();

    /**
     * Sets the licht.
     *
     * @param licht
     *            the new licht
     */
    @JsonSetter(ApiNames.LICHT)
    @JsonDeserialize(as = Licht.class)
    void setLicht(ILicht licht);

    /**
     * Gets the kupplung.
     *
     * @return the kupplung
     */
    @JsonGetter(ApiNames.KUPPLUNG)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IKupplungRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IKupplungRef", value = "Coupling configuration")
    IKupplung getKupplung();

    /**
     * Sets the kupplung.
     *
     * @param kupplung
     *            the new kupplung
     */
    @JsonSetter(ApiNames.KUPPLUNG)
    @JsonDeserialize(as = Kupplung.class)
    void setKupplung(IKupplung kupplung);

    /**
     * Gets the steuerung.
     *
     * @return the steuerung
     */
    @JsonGetter(ApiNames.STEUERUNG)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= ISteuerungRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.ISteuerungRef", value = "Control method")
    ISteuerung getSteuerung();

    /**
     * Sets the steuerung.
     *
     * @param steuerung
     *            the new steuerung
     */
    @JsonSetter(ApiNames.STEUERUNG)
    @JsonDeserialize(as = Steuerung.class)
    void setSteuerung(ISteuerung steuerung);

    /**
     * Gets the decoder typ.
     *
     * @return the decoder typ
     */
    @JsonGetter(ApiNames.DECODER_TYP)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as = IDecoderTypRef.class) 
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IDecoderTypRef", value = "Decoder type")
    IDecoderTyp getDecoderTyp();

    /**
     * Sets the decoder typ.
     *
     * @param decoderTyp
     *            the new decoder typ
     */
    @JsonSetter(ApiNames.DECODER_TYP)
    @JsonDeserialize(as = DecoderTyp.class)
    void setDecoderTyp(IDecoderTyp decoderTyp);

    /**
     * Gets the motor typ.
     *
     * @return the motor typ
     */
    @JsonGetter(ApiNames.MOTOR_TYP)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IMotorTypRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IMotorTypRef", value = "Motor type")
    IMotorTyp getMotorTyp();

    /**
     * Sets the motor typ.
     *
     * @param motorTyp
     *            the new motor typ
     */
    @JsonSetter(ApiNames.MOTOR_TYP)
    @JsonDeserialize(as = MotorTyp.class)
    void setMotorTyp(IMotorTyp motorTyp);

    /**
     * Sets the lange.
     *
     * @param lange
     *            the new lange
     */
    @JsonSetter(ApiNames.LANGE)
    void setLange(BigDecimal lange);

    /**
     * Gets the anmerkung.
     *
     * @return the anmerkung
     */
    @JsonGetter(ApiNames.ANMERKUNG)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "Remarks", example = "Ex set")
    String getAnmerkung();

    /**
     * Sets the anmerkung.
     *
     * @param anmerkung
     *            the new anmerkung
     */
    @JsonSetter(ApiNames.ANMERKUNG)
    void setAnmerkung(String anmerkung);

    /**
     * Gets the anleitungen.
     *
     * @return the anleitungen
     */
    @JsonGetter(ApiNames.ANLEITUNGEN)
    @JsonView(Views.Public.class)
    @JsonSerialize(using = PathSerializer.class)
    @ApiModelProperty(dataType = "String", value = "Instructions URL", example = "http://localhost/Modelbahn/produkt/MARKLIN/3000/betrieb_3000.pdf", accessMode = AccessMode.READ_ONLY)
    Path getAnleitungen();

    /**
     * Sets the anleitungen.
     *
     * @param anleitungen
     *            the new anleitungen
     */
    @JsonIgnore
    void setAnleitungen(Path anleitungen);

    /**
     * Gets the explosionszeichnung.
     *
     * @return the explosionszeichnung
     */
    @JsonGetter(ApiNames.EXPLOSIONSZEICHNUNG)
    @JsonView(Views.Public.class)
    @JsonSerialize(using = PathSerializer.class)
    @ApiModelProperty(dataType = "String", value = "Parts diagram URL", example = "http://localhost/Modelbahn/produkt/MARKLIN/3000/explo_3000.pdf", accessMode = AccessMode.READ_ONLY)
    Path getExplosionszeichnung();

    /**
     * Sets the explosionszeichnung.
     *
     * @param explosionszeichnung
     *            the new explosionszeichnung
     */
    @JsonIgnore
    void setExplosionszeichnung(Path explosionszeichnung);

    /**
     * Sets the abbildung.
     *
     * @param abbildung
     *            the new abbildung
     */
    @JsonIgnore
    void setAbbildung(Path abbildung);

    /**
     * Gets the teilen.
     *
     * @return the teilen
     */
    @JsonGetter(ApiNames.TEILEN)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentAs = IProduktTeilRef.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.model.refs.IProduktTeilRef;", value = "Product components", accessMode = AccessMode.READ_ONLY)
    Set<IProduktTeil> getTeilen();

    /**
     * Sets the teilen.
     *
     * @param teilen
     *            the new teilen
     */
    @JsonIgnore
    void setTeilen(Set<IProduktTeil> teilen);

    void addTeil(IProduktTeil funktion);

    void removeTeil(IProduktTeil funktion);
}