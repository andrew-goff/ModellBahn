package com.linepro.modellbahn.rest.json.serialization;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModelProperty;

public interface ILinkRef {
    @JsonGetter(ApiNames.LINKS)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(contentUsing= LinkSerializer.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.rest.json.serialization.ILink;", value = "", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    Set<ILink> getLinks();
}