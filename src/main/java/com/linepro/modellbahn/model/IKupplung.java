package com.linepro.modellbahn.model;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.linepro.modellbahn.model.refs.IKupplungRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.serialization.PathSerializer;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * IKupplung.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.KUPPLUNG)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.ABBILDUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.KUPPLUNG, description = "Coupling configuration - Märklin coding.")
public interface IKupplung extends INamedItem, IKupplungRef {

    /**
     * Gets the abbildung.
     *
     * @return the abbildung
     */
    @JsonGetter(ApiNames.ABBILDUNG)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(using = PathSerializer.class)
    @ApiModelProperty(dataType = "String", value = "Image URL", accessMode = AccessMode.READ_ONLY)
    Path getAbbildung();

    /**
     * Sets the abbildung.
     *
     * @param abbildung
     *            the new abbildung
     */
    @JsonIgnore
    void setAbbildung(Path abbildung);
}