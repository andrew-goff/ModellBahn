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
import com.linepro.modellbahn.model.IAchsfolg;
import com.linepro.modellbahn.model.IAufbau;
import com.linepro.modellbahn.model.IBahnverwaltung;
import com.linepro.modellbahn.model.IEpoch;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.IKupplung;
import com.linepro.modellbahn.model.ILicht;
import com.linepro.modellbahn.model.IMassstab;
import com.linepro.modellbahn.model.IMotorTyp;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.IProduktTeil;
import com.linepro.modellbahn.model.ISonderModell;
import com.linepro.modellbahn.model.ISpurweite;
import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.IVorbild;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.impl.Produkt;
import com.linepro.modellbahn.model.impl.ProduktTeil;
import com.linepro.modellbahn.model.impl.UnterKategorie;
import com.linepro.modellbahn.model.keys.ProduktKey;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.impl.StaticPersisterFactory;
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

/**
 * ProduktService.
 * CRUD service for Produkt
 * @author  $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.PRODUKT, description = "Produkt maintenance")
@Path(ApiPaths.PRODUKT)
public class ProduktService extends AbstractItemService<ProduktKey, IProdukt> {

    private final IPersister<IProduktTeil> teilPersister;
    
    public ProduktService() {
        super(IProdukt.class);

        teilPersister = StaticPersisterFactory.get().createPersister(IProduktTeil.class);
    }

    @GET
    @Path(ApiPaths.PRODUKT_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response get(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr) {
        try {
            return super.get(new ProduktKey(findHersteller(herstellerStr, false), bestellNr));
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Produkten by example", response = Produkt.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "Produkt id", dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.HERSTELLER, value = "Produkt manufacturer", example = "MARKLIN", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BESTELL_NR, value = "Produkt part number", example = "3000", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BEZEICHNUNG, value = "Produkt description", example = "Dampftenderlok BR 89.0", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.UNTER_KATEGORIE, value = "Category and subcategory", example = "[\"LOKOMOTIV\",\"DAMPF\"]", dataType = "[Ljava.lang.String;", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.MASSSTAB, value = "Produkt scale", example = "H0", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.SPURWEITE, value = "Produkt track gauge", example = "H0", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.EPOCH, value = "Produkt era", example = "III", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BAHNVERWALTUNG, value = "Produkt railway company", example = "DB", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.GATTUNG, value = "Produkt rolling stock class", example = "BR89.0", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BETREIBSNUMMER, value = "Produkt service number", example = "89 006", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BAUZEIT, value = "Produkt construction date", example = "1934-01-01", dataType = "java.time.LocalDate", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.VORBILD, value = "Produkt prototype", example = "BR89.0", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ACHSFOLG, value = "Produkt axle configuration", example = "C1HT", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ANMERKUNG, value = "Produkt remarks", example = "Aus set 29400", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.SONDERMODELL, value = "Produkt SonderModell", example = "MHI", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.AUFBAU, value = "Produkt construction", example = "LK", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.LICHT, value = "Produkt light configuration", example = "L1V", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.KUPPLUNG, value = "Produkt coupling configuration", example = "RELEX", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.STEUERUNG, value = "Produkt control method", example = "FRU", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DECODER_TYP, value = "Produkt decoder type", example = "[\"ESU\",\"62400\"]", dataType = "[Ljava.lang.String;", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.MOTOR_TYP, value = "Produkt motor type", example = "SFCM", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.LANGE, value = "Produkt length", example = "11.0", dataType = "Number", paramType = "query"),
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
    @ApiOperation(code = 201, value = "Adds a Produkt", response = Produkt.class)
    public Response add(Produkt entity) {
        try {
            return super.add(entity);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.PRODUKT_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response update(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr, Produkt entity) {
        try {
            return super.update(new ProduktKey(findHersteller(herstellerStr, false), bestellNr), entity);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @DELETE
    @Path(ApiPaths.PRODUKT_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a Produkt by hersteller and bestell nr")
    public Response delete(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr) {
        try {
            return super.delete(new ProduktKey(findHersteller(herstellerStr, false), bestellNr));
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @POST
    @Path(ApiPaths.PRODUKT_TEIL_PATH)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds a sub produkt  a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response addTeil(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr, @PathParam(ApiPaths.TEIL_HERSTELLER_PARAM_NAME) String teilHerstellerStr, @PathParam(ApiPaths.TEIL_BESTELL_NR_PARAM_NAME) String teilBestellNr) {
        try {
            logPost(herstellerStr + "/" + bestellNr + teilHerstellerStr + "/" + teilBestellNr);

            Produkt produkt = (Produkt) findProdukt(herstellerStr, bestellNr, true);

            if (produkt == null) {
                return getResponse(badRequest(null, "Produkt " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            
            Produkt teil =  (Produkt) findProdukt(teilHerstellerStr, teilBestellNr, true);

            if (teil == null) {
                return getResponse(badRequest(null, "Produkt " + teilHerstellerStr + "/" + teilBestellNr + " does not exist"));
            }

            // TODO: check for cycles

            IProduktTeil produktTeil = new ProduktTeil(null, produkt, teil, 1, false);

            produkt.addTeil(produktTeil);

            getPersister().update(produkt);

            return getResponse(created(), produktTeil, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.PRODUKT_TEIL_PATH)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a sub produkt a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response updateTeil(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr, @PathParam(ApiPaths.TEIL_HERSTELLER_PARAM_NAME) String teilHerstellerStr, @PathParam(ApiPaths.TEIL_BESTELL_NR_PARAM_NAME) String teilBestellNr) {
        try {
            logPut(herstellerStr + "/" + bestellNr + teilHerstellerStr + "/" + teilBestellNr);

            IProduktTeil produktTeil = findProduktTeil(herstellerStr, bestellNr, teilHerstellerStr, teilBestellNr, true);

            if (produktTeil == null) {
                return getResponse(badRequest(null, "ProduktTeil " + herstellerStr + "/" + bestellNr + teilHerstellerStr + "/" + teilBestellNr + " does not exist"));
            }

            @SuppressWarnings("unused")
            IProdukt produkt = produktTeil.getProdukt();

            // TODO: update produktTeil

            produktTeil = getTeilPersister().update(produktTeil);

            return getResponse(created(), produktTeil, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @DELETE
    @Path(ApiPaths.PRODUKT_TEIL_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a sub produkt for a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response deleteTeil(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr, @PathParam(ApiPaths.TEIL_HERSTELLER_PARAM_NAME) String teilHerstellerStr, @PathParam(ApiPaths.TEIL_BESTELL_NR_PARAM_NAME) String teilBestellNr) {
        try {
            logDelete(herstellerStr + "/" + bestellNr + teilHerstellerStr + "/" + teilBestellNr);

            IProduktTeil produktTeil = findProduktTeil(herstellerStr, bestellNr, teilHerstellerStr, teilBestellNr, true);

            if (produktTeil == null) {
                return getResponse(badRequest(null, "ProduktTeil " + herstellerStr + "/" + bestellNr + teilHerstellerStr + "/" + teilBestellNr + " does not exist"));
            }

            Produkt produkt = (Produkt) produktTeil.getProdukt();

            produkt.removeTeil(produktTeil);

            //getProduktTeilPersister().delete(produktTeil);

            getPersister().update(produkt);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.PRODUKT_ABBILDUNG)
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds or updates the picture for a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response updateAbbildung(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
                                    @FormDataParam(ApiPaths.MULTIPART_FILE_DETAIL) FormDataContentDisposition fileDetail,
                                    @FormDataParam(ApiPaths.MULTIPART_FILE_DATA) InputStream fileData) {
        IFileUploadHandler handler = new FileUploadHandler();

        try {
            if (!handler.isAcceptable(fileDetail, fileData, AcceptableMediaTypes.IMAGES)) {
                return getResponse(badRequest(null, "Invalid file '" + fileDetail.getFileName() + "'"));
            }

            IProdukt produkt = findProdukt(herstellerStr, bestellNr, false);

            if (produkt != null) {
                java.nio.file.Path file = handler.upload(ApiNames.PRODUKT, new String[] { herstellerStr, bestellNr }, fileDetail, fileData);

                produkt.setAbbildung(file);

                getPersister().update(produkt);

                return getResponse(ok(produkt));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }

    @DELETE
    @Path(ApiPaths.PRODUKT_ABBILDUNG)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes the picture for a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response deleteAbbildung(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr) {
        try {
            IProdukt produkt = findProdukt(herstellerStr, bestellNr, false);

            if (produkt != null && produkt.getAbbildung() != null) {
                StaticContentFinder.getStore().removeFile(produkt.getAbbildung());

                produkt.setAbbildung(null);

                getPersister().update(produkt);

                return getResponse(ok(produkt));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }

    @PUT
    @Path(ApiPaths.PRODUKT_ANLEITUNGEN)
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds or updates the instructions for a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response updateAnleitungen(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
                                      @FormDataParam(ApiPaths.MULTIPART_FILE_DETAIL) FormDataContentDisposition fileDetail,
                                    @FormDataParam(ApiPaths.MULTIPART_FILE_DATA) InputStream fileData) {
        IFileUploadHandler handler = new FileUploadHandler();

        try {
            if (!handler.isAcceptable(fileDetail, fileData, AcceptableMediaTypes.DOCUMENTS)) {
                return getResponse(badRequest(null, "Invalid file '" + fileDetail.getFileName() + "'"));
            }

            IProdukt produkt = findProdukt(herstellerStr, bestellNr, false);

            if (produkt != null) {
                java.nio.file.Path file = handler.upload(ApiNames.PRODUKT, new String[] { herstellerStr, bestellNr }, fileDetail, fileData);

                produkt.setAnleitungen(file);

                getPersister().update(produkt);

                return getResponse(ok(produkt));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }

    @DELETE
    @Path(ApiPaths.PRODUKT_ANLEITUNGEN)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes the instructions for a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response deleteAnleitungen(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr) {
        try {
            IProdukt produkt = findProdukt(herstellerStr, bestellNr, false);

            if (produkt != null && produkt.getAbbildung() != null) {
                StaticContentFinder.getStore().removeFile(produkt.getAnleitungen());

                produkt.setAnleitungen(null);

                getPersister().update(produkt);

                return getResponse(ok(produkt));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }

    @PUT
    @Path(ApiPaths.PRODUKT_EXPLOSIONSZEICHNUNG)
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds or updates the drawing for a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response updateExplosionszeichnung(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
                                              @FormDataParam(ApiPaths.MULTIPART_FILE_DETAIL) FormDataContentDisposition fileDetail,
                                    @FormDataParam(ApiPaths.MULTIPART_FILE_DATA) InputStream fileData) {
        IFileUploadHandler handler = new FileUploadHandler();

        try {
            if (!handler.isAcceptable(fileDetail, fileData, AcceptableMediaTypes.DOCUMENTS)) {
                return getResponse(badRequest(null, "Invalid file '" + fileDetail.getFileName() + "'"));
            }

            IProdukt produkt = findProdukt(herstellerStr, bestellNr, false);

            if (produkt != null) {
                java.nio.file.Path file = handler.upload(ApiNames.PRODUKT, new String[] { herstellerStr, bestellNr }, fileDetail, fileData);

                produkt.setExplosionszeichnung(file);

                getPersister().update(produkt);

                return getResponse(ok(produkt));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }

    @DELETE
    @Path(ApiPaths.PRODUKT_EXPLOSIONSZEICHNUNG)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes the drawing for a Produkt by hersteller and bestell nr", response = Produkt.class)
    public Response deleteExplosionszeichnung(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr) {
        try {
            IProdukt produkt = findProdukt(herstellerStr, bestellNr, false);

            if (produkt != null && produkt.getAbbildung() != null) {
                StaticContentFinder.getStore().removeFile(produkt.getExplosionszeichnung());

                produkt.setExplosionszeichnung(null);

                getPersister().update(produkt);

                return getResponse(ok(produkt));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }

    private IPersister<IProduktTeil> getTeilPersister() {
        return teilPersister;
    }
}
