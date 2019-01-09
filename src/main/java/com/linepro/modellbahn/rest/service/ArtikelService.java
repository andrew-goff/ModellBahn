package com.linepro.modellbahn.rest.service;

import com.linepro.modellbahn.persistence.IDecoderPersister;
import com.linepro.modellbahn.persistence.INamedItemPersister;
import com.linepro.modellbahn.persistence.IProduktPersister;
import com.linepro.modellbahn.persistence.impl.StaticPersisterFactory;
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

import com.linepro.modellbahn.model.util.Status;
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
import org.hibernate.annotations.Persister;

/**
 * ArtikelService. CRUD service for Artikel
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.ARTIKEL, description = "Artikel maintenance")
@Path(ApiPaths.ARTIKEL)
public class ArtikelService extends AbstractItemService<IArtikel> {

    protected final IProduktPersister produktPersister;
    protected final INamedItemPersister<IWahrung> wahrungPersister;
    protected final INamedItemPersister<ISteuerung> steuerungPersister;
    protected final INamedItemPersister<IMotorTyp> motorTypPersister;
    protected final INamedItemPersister<ILicht> lichtPersister;
    protected final INamedItemPersister<IKupplung> kupplungPersister;
    protected final IDecoderPersister decoderPersister;

    public ArtikelService() {
        super(Artikel.class);
        produktPersister = (IProduktPersister) StaticPersisterFactory.get().createPersister(IProdukt.class);
        wahrungPersister = (INamedItemPersister<IWahrung>) StaticPersisterFactory.get().createPersister(IWahrung.class);
        steuerungPersister = (INamedItemPersister<ISteuerung>) StaticPersisterFactory.get().createPersister(ISteuerung.class);
        motorTypPersister = (INamedItemPersister<IMotorTyp>) StaticPersisterFactory.get().createPersister(ISteuerung.class);
        lichtPersister = (INamedItemPersister<ILicht>) StaticPersisterFactory.get().createPersister(ILicht.class);
        kupplungPersister = (INamedItemPersister<IKupplung>) StaticPersisterFactory.get().createPersister(IKupplung.class);
        decoderPersister = (IDecoderPersister) StaticPersisterFactory.get().createPersister(IDecoder.class);
    }

    @JsonCreator
    public Artikel create(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
            @JsonProperty(value = ApiNames.BESTELL_NR) String bestellNr,
            @JsonProperty(value = ApiNames.KAUFDATUM) LocalDate kaufdatum,
            @JsonProperty(value = ApiNames.WAHRUNG) String wahrungStr,
            @JsonProperty(value = ApiNames.PREIS) BigDecimal preis,
            @JsonProperty(value = ApiNames.STUCK) Integer stuck,
            @JsonProperty(value = ApiNames.STEUERUNG) String steuerungStr,
            @JsonProperty(value = ApiNames.MOTOR_TYP) String motorTypStr,
            @JsonProperty(value = ApiNames.LICHT) String lichtStr,
            @JsonProperty(value = ApiNames.KUPPLUNG) String kupplungStr,
            @JsonProperty(value = ApiNames.DECODER) String decoderId,
            @JsonProperty(value = ApiNames.NAMEN) String artikelNr,
            @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
            @JsonProperty(value = ApiNames.ANMERKUNG) String anmerkung,
            @JsonProperty(value = ApiNames.BELADUNG) String beladung,
            @JsonProperty(value = ApiNames.ABBILDUNG) String abbildungStr,
            @JsonProperty(value = ApiNames.STATUS) String statusStr,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
        IProdukt produkt = produktPersister.findByKey(herstellerStr, bestellNr, false);
        IWahrung wahrung = wahrungPersister.findByKey(wahrungStr, false);
        ISteuerung steuerung = steuerungPersister.findByKey(steuerungStr, false);
        IMotorTyp motorTyp = motorTypPersister.findByKey(motorTypStr, false);
        ILicht licht = lichtPersister.findByKey(lichtStr, false);
        IKupplung kupplung = kupplungPersister.findByKey(kupplungStr, false);
        IDecoder decoder = decoderPersister.findByKey(decoderId, false);
        Status status = Status.valueOf(statusStr);
        
        Artikel entity = new Artikel(id, produkt, kaufdatum, wahrung, preis, stuck,
                steuerung, motorTyp, licht, kupplung, decoder,
                artikelNr, bezeichnung, anmerkung,
                beladung, status, deleted);

        debug("created: " + entity);

       return entity;
    }

    @GET
    @Path(ApiPaths.ARTIKEL_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds an Artikel by id", response = Artikel.class)
    public Response get(@PathParam(ApiPaths.ARTIKEL_ID_PARAM_NAME) String artikelId) {
        return super.get(artikelId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Artikeln by example", response = Artikel.class, responseContainer = "List")
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
        @ApiImplicitParam( name = ApiNames.ANMERKUNG, value = "Artikel remarks", dataType = "String", example = "", paramType = "query"),
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
    @ApiOperation(code = 201, value = "Adds an Artikel", response = Artikel.class)
    public Response add(Artikel entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.ARTIKEL_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates an Artikel by id", response = Artikel.class)
    public Response update(@PathParam(ApiPaths.ARTIKEL_ID_PARAM_NAME) String artikelId, Artikel entity) {
        return super.update(artikelId, entity);
    }

    @DELETE
    @Path(ApiPaths.ARTIKEL_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes an Artikel by id")
    public Response delete(@PathParam(ApiPaths.ARTIKEL_ID_PARAM_NAME) String artikelId) {
        return super.delete(artikelId);
    }

    @PUT
    @Path(ApiPaths.ARTIKEL_ABBILDUNG)
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Adds or updates the image for a named Artikel", response = Artikel.class)
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

            IArtikel artikel = getPersister().findByKey(artikelId, false);

            if (artikel != null) {
                java.nio.file.Path file = handler.upload(ApiNames.ARTIKEL, new String[] { artikelId }, fileDetail, fileData);

                artikel.setAbbildung(file);

                getPersister().update((Artikel) artikel);

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
    @ApiOperation(value = "Deletes the image for a named Artikel", response = Artikel.class)
    @ApiResponses({
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response deleteAbbildung(@PathParam(ApiPaths.ID_PARAM_NAME) String artikelId) {
        try {
            IArtikel artikel = getPersister().findByKey(artikelId, false);

            if (artikel != null && artikel.getAbbildung() != null) {
                StaticContentFinder.getStore().removeFile(artikel.getAbbildung());

                artikel.setAbbildung(null);

                getPersister().update((Artikel) artikel);

                return getResponse(ok(artikel));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }
}
