package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.IZugTypRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * IZugTyp.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.ZUG_TYP)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.ZUG_TYP, description = "Type of train.")
public interface IZugTyp extends INamedItem, IZugTypRef {

}