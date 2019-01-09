package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.IMassstabRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * IMassstab.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.MASSSTAB)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.MASSSTAB, description = "Scale - NEM 010.")
public interface IMassstab extends INamedItem, IMassstabRef {

}