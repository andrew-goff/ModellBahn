package com.linepro.modellbahn.model;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.impl.Achsfolg;
import com.linepro.modellbahn.model.impl.Antrieb;
import com.linepro.modellbahn.model.impl.Bahnverwaltung;
import com.linepro.modellbahn.model.impl.Gattung;
import com.linepro.modellbahn.model.impl.UnterKategorie;

import com.linepro.modellbahn.model.refs.IAchsfolgRef;
import com.linepro.modellbahn.model.refs.IAntriebRef;
import com.linepro.modellbahn.model.refs.IBahnverwaltungRef;
import com.linepro.modellbahn.model.refs.IUnterKategorieRef;
import com.linepro.modellbahn.model.refs.IVorbildRef;
import com.linepro.modellbahn.model.util.LeistungsUbertragung;
import com.linepro.modellbahn.rest.json.Formats;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.serialization.LocalDateDeserializer;
import com.linepro.modellbahn.rest.json.serialization.LocalDateSerializer;
import com.linepro.modellbahn.rest.util.ApiNames;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * IVorbild.
 * @author   $Author$
 * @version  $Id$
 */
@JsonRootName(value = ApiNames.VORBILD)
@JsonPropertyOrder({ApiNames.ID, ApiNames.GATTUNG, ApiNames.BEZEICHNUNG, ApiNames.UNTER_KATEGORIE, ApiNames.BAHNVERWALTUNG, ApiNames.HERSTELLER, ApiNames.BAUZEIT, ApiNames.ANZAHL, ApiNames.BETREIBSNUMMER, ApiNames.ANTRIEB, ApiNames.ACHSFOLG, ApiNames.ANFAHRZUGKRAFT, ApiNames.LEISTUNG, ApiNames.DIENSTGEWICHT, ApiNames.GESCHWINDIGKEIT, ApiNames.LANGE, ApiNames.AUSSERDIENST, ApiNames.DMTREIBRAD, ApiNames.DMLAUFRADVORN, ApiNames.DMLAUFRADHINTEN, ApiNames.ZYLINDER, ApiNames.DMZYLINDER, ApiNames.KOLBENHUB, ApiNames.KESSELUEBERDRUCK, ApiNames.ROSTFLAECHE, ApiNames.UEBERHITZERFLAECHE, ApiNames.WASSERVORRAT, ApiNames.VERDAMPFUNG, ApiNames.STEUERUNG, ApiNames.FAHRMOTOREN, ApiNames.MOTORBAUART, ApiNames.LEISTUNGSUBERTRAGUNG, ApiNames.REICHWEITE, ApiNames.KAPAZITAT, ApiNames.KLASSE, ApiNames.SITZPLATZEKL1, ApiNames.SITZPLATZEKL2, ApiNames.SITZPLATZEKL3, ApiNames.SITZPLATZEKL4, ApiNames.AUFBAU, ApiNames.TRIEBKOEPFE, ApiNames.MITTELWAGEN, ApiNames.DREHGESTELLBAUART, ApiNames.ABBILDUNG, ApiNames.DELETED, ApiNames.LINKS})
@ApiModel(value = ApiNames.VORBILD, description = "A real world prototype.")
public interface IVorbild extends IItem, IVorbildRef {
    
    @JsonSetter(ApiNames.GATTUNG)
    @JsonDeserialize(as= Gattung.class)
    void setGattung(IGattung gattung);

    @JsonSetter(ApiNames.BEZEICHNUNG)
    void setBezeichnung(String bezeichnung);
    
    @JsonGetter(ApiNames.UNTER_KATEGORIE)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as = IUnterKategorieRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IUnterKategorieRef", value = "Category and subcategory", required = true)
    IUnterKategorie getUnterKategorie();
    
    @JsonSetter(ApiNames.UNTER_KATEGORIE)
    @JsonDeserialize(as=UnterKategorie.class)
    void setUnterKategorie(IUnterKategorie unterKategorie);

