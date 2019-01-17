package com.linepro.modellbahn.rest.service;

import com.linepro.modellbahn.model.enums.DecoderStatus;
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
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderAdress;
import com.linepro.modellbahn.model.IDecoderCV;
import com.linepro.modellbahn.model.IDecoderFunktion;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.impl.Decoder;
import com.linepro.modellbahn.model.impl.DecoderAdress;
import com.linepro.modellbahn.model.impl.DecoderCV;
import com.linepro.modellbahn.model.impl.DecoderFunktion;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.impl.Protokoll;
import com.linepro.modellbahn.model.keys.DecoderKey;
import com.linepro.modellbahn.model.enums.AdressTyp;
import com.linepro.modellbahn.model.util.DecoderCreator;
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
 * DecoderService. CRUD service for Decoder
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.DECODER)
@Path(ApiPaths.DECODER)
public class DecoderService extends AbstractItemService<DecoderKey, IDecoder> {

    public DecoderService() {
        super(IDecoder.class);
    }

    @POST
    @Path(ApiPaths.DECODER_TYP_PATH)
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds a Decoder by hersteller and bestell nr", response = IDecoder.class)
    @ApiResponses({@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response addDecoder(@PathParam(ApiNames.HERSTELLER) String herstellerStr,
            @PathParam(ApiNames.BESTELL_NR) String bestellNr) {
        try {
            IDecoderTyp decoderTyp = findDecoderTyp(herstellerStr, bestellNr, true);

            if (decoderTyp == null) {
                return getResponse(
                        badRequest(null, "DecoderTyp " + herstellerStr + "/" + bestellNr + " does not exist"));
            }

            IDecoder decoder = new DecoderCreator(getPersister()).create(decoderTyp);

            return getResponse(created(), decoder, true, true);
        } catch (Exception e) {
            return serverError(e).build();
        }
    }

    @GET
    @Path(ApiPaths.DECODER_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a Decoder by decoderId", response = IDecoder.class)
    public Response get(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId) {
        return super.get(new DecoderKey(decoderId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Decoderen by example", response = IDecoder.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = ApiNames.ID, value = "Decoder id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.DECODER_ID, value = "Decoder code", example = "00001", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.DECODER_TYP, value = "Decoder type", example = "[\"ESU\",\"62400\"]", dataType = "[Ljava.lang.String;", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.BEZEICHNUNG, value = "Decoder description", example = "LokSound M4", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.PROTOKOLL, value = "Decoder protocoll code", example = "MFX", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.FAHRSTUFE, value = "Decoder speed steps", example = "27", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.DELETED, value = "If true search for soft deleted items", example = "false", dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.PAGE_NUMBER, value = "0 based page number for paged queries", example = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = ApiNames.PAGE_SIZE, value = "Page size for paged queries", example = "10", dataType = "Integer", paramType = "query"),})
    public Response search(@Context UriInfo uriInfo) {
        return super.search(uriInfo);
    }

    @PUT
    @Path(ApiPaths.DECODER_PART)
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a Decoder by decoderId", response = IDecoder.class)
    public Response update(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId, Decoder entity) {
        return super.update(new DecoderKey(decoderId), entity);
    }

    @DELETE
    @Path(ApiPaths.DECODER_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a Decoder by decoderId", response = IDecoder.class)
    public Response delete(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId) {
        return super.delete(new DecoderKey(decoderId));
    }

    @GET
    @Path(ApiPaths.DECODER_ADRESS_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a DecoderAdress by decoderId and position", response = DecoderAdress.class)
    @ApiResponses({@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getAdress(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId,
            @PathParam(ApiPaths.INDEX_PARAM_NAME) Integer index) {
        try {
            IDecoder decoder = findDecoder(decoderId, true);

            if (decoder == null) {
                return getResponse(badRequest(null, "Decoder " + decoderId + " does not exist"));
            }

            IDecoderAdress decoderAdress = findDecoderAdress(decoder, index, true);

            if (decoderAdress != null) {
                return getResponse(ok(), decoderAdress, true, true);
            }

            return getResponse(notFound());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.DECODER_ADRESS_PATH)
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a DecoderAdress by decoderId and position", response = DecoderAdress.class)
    @ApiResponses({@ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response updateAdress(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId,
            @PathParam(ApiPaths.INDEX_PARAM_NAME) Integer index, @QueryParam(ApiNames.ADRESS) Integer adress) {
        try {
            logPut(decoderId + "/" + index + ": " + adress);

            IDecoder decoder = findDecoder(decoderId, true);

            if (decoder == null) {
                return getResponse(badRequest(null, "Decoder " + decoderId + " does not exist"));
            }

            IDecoderAdress decoderAdress = findDecoderAdress(decoder, index, true);

            if (decoderAdress == null) {
                return getResponse(badRequest(null, "Decoder Adress " + decoderId + "/" + index + " does not exist"));
            }

            decoderAdress.setAdress(adress);

            getPersister().save(decoder);

            return getResponse(accepted(), decoderAdress, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Path(ApiPaths.DECODER_CV_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a DecoderCV by decoderId and cv", response = DecoderCV.class)
    @ApiResponses({@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 402, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getCv(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId,
            @PathParam(ApiPaths.CV_PARAM_NAME) Integer cv) {
        try {
            IDecoder decoder = findDecoder(decoderId, true);

            if (decoder == null) {
                return getResponse(badRequest(null, "Decoder " + decoderId + " does not exist"));
            }

            IDecoderCV decoderCV = findDecoderCV(decoder, cv, true);

            if (decoderCV != null) {
                return getResponse(ok(), decoderCV, true, true);
            }

            return getResponse(notFound());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.DECODER_CV_PATH)
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a DecoderCV by decoderId and cv", response = DecoderCV.class)
    @ApiResponses({@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 402, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response updateCv(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId,
            @PathParam(ApiPaths.CV_PARAM_NAME) Integer cv, @QueryParam(ApiNames.WERT) Integer wert) {
        try {
            logPut(decoderId + "/" + cv + ": " + wert);

            IDecoder decoder = findDecoder(decoderId, true);

            if (decoder == null) {
                return getResponse(badRequest(null, "Decoder " + decoderId + " does not exist"));
            }

            IDecoderCV decoderCV = findDecoderCV(decoder, cv, true);

            if (decoderCV == null) {
                return getResponse(badRequest(null, "Decoder CV " + decoderId + "/" + cv + " does not exist"));
            }

            decoderCV.setWert(wert);

            getPersister().save(decoder);

            return getResponse(accepted(), decoderCV, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Path(ApiPaths.DECODER_FUNKTION_PATH)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a DecoderFunktion by decoderId and fn", response = DecoderFunktion.class)
    @ApiResponses({@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 402, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response getFunktion(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId,
            @PathParam(ApiPaths.REIHE_PARAM_NAME) Integer reihe,
            @PathParam(ApiPaths.FUNKTION_PARAM_NAME) String funktion) {
        try {
            IDecoder decoder = findDecoder(decoderId, true);

            if (decoder == null) {
                return getResponse(badRequest(null, "Decoder " + decoderId + " does not exist"));
            }

            IDecoderFunktion decoderFunktion = findDecoderFunktion(decoder, reihe, funktion, true);

            if (decoderFunktion != null) {
                return getResponse(ok(), decoderFunktion, true, true);
            }

            return getResponse(notFound());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @PUT
    @Path(ApiPaths.DECODER_FUNKTION_PATH)
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a DecoderFunktion by decoderId and fn", response = DecoderFunktion.class)
    @ApiResponses({@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 402, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public Response updateFunktion(@PathParam(ApiPaths.DECODER_ID_PARAM_NAME) String decoderId,
            @PathParam(ApiPaths.REIHE_PARAM_NAME) Integer reihe,
            @PathParam(ApiPaths.FUNKTION_PARAM_NAME) String funktion,
            @QueryParam(ApiNames.BEZEICHNUNG) String descirption) {
        try {
            logPut(decoderId + "/" + reihe + "/" + funktion + ": " + descirption);

            IDecoder decoder = findDecoder(decoderId, true);

            if (decoder == null) {
                return getResponse(badRequest(null, "Decoder " + decoderId + " does not exist"));
            }

            IDecoderFunktion decoderFunktion = findDecoderFunktion(decoder, reihe, funktion, true);

            if (decoderFunktion == null) {
                return getResponse(badRequest(null,
                        "Decoder Funktion " + decoderId + "/" + reihe + "/" + funktion + " does not exist"));
            }

            decoderFunktion.setBezeichnung(descirption);

            getPersister().save(decoder);

            return getResponse(accepted(), decoderFunktion, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    private IDecoderAdress findDecoderAdress(IDecoder decoder, Integer index, boolean eager) {
        try {
            return decoder.getAdressen().toArray(new IDecoderAdress[0])[index];
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            error("Error fetching DecoderAddressen for decoder " + decoder, e);
        }

        return null;
    }

    private IDecoderCV findDecoderCV(IDecoder decoder, Integer cv, boolean eager) {
        try {
            for (IDecoderCV decoderCV : decoder.getCVs()) {
                if (cv.equals(decoderCV.getWert())) {
                    return decoderCV;
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            error("Error fetching DecoderCV for decoder " + decoder, e);
        }

        return null;
    }

    private IDecoderFunktion findDecoderFunktion(IDecoder decoder, Integer reihe, String funktion, boolean eager) {
        try {
            for (IDecoderFunktion decoderFunktion : decoder.getFunktionen()) {
                if (reihe.equals(decoderFunktion.getFunktion().getReihe())
                        && funktion.equals(decoderFunktion.getFunktion().getFunktion())) {
                    return decoderFunktion;
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            error("Error fetching DecoderFunktion for decoder " + decoder, e);
        }

        return null;
    }
}
