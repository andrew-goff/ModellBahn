package com.linepro.modellbahn.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IZug;
import com.linepro.modellbahn.model.IZugConsist;
import com.linepro.modellbahn.model.IZugTyp;
import com.linepro.modellbahn.model.impl.Zug;
import com.linepro.modellbahn.model.impl.ZugConsist;
import com.linepro.modellbahn.model.keys.NameKey;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.impl.StaticPersisterFactory;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.AbstractItemService;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * ZugService. CRUD service for Zug
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.ZUG)
@Path(ApiPaths.ZUG)
public class ZugService extends AbstractItemService<NameKey, IZug> {

    private final IPersister<IZugConsist> consistPersister;

    public ZugService() {
        super(IZug.class);
        
        consistPersister = StaticPersisterFactory.get().createPersister(IZugConsist.class);
    }

    @GET
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a IZug by name", response = IZug.class)
    public Response get(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.get(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Zugen by example", response = IZug.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "Zug id", dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ZUG_TYP, value = "Zug typ", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.NAMEN, value = "Zug code", example = "BAVARIA", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BEZEICHNUNG, value = "Zug description", example = "TEE „Bavaria“", dataType = "String", paramType = "query"),
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
    @ApiOperation(code = 201, value = "Adds a Zug", response = IZug.class)
    public Response add(IZug entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.NAME_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a Zug by name", response = IZug.class)
    public Response update(@PathParam(ApiPaths.NAME_PARAM_NAME) String name, IZug entity) {
        return super.update(name, entity);
    }

    @DELETE
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a Zug by name")
    public Response delete(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.delete(name);
    }

    @POST
    @Path(ApiPaths.NAME_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds a vehicle to a named Zug", response = IZugConsist.class)
    public Response addConsist(@PathParam(ApiPaths.NAME_PARAM_NAME) String zugStr, @QueryParam(ApiPaths.ARTIKEL) String artikelId) {
        try {
            logPost(zugStr + "/" + artikelId);

            IZug zug = findZug(zugStr, true);

            if (zug == null) {
                return getResponse(badRequest(null, "Zug " + zugStr + " does not exist"));
            }

            IArtikel artikel = findArtikel(artikelId, true);

            if (artikel == null) {
                return getResponse(badRequest(null, "Artikel " + artikelId + " does not exist"));
            }

            IZugConsist zugConsist = new ZugConsist(null, zug, null, artikel, false);

            zug.addConsist(zugConsist);

            getPersister().update(zug);

            return getResponse(created(), zugConsist, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.ZUG_CONSIST_PATH)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a vehicle in a named Zug", response = IZugConsist.class)
    public Response updateConsist(@PathParam(ApiPaths.ZUG_PARAM_NAME) String zugStr, @PathParam(ApiPaths.POSITION_PARAM_NAME) Integer position, @QueryParam(ApiPaths.ARTIKEL) String artikelId) {
        try {
            logPost(zugStr + "/" + position + "?" + artikelId);

            IZugConsist consist = findZugConsist(zugStr, position, true);

            if (consist == null) {
                return getResponse(badRequest(null, "ZugConsist " + zugStr + "/" + position + " does not exist"));
            }

            IArtikel artikel = findArtikel(artikelId, true);

            if (artikel == null) {
                return getResponse(badRequest(null, "Artikel " + artikelId + " does not exist"));
            }

            consist.setArtikel(artikel);

            consist = getConsistPersister().update(consist);

            return getResponse(created(), consist, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @DELETE
    @Path(ApiPaths.ZUG_CONSIST_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Removes a vehicle from a named Zug")
    public Response deleteConsist(@PathParam(ApiPaths.ZUG_PARAM_NAME) String zugStr, @PathParam(ApiPaths.POSITION_PARAM_NAME) Integer position) {
        try {
            IZugConsist zugConsist = findZugConsist(zugStr, position, true);

            if (zugConsist == null) {
                return getResponse(badRequest(null, "ZugConsist " + zugStr + "/" + position + " does not exist"));
            }

            IZug zug = zugConsist.getZug();

            zug.removeConsist(zugConsist);

            //getZugConsistPersister().delete(zugConsist);
            // TODO: resequence position for remaining items
            
            getPersister().update(zug);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    private IPersister<IZugConsist> getConsistPersister() {
        return consistPersister;
    }
}
