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
import com.linepro.modellbahn.model.IAntrieb;
import com.linepro.modellbahn.model.IBahnverwaltung;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.IVorbild;
import com.linepro.modellbahn.model.impl.Vorbild;
import com.linepro.modellbahn.model.keys.VorbildKey;
import com.linepro.modellbahn.model.enums.LeistungsUbertragung;
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
 * VorbildService. CRUD service for Vorbild
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Api(value = ApiNames.VORBILD)
@Path(ApiPaths.VORBILD)
public class VorbildService extends AbstractItemService<VorbildKey, IVorbild> {

    public VorbildService() {
        super(IVorbild.class);
    }

    @GET
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(value = "Finds a Vorbild by name", response = IVorbild.class)
    public Response get(@PathParam(ApiPaths.NAME_PARAM_NAME) String name) {
        try {
            return super.get(new VorbildKey(findGattung(name, false)));
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    @ApiOperation(value = "Finds Vorbilden by example", response = IVorbild.class, responseContainer = "List")
    @ApiImplicitParams({
        @ApiImplicitParam( name = ApiNames.ID, value = "Vorbild id", dataType = "Long", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.GATTUNG, value = "Rolling stock class", example = "BR89.0", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.UNTER_KATEGORIE, value = "Category and subcategory", example = "[\"LOKOMOTIV\",\"DAMPF\"]", dataType = "[Ljava.lang.String;", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BAHNVERWALTUNG, value = "Railway company code", example = "DB", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.HERSTELLER, value = "Manufacturer", example = "Henschel", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BAUZEIT, value = "Construction date", example = "1934-01-01", dataType = "java.time.LocalDate", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ANZAHL, value = "Number built", example = "10", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.BETREIBSNUMMER, value = "Service number", example = "89 006", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ANTRIEB, value = "Drive method", example = "DAMPF", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ACHSFOLG, value = "Axle configuration", example = "C1HT", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ANFAHRZUGKRAFT, value = "Tractive Effort in kN", example = "300", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.LEISTUNG, value = "Power in kW", example = "385", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DIENSTGEWICHT, value = "Service weight in metric tons", example = "46", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.GESCHWINDIGKEIT, value = "Maximum speed in km/h", example = "45", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.LANGE, value = "Length over puffers in mm", example = "9600", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.AUSSERDIENST, value = "Out of service date", example = "1962", dataType = "java.time.LocalDate", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DMTREIBRAD, value = "Drive wheel diamerter in mm", example = "1100", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DMLAUFRADVORN, value = "Running wheel diameter front", example = "0", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DMLAUFRADHINTEN, value = "Running wheel diameter rear", example = "0", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ZYLINDER, value = "Number of cylinders", example = "2", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DMZYLINDER, value = "Cylinder diameter", example = "500", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.KOLBENHUB, value = "Cylinder stroke", example = "550", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.KESSELUBERDRUCK, value = "Boiler pressure in bar", example = "14", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.ROSTFLACHE, value = "Grate area in m²", example = "1.42", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.UBERHITZERFLACHE, value = "Super heater area in m²", example = "11.9", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.WASSERVORRAT, value = "Water capactity in m³", example = "5.5", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.VERDAMPFUNG, value = "Evaporative heater area in m²", example = "118.4", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.FAHRMOTOREN, value = "Number of drive motors", example = "1", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.MOTORBAUART, value = "Engine manufacturer and model (IC engines)", example = "Henschel 12V1516A", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.LEISTUNGSUBERTRAGUNG, value = "Power transfer method (IC engines)", example = "MECHANISH", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.REICHWEITE, value = "Range (fueled vehicles) km", example = "500", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.KAPAZITAT, value = "Battery capacity in kwH", example = "190", dataType = "Number", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.KLASSE, value = "Number of classes (passenger wagens / multiple units)", example = "2", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.SITZPLATZEKL1, value = "First class seating", example = "20", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.SITZPLATZEKL2, value = "Second class seating", example = "80", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.SITZPLATZEKL3, value = "Third class seating", example = "90", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.SITZPLATZEKL4, value = "Fourth class seating", example = "150", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.AUFBAU, value = "Construction", example = "Holz", dataType = "String", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.TRIEBKOPF, value = "Number of drive wagons (multiple units)", example = "2", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.MITTELWAGEN, value = "Number of middle wagons (multiple units)", example = "6", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam( name = ApiNames.DREHGESTELLBAUART, value = "Bogie Manufacturer and type", example = "Y 25", dataType = "String", paramType = "query"),
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
    @ApiOperation(code = 201, value = "Adds a Vorbild", response = IVorbild.class)
    public Response add(IVorbild entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.NAME_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 202, value = "Updates a Vorbild by name", response = IVorbild.class)
    public Response update(@PathParam(ApiPaths.NAME_PARAM_NAME) String name, IVorbild entity) {
        return super.update(name, entity);
    }

    @DELETE
    @Path(ApiPaths.NAME_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 204, value = "Deletes a Vorbild by name", response = IVorbild.class)
    public Response delete(@PathParam(ApiPaths.ID_PARAM_NAME) String name) {
        return super.delete(name);
    }

    @PUT
    @Path(ApiPaths.ABBILDUNG_PART)
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    @ApiOperation(code = 201, value = "Adds or updates thr picture for a named Vorbild", response = IVorbild.class)
    public Response updateAbbildung(@PathParam(ApiPaths.NAME_PARAM_NAME) String name,
                                    @FormDataParam(ApiPaths.MULTIPART_FILE_DETAIL) FormDataContentDisposition fileDetail,
                                    @FormDataParam(ApiPaths.MULTIPART_FILE_DATA) InputStream fileData) {
        IFileUploadHandler handler = new FileUploadHandler();

        try {
            if (!handler.isAcceptable(fileDetail, fileData, AcceptableMediaTypes.IMAGES)) {
                return getResponse(badRequest(null, "Invalid file '" + fileDetail.getFileName() + "'"));
            }

            IVorbild vorbild = findVorbild(name, false);

            if (vorbild != null) {
                java.nio.file.Path file = handler.upload(ApiNames.VORBILD, new String[] { name }, fileDetail, fileData);

                vorbild.setAbbildung(file);

                getPersister().update(vorbild);

                return getResponse(ok(vorbild));
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
    @ApiOperation(value = "Removes the picture from a named Vorbild", response = IVorbild.class)
    public Response deleteAbbildung(@PathParam(ApiPaths.ID_PARAM_NAME) String name) {
        try {
            IVorbild vorbild = findVorbild(name, false);

            if (vorbild != null && vorbild.getAbbildung() != null) {
                StaticContentFinder.getStore().removeFile(vorbild.getAbbildung());

                vorbild.setAbbildung(null);

                getPersister().update(vorbild);

                return getResponse(ok(vorbild));
            }
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

        return getResponse(notFound());
    }
}
