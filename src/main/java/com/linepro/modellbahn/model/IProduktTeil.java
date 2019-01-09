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

import com.linepro.modellbahn.model.refs.IProduktRef;
import com.linepro.modellbahn.model.refs.IProduktTeilRef;
import com.linepro.modellbahn.rest.json.Views;
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
public interface IProduktTeil extends IItem, IProduktTeilRef {

    @JsonGetter(ApiNames.PRODUKT)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IProduktRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IProduktRef", value = "Head product", accessMode = AccessMode.READ_ONLY, required = true)
    IProdukt getProdukt();

    @JsonSetter(ApiNames.PRODUKT)
    @JsonDeserialize(as= Produkt.class)
    void setProdukt(IProdukt produkt);

    @JsonSetter(ApiNames.TEIL)
    @JsonDeserialize(as= ProduktTeil.class)
    void setTeil(IProdukt teil);

    @JsonSetter(ApiNames.ANZAHL)
    void setAnzahl(Integer anzahl);
}