    @JsonGetter(ApiNames.BAHNVERWALTUNG)
    @JsonView(Views.DropDown.class)
    @JsonSerialize(as= IBahnverwaltungRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IBahnverwaltungRef", value = "Railway company", required = true)
    IBahnverwaltung getBahnverwaltung();

    @JsonSetter(ApiNames.BAHNVERWALTUNG)
    @JsonDeserialize(as=Bahnverwaltung.class)
    void setBahnverwaltung(IBahnverwaltung bahnverwaltung);
    
    @JsonGetter(ApiNames.HERSTELLER)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "Manunfacturer", example = "Henschel")
    String getHersteller();
    
    @JsonSetter(ApiNames.HERSTELLER)
    void setHersteller(String hersteller);

    @JsonGetter(ApiNames.BAUZEIT)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonView(Views.DropDown.class)
    @JsonFormat(shape=Shape.STRING, pattern= Formats.ISO8601_DATE)
    @ApiModelProperty(dataType = "java.time.LocalDate", value = "Construction date", example = "1934-01-01")
    LocalDate getBauzeit();
    
    @JsonSetter(ApiNames.BAUZEIT)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    void setBauzeit(LocalDate bauzeit);
    
    @JsonGetter(ApiNames.ANZAHL)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Number built", example = "10")
    Integer getAnzahl();
    
    @JsonSetter(ApiNames.ANZAHL)
    void setAnzahl(Integer anzahl);
    
    @JsonGetter(ApiNames.BETREIBSNUMMER)
    @JsonView(Views.DropDown.class)
    @ApiModelProperty(value = "Service number", example = "89 006")
    String getBetreibsNummer();
    
    @JsonSetter(ApiNames.BETREIBSNUMMER)
    void setBetreibsNummer(String betreibsNummer);
    
    @JsonGetter(ApiNames.ANTRIEB)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IAntriebRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IAntriebRef", value = "Drive method")
    IAntrieb getAntrieb();
    
    @JsonSetter(ApiNames.ANTRIEB)
    @JsonDeserialize(as=Antrieb.class)
    void setAntrieb(IAntrieb antrieb);
    
    @JsonGetter(ApiNames.ACHSFOLG)
    @JsonView(Views.Public.class)
    @JsonSerialize(as= IAchsfolgRef.class)
    @ApiModelProperty(dataType = "com.linepro.modellbahn.model.refs.IAchsfolgRef", value = "Axle configuration")
    IAchsfolg getAchsfolg();
    
    @JsonSetter(ApiNames.ACHSFOLG)
    @JsonDeserialize(as=Achsfolg.class)
    void setAchsfolg(IAchsfolg achsfolg);
    
    @JsonGetter(ApiNames.ANFAHRZUGKRAFT)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Tractive Effort in kN", example = "300")
    BigDecimal getAnfahrzugkraft();
    
    @JsonSetter(ApiNames.ANFAHRZUGKRAFT)
    void setAnfahrzugkraft(BigDecimal anfahrzugkraft);
    
    @JsonGetter(ApiNames.LEISTUNG)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Power in kW", example = "385")
    BigDecimal getLeistung();
    
    @JsonSetter(ApiNames.LEISTUNG)
    void setLeistung(BigDecimal leistung);

    @JsonGetter(ApiNames.DIENSTGEWICHT)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Service weight in metric tons", example = "46")
    BigDecimal getDienstgewicht();

    @JsonSetter(ApiNames.DIENSTGEWICHT)
    void setDienstgewicht(BigDecimal dienstgewicht);

    @JsonGetter(ApiNames.GESCHWINDIGKEIT)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Maximum speed in km/h", example = "45")
    Integer getGeschwindigkeit();

    @JsonSetter(ApiNames.GESCHWINDIGKEIT)
    void setGeschwindigkeit(Integer geschwindigkeit);

    @JsonGetter(ApiNames.LANGE)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Length over puffers in mm", example = "9600")
    BigDecimal getLange();

