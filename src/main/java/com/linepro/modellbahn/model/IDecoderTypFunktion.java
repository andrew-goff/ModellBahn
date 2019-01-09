package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.DecoderTyp;

import com.linepro.modellbahn.model.refs.IDecoderTypFunktionRef;
import com.linepro.modellbahn.model.refs.IDecoderTypRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IDecoderTypFunktion.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(ApiNames.FUNKTION)
@JsonPropertyOrder({ApiNames.ID, ApiNames.DECODER_TYP,  ApiNames.REIHE,  ApiNames.FUNKTION,  ApiNames.BEZEICHNUNG,  ApiNames.PROGRAMMABLE, ApiNames.DELETED, ApiNames.LINKS})
@ApiModel(value = ApiNames.FUNKTION, description = "Decoder type function mapping - template for Decoder.")
public interface IDecoderTypFunktion extends IItem, IDecoderTypFunktionRef {


    @JsonGetter(ApiNames.DECODER_TYP)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IDecoderTypRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IDecoderTypRef", value = "Decoder type", required = true)
    IDecoderTyp getDecoderTyp();

    @JsonSetter(ApiNames.DECODER_TYP)
    @JsonDeserialize(as= DecoderTyp.class)
    void setDecoderTyp(IDecoderTyp decoderTyp);

    @JsonSetter(ApiNames.REIHE)
    void setReihe(Integer reihe);

    @JsonSetter(ApiNames.FUNKTION)
    void setFunktion(String bezeichnung);

    @JsonSetter(ApiNames.BEZEICHNUNG)
    void setBezeichnung(String bezeichnung);

    @JsonSetter(ApiNames.PROGRAMMABLE)
    void setProgrammable(Boolean programmable);
}