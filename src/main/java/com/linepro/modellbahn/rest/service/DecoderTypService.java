package com.linepro.modellbahn.rest.service;

import com.linepro.modellbahn.persistence.IDecoderTypAdressPersister;
import com.linepro.modellbahn.persistence.IDecoderTypCVPersister;
import com.linepro.modellbahn.persistence.IDecoderTypFunktionPersister;
import com.linepro.modellbahn.persistence.INamedItemPersister;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypAdress;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.IProtokoll;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.impl.DecoderTypAdress;
import com.linepro.modellbahn.model.impl.DecoderTypCV;
import com.linepro.modellbahn.model.impl.DecoderTypFunktion;

import com.linepro.modellbahn.model.util.AdressTyp;
import com.linepro.modellbahn.model.util.Konfiguration;
import com.linepro.modellbahn.persistence.impl.StaticPersisterFactory;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.AbstractItemService;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * DecoderTypService. CRUD service for DecoderTyp, DecoderTypCV and DecoderTypFunktion
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.DECODER_TYP, description = "DecoderTyp maintenance")
@Path(ApiPaths.DECODER_TYP)
public class DecoderTypService extends AbstractItemService<IDecoderTyp> {

    private final INamedItemPersister<IHersteller> herstellerPersister;

    private final INamedItemPersister<IProtokoll> protokollPersister;

    private final IDecoderTypAdressPersister adressPersister;

    private final IDecoderTypCVPersister cvPersister;

    private final IDecoderTypFunktionPersister funktionPersister;

    public DecoderTypService() {
        super(DecoderTyp.class);

        herstellerPersister = StaticPersisterFactory.get().createPersister(IHersteller.class);
        protokollPersister = StaticPersisterFactory.get().createPersister(IProtokoll.class);

        adressPersister = StaticPersisterFactory.get().createPersister(IDecoderTypAdress.class);
        cvPersister = StaticPersisterFactory.get().createPersister(IDecoderTypCV.class);
        funktionPersister = StaticPersisterFactory.get().createPersister(IDecoderTypFunktion.class);
    }

    @JsonCreator
    public DecoderTyp create(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
            @JsonProperty(value = ApiNames.PROTOKOLL) String protokollStr,
            @JsonProperty(value = ApiNames.NAMEN) String name,
            @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
            @JsonProperty(value = ApiNames.GERAUSCH) Boolean sound,
            @JsonProperty(value = ApiNames.KONFIGURATION) String konfigurationStr,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
        IHersteller hersteller = getHerstellerPersister().findByKey(herstellerStr, false);
        IProtokoll protokoll = getProtokollPersister().findByKey(protokollStr, false);
        Konfiguration konfiguration = Konfiguration.valueOf(konfigurationStr);

        DecoderTyp entity = new DecoderTyp(id, hersteller, protokoll, name, bezeichnung, sound, konfiguration,
                deleted);

        debug("created: " + entity);

        return entity;
    }

    @JsonCreator
    public DecoderTypAdress createAdress(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
            @JsonProperty(value = ApiNames.BESTELL_NR) String bestellNr,
            @JsonProperty(value = ApiNames.INDEX) Integer index,
            @JsonProperty(value = ApiNames.ADRESS_TYP) String adressTypStr,
            @JsonProperty(value = ApiNames.SPAN) Integer span,
            @JsonProperty(value = ApiNames.WERKSEINSTELLUNG) Integer werkeinstellung,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
        IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);
        AdressTyp adressTyp = AdressTyp.valueOf(adressTypStr);
        
        DecoderTypAdress entity = new DecoderTypAdress(id, decoderTyp, index, adressTyp, span, werkeinstellung, deleted);

        debug("created: " + entity);

