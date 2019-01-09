package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.ISpurweiteRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * ISpurweite.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.SPURWEITE)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.SPURWEITE, description = "Track gauge - NEM 010.")
public interface ISpurweite extends INamedItem, ISpurweiteRef {
}