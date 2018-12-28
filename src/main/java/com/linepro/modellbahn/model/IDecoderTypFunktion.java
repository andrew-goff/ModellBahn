package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.keys.DecoderTypFunktionKey;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.serialization.DecoderTypSerializer;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IDecoderTypFunktion.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(ApiNames.FUNKTION)
@JsonPropertyOrder({ApiNames.ID, ApiNames.DECODER_TYP,  ApiNames.REIHE,  ApiNames.PROGRAMMABLE, ApiNames.DELETED, ApiNames.LINKS})
@ApiModel(value = ApiNames.FUNKTION, description = "Decoder type function mapping - template for Decoder.")
public interface IDecoderTypFunktion extends INamedItem<DecoderTypFunktionKey> {

    /**
     * Gets the decoder typ.
     *
     * @return the decoder typ
     */
    @JsonGetter(ApiNames.DECODER_TYP)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(using= DecoderTypSerializer.class)
    @ApiModelProperty(name = ApiNames.DECODER_TYP, dataType = "com.linepro.modellbahn.rest.json.serialization.IDecoderTypRef")
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
     * Gets the reihe.
     *
     * @return the reihe
     */
    @JsonGetter(ApiNames.REIHE)
    @JsonView(Views.DropDown.class)
    Integer getReihe();

    /**
     * Sets the reihe.
     *
     * @param reihe the new reihe
     */
    @JsonSetter(ApiNames.REIHE)
    void setReihe(Integer reihe);

    /**
     * Gets the programmable.
     *
     * @return the programmable
     */
    @JsonGetter(ApiNames.PROGRAMMABLE)
    @JsonView(Views.Public.class)
    Boolean getProgrammable();
    
    /**
     * Sets the programmable.
     *
     * @param programmable the new programmable
     */
    @JsonSetter(ApiNames.PROGRAMMABLE)
    void setProgrammable(Boolean programmable);
}