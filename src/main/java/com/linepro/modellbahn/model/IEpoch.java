package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.IEpochRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * IEpoch.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.EPOCH)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.EPOCH, description = "Era - NEM 800.")
public interface IEpoch extends INamedItem, IEpochRef {

}