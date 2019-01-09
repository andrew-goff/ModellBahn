package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.Wahrung;

import com.linepro.modellbahn.model.refs.ILandRef;
import com.linepro.modellbahn.model.refs.IWahrungRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ILand.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.LAND)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.WAHRUNG, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.LAND, description = "Country.")
public interface ILand extends INamedItem, ILandRef {

    /**
     * Gets the wahrung.
     *
     * @return the wahrung
     */
    @JsonGetter(ApiNames.WAHRUNG)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IWahrungRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IWahrungRef", value = "Currency", required = true)
    IWahrung getWahrung();

    /**
     * Sets the wahrung.
     *
     * @param wharung the new wahrung
     */
    @JsonSetter(ApiNames.WAHRUNG)
    @JsonDeserialize(as= Wahrung.class)
    void setWahrung(IWahrung wharung);

}