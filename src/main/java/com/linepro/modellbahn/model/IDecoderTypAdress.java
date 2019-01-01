package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.keys.DecoderTypAdressKey;
import com.linepro.modellbahn.model.util.AdressTyp;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.serialization.DecoderTypSerializer;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IDecoderTypAdress.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.ADRESS)
@JsonPropertyOrder({ApiNames.ID, ApiNames.DECODER_TYP,  ApiNames.INDEX,  ApiNames.ADRESS_TYP,  ApiNames.SPAN,  ApiNames.WERKSEINSTELLUNG, ApiNames.DELETED, ApiNames.LINKS})
@ApiModel(value = ApiNames.ADRESS, description = "Decoder type address - template for Decoder.")
public interface IDecoderTypAdress extends IItem<DecoderTypAdressKey>, IAdress {

    /**
     * Gets the decoder typ.
     *
     * @return the decoder typ
     */
    @JsonGetter(ApiNames.DECODER_TYP)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(using= DecoderTypSerializer.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.rest.json.serialization.IDecoderTypRef", value = "", required = true)
    IDecoderTyp getDecoderTyp();

    /**
     * Sets the decoder typ.
     *
     * @param decoderTyp the new decoder typ
     */
    @JsonSetter(ApiNames.DECODER_TYP)
    @JsonDeserialize(as= DecoderTyp.class)
    void setDecoderTyp(IDecoderTyp decoderTyp);

    /**
     * Gets the index.
     *
     * @return the index
     */
    @JsonGetter(ApiNames.INDEX)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "", required = true)
    Integer getIndex();

    /**
     * Sets the index.
     *
     * @param index the new index
     */
    @JsonSetter(ApiNames.INDEX)
    void setIndex(Integer index);

    /**
     * Sets the adressTyp.
     *
     * @param adressTyp the new adressTyp
     */
    @JsonSetter(ApiNames.ADRESS_TYP)
    void setAdressTyp(AdressTyp adressTyp);

    /**
     * Gets the span.
     *
     * @return the span
     */
    @JsonGetter(ApiNames.SPAN)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "", required = true)
    Integer getSpan();

    /**
     * Sets the span.
     *
     * @param span the new span
     */
    @JsonSetter(ApiNames.SPAN)
    void setSpan(Integer span);

    /**
     * Gets the werkseinstellung.
     *
     * @return the werkseinstellung
     */
    @JsonGetter(ApiNames.WERKSEINSTELLUNG)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "")
    Integer getAdress();

    /**
     * Sets the werkseinstellung.
     *
     * @param werkseinstellung the new werkseinstellung
     */
    @JsonSetter(ApiNames.WERKSEINSTELLUNG)
    void setAdress(Integer werkseinstellung);
}