    @JsonSetter(ApiNames.LANGE)
    void setLange(BigDecimal lange);

    @JsonGetter(ApiNames.AUSSERDIENST)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonView(Views.DropDown.class)
    @JsonFormat(shape=Shape.STRING, pattern=Formats.ISO8601_DATE)
    @ApiModelProperty(dataType = "java.time.LocalDate", value = "Out of service date", example = "1962")
    LocalDate getAusserdienst();

    @JsonSetter(ApiNames.AUSSERDIENST)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    void setAusserdienst(LocalDate ausserdienst);

    @JsonGetter(ApiNames.DMTREIBRAD)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Drive wheel diamerter in mm", example = "1100")
    BigDecimal getDmTreibrad();

    @JsonSetter(ApiNames.DMTREIBRAD)
    void setDmTreibrad(BigDecimal dmTreibrad);

    @JsonGetter(ApiNames.DMLAUFRADVORN)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Running wheel diameter front", example = "0")
    BigDecimal getDmLaufradVorn();

    @JsonSetter(ApiNames.DMLAUFRADVORN)
    void setDmLaufradVorn(BigDecimal dmLaufradVorn);

    @JsonGetter(ApiNames.DMLAUFRADHINTEN)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Running wheel diameter rear", example = "0")
    BigDecimal getDmLaufradHinten();

    @JsonSetter(ApiNames.DMLAUFRADHINTEN)
    void setDmLaufradHinten(BigDecimal dmLaufradHinten);

    @JsonGetter(ApiNames.ZYLINDER)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Number of cylinders", example = "2")
    Integer getZylinder();

    @JsonSetter(ApiNames.ZYLINDER)
    void setZylinder(Integer zylinder);

    @JsonGetter(ApiNames.DMZYLINDER)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Cylinder diameter", example = "500")
    BigDecimal getDmZylinder();

    @JsonSetter(ApiNames.DMZYLINDER)
    void setDmZylinder(BigDecimal dmZylinder);

    @JsonGetter(ApiNames.KOLBENHUB)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Cylinder stroke", example = "550")
    BigDecimal getKolbenhub();

    @JsonSetter(ApiNames.KOLBENHUB)
    void setKolbenhub(BigDecimal kolbenhub);

    @JsonGetter(ApiNames.KESSELUEBERDRUCK)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Boiler pressure in bar", example = "14")
    BigDecimal getKesselueberdruck();

    @JsonSetter(ApiNames.KESSELUEBERDRUCK)
    void setKesselueberdruck(BigDecimal kesselueberdruck);

    @JsonGetter(ApiNames.ROSTFLAECHE)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Grate area in m²", example = "1.42")
    BigDecimal getRostflaeche();

    @JsonSetter(ApiNames.ROSTFLAECHE)
    void setRostflaeche(BigDecimal rostflaeche);

    @JsonGetter(ApiNames.UEBERHITZERFLAECHE)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Super heater area in m²", example = "11.9")
    BigDecimal getUeberhitzerflaeche();

    @JsonSetter(ApiNames.UEBERHITZERFLAECHE)
    void setUeberhitzerflaeche(BigDecimal ueberhitzerflaeche);

    @JsonGetter(ApiNames.WASSERVORRAT)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Water capactity in m³", example = "5.5")
    BigDecimal getWasservorrat();

    @JsonSetter(ApiNames.WASSERVORRAT)
    void setWasservorrat(BigDecimal wasservorrat);

    @JsonGetter(ApiNames.VERDAMPFUNG)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Evaporative heater area in m²", example = "118.4")
    BigDecimal getVerdampfung();

    @JsonSetter(ApiNames.VERDAMPFUNG)
    void setVerdampfung(BigDecimal verdampfung);

    @JsonGetter(ApiNames.FAHRMOTOREN)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Number of drive motors", example = "1")
    Integer getFahrmotoren();

