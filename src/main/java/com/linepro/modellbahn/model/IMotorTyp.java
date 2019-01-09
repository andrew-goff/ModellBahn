package com.linepro.modellbahn.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.linepro.modellbahn.model.refs.IMotorTypRef;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;

/**
 * IMotorTyp.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.MOTOR_TYP)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.LINKS })
@ApiModel(value = ApiNames.MOTOR_TYP, description = "Type of motor - Märklin coding.")
public interface IMotorTyp extends INamedItem, IMotorTypRef {
}