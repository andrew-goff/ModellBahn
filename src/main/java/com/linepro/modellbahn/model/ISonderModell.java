package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.ISonderModellRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * ISonderModell.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.SONDERMODELL)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.SONDERMODELL, description = "Special model - e.g,. MHI &c.")
public interface ISonderModell extends INamedItem, ISonderModellRef {

}