    @JsonSetter(ApiNames.FAHRMOTOREN)
    void setFahrmotoren(Integer fahrmotoren);

    @JsonGetter(ApiNames.MOTORBAUART)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Engine manufacturer and model (IC engines)", example = "Henschel 12V1516A")
    String getMotorbauart();

    @JsonSetter(ApiNames.MOTORBAUART)
    void setMotorbauart(String motorbauart);

    @JsonGetter(ApiNames.LEISTUNGSUBERTRAGUNG)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Power transfer method (IC engines)", example = "MECHANISH")
    LeistungsUbertragung getLeistungsubertragung();

    @JsonSetter(ApiNames.LEISTUNGSUBERTRAGUNG)
    void setLeistungsubertragung(LeistungsUbertragung leistungsubertragung);

    @JsonGetter(ApiNames.REICHWEITE)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Range (fueled vehicles) km", example = "500")
    BigDecimal getReichweite();

    @JsonSetter(ApiNames.REICHWEITE)
    void setReichweite(BigDecimal reichweite);

    @JsonGetter(ApiNames.KAPAZITAT)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Battery capacity in kwH", example = "190")
    BigDecimal getKapazitat();

    @JsonSetter(ApiNames.KAPAZITAT)
    void setKapazitat(BigDecimal kapazitat);

    @JsonGetter(ApiNames.KLASSE)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Number of classes (passenger wagens / multiple units)", example = "2")
    Integer getKlasse();

    @JsonSetter(ApiNames.KLASSE)
    void setKlasse(Integer klasse);

    @JsonGetter(ApiNames.SITZPLATZEKL1)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "First class seating", example = "20")
    Integer getSitzPlatzeKL1();

    @JsonSetter(ApiNames.SITZPLATZEKL1)
    void setSitzPlatzeKL1(Integer sitzPlatzeKL1);

    @JsonGetter(ApiNames.SITZPLATZEKL2)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Second class seating", example = "80")
    Integer getSitzPlatzeKL2();

    @JsonSetter(ApiNames.SITZPLATZEKL2)
    void setSitzPlatzeKL2(Integer sitzPlatzeKL2);

    @JsonGetter(ApiNames.SITZPLATZEKL3)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Third class seating", example = "90")
    Integer getSitzPlatzeKL3();

    @JsonSetter(ApiNames.SITZPLATZEKL3)
    void setSitzPlatzeKL3(Integer sitzPlatzeKL3);

    @JsonGetter(ApiNames.SITZPLATZEKL4)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Fourth class seating", example = "150")
    Integer getSitzPlatzeKL4();

    @JsonSetter(ApiNames.SITZPLATZEKL4)
    void setSitzPlatzeKL4(Integer sitzPlatzeKL4);

    @JsonGetter(ApiNames.AUFBAU)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Construction", example = "Holz")
    String getAufbau();

    @JsonSetter(ApiNames.AUFBAU)
    void setAufbau(String aufbau);

    @JsonGetter(ApiNames.TRIEBKOEPFE)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Number of drive wagons (multiple units)", example = "2")
    Integer getTriebkoepfe();

    @JsonSetter(ApiNames.TRIEBKOEPFE)
    void setTriebkoepfe(Integer triebkoepfe);

    @JsonGetter(ApiNames.MITTELWAGEN)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Number of middle wagons (multiple units)", example = "6")
    Integer getMittelwagen();

    @JsonSetter(ApiNames.MITTELWAGEN)
    void setMittelwagen(Integer mittelwagen);

    @JsonGetter(ApiNames.DREHGESTELLBAUART)
    @JsonView(Views.Public.class)
    @ApiModelProperty(value = "Bogie Manufacturer and type", example = "Y 25")
    String getDrehgestellBauart();

    @JsonSetter(ApiNames.DREHGESTELLBAUART)
    void setDrehgestellBauart(String drehgestellbauart);

    @JsonIgnore
    void setAbbildung(Path abbildung);
}