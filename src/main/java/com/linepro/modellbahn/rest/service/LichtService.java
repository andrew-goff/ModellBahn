package com.linepro.modellbahn.rest.service;

import java.io.InputStream;

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

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.linepro.modellbahn.model.ILicht;
import com.linepro.modellbahn.model.impl.Licht;
import com.linepro.modellbahn.model.keys.NameKey;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.AbstractItemService;
import com.linepro.modellbahn.rest.util.AcceptableMediaTypes;
import com.linepro.modellbahn.rest.util.ApiMessages;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.rest.util.FileUploadHandler;
import com.linepro.modellbahn.rest.util.IFileUploadHandler;
import com.linepro.modellbahn.util.StaticContentFinder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * LichtService.
 * CRUD service for Licht
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.LICHT)
@Path(ApiPaths.LICHT)
public class LichtService extends AbstractItemService<NameKey, ILicht> {

    public LichtService() {
        super(ILicht.class);
    }

    @JsonCreator(mode= Mode.DELEGATING)
    public static Licht create() {
        return new Licht();
    }
    
    @JsonCreator(mode= Mode.PROPERTIES)
    public static Licht create(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiPaths.NAME_PARAM_NAME) String name,
            @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
        return new Licht(id, name, bezeichnung, deleted);
    }

    @Override
    @GET
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a Licht by name", response = ILicht.class)
    public Response get(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.get(name);
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Lichten by example", response = ILicht.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = ApiNames.ID, value = "Licht id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.NAMEN, value = "Licht code", example = "L1V", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.BEZEICHNUNG, value = "Licht description", example = "Einfach-Spitzensignal vorne", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.DELETED, value = "If true search for soft deleted items", example = "false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.PAGE_NUMBER, value = "0 based page number for paged queries", example = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.PAGE_SIZE, value = "Page size for paged queries", example = "10", dataType = "Integer", paramType = "query")})
    public Response search(@Context UriInfo uriInfo) {
        return super.search(uriInfo);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds a Licht", response = ILicht.class)
    public Response add(Licht entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.NAME_PART)
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a Licht by name", response = ILicht.class)
    public Response update(@PathParam(ApiPaths.NAME_PARAM_NAME) String name, Licht entity) {
        return super.update(name, entity);
    }

    @Override
    @DELETE
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a Licht by name")
    public Response delete(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.delete(name);
    }

    @PUT
    @Path(ApiPaths.ABBILDUNG_PART)
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds or updates the picture for a named Licht", response = ILicht.class)
    public Response updateAbbildung(@PathParam(ApiPaths.NAME_PARAM_NAME) String name,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader,  
            @FormDataParam("file") FormDataBodyPart body) {
        logPut(String.format(ApiPaths.ABBILDUNG_LOG, getEntityClassName(), name) + ": " + contentDispositionHeader.getFileName());

        IFileUploadHandler handler = new FileUploadHandler();

        try {
            if (!handler.isAcceptable(body, AcceptableMediaTypes.IMAGE_TYPES)) {
                return getResponse(badRequest(getMessage(ApiMessages.INVALID_FILE, contentDispositionHeader.getFileName())));
            }

            ILicht licht = findLicht(name, false);

            if (licht != null) {
                java.nio.file.Path file = handler.upload(ApiNames.LICHT, new String[] {name}, contentDispositionHeader, fileInputStream);

                licht.setAbbildung(file);

                getPersister().update(licht);

                return getResponse(ok(), licht, true);
            }
        } catch (Exception e) {
            return getResponse(e);
        }

        return getResponse(notFound());
    }

    @DELETE
    @Path(ApiPaths.ABBILDUNG_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes the picture for a named Licht", response = ILicht.class)
    public Response deleteAbbildung(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        logDelete(String.format(ApiPaths.ABBILDUNG_LOG, getEntityClassName(), name));
        
        try {
            ILicht licht = findLicht(name, false);

            if (licht != null && licht.getAbbildung() != null) {
                StaticContentFinder.getStore().removeFile(licht.getAbbildung());

                licht.setAbbildung(null);

                getPersister().update(licht);

                return getResponse(ok(), licht, true);
            }
        } catch (Exception e) {
            return getResponse(e);
        }

        return getResponse(notFound());
    }
}
