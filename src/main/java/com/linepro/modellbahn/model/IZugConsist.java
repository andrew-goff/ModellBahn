package com.linepro.modellbahn.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.linepro.modellbahn.model.impl.Artikel;
import com.linepro.modellbahn.model.impl.Zug;
import com.linepro.modellbahn.model.keys.ZugConsistKey;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.resolver.ArtikelResolver;
import com.linepro.modellbahn.rest.json.resolver.ZugResolver;
import com.linepro.modellbahn.rest.util.ApiNames;

/**
 * IZugConsist.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.CONSIST)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.ZUG, ApiNames.POSITION, ApiNames.ARTIKEL, ApiNames.DELETED, ApiNames.LINKS })
public interface IZugConsist extends IItem<ZugConsistKey> {

    /**
     * Gets the zug.
     *
     * @return the zug
     */
    @JsonGetter(ApiNames.ZUG)
    @JsonView(Views.DropDown.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = ApiNames.NAMEN, resolver= ZugResolver.class)
    IZug getZug();

    /**
     * Sets the zug.
     *
     * @param zug the new zug
     */
    @JsonSetter(ApiNames.ZUG)
    @JsonDeserialize(as= Zug.class)
    void setZug(IZug zug);

    /**
     * Gets the position.
     *
     * @return the position
     */
    @JsonGetter(ApiNames.POSITION)
    @JsonView(Views.DropDown.class)
    Integer getPosition();

    /**
     * Sets the position.
     *
     * @param position the new position
     */
    @JsonSetter(ApiNames.POSITION)
    void setPosition(Integer position);

    /**
     * Gets the artikel.
     *
     * @return the artikel
     */
    @JsonGetter(ApiNames.ARTIKEL)
    @JsonView(Views.DropDown.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = ApiNames.NAMEN, resolver= ArtikelResolver.class)
    IArtikel getArtikel();

    /**
     * Sets the artikel.
     *
     * @param artikel the new artikel
     */
    @JsonSetter(ApiNames.ARTIKEL)
    @JsonDeserialize(contentAs = Artikel.class)
    void setArtikel(IArtikel artikel);

}