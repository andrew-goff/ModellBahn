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
import com.linepro.modellbahn.model.IKupplung;
import com.linepro.modellbahn.model.impl.Kupplung;
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
 * KupplungService. CRUD service for Kupplung
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.KUPPLUNG)
@Path(ApiPaths.KUPPLUNG)
public class KupplungService extends AbstractItemService<NameKey, IKupplung> {

    public KupplungService() {
        super(IKupplung.class);
    }

    @JsonCreator(mode= Mode.DELEGATING)
    public static Kupplung create() {
        return new Kupplung();
    }
    
    @JsonCreator(mode= Mode.PROPERTIES)
    public static Kupplung create(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiNames.NAMEN) String name,
            @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
        return new Kupplung(id, name, bezeichnung, deleted);
    }

    @Override
    @GET
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a Kupplung by name", response = IKupplung.class)
    public Response get(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.get(name);
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Kupplungen by example", response = IKupplung.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "Kupplung id", dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.NAMEN, value = "Kupplung code", example = "RELEX", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BEZEICHNUNG, value = "Kupplung description", example = "Relex", dataType = "String", paramType = "query"),
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
    @ApiOperation(code = 201, value = "Adds a Kupplung", response = IKupplung.class)
    public Response add(Kupplung entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.NAME_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a Kupplung by name", response = IKupplung.class)
    public Response update(@PathParam(ApiPaths.NAME_PARAM_NAME) String name, Kupplung entity) {
        return super.update(name, entity);
    }

    @Override
    @DELETE
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a Kupplung by name")
    public Response delete(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        return super.delete(name);
    }

    @PUT
    @Path(ApiPaths.ABBILDUNG_PART)
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds or updates the picture for a named Kupplung", response = IKupplung.class)
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

            IKupplung kupplung = findKupplung(name, false);

            if (kupplung != null) {
                java.nio.file.Path file = handler.upload(ApiNames.KUPPLUNG, new String[] { name }, contentDispositionHeader, fileInputStream);

                kupplung.setAbbildung(file);

                getPersister().update(kupplung);

                return getResponse(ok(), kupplung, true);
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
    @ApiOperation(code = 204, value = "Deletes the picture from a named Kupplung", response = IKupplung.class)
    public Response deleteAbbildung(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        logDelete(String.format(ApiPaths.ABBILDUNG_LOG, getEntityClassName(), name));
        
        try {
            IKupplung kupplung = findKupplung(name, false);

            if (kupplung != null && kupplung.getAbbildung() != null) {
                StaticContentFinder.getStore().removeFile(kupplung.getAbbildung());

                kupplung.setAbbildung(null);

                getPersister().update(kupplung);

                return getResponse(ok(), kupplung, true);
            }
        } catch (Exception e) {
            return getResponse(e);
        }

        return getResponse(notFound());
    }
}
