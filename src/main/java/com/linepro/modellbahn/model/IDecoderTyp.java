package com.linepro.modellbahn.model;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.Hersteller;
import com.linepro.modellbahn.model.impl.Protokoll;

import com.linepro.modellbahn.model.refs.IDecoderTypAdressRef;
import com.linepro.modellbahn.model.refs.IDecoderTypCVRef;
import com.linepro.modellbahn.model.refs.IDecoderTypFunktionRef;
import com.linepro.modellbahn.model.refs.IDecoderTypRef;
import com.linepro.modellbahn.model.refs.IProtokollRef;
import com.linepro.modellbahn.model.util.Konfiguration;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * IDecoderTyp.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.DECODER_TYP)
@JsonPropertyOrder({ApiNames.ID, ApiNames.HERSTELLER, ApiNames.BESTELL_NR, ApiNames.BEZEICHNUNG, ApiNames.PROTOKOLL, ApiNames.FAHRSTUFE, ApiNames.GERAUSCH, ApiNames.I_MAX, ApiNames.KONFIGURATION, ApiNames.DELETED, ApiNames.ADRESSEN, ApiNames.CVS, ApiNames.FUNKTIONEN, ApiNames.LINKS})
@ApiModel(value = ApiNames.DECODER_TYP, description = "Decoder type - template for Decoder.")
public interface IDecoderTyp extends IItem, IDecoderTypRef {

    @JsonSetter(ApiNames.HERSTELLER)
    @JsonDeserialize(as= Hersteller.class)
    void setHersteller(IHersteller hersteller);

    @JsonSetter(ApiNames.BESTELL_NR)
    void setBestellNr(String name);

    @JsonSetter(ApiNames.BEZEICHNUNG)
    void setBezeichnung(String bezeichnung);

    @JsonGetter(ApiNames.I_MAX)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Maximum current in mA", example = "1100")
    BigDecimal getiMax();

    /**
     * Sets the i max.
     *
     * @param iMax the new i max
     */
    @JsonSetter(ApiNames.I_MAX)
    void setiMax(BigDecimal iMax);

    /**
     * Gets the protokoll.
     *
     * @return the protokoll
     */
    @JsonGetter(ApiNames.PROTOKOLL)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IProtokollRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IProtokollRef", value = "Default protocoll", required = true)
    IProtokoll getProtokoll();

    /**
     * Sets the protokoll.
     *
     * @param protokoll the new protokoll
     */
    @JsonSetter(ApiNames.PROTOKOLL)
    @JsonDeserialize(as= Protokoll.class)
    void setProtokoll(IProtokoll protokoll);

    /**
     * Gets the fahrstufe.
     *
     * @return the fahrstufe
     */
    @JsonGetter(ApiNames.FAHRSTUFE)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Default speed steps", example = "127", required = true)
    Integer getFahrstufe();

    /**
     * Sets the fahrstufe.
     *
     * @param fahrstufe the new fahrstufe
     */
    @JsonSetter(ApiNames.FAHRSTUFE)
    void setFahrstufe(Integer fahrstufe);

    /**
     * Gets the sound.
     *
     * @return the sound
     */
    @JsonGetter(ApiNames.GERAUSCH)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "True if decoder supports sound", example = "true", required = true)
    Boolean getSound();

    /**
     * Sets the sound.
     *
     * @param sound the new sound
     */
    @JsonSetter(ApiNames.GERAUSCH)
    void setSound(Boolean sound);

    /**
     * Gets the konfiguration.
     *
     * @return the konfiguration
     */
    @JsonGetter(ApiNames.KONFIGURATION)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "Configuration method", example = "CV", required = true)
    Konfiguration getKonfiguration();

    /**
     * Sets the konfiguration.
     *
     * @param konfiguration the new konfiguration
     */
    @JsonSetter(ApiNames.KONFIGURATION)
    void setKonfiguration(Konfiguration konfiguration);

    /**
     * Gets the adressen.
     *
     * @return the adressen
     */
    @JsonIgnore
    Set<IDecoderTypAdress> getAdressen();

    /**
     * Sets the adressen.
     *
     * @param adressen the new adressen
     */
    @JsonIgnore
    void setAdressen(Set<IDecoderTypAdress> adressen);

    /**
     * Gets the cv.
     *
     * @return the cv
     */
    @JsonIgnore
    Set<IDecoderTypCV> getCVs();

    /**
     * Sets the cv.
     *
     * @param cv the new cv
     */
    @JsonIgnore
    void setCVs(Set<IDecoderTypCV> cv);

    /**
     * Gets the funktion.
     *
     * @return the funktion
     */
    @JsonIgnore
    Set<IDecoderTypFunktion> getFunktionen();

    /**
     * Sets the funktion.
     *
     * @param funktion the new funktion
     */
    @JsonIgnore
    void setFunktionen(Set<IDecoderTypFunktion> funktion);

    void addAdress(IDecoderTypAdress adress);

    void removeAdress(IDecoderTypAdress adress);

    void addCV(IDecoderTypCV CV);

    void removeCV(IDecoderTypCV CV);

    void addFunktion(IDecoderTypFunktion funktion);

    void removeFunktion(IDecoderTypFunktion funktion);

    @JsonGetter(ApiNames.ADRESSEN)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentAs = IDecoderTypAdressRef.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.model.refs.IDecoderTypAdressRef;", value = "Assignable adresses", accessMode = AccessMode.READ_ONLY, required = true)
    Set<IDecoderTypAdress> getSortedAdressen();

    @JsonGetter(ApiNames.CVS)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentAs = IDecoderTypCVRef.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.model.refs.IDecoderTypCVRef;", value = "Configurable CVs", accessMode = AccessMode.READ_ONLY)
    Set<IDecoderTypCV> getSortedCVs();

    @JsonGetter(ApiNames.FUNKTIONEN)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentAs = IDecoderTypFunktionRef.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.model.refs.IDecoderTypFunktionRef;", value = "Available function mappings", accessMode = AccessMode.READ_ONLY, required = true)
    Set<IDecoderTypFunktion> getSortedFunktionen();
}