package com.linepro.modellbahn.rest.util;

import java.util.List;

import javax.ws.rs.core.Link;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.serialization.LinkSerializer;

import io.swagger.annotations.ApiModelProperty;

public class ListWithLinks<E extends IItem> {

    private final List<E> entities;
    
    private final List<Link> links;

    public ListWithLinks(List<E> entities, List<Link> links) {
        this.entities = entities;
        this.links = links;

        entities.sort(null);
    }

    @JsonGetter
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "Entities", required = true)
    public List<E> getEntities() {
        return entities;
    }

    @JsonGetter(ApiNames.LINKS)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(contentUsing=LinkSerializer.class)
    @ApiModelProperty(dataType = "[Lcom.linepro.modellbahn.rest.json.serialization.ILink;", value = "HAETOS Links for API manipulation", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    public List<Link> getLinks() {
        return links;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("entities", entities)
                .append("links", links)
                .toString();
    }
    
}
