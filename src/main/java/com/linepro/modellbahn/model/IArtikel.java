package com.linepro.modellbahn.model;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;

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
import com.linepro.modellbahn.model.impl.Decoder;
import com.linepro.modellbahn.model.impl.Kupplung;
import com.linepro.modellbahn.model.impl.Licht;
import com.linepro.modellbahn.model.impl.MotorTyp;
import com.linepro.modellbahn.model.impl.Produkt;
import com.linepro.modellbahn.model.impl.Steuerung;
import com.linepro.modellbahn.model.impl.Wahrung;

import com.linepro.modellbahn.model.refs.IArtikelRef;
import com.linepro.modellbahn.model.refs.IDecoderRef;
import com.linepro.modellbahn.model.refs.IKupplungRef;
import com.linepro.modellbahn.model.refs.ILichtRef;
import com.linepro.modellbahn.model.refs.IMotorTypRef;
import com.linepro.modellbahn.model.refs.ISteuerungRef;
import com.linepro.modellbahn.model.refs.IWahrungRef;
import com.linepro.modellbahn.model.util.Status;
import com.linepro.modellbahn.rest.json.Formats;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.serialization.LocalDateDeserializer;
import com.linepro.modellbahn.rest.json.serialization.LocalDateSerializer;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IArtikel.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.ARTIKEL)
@JsonPropertyOrder({ApiNames.ID, ApiNames.ARTIKEL_ID, ApiNames.BEZEICHNUNG, ApiNames.PRODUKT, ApiNames.KAUFDATUM, ApiNames.WAHRUNG, ApiNames.PREIS, ApiNames.STUCK, ApiNames.STEUERUNG, ApiNames.MOTOR_TYP, ApiNames.LICHT, ApiNames.KUPPLUNG, ApiNames.DECODER, ApiNames.ANMERKUNG, ApiNames.BELADUNG, ApiNames.ABBILDUNG, ApiNames.STATUS, ApiNames.DELETED, ApiNames.LINKS})
@ApiModel(value = ApiNames.ARTIKEL, description = "An article - may differ from product because of modificiations")
public interface IArtikel extends IItem, IArtikelRef {

    @JsonSetter(ApiNames.ARTIKEL_ID)
    void setArtikelId(String artikelId);
    
    @JsonSetter(ApiNames.BEZEICHNUNG)
    void setBezeichnung(String bezeichnung);

    /**
     * Sets the produkt.
     *
     * @param produkt the new produkt
     */
    @JsonSetter(ApiNames.PRODUKT)
    @JsonDeserialize(as= Produkt.class)
    void setProdukt(IProdukt produkt);

    /**
     * Gets the kaufdatum.
     *
     * @return the kaufdatum
     */
    @JsonGetter(ApiNames.KAUFDATUM)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonView(Views.Public.class)
    @JsonFormat(shape=Shape.STRING, pattern= Formats.ISO8601_DATE)
    @ApiModelProperty(dataType = "java.time.LocalDate", value = "Purchase date", example = "1967-08-10")
    LocalDate getKaufdatum();

    /**
     * Sets the kaufdatum.
     *
     * @param kaufdatum the new kaufdatum
     */
    @JsonSetter(ApiNames.KAUFDATUM)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    void setKaufdatum(LocalDate kaufdatum);

    /**
     * Gets the wahrung.
     *
     * @return the wahrung
     */
    @JsonGetter(ApiNames.WAHRUNG)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IWahrungRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IWahrungRef", value = "Purchase currency")
    IWahrung getWahrung();

    /**
     * Sets the wahrung.
     *
     * @param wahrung the new wahrung
     */
    @JsonSetter(ApiNames.WAHRUNG)
    @JsonDeserialize(as= Wahrung.class)
    void setWahrung(IWahrung wahrung);

    /**
     * Gets the preis.
     *
     * @return the preis
     */
    @JsonGetter(ApiNames.PREIS)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Purchase price", example = "115.95")
    BigDecimal getPreis();

    /**
     * Sets the preis.
     *
     * @param preis the new preis
     */
    @JsonSetter(ApiNames.PREIS)
    void setPreis(BigDecimal preis);

    /**
     * Gets the stuck.
     *
     * @return the stuck
     */
    @JsonGetter(ApiNames.STUCK)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Purchase Quantity", example = "1", required = true)
    Integer getStuck();

    /**
     * Sets the stuck.
     *
     * @param stuck the new stuck
     */
    @JsonSetter(ApiNames.STUCK)
    void setStuck(Integer stuck);

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
     * @param steuerung the new steuerung
     */
    @JsonSetter(ApiNames.STEUERUNG)
    @JsonDeserialize(as= Steuerung.class)
    void setSteuerung(ISteuerung steuerung);

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
     * @param motorTyp the new motor typ
     */
    @JsonSetter(ApiNames.MOTOR_TYP)
    @JsonDeserialize(as= MotorTyp.class)
    void setMotorTyp(IMotorTyp motorTyp);

    /**
     * Gets the licht.
     *
     * @return the licht
     */
    @JsonGetter(ApiNames.LICHT)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= ILichtRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.ILichtRef", value = "Light Configuration")
    ILicht getLicht();

    /**
     * Sets the licht.
     *
     * @param licht the new licht
     */
    @JsonSetter(ApiNames.LICHT)
    @JsonDeserialize(as= Licht.class)
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
     * @param kupplung the new kupplung
     */
    @JsonSetter(ApiNames.KUPPLUNG)
    @JsonDeserialize(as= Kupplung.class)
    void setKupplung(IKupplung kupplung);

    /**
     * Gets the decoder.
     *
     * @return the decoder
     */
    @JsonGetter(ApiNames.DECODER)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IDecoderRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IDecoderRef", value = "Decoder")
    IDecoder getDecoder();

    /**
     * Sets the decoder.
     *
     * @param decoder the new decoder
     */
    @JsonSetter(ApiNames.DECODER)
    @JsonDeserialize(as= Decoder.class)
    void setDecoder(IDecoder decoder);

    /**
     * Gets the anmerkung.
     *
     * @return the anmerkung
     */
    @JsonGetter(ApiNames.ANMERKUNG)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "Remarks", example = "5* Motor and decoder")
    String getAnmerkung();

    /**
     * Sets the anmerkung.
     *
     * @param anmerkung the new anmerkung
     */
    @JsonSetter(ApiNames.ANMERKUNG)
    void setAnmerkung(String anmerkung);

    /**
     * Gets the beladung.
     *
     * @return the beladung
     */
    @JsonGetter(ApiNames.BELADUNG)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Wagon load", example = "holz")
    String getBeladung();

    /**
     * Sets the beladung.
     *
     * @param beladung the new beladung
     */
    @JsonSetter(ApiNames.BELADUNG)
    void setBeladung(String beladung);

    /**
     * Sets the abbildung.
     *
     * @param abbildung
     *            the new abbildung
     */
    @JsonIgnore
    void setAbbildung(Path abbildung);

    /**
     * Gets the status.
     *
     * @return the status
     */
    @JsonGetter(ApiNames.STATUS)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "Status", example = "GEKAUFT", required = true)
    Status getStatus();

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    @JsonSetter(ApiNames.STATUS)
    void setStatus(Status status);
}
