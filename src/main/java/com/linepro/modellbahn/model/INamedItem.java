/*
 * INamedItem
 * Author:  JohnG
 * Version: $id$
 */
package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.linepro.modellbahn.model.refs.INamedItemRef;
import com.linepro.modellbahn.rest.util.ApiNames;

/**
 * INamedItem.
 * @author   $Author$
 * @version  $Id$
 */
@JsonPropertyOrder({ApiNames.ID,ApiNames.NAMEN,ApiNames.BEZEICHNUNG,ApiNames.DELETED, ApiNames.LINKS})
public interface INamedItem extends INamedItemRef, IItem {

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    @JsonSetter(ApiNames.NAMEN)
    void setName(String name);

    /**
     * Sets the bezeichnung.
     *
     * @param bezeichnung the new bezeichnung
     */
    @JsonSetter(ApiNames.BEZEICHNUNG)
    void setBezeichnung(String bezeichnung);
}