package com.linepro.modellbahn.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.DecoderAdress;
import com.linepro.modellbahn.model.impl.DecoderCV;
import com.linepro.modellbahn.model.impl.DecoderFunktion;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.impl.Protokoll;

import com.linepro.modellbahn.model.refs.IDecoderAdressRef;
import com.linepro.modellbahn.model.refs.IDecoderCVRef;
import com.linepro.modellbahn.model.refs.IDecoderFunktionRef;
import com.linepro.modellbahn.model.refs.IDecoderRef;
import com.linepro.modellbahn.model.refs.IProtokollRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * IDecoder.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.DECODER)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.DECODER_ID, ApiNames.DECODER_TYP, ApiNames.BEZEICHNUNG, ApiNames.PROTOKOLL, ApiNames.FAHRSTUFE, ApiNames.DELETED, ApiNames.ADRESSEN, ApiNames.CVS, ApiNames.FUNKTIONEN, ApiNames.LINKS })
@ApiModel(value = ApiNames.DECODER, description = "Decoder - installed or spare.")
public interface IDecoder extends IItem, IDecoderRef {

    @JsonSetter(ApiNames.DECODER_ID)
    void setDecoderId(String  name);

    @JsonSetter(ApiNames.DECODER_TYP)
    @JsonDeserialize(as= DecoderTyp.class)
    void setDecoderTyp(IDecoderTyp decoderTyp );

    void setBezeichnung(String bezeichnung);

    @JsonGetter(ApiNames.PROTOKOLL)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IProtokollRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IProtokollRef", value = "Decoder protocol", required = true)
    IProtokoll getProtokoll();

    @JsonSetter(ApiNames.PROTOKOLL)
    @JsonDeserialize(as= Protokoll.class)
    void setProtokoll(IProtokoll protokoll);

    @JsonGetter(ApiNames.FAHRSTUFE)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Decoder speed steps", example="27", required = true)
    Integer getFahrstufe();

    @JsonSetter(ApiNames.FAHRSTUFE)
    void setFahrstufe(Integer fahrstufe);

    @JsonGetter(ApiNames.ADRESSEN)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentAs= IDecoderAdressRef.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.model.refs.IDecoderAdressRef;", value = "Decoder addresses", accessMode = AccessMode.READ_ONLY, required = true)
    Set<IDecoderAdress> getAdressen();

    @JsonSetter(ApiNames.ADRESSEN)
    @JsonDeserialize(contentAs= DecoderAdress.class)
    void setAdressen(Set<IDecoderAdress> adressen);

    void addAdress(IDecoderAdress adress);

    void removeAdress(IDecoderAdress adress);

    /**
     * Gets the cv.
     *
     * @return the cv
     */
    @JsonGetter(ApiNames.CVS)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentAs= IDecoderCVRef.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.model.refs.IDecoderCVRef;", value = "Decoder cv values", accessMode = AccessMode.READ_ONLY)
    Set<IDecoderCV> getCVs();

    @JsonSetter(ApiNames.CVS)
    @JsonDeserialize(contentAs= DecoderCV.class)
    void setCVs(Set<IDecoderCV> cv);

    void addCV(IDecoderCV cv);

    void removeCV(IDecoderCV cv);

    /**
     * Gets the funktionen.
     *
     * @return the funktionen
     */
    @JsonGetter(ApiNames.FUNKTIONEN)
    @JsonView(Views.Public.class)
    @JsonSerialize(contentAs= IDecoderFunktionRef.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.model.refs.IDecoderFunktionRef;", value = "Decoder functions", accessMode = AccessMode.READ_ONLY, required = true)
    Set<IDecoderFunktion> getFunktionen();

    /**
     * Sets the funktionen.
     *
     * @param funktionen the new funktionen
     */
    @JsonSetter(ApiNames.FUNKTIONEN)
    @JsonDeserialize(contentAs= DecoderFunktion.class)
    void setFunktionen(Set<IDecoderFunktion> funktionen);

    void addFunktion(IDecoderFunktion cv);

    void removeFunktion(IDecoderFunktion cv);
}