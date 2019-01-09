package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.IProtokollRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * IProtokoll.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.PROTOKOLL)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.PROTOKOLL, description = "Digital protocoll.")
public interface IProtokoll extends INamedItem, IProtokollRef {

}