package com.linepro.modellbahn.rest.service;

import java.math.BigDecimal;
import java.util.Date;

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
import com.linepro.modellbahn.model.IAchsfolg;
import com.linepro.modellbahn.model.IAntrieb;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.impl.Vorbild;
import com.linepro.modellbahn.model.keys.NameKey;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.util.AbstractItemService;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;

/**
 * VorbildService. CRUD service for Vorbild
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Path(ApiPaths.VORBILD)
public class VorbildService extends AbstractItemService<NameKey, Vorbild> {

    public VorbildService() {
        super(Vorbild.class);
    }

    @JsonCreator
    public Vorbild create(@JsonProperty(value=ApiNames.ID, required=false) Long id,
            @JsonProperty(value=ApiNames.GATTUNG, required=false) IGattung gattung,
            @JsonProperty(value=ApiNames.UNTER_KATEGORIE, required=false) IUnterKategorie unterKategorie,
            @JsonProperty(value=ApiNames.HERSTELLER, required=false) String hersteller,
            @JsonProperty(value=ApiNames.BAUZEIT, required=false) Date bauzeit,
            @JsonProperty(value=ApiNames.ANZAHL, required=false) Integer anzahl,
            @JsonProperty(value=ApiNames.BETREIBSNUMMER, required=false) String betreibsNummer,
            @JsonProperty(value=ApiNames.ANTRIEB, required=false) IAntrieb antrieb,
            @JsonProperty(value=ApiNames.ACHSFOLG, required=false) IAchsfolg achsfolg,
            @JsonProperty(value=ApiNames.ANFAHRZUGKRAFT, required=false) BigDecimal anfahrzugkraft,
            @JsonProperty(value=ApiNames.LEISTUNG, required=false) BigDecimal leistung,
            @JsonProperty(value=ApiNames.DIENSTGEWICHT, required=false) BigDecimal dienstgewicht,
            @JsonProperty(value=ApiNames.GESCHWINDIGKEIT, required=false) Long geschwindigkeit,
            @JsonProperty(value=ApiNames.LANGE, required=false) BigDecimal lange,
            @JsonProperty(value=ApiNames.AUSSERDIENST, required=false) Date ausserdienst,
            @JsonProperty(value=ApiNames.DMTREIBRAD, required=false) BigDecimal dmTreibrad,
            @JsonProperty(value=ApiNames.DMLAUFRADVORN, required=false) BigDecimal dmLaufradVorn,
            @JsonProperty(value=ApiNames.DMLAUFRADHINTEN, required=false) BigDecimal dmLaufradHinten,
            @JsonProperty(value=ApiNames.ZYLINDER, required=false) Integer zylinder,
            @JsonProperty(value=ApiNames.DMZYLINDER, required=false) BigDecimal dmZylinder,
            @JsonProperty(value=ApiNames.KOLBENHUB, required=false) BigDecimal kolbenhub,
            @JsonProperty(value=ApiNames.KESSELUEBERDRUCK, required=false) BigDecimal kesselueberdruck,
            @JsonProperty(value=ApiNames.ROSTFLAECHE, required=false) BigDecimal rostflaeche,
            @JsonProperty(value=ApiNames.UEBERHITZERFLAECHE, required=false) BigDecimal ueberhitzerflaeche,
            @JsonProperty(value=ApiNames.WASSERVORRAT, required=false) BigDecimal wasservorrat,
            @JsonProperty(value=ApiNames.VERDAMPFUNG, required=false) BigDecimal verdampfung,
            @JsonProperty(value=ApiNames.STEUERUNG, required=false) ISteuerung steuerung,
            @JsonProperty(value=ApiNames.FAHRMOTOREN, required=false) Integer fahrmotoren,
            @JsonProperty(value=ApiNames.MOTORBAUART, required=false) String motorbauart,
            @JsonProperty(value=ApiNames.LEISTUNGSUEBERTRAGUNG, required=false) BigDecimal leistungsuebertragung,
            @JsonProperty(value=ApiNames.REICHWEITE, required=false) BigDecimal reichweite,
            @JsonProperty(value=ApiNames.KAPAZITAT, required=false) BigDecimal kapazitaet,
            @JsonProperty(value=ApiNames.KLASSE, required=false) Integer klasse,
            @JsonProperty(value=ApiNames.SITZPLATZEKL1, required=false) Long sitzPlatzeKL1,
            @JsonProperty(value=ApiNames.SITZPLATZEKL2, required=false) Long sitzPlatzeKL2,
            @JsonProperty(value=ApiNames.SITZPLATZEKL3, required=false) Long sitzPlatzeKL3,
            @JsonProperty(value=ApiNames.SITZPLATZEKL4, required=false) Long sitzPlatzeKL4,
            @JsonProperty(value=ApiNames.AUFBAU, required=false) String aufbauten,
            @JsonProperty(value=ApiNames.TRIEBZUGANZEIGEN, required=false) Boolean triebzugAnzeigen,
            @JsonProperty(value=ApiNames.TRIEBKOEPFE, required=false) Long triebkoepfe,
            @JsonProperty(value=ApiNames.MITTELWAGEN, required=false) Long mittelwagen,
            @JsonProperty(value=ApiNames.SITZPLATZETZKL1, required=false) Long sitzPlatzeTZKL1,
            @JsonProperty(value=ApiNames.SITZPLATZETZKL2, required=false) Long sitzPlatzeTzKL2,
            @JsonProperty(value=ApiNames.DREHGESTELLBAUART, required=false) String drehgestellbauart,
            @JsonProperty(value=ApiNames.ANMERKUNG, required=false) String anmerkung,
            @JsonProperty(value=ApiNames.DELETED, required=false) Boolean deleted) throws Exception {
        Vorbild entity = new Vorbild(sitzPlatzeTzKL2, null, null, anmerkung, null, klasse, anmerkung, null, null, null, null, null, sitzPlatzeTzKL2, null, null, null, null, null, klasse, null, null, null, null, null, null, null, null, klasse, anmerkung, null, null, null, klasse, sitzPlatzeTzKL2, sitzPlatzeTzKL2, sitzPlatzeTzKL2, sitzPlatzeTzKL2, anmerkung, deleted, sitzPlatzeTzKL2, sitzPlatzeTzKL2, sitzPlatzeTzKL2, sitzPlatzeTzKL2, anmerkung, deleted, anmerkung);

        info("create " + entity);

        return entity;
    }

    @GET
    @Path(ApiPaths.ID_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    public Response get(@PathParam(ApiPaths.ID_PARAM_NAME) Long name) {
        return super.get(name);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.DropDown.class)
    public Response search(@Context UriInfo uriInfo) {
        return super.search(uriInfo);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    public Response add(Vorbild entity) {
        return super.add(entity);
    }

    @PUT
    @Path(ApiPaths.ID_PART)
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    public Response update(@PathParam(ApiPaths.ID_PARAM_NAME) Long name, Vorbild entity) {
        return super.update(name, entity);
    }

    @DELETE
    @Path(ApiPaths.ID_PART)
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    public Response delete(@PathParam(ApiPaths.ID_PARAM_NAME) Long name) {
        return super.delete(name);
    }
}