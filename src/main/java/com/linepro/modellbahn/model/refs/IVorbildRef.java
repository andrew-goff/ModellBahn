package com.linepro.modellbahn.model.refs;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonPropertyOrder({ ApiNames.GATTUNG, ApiNames.BEZEICHNUNG, ApiNames.ABBILDUNG })
@ApiModel(value = ApiNames.VORBILD, description = "A real world prototype.")
public interface IVorbildRef extends IPictureRef, IRef {
    @JsonGetter(ApiNames.GATTUNG)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IGattungRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IGattungRef", value = "Rolling stock class", example = "BR 89.0", required = true)
    IGattung getGattung();

    @JsonGetter(ApiNames.BEZEICHNUNG)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "Description", example = "Dampftenderlok BR 89.0")
    String getBezeichnung();
}
