package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.IGattungRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * IGattung.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.GATTUNG)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.GATTUNG, description = "Rolling stock class (amalgamation of Baureihe and Gattung).")
public interface IGattung extends INamedItem, IGattungRef {

}