package com.linepro.modellbahn.model;

import java.io.Serializable;
import java.net.URI;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.linepro.modellbahn.model.refs.ILinkRef;
import com.linepro.modellbahn.persistence.IKey;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

/**
 * IItem
 * @author   $Author$
 * @version  $Id$
 *
 * IItem is the root for all data items in this project.
 * 
 * NB. All properties are objects because they may need to be null for template matching.
 */
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public interface IItem extends Comparable<IItem>, Serializable, ILinkRef {

    /**
     * Gets the primary key of this item
     *
     * @return the primary key (unique by class but can be repeated in other classes).
     */
    @JsonGetter(ApiNames.ID)
    @JsonView(Views.Internal.class)
    @ApiModelProperty(accessMode = AccessMode.READ_ONLY, required = true)
    Long getId();

    /**
     * Sets the primary key for this item
     *
     * @param id the primary key (unique by class but can be repeated in other classes).
     */
    @JsonSetter(ApiNames.ID)
    void setId(Long id);

    /**
     * Gets the state of this item.
     *
     * @return true if this item is soft deleted otherwise false.
     */
    @JsonGetter(ApiNames.DELETED)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "True if soft deleted", example = "false", required = true)
    Boolean getDeleted();

    /**
     * Sets the state for this item.
     *
     * @param deleted - true if this item is soft deleted otherwise false.
     */
    @JsonSetter(ApiNames.DELETED)
    void setDeleted(Boolean deleted);

    /**
     * Adds the HATEOAS links for this item.
     * Always adds self and parent, optionally adds update and delete
     * @param root the root uri
     * @param update add an update link
     * @param delete add a delete link
     */
    IItem addLinks(URI root, boolean update, boolean delete);

    @JsonIgnore
    String getParentId();

    @JsonIgnore
    String getLinkId();
}
