package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.IAntriebRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * IAchsfolg.
 * Represents an axle configuration (German nomenclature)
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.ANTRIEB)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.ANTRIEB, description = "Drive method")
public interface IAntrieb extends INamedItem, IAntriebRef {
}