        return entity;
    }

    @JsonCreator
    public DecoderTypCV createCV(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
            @JsonProperty(value = ApiNames.BESTELL_NR) String bestellNr,
            @JsonProperty(value = ApiNames.NAMEN) String name,
            @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
            @JsonProperty(value = ApiNames.CV) Integer cv,
            @JsonProperty(value = ApiNames.MINIMAL) Integer max,
            @JsonProperty(value = ApiNames.MAXIMAL) Integer min,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
        IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);

        DecoderTypCV entity = new DecoderTypCV(id, decoderTyp, cv, bezeichnung, cv, min, max, deleted);

        debug("created: " + entity);

        return entity;
    }

    @JsonCreator
    public DecoderTypFunktion createFunktion(@JsonProperty(value = ApiNames.ID) Long id,
            @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
            @JsonProperty(value = ApiNames.BESTELL_NR) String bestellNr,
            @JsonProperty(value = ApiNames.REIHE) Integer reihe,
            @JsonProperty(value = ApiNames.NAMEN) String funktion,
            @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
            @JsonProperty(value = ApiNames.PROGRAMMABLE) Boolean programmable,
            @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
        IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);

        DecoderTypFunktion entity = new DecoderTypFunktion(id, decoderTyp, reihe, funktion, bezeichnung, programmable,
                deleted);

        debug("created: " + entity);

        return entity;
    }

    @GET
    @Path(ApiPaths.DECODER_TYP_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a DecoderTyp by hersteller and bestell nr", response = DecoderTyp.class)
    public Response get(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr,
            @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr) {
        try {
            return super.get(herstellerStr, bestellNr);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds DecoderTypen by example", response = DecoderTyp.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "Decoder id", dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DECODER_ID, value = "Decoder code", example = "00001", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DECODER_TYP, value = "Decoder type", example = "[\"ESU\",\"62400\"]", dataType = "[Ljava.lang.String;", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BEZEICHNUNG, value = "Decoder description", example = "LokSound M4", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.PROTOKOLL, value = "Decoder protocoll code", example = "MFX", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.FAHRSTUFE, value = "Decoder speed steps", example = "27", dataType = "Integer", paramType = "query"),
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
    @ApiOperation(code = 201, value = "Adds a DecoderTyp", response = DecoderTyp.class)
    public Response add(DecoderTyp entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.DECODER_TYP_PATH)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code=202, value = "Updates a DecoderTyp by hersteller and bestell nr", response = DecoderTyp.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response update(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr,
            @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr, DecoderTyp entity) {
        try {
            return super.update(herstellerStr, bestellNr, entity);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @DELETE
    @Path(ApiPaths.DECODER_TYP_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a DecoderTyp by hersteller and bestell nr", response = DecoderTyp.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response delete(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr,
            @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr) {
        try {
            return super.delete(herstellerStr, bestellNr);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Path(ApiPaths.DECODER_TYP_ADRESS_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a DecoderTypAdress by hersteller and bestell nr", response = DecoderTypAdress.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response getAdress(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.INDEX_PARAM_NAME) Integer index) {
        try {
            IDecoderTypAdress decoderTypAdress = getAdressPersister()
                .findByKey(herstellerStr, bestellNr, index, true);

            if (decoderTypAdress != null) {
                return getResponse(ok(), decoderTypAdress, true, true);
            }

            return getResponse(notFound());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @POST
    @Path(ApiPaths.DECODER_TYP_ADRESS_ROOT)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds a Adress to a DecoderTyp", response = DecoderTypAdress.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response addAdress(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr, DecoderTypAdress decoderTypAdress) {
        try {
            logPost(herstellerStr + "/" + bestellNr + "/" + decoderTypAdress);

            IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);

            if (decoderTyp == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            decoderTypAdress.setDeleted(false);

            decoderTyp.addAdress(decoderTypAdress);

            getPersister().update(decoderTyp);

            return getResponse(created(), decoderTypAdress, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.DECODER_TYP_ADRESS_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a DecoderTypAdress by hersteller and bestell nr", response = DecoderTypAdress.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response updateAdress(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.INDEX_PARAM_NAME) Integer index, DecoderTypAdress newDecoderTypAdress) {
        try {
            logPut(herstellerStr + "/" + bestellNr + "/" + index + ": " + newDecoderTypAdress);

            IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);

            if (decoderTyp == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            IDecoderTypAdress decoderTypAdress = getAdressPersister()
                .findByKey(herstellerStr, bestellNr, index, true);

            if (decoderTypAdress == null) {
                return getResponse(badRequest(null, "DecoderTypAdress " + herstellerStr + "/" + bestellNr + " does not exist"));
            } else if (newDecoderTypAdress.getDecoderTyp() == null) {
                newDecoderTypAdress.setDecoderTyp(decoderTyp);
            } else if (!newDecoderTypAdress.getDecoderTyp().equals(decoderTyp)) {
                // Attempt to change decoderTyp not allowed
                return getResponse(badRequest(null, "You cannot change the decoderTyp for a decoderTypAdress, create a new one"));
            }

            decoderTypAdress = getAdressPersister().update(newDecoderTypAdress);

            return getResponse(accepted(), decoderTypAdress, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @DELETE
    @Path(ApiPaths.DECODER_TYP_ADRESS_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a DecoderTypAdress by hersteller and bestell nr", response = DecoderTypAdress.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response deleteAdress(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.INDEX_PARAM_NAME) Integer index) {
        try {
            IDecoderTypAdress decoderTypAdress = getAdressPersister()
                .findByKey(herstellerStr, bestellNr, index, true);

            if (decoderTypAdress == null) {
                return getResponse(badRequest(null, "DecoderTypAdress " + herstellerStr + "/" + bestellNr + "/" + index + " does not exist"));
            }

            IDecoderTyp decoderTyp = decoderTypAdress.getDecoderTyp();

            decoderTyp.removeAdress(decoderTypAdress);

            //getDecoderTypAdressPersister().delete(decoderTypAdress);

            getPersister().update(decoderTyp);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Path(ApiPaths.DECODER_TYP_CV_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a DecoderTypCV by hersteller and bestell nr", response = DecoderTypCV.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response getCV(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.CV_PARAM_NAME) Integer cv) {
        try {
            IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, true);

            if (decoderTyp == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            IDecoderTypCV decoderTypCV = getCvPersister()
                .findByKey(herstellerStr, bestellNr, cv, true);

            if (decoderTypCV != null) {
                return getResponse(ok(), decoderTypCV, true, true);
            }

            return getResponse(notFound());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @POST
    @Path(ApiPaths.DECODER_TYP_CV_ROOT)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a DecoderTypCV by hersteller and bestell nr", response = DecoderTypCV.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response addCV(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr, DecoderTypCV decoderTypCV) {
        try {
            logPost(herstellerStr + "/" + bestellNr + "/" + decoderTypCV);

            IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);

            if (decoderTyp == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            decoderTypCV.setDeleted(false);

            decoderTyp.addCV(decoderTypCV);

            getPersister().update(decoderTyp);

            return getResponse(created(), decoderTypCV, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.DECODER_TYP_CV_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a DecoderTypCV by hersteller and bestell nr", response = DecoderTypCV.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response updateCV(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.CV_PARAM_NAME) Integer cv, DecoderTypCV newDecoderTypCV) {
        try {
            logPut(herstellerStr + "/" + bestellNr + "/" + cv + ": " + newDecoderTypCV);

            IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);

            if (decoderTyp == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            IDecoderTypCV decoderTypCV = getCvPersister()
                .findByKey(herstellerStr, bestellNr, cv, true);

            if (decoderTypCV == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            } else if (newDecoderTypCV.getDecoderTyp() == null) {
                newDecoderTypCV.setDecoderTyp(decoderTyp);
            } else if (!newDecoderTypCV.getDecoderTyp().equals(decoderTyp)) {
                // Attempt to change decoderTyp not allowed
                return getResponse(badRequest(null, "You cannot change the decoderTyp for a decoderTypCV, create a new one"));
            }

            // Validate  0 < CV < 256 
            // Validate  0 <= Maximal <= 255
            // Validate  0 <= Minimal <= 255
            // Validate  0 <= Werkeinstelling <= 255
            // Validate  Minimal <= Maximal 
            // Validate bezeichnung unique by decoderTyp

            decoderTypCV = getCVPersister().update(newDecoderTypCV);

            return getResponse(accepted(), decoderTypCV, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @DELETE
    @Path(ApiPaths.DECODER_TYP_CV_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a DecoderTypCV by hersteller and bestell nr", response = DecoderTypCV.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response deleteCV(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.CV_PARAM_NAME) Integer cv) {
        try {
            IDecoderTypCV decoderTypCV = getCvPersister()
                .findByKey(herstellerStr, bestellNr, cv, true);

            if (decoderTypCV == null) {
                return getResponse(badRequest(null, "DecoderTypCV " + herstellerStr + "/" + bestellNr + "/" + cv + " does not exist"));
            }

            IDecoderTyp decoderTyp = decoderTypCV.getDecoderTyp();

            decoderTyp.removeCV(decoderTypCV);

            //getDecoderTypCVPersister().delete(decoderTypCV);

            getPersister().update(decoderTyp);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Path(ApiPaths.DECODER_TYP_FUNKTION_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a DecoderTypFunktion by hersteller and bestell nr", response = DecoderTypFunktion.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response getFunktion(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.REIHE_PARAM_NAME) Integer reihe, @PathParam(ApiPaths.FUNKTION_PARAM_NAME) String funktion) {
        try {
            IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, true);

            if (decoderTyp == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            IDecoderTypFunktion decoderTypFunktion = getFunktionPersister()
                .findByKey(herstellerStr, bestellNr, reihe, funktion, true);

            if (decoderTypFunktion != null) {
                return getResponse(ok(), decoderTypFunktion, true, true);
            }

            return getResponse(notFound());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @POST
    @Path(ApiPaths.DECODER_TYP_FUNKTION_ROOT)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a DecoderTypFunktion by hersteller and bestell nr", response = DecoderTypFunktion.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response addFunktion(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr, DecoderTypFunktion decoderTypFunktion) {
        try {
            logPost(herstellerStr + "/" + bestellNr + "/" + decoderTypFunktion);

            IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);

            if (decoderTyp == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            // Validate decoderTypFunktion like F\d{1,2}|K\d{1,2}|S[1-4]
            // Validate reihe [01]
            // Validate bezeichnung unique by reihe / decoderTyp
            decoderTypFunktion.setDeleted(false);

            decoderTyp.addFunktion(decoderTypFunktion);

            getPersister().update(decoderTyp);

            return getResponse(created(), decoderTypFunktion, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.DECODER_TYP_FUNKTION_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Finds a DecoderTypFunktion by hersteller and bestell nr", response = DecoderTypFunktion.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response updateFunktion(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.REIHE_PARAM_NAME) Integer reihe, @PathParam(ApiPaths.FUNKTION_PARAM_NAME) String funktion, DecoderTypFunktion newDecoderTypFunktion) {
        try {
            logPost(herstellerStr + "/" + bestellNr + "/" + reihe + "/" + funktion + ": " + newDecoderTypFunktion);

            IDecoderTyp decoderTyp = getPersister().findByKey(herstellerStr, bestellNr, false);

            if (decoderTyp == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            IDecoderTypFunktion decoderTypFunktion = getFunktionPersister()
                .findByKey(herstellerStr, bestellNr, reihe, funktion, true);

            if (decoderTypFunktion == null) {
                return getResponse(badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            } else if (newDecoderTypFunktion.getDecoderTyp() == null) {
                newDecoderTypFunktion.setDecoderTyp(decoderTyp);
            } else if (!newDecoderTypFunktion.getDecoderTyp().equals(decoderTyp)) {
                // Attempt to change decoderTyp not allowed
                return getResponse(badRequest(null, "You cannot change the decoderTyp for a decoderTypFunktion, create a new one"));
            }

            decoderTypFunktion = getFunktionPersister().update(newDecoderTypFunktion);

            return getResponse(accepted(), decoderTypFunktion, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @DELETE
    @Path(ApiPaths.DECODER_TYP_FUNKTION_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(code = 204, value = "Deletes a DecoderTypFunktion by hersteller and bestell nr", response = DecoderTypFunktion.class)
    @ApiResponses({
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 402, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
        })
    public Response deleteFunktion(@PathParam(ApiPaths.HERSTELLER_PARAM_NAME) String herstellerStr, @PathParam(ApiPaths.BESTELL_NR_PARAM_NAME) String bestellNr,
            @PathParam(ApiPaths.REIHE_PARAM_NAME) Integer reihe, @PathParam(ApiPaths.FUNKTION_PARAM_NAME) String funktion) {
        try {
            IDecoderTypFunktion decoderTypFunktion = getFunktionPersister()
                .findByKey(herstellerStr, bestellNr, reihe, funktion, true);

            if (decoderTypFunktion == null) {
                return getResponse(badRequest(null, "DecoderTypFunktion " + herstellerStr + "/" + bestellNr + "/" + reihe + "/" + funktion + " does not exist"));
            }

            IDecoderTyp decoderTyp = decoderTypFunktion.getDecoderTyp();

            decoderTyp.removeFunktion(decoderTypFunktion);

            //getDecoderTypFunktionPersister().delete(decoderTypFunktion);

            getPersister().update(decoderTyp);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    private IDecoderTypAdressPersister getAdressPersister() {
        return adressPersister;
    }

    private IDecoderTypCVPersister getCVPersister() {
        return getCvPersister();
    }

    private IDecoderTypFunktionPersister getFunktionPersister() {
        return funktionPersister;
    }

  public INamedItemPersister<IHersteller> getHerstellerPersister() {
    return herstellerPersister;
  }

  public INamedItemPersister<IProtokoll> getProtokollPersister() {
    return protokollPersister;
  }

  public IDecoderTypCVPersister getCvPersister() {
    return cvPersister;
  }
}
