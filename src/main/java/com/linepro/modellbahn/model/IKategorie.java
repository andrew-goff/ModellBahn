/*
 * IKategorie
 * Author:  JohnG
 * Version: $id$
 */
package com.linepro.modellbahn.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.UnterKategorie;

import com.linepro.modellbahn.model.refs.IKategorieRef;
import com.linepro.modellbahn.model.refs.IUnterKategorieRef;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * IKategorie.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.KATEGORIE)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.NAMEN, ApiNames.BEZEICHNUNG, ApiNames.DELETED, ApiNames.UNTER_KATEGORIEN,
        ApiNames.LINKS })
@ApiModel(value = ApiNames.KATEGORIE, description = "Category.")
public interface IKategorie extends INamedItem, IKategorieRef {
    
    @JsonGetter(ApiNames.UNTER_KATEGORIEN)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(contentAs = IUnterKategorieRef.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.model.refs.IUnterKategorieRef;", value = "Sub categories", accessMode = AccessMode.READ_ONLY, required = true)
    Set<IUnterKategorie> getUnterKategorien();

    /**
     * Sets the unter kategorie.
     *
     * @param unterKategorien the new unter kategorie
     */
    @JsonSetter(ApiNames.UNTER_KATEGORIEN)
    @JsonDeserialize(contentAs = UnterKategorie.class)
    void setUnterKategorien(Set<IUnterKategorie> unterKategorien);

    void addUnterKategorie(IUnterKategorie unterKategorie);

    void removeUnterKategorie(IUnterKategorie unterKategorie);
}