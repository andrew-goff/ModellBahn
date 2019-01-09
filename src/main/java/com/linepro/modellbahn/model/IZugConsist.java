package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.Artikel;
import com.linepro.modellbahn.model.impl.Zug;

import com.linepro.modellbahn.model.refs.IZugConsistRef;
import com.linepro.modellbahn.model.refs.IZugRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * IZugConsist.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.CONSIST)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.ZUG, ApiNames.POSITION, ApiNames.ARTIKEL, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.CONSIST, description = "Rolling stock by poisition in a train.")
public interface IZugConsist extends IItem, IZugConsistRef {

    @JsonGetter(ApiNames.ZUG)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IZugRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IZugRef", value = "Parent train", accessMode = AccessMode.READ_ONLY, required = true)
    IZug getZug();

    @JsonSetter(ApiNames.ZUG)
    @JsonDeserialize(as= Zug.class)
    void setZug(IZug zug);

    @JsonSetter(ApiNames.POSITION)
    void setPosition(Integer position);

    @JsonSetter(ApiNames.ARTIKEL)
    @JsonDeserialize(contentAs = Artikel.class)
    void setArtikel(IArtikel artikel);

}