package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.DecoderTyp;

import com.linepro.modellbahn.model.refs.IDecoderTypCVRef;
import com.linepro.modellbahn.model.refs.IDecoderTypRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IDecoderTypCV.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.CV)
@JsonPropertyOrder({ApiNames.ID, ApiNames.DECODER_TYP,  ApiNames.CV,  ApiNames.BEZEICHNUNG,  ApiNames.MINIMAL,  ApiNames.MAXIMAL,  ApiNames.WERKSEINSTELLUNG, ApiNames.DELETED, ApiNames.LINKS})
@ApiModel(value = ApiNames.CV, description = "Decoder type CV - template for Decoder.")
public interface IDecoderTypCV extends IItem, IDecoderTypCVRef {

    @JsonGetter(ApiNames.DECODER_TYP)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IDecoderTypRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IDecoderTypRef", value = "Decoder type", required = true)
    IDecoderTyp getDecoderTyp();

    @JsonSetter(ApiNames.DECODER_TYP)
    @JsonDeserialize(as= DecoderTyp.class)
    void setDecoderTyp(IDecoderTyp decoderTyp);

    @JsonGetter(ApiNames.CV)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "CV", required = true)
    Integer getCv();

    @JsonSetter(ApiNames.CV)
    void setCv(Integer cv);

    @JsonSetter(ApiNames.BEZEICHNUNG)
    void setBezeichnung(String bezeichnung);

    @JsonGetter(ApiNames.MINIMAL)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Minimum value", example = "1")
    Integer getMinimal();

    @JsonSetter(ApiNames.MINIMAL)
    void setMinimal(Integer minimal);

    @JsonGetter(ApiNames.MAXIMAL)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Maximum value", example = "63")
    Integer getMaximal();

    @JsonSetter(ApiNames.MAXIMAL)
    void setMaximal(Integer maximal);

    @JsonGetter(ApiNames.WERKSEINSTELLUNG)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Default value", example = "40")
    Integer getWerkseinstellung();

    @JsonSetter(ApiNames.WERKSEINSTELLUNG)
    void setWerkseinstellung(Integer werkseinstellung);
}