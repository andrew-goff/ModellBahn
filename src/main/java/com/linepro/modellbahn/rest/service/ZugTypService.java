package com.linepro.modellbahn.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.linepro.modellbahn.model.impl.ZugTyp;
import com.linepro.modellbahn.model.keys.NameKey;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.AbstractItemService;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * ZugTypService. CRUD service for ZugTyp
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.ZUG_TYP, description = "ZugTyp maintenance")
@Path(ApiPaths.ZUG_TYP)
public class ZugTypService extends AbstractItemService<NameKey, ZugTyp> {

    public ZugTypService() {
        super(ZugTyp.class);
    }

    @JsonCreator
    public ZugTyp create(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiNames.NAMEN) String name,
            @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
        ZugTyp entity = new ZugTyp(id, name, bezeichnung, deleted);

        debug("created: " + entity);

        return entity;
    }

    @GET
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a ZugTyp by name", response = ZugTyp.class)
    public Response get(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.get(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds ZugTypen by example", response = ZugTyp.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "ZugTyp's id", required = false, dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.NAMEN, value = "ZugTyp's name", required = false, dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BEZEICHNUNG, value = "ZugTyp's description", required = false, dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DELETED, value = "true if ZugTyp is deleted", required = false, dataType = "Boolean", paramType = "query")
})
    public Response search(@Context UriInfo uriInfo) {
        return super.search(uriInfo);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds a ZugTyp", response = ZugTyp.class)
    public Response add(ZugTyp entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.NAME_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a ZugTyp by name", response = ZugTyp.class)
    public Response update(@PathParam(ApiPaths.NAME_PARAM_NAME) String name, ZugTyp entity) {
        return super.update(name, entity);
    }

    @DELETE
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a ZugTyp by name")
    public Response delete(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.delete(name);
    }
}
