package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.Decoder;

import com.linepro.modellbahn.model.refs.IDecoderCVRef;
import com.linepro.modellbahn.model.refs.IDecoderRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IDecoderCV.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.CV)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.DECODER, ApiNames.CV, ApiNames.WERT, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.CV, description = "Decoder CV setting.")
public interface IDecoderCV extends IItem, IDecoderCVRef {

    /**
     * Gets the decoder.
     *
     * @return the decoder
     */
    @JsonGetter(ApiNames.DECODER)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IDecoderRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IDecoderRef", value = "Parent decoder", required = true)
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
     * Gets the cv.
     *
     * @return the cv
     */
    @JsonIgnore
    IDecoderTypCV getCv();

    /**
     * Sets the cv.
     *
     * @param cv the new cv
     */
    @JsonIgnore
    void setCv(IDecoderTypCV cv);

    @JsonSetter(ApiNames.CV)
    void setCvValue(Integer cv);

    /**
     * Sets the wert.
     *
     * @param wert the new wert
     */
    @JsonSetter(ApiNames.WERT)
    void setWert(Integer wert);
}