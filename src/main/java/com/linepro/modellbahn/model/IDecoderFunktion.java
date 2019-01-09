package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.linepro.modellbahn.model.refs.IDecoderFunktionRef;
import com.linepro.modellbahn.model.refs.IDecoderRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IDecoderFunktion.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.FUNKTION)
@JsonPropertyOrder({ApiNames.ID, ApiNames.DECODER, ApiNames.FUNKTION,  ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS})
@ApiModel(value = ApiNames.FUNKTION, description = "Decoder function mapping.")
public interface IDecoderFunktion extends IItem, IDecoderFunktionRef {

    @JsonGetter(ApiNames.DECODER)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IDecoderRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IDecoderRef", value = "Parent decoder", required = true)
    IDecoder getDecoder();

    @JsonIgnore
    void setDecoder(IDecoder decoder);

    @JsonIgnore
    void setFunktion(IDecoderTypFunktion funktion);

    @JsonSetter(ApiNames.BEZEICHNUNG)
    void setBezeichnung(String bezeichnung);
}
