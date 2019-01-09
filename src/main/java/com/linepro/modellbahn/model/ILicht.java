package com.linepro.modellbahn.model;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.ILichtRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * ILicht.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.LICHT)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.ABBILDUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.LICHT, description = "Light configuration - Märklin coding.")
public interface ILicht extends INamedItem, ILichtRef {
    /**
     * Sets the abbildung.
     *
     * @param abbildung
     *            the new abbildung
     */
    @JsonIgnore
    void setAbbildung(Path abbildung);
}