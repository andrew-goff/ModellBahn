package com.linepro.modellbahn.rest.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IKupplung;
import com.linepro.modellbahn.model.ILicht;
import com.linepro.modellbahn.model.IMotorTyp;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.IWahrung;
import com.linepro.modellbahn.model.impl.Artikel;
import com.linepro.modellbahn.model.keys.ArtikelKey;
import com.linepro.modellbahn.model.enums.Status;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.AbstractItemService;
import com.linepro.modellbahn.rest.util.AcceptableMediaTypes;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.rest.util.FileUploadHandler;
import com.linepro.modellbahn.rest.util.IFileUploadHandler;
import com.linepro.modellbahn.util.StaticContentFinder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ArtikelService. CRUD service for Artikel
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.ARTIKEL)
@Path(ApiPaths.ARTIKEL)
public class ArtikelService extends AbstractItemService<ArtikelKey, IArtikel> {

    public ArtikelService() {
        super(IArtikel.class);
    }

    @GET
    @Path(ApiPaths.ARTIKEL_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds an Artikel by id", response = IArtikel.class)
    public Response get(@PathParam(ApiPaths.ARTIKEL_ID_PARAM_NAME) String id) {
        return super.get(new ArtikelKey(id));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Artikeln by example", response = IArtikel.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "Artikel id", dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.PRODUKT, value = "Artikel manufacturer", example = "[\"MARKLIN\",\"3000\"]", dataType = "[Ljava.lang.String;", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.KAUFDATUM, value = "Artikel purchase date", example = "1967-02-15", dataType = "java.time.LocalDate", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.WAHRUNG, value = "Artikel currency", example = "HKD", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.PREIS, value = "Artikel price", example = "110.50", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.STUCK, value = "Artikel count", example = "1", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.STEUERUNG, value = "Artikel control method code", example = "FRU", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.MOTOR_TYP, value = "Artikel motor type code", example = "SFCM", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.LICHT, value = "Artikel light configuration code", example = "L1K", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.KUPPLUNG, value = "Artikel coupling configuration code", example = "RELEX", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DECODER, value = "Artikel decoderId", example = "00001", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.NAMEN, value = "Artikel code", example = "00001", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BEZEICHNUNG, value = "Artikel description", example = "Aus set", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ANMERKUNG, value = "Artikel remarks", dataType = "String", example = "My favorite", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BELADUNG, value = "Artikel load", dataType = "String", example = "Holz", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.STATUS, value = "Artikel status", dataType = "String", example = "GEKAUFT", paramType = "query"),
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
    @ApiOperation(code = 201, value = "Adds an Artikel", response = IArtikel.class)
    public Response add(IArtikel entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.ARTIKEL_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates an Artikel by id", response = IArtikel.class)
    public Response update(@PathParam(ApiPaths.ARTIKEL_ID_PARAM_NAME) String artikelId, IArtikel entity) {
        return super.update(new ArtikelKey(artikelId), entity);
    }

    @DELETE
    @Path(ApiPaths.ARTIKEL_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes an Artikel by id")
    public Response delete(@PathParam(ApiPaths.ARTIKEL_ID_PARAM_NAME) String artikelId) {
        return super.delete(new ArtikelKey(artikelId));
    }

    @PUT
    @Path(ApiPaths.ARTIKEL_ABBILDUNG)
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Adds or updates the image for a named Artikel", response = IArtikel.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response updateAbbildung(@PathParam(ApiPaths.ARTIKEL_ID_PARAM_NAME) String artikelId,
                                    @FormDataParam(ApiPaths.MULTIPART_FILE_DETAIL) FormDataContentDisposition fileDetail,
                                    @FormDataParam(ApiPaths.MULTIPART_FILE_DATA) InputStream fileData) {
        IFileUploadHandler handler = new FileUploadHandler();

        try {
            if (!handler.isAcceptable(fileDetail, fileData, AcceptableMediaTypes.IMAGES)) {
                return getResponse(badRequest(null, "Invalid file '" + fileDetail.getFileName() + "'"));
            }

            IArtikel artikel = findArtikel(artikelId, false);

            if (artikel != null) {
                java.nio.file.Path file = handler.upload(ApiNames.ARTIKEL, new String[] { artikelId }, fileDetail, fileData);

                artikel.setAbbildung(file);

                getPersister().update(artikel);

                return getResponse(ok(artikel));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }

    @DELETE
    @Path(ApiPaths.ABBILDUNG_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Deletes the image for a named Artikel", response = IArtikel.class)
    @ApiResponses({
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response deleteAbbildung(@PathParam(ApiPaths.ID_PARAM_NAME) String artikelId) {
        try {
            IArtikel artikel = findArtikel(artikelId, false);

            if (artikel != null && artikel.getAbbildung() != null) {
                StaticContentFinder.getStore().removeFile(artikel.getAbbildung());

                artikel.setAbbildung(null);

                getPersister().update(artikel);

                return getResponse(ok(artikel));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }
}
