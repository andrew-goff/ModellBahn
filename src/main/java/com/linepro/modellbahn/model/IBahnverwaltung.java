package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.IBahnverwaltungRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * IBahnverwaltung.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.BAHNVERWALTUNG)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.BAHNVERWALTUNG, description = "Railway company.")
public interface IBahnverwaltung extends INamedItem, IBahnverwaltungRef {

}