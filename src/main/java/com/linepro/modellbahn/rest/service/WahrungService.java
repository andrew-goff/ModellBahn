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
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.linepro.modellbahn.model.IWahrung;
import com.linepro.modellbahn.model.impl.Wahrung;
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
 * WahrungService. CRUD service for Wahrung
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.WAHRUNG)
@Path(ApiPaths.WAHRUNG)
public class WahrungService extends AbstractItemService<NameKey, IWahrung> {

    public WahrungService() {
        super(IWahrung.class);
    }

    @JsonCreator(mode= Mode.DELEGATING)
    public static Wahrung create() {
        return new Wahrung();
    }
    
    @JsonCreator(mode= Mode.PROPERTIES)
    public static Wahrung create(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiNames.NAMEN) String name,
            @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
            @JsonProperty(value = ApiNames.DEZIMAL) Integer dezimal,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
        return new Wahrung(id, name, bezeichnung, dezimal, deleted);
    }

    @GET
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a Wahrung by name", response = IWahrung.class)
    public Response get(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.get(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Wahrungen by example", response = IWahrung.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "Wahrung id", dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.NAMEN, value = "Wahrung code", example = "GBP", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BEZEICHNUNG, value = "Wahrung description", example = "Pound Serling", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DEZIMAL, value = "Wahrung dezimal", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DELETED, value = "If true search for soft deleted items", example = "false", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.PAGE_NUMBER, value = "0 based page number for paged queries", example = "1", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.PAGE_SIZE, value = "Page size for paged queries", example = "10", dataType = "Integer", paramType = "query"),
    })
    public Response search(@Context UriInfo uriInfo) {
        return super.search(uriInfo);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds a Wahrung", response = IWahrung.class)
    public Response add(Wahrung entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.NAME_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a Wahrung by name", response = IWahrung.class)
    public Response update(@PathParam(ApiPaths.NAME_PARAM_NAME) String name, Wahrung entity) {
        return super.update(name, entity);
    }

    @DELETE
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a Wahrung by name")
    public Response delete(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.delete(name);
    }
}
