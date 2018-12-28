package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.Produkt;
import com.linepro.modellbahn.model.impl.ProduktTeil;
import com.linepro.modellbahn.model.keys.ProduktTeilKey;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.serialization.ProduktSerializer;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * IProduktTeil.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.TEIL)
@JsonPropertyOrder({ApiNames.ID, ApiNames.PRODUKT,ApiNames.TEIL, ApiNames.ANZAHL, ApiNames.DELETED, ApiNames.LINKS})
@ApiModel(value = ApiNames.TEIL, description = "Part of product (spares for rolling stock - contents for set &c).")
public interface IProduktTeil extends IItem<ProduktTeilKey> {

    /**
     * Gets the produkt.
     *
     * @return the produkt
     */
    @JsonGetter(ApiNames.PRODUKT)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(using= ProduktSerializer.class)
    @ApiModelProperty(name=ApiNames.PRODUKT, dataType = "IProduktRef", accessMode = AccessMode.READ_ONLY)
    IProdukt getProdukt();

    /**
     * Sets the produkt.
     *
     * @param produkt the new produkt
     */
    @JsonSetter(ApiNames.PRODUKT)
    @JsonDeserialize(as= Produkt.class)
    void setProdukt(IProdukt produkt);

    @JsonGetter(ApiNames.TEIL)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(using= ProduktSerializer.class)
    @ApiModelProperty(name=ApiNames.TEIL, dataType = "IProduktRef", accessMode = AccessMode.READ_ONLY)
    IProdukt getTeil();

    @JsonSetter(ApiNames.TEIL)
    @JsonDeserialize(as= ProduktTeil.class)
    void setTeil(IProdukt teil);

    /**
     * Gets the anzahl.
     *
     * @return the anzahl
     */
    @JsonGetter(ApiNames.ANZAHL)
    @JsonView(Views.DropDown.class)
    Integer getAnzahl();

    /**
     * Sets the anzahl.
     *
     * @param anzahl the new anzahl
     */
    @JsonSetter(ApiNames.ANZAHL)
    void setAnzahl(Integer anzahl);

}