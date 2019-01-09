package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.ISteuerungRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * ISteuerung.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.STEUERUNG)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.STEUERUNG, description = "Control system.")
public interface ISteuerung extends INamedItem, ISteuerungRef {

}