package com.linepro.modellbahn.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.linepro.modellbahn.model.IKategorie;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.impl.Kategorie;
import com.linepro.modellbahn.model.impl.UnterKategorie;
import com.linepro.modellbahn.model.keys.NameKey;
import com.linepro.modellbahn.persistence.DBNames;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * KategorieService. CRUD service for Kategorie and UnterKategorie
 * Transpires that two way ManyToOne are best updated from the parent end 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.KATEGORIE)
@Path(ApiPaths.KATEGORIE)
public class KategorieService extends AbstractItemService<NameKey,  IKategorie> {

    private final IPersister<IUnterKategorie> unterKategoriePersister;

    public KategorieService() {
        super(IKategorie.class);

        unterKategoriePersister = StaticPersisterFactory.get().createPersister(IUnterKategorie.class);
    }

    @GET
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds an Kategorie by name", response = IKategorie.class)
    public Response get(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.get(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Kategorieen by example", response = IKategorie.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "Kategorie id", dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.NAMEN, value = "Kategorie code", example = "LOKOMOTIV", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BEZEICHNUNG, value = "Kategorie description", example = "Lokomotiv", dataType = "String", paramType = "query"),
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
    @ApiOperation(code = 201, value = "Adds an Kategorie", response = IKategorie.class)
    public Response add(IKategorie entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.NAME_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates an Kategorie by name", response = IKategorie.class)
    public Response update(@PathParam(ApiPaths.NAME_PARAM_NAME) String name, IKategorie entity) {
        return super.update(name, entity);
    }

    @DELETE
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes an Kategorie by name")
    public Response delete(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.delete(name);
    }

    @GET
    @Path(ApiPaths.UNTER_KATEGORIE_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds an UnterKategorie by kategorie and name", response = IUnterKategorie.class)
    public Response get(@PathParam(ApiPaths.KATEGORIE_PARAM_NAME) String kategorieStr,
            @PathParam(ApiPaths.UNTER_KATEGORIE_PARAM_NAME) String unterKategorieStr) {
        try {
            IKategorie kategorie = findKategorie(kategorieStr, true);

            if (kategorie == null) {
                return getResponse(badRequest(null, "Kategorie " + kategorieStr + " does not exist"));
            }

            IUnterKategorie unterKategorie = findUnterKategorie(kategorieStr, unterKategorieStr, false);

            if (unterKategorie != null) {
                return getResponse(ok(), unterKategorie, true, true);
            }

            return getResponse(notFound());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @POST
    @Path(ApiPaths.KATEGORIE_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Adds an UnterKategorie to a kategorie", response = IUnterKategorie.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response add(@PathParam(ApiPaths.KATEGORIE_PARAM_NAME) String kategorieStr, IUnterKategorie unterKategorie) {
        try {
            logPost(kategorieStr + "/" + unterKategorie);

            IKategorie kategorie = findKategorie(kategorieStr, true);

            if (kategorie == null) {
                return getResponse(badRequest(null, "Kategorie " + kategorieStr + " does not exist"));
            }

            unterKategorie.setDeleted(false);

            kategorie.addUnterKategorie(unterKategorie);

            getPersister().update(kategorie);

            return getResponse(created(), unterKategorie, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.UNTER_KATEGORIE_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(code = 202, value = "Updates an UnterKategorie by kategorie and name", response = IUnterKategorie.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response update(@PathParam(ApiPaths.KATEGORIE_PARAM_NAME) String kategorieStr,
            @PathParam(ApiPaths.UNTER_KATEGORIE_PARAM_NAME) String unterKategorieStr, IUnterKategorie newUnterKategorie) {
        try {
            logPut(kategorieStr + "/" + unterKategorieStr + ": " + newUnterKategorie);

            IKategorie kategorie = findKategorie(kategorieStr, false);

            if (kategorie == null) {
                return getResponse(badRequest(null, "Kategorie " + kategorieStr + " does not exist"));
            }

            IUnterKategorie unterKategorie = findUnterKategorie(kategorieStr, unterKategorieStr, false);

            if (unterKategorie == null) {
                return getResponse(badRequest(null, "Kategorie " + kategorieStr + " does not exist"));
            } else if (newUnterKategorie.getKategorie() == null) {
                newUnterKategorie.setKategorie(kategorie);
            } else if (!newUnterKategorie.getKategorie().equals(kategorie)) {
                // Attempt to change kategorie not allowed
                return getResponse(badRequest(null, "You cannot change the kategorie for an unterkategorie, create a new one"));
            }

            unterKategorie = getUnterKategoriePersister().update(newUnterKategorie);

            return getResponse(accepted(), unterKategorie, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @DELETE
    @Path(ApiPaths.UNTER_KATEGORIE_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(code = 204, value = "Deletes an UnterKategorie by kategorie and name")
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response delete(@PathParam(ApiPaths.KATEGORIE_PARAM_NAME) String kategorieStr,
            @PathParam(ApiPaths.UNTER_KATEGORIE_PARAM_NAME) String unterKategorieStr) {
        try {
            IKategorie kategorie = findKategorie(kategorieStr, true);

            if (kategorie == null) {
                return getResponse(badRequest(null, "Kategorie " + kategorieStr + " does not exist"));
            }

            IUnterKategorie unterKategorie = findUnterKategorie(kategorie, unterKategorieStr, true);

            if (unterKategorie == null) {
                return getResponse(badRequest(null, "UnterKategorie " + kategorieStr + "/" + unterKategorieStr + " does not exist"));
            }

            kategorie.removeUnterKategorie(unterKategorie);

            //getUnterKategoriePersister().delete(unterKategorie);

            getPersister().update(kategorie);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Path(ApiPaths.UNTER_KATEGORIEN_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds UnterKategorieen by kategorie", response = IUnterKategorie.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam( name = ApiNames.KATEGORIE, value = "List of Kategorie names", dataType = "[Ljava.lang.String;", paramType = "query"),
            @ApiImplicitParam( name = ApiNames.PAGE_NUMBER, value = "0 based page number for paged queries", example = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam( name = ApiNames.PAGE_SIZE, value = "Page size for paged queries", example = "10", dataType = "Integer", paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Response search(@Context UriInfo info, @QueryParam(ApiNames.KATEGORIE) final List<String> kategorien, @QueryParam(ApiNames.PAGE_NUMBER) Integer pageNumber, @QueryParam(ApiNames.PAGE_SIZE) Integer pageSize) {
        try {
            Integer maxPage = null;
            Map<String,List<String>> references = new HashMap<>(1);

            if (kategorien != null && !kategorien.isEmpty()) {
                references.put(DBNames.KATEGORIE, kategorien);
            }

            final IUnterKategorie template = new UnterKategorie();

            if (pageNumber != null || pageSize != null) {
                pageNumber = pageNumber != null ? pageNumber : FIRST_PAGE;
                pageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;

                Long rowCount = getUnterKategoriePersister().countAll(template, references);

                maxPage = new Double(Math.floor(rowCount.doubleValue() / pageSize.doubleValue())).intValue();
            }

            List<IUnterKategorie> entities = new ArrayList<>(getUnterKategoriePersister().findAll(template, references, pageNumber, pageSize));

            if (entities.isEmpty()) {
                return getResponse(noContent());
            }

            List<Link> navigation = getNavLinks(info, pageNumber, pageSize, maxPage);

            return getResponse(ok(), new ArrayList<>(entities), true, true, navigation);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    private IPersister<IUnterKategorie> getUnterKategoriePersister() {
        return unterKategoriePersister;
    }
}
