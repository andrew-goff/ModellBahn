package com.linepro.modellbahn.rest.json.serialization;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.linepro.modellbahn.model.IAchsfolg;
import com.linepro.modellbahn.model.IAntrieb;
import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IAufbau;
import com.linepro.modellbahn.model.IBahnverwaltung;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderAdress;
import com.linepro.modellbahn.model.IDecoderCV;
import com.linepro.modellbahn.model.IDecoderFunktion;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypAdress;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.IEpoch;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.IKategorie;
import com.linepro.modellbahn.model.IKupplung;
import com.linepro.modellbahn.model.ILand;
import com.linepro.modellbahn.model.ILicht;
import com.linepro.modellbahn.model.IMassstab;
import com.linepro.modellbahn.model.IMotorTyp;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.IProduktTeil;
import com.linepro.modellbahn.model.IProtokoll;
import com.linepro.modellbahn.model.ISonderModell;
import com.linepro.modellbahn.model.ISpurweite;
import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.IVorbild;
import com.linepro.modellbahn.model.IWahrung;
import com.linepro.modellbahn.model.IZug;
import com.linepro.modellbahn.model.IZugConsist;
import com.linepro.modellbahn.model.IZugTyp;
import com.linepro.modellbahn.model.enums.AdressTyp;
import com.linepro.modellbahn.model.enums.DecoderStatus;
import com.linepro.modellbahn.model.enums.Konfiguration;
import com.linepro.modellbahn.model.enums.LeistungsUbertragung;
import com.linepro.modellbahn.model.enums.Status;
import com.linepro.modellbahn.model.enums.Stecker;
import com.linepro.modellbahn.model.impl.Achsfolg;
import com.linepro.modellbahn.model.impl.Antrieb;
import com.linepro.modellbahn.model.impl.Artikel;
import com.linepro.modellbahn.model.impl.Aufbau;
import com.linepro.modellbahn.model.impl.Bahnverwaltung;
import com.linepro.modellbahn.model.impl.Decoder;
import com.linepro.modellbahn.model.impl.DecoderAdress;
import com.linepro.modellbahn.model.impl.DecoderCV;
import com.linepro.modellbahn.model.impl.DecoderFunktion;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.impl.DecoderTypAdress;
import com.linepro.modellbahn.model.impl.DecoderTypCV;
import com.linepro.modellbahn.model.impl.DecoderTypFunktion;
import com.linepro.modellbahn.model.impl.Epoch;
import com.linepro.modellbahn.model.impl.Gattung;
import com.linepro.modellbahn.model.impl.Hersteller;
import com.linepro.modellbahn.model.impl.Kategorie;
import com.linepro.modellbahn.model.impl.Kupplung;
import com.linepro.modellbahn.model.impl.Land;
import com.linepro.modellbahn.model.impl.Licht;
import com.linepro.modellbahn.model.impl.Massstab;
import com.linepro.modellbahn.model.impl.MotorTyp;
import com.linepro.modellbahn.model.impl.Produkt;
import com.linepro.modellbahn.model.impl.ProduktTeil;
import com.linepro.modellbahn.model.impl.Protokoll;
import com.linepro.modellbahn.model.impl.SonderModell;
import com.linepro.modellbahn.model.impl.Spurweite;
import com.linepro.modellbahn.model.impl.Steuerung;
import com.linepro.modellbahn.model.impl.UnterKategorie;
import com.linepro.modellbahn.model.impl.Vorbild;
import com.linepro.modellbahn.model.impl.Wahrung;
import com.linepro.modellbahn.model.impl.Zug;
import com.linepro.modellbahn.model.impl.ZugConsist;
import com.linepro.modellbahn.model.impl.ZugTyp;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;

public class JsonFactory {
  @JsonCreator(mode=DELEGATING)
  public static IAchsfolg createAchsfolgNoArgs() {
    IAchsfolg entity = new Achsfolg();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IAntrieb createAntriebNoArgs() {
    IAntrieb entity = new Antrieb();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IArtikel createArtikelNoArgs() {
    IArtikel entity = new Artikel();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IBahnverwaltung createBahnverwaltungNoArgs() {
    IBahnverwaltung entity = new Bahnverwaltung();

    debug(entity);

    return entity;
  }


  @JsonCreator(mode=DELEGATING)
  public static IDecoder createDecoderNoArgs() {
    IDecoder entity = new Decoder();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IDecoderAdress createDecoderAdressNoArgs() {
    IDecoderAdress entity = new DecoderAdress();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IDecoderCV createDecoderCVNoArgs() {
    IDecoderCV entity = new DecoderCV();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IDecoderFunktion createDecoderFunktionNoArgs() {
    IDecoderFunktion entity = new DecoderFunktion();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IDecoderTyp createDecoderTypNoArgs() {
    IDecoderTyp entity = new DecoderTyp();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IDecoderTypAdress createDecoderTypAdressNoArgs() {
    IDecoderTypAdress entity = new DecoderTypAdress();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IDecoderTypCV createDecoderTypCVNoArgs() {
    IDecoderTypCV entity = new DecoderTypCV();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IDecoderTypFunktion createDecoderTypFunktionNoArgs() {
    IDecoderTypFunktion entity = new DecoderTypFunktion();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IEpoch createEpochNoArgs() {
    IEpoch entity = new Epoch();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IGattung createGattungNoArgs() {
    IGattung entity = new Gattung();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IHersteller createHerstellerNoArgs() {
    IHersteller entity = new Hersteller();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IKategorie createKategorieNoArgs() {
    IKategorie entity = new Kategorie();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IKupplung createKupplungNoArgs() {
    IKupplung entity = new Kupplung();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static ILand createLandNoArgs() {
    ILand entity = new Land();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static ILicht createLichtNoArgs() {
    ILicht entity = new Licht();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IMassstab createMassstabNoArgs() {
    IMassstab entity = new Massstab();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IMotorTyp createMotorTypNoArgs() {
    IMotorTyp entity = new MotorTyp();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IProdukt createProduktNoArgs() {
    IProdukt entity = new Produkt();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  IProduktTeil createProduktTeilNoArgs() {
    IProduktTeil entity = new ProduktTeil();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IProtokoll createProtokollNoArgs() {
    IProtokoll entity = new Protokoll();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static ISonderModell createSonderModellNoArgs() {
    ISonderModell entity = new SonderModell();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static ISpurweite createSpurweiteNoArgs() {
    ISpurweite entity = new Spurweite();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static ISteuerung createSteuerungNoArgs() {
    ISteuerung entity = new Steuerung();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IUnterKategorie createUnterKategorieNoArgs() {
    IUnterKategorie entity = new UnterKategorie();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IVorbild createVorbildNoArgs() {
    IVorbild entity = new Vorbild();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IWahrung createWahrungNoArgs() {
    IWahrung entity = new Wahrung();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IZug createZugNoArgs() {
    IZug entity = new Zug();
  
    debug(entity);
  
    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IZugConsist createZugConsistNoArgs() {
    IZugConsist entity = new ZugConsist();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=DELEGATING)
  public static IZugTyp createZugTypNoArgs() {
    IZugTyp entity = new ZugTyp();

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IAchsfolg createAchsfolg(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IAchsfolg entity = new Achsfolg(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IAntrieb createAntrieb(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IAntrieb entity = new Antrieb(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IArtikel createArtikel(@JsonProperty(value = ApiNames.ID) Long id,
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
    IProdukt produkt = findProdukt(herstellerStr, bestellNr, false);
    IWahrung wahrung = findWahrung(wahrungStr, false);
    ISteuerung steuerung = findSteuerung(steuerungStr, false);
    IMotorTyp motorTyp = findMotorTyp(motorTypStr, false);
    ILicht licht = findLicht(lichtStr, false);
    IKupplung kupplung = findKupplung(kupplungStr, false);
    IDecoder decoder = findDecoder(decoderId, false);
    Status status = Status.valueOf(statusStr);

    IArtikel entity = new Artikel(id, produkt, kaufdatum, wahrung, preis, stuck,
        steuerung, motorTyp, licht, kupplung, decoder,
        artikelNr, bezeichnung, anmerkung,
        beladung, status, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IBahnverwaltung createBahnverwaltung(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IBahnverwaltung entity = new Bahnverwaltung(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }


  @JsonCreator(mode=PROPERTIES)
  public static IDecoder createDecoder(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.DECODER_TYP) DecoderTyp decoderTyp,
      @JsonProperty(value = ApiNames.PROTOKOLL) Protokoll protokoll,
      @JsonProperty(value = ApiNames.DECODER_ID) String decoderId,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.FAHRSTUFE) Integer fahrstufe,
      @JsonProperty(value = ApiNames.STATUS) String statusStr,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    DecoderStatus status = DecoderStatus.valueOf(statusStr);

    IDecoder entity = new Decoder(id, decoderTyp, protokoll, decoderId, bezeichnung, fahrstufe, status, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IDecoderAdress createDecoderAdress(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.DECODER_ID) String decoderId,
      @JsonProperty(value = ApiNames.REIHE) Integer reihe,
      @JsonProperty(value = ApiNames.ADRESS_TYP) String adressTypStr,
      @JsonProperty(value = ApiNames.ADRESS) Integer adress,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IDecoder decoder = findDecoder(decoderId, false);
    AdressTyp adressTyp = AdressTyp.valueOf(adressTypStr);

    IDecoderAdress entity = new DecoderAdress(id, decoder, reihe, adressTyp, adress, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IDecoderCV createDecoderCV(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.DECODER_ID) String decoderId,
      @JsonProperty(value = ApiNames.CV) Integer cvValue, @JsonProperty(value = ApiNames.WERT) Integer wert,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IDecoder decoder = findDecoder(decoderId, false);

    IDecoderTypCV decoderTypCV = findDecoderTypCV(decoder.getDecoderTyp(), cvValue, true);

    IDecoderCV entity = new DecoderCV(id, decoder, decoderTypCV, wert, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IDecoderFunktion createDecoderFunktion(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.DECODER_ID) String decoderId,
      @JsonProperty(value = ApiNames.REIHE) Integer reihe,
      @JsonProperty(value = ApiNames.FUNKTION) String funktion,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IDecoder decoder = findDecoder(decoderId, false);

    IDecoderTypFunktion decoderTypFunktion = findDecoderTypFunktion(decoder.getDecoderTyp(), reihe, funktion, true);

    IDecoderFunktion entity = new DecoderFunktion(id, decoder, decoderTypFunktion, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IDecoderTyp createDecoderTyp(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
      @JsonProperty(value = ApiNames.PROTOKOLL) String protokollStr,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.GERAUSCH) Boolean sound,
      @JsonProperty(value = ApiNames.KONFIGURATION) String konfigurationStr,
      @JsonProperty(value = ApiNames.STECKER) String steckerStr,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IHersteller hersteller = findHersteller(herstellerStr, false);
    IProtokoll protokoll = findProtokoll(protokollStr, false);
    Konfiguration konfiguration = Konfiguration.valueOf(konfigurationStr);
    Stecker stecker = Stecker.valueOf(steckerStr);

    IDecoderTyp entity = new DecoderTyp(id, hersteller, protokoll, name, bezeichnung, sound, konfiguration, stecker,
        deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IDecoderTypAdress createDecoderTypAdress(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
      @JsonProperty(value = ApiNames.BESTELL_NR) String bestellNr,
      @JsonProperty(value = ApiNames.INDEX) Integer index,
      @JsonProperty(value = ApiNames.ADRESS_TYP) String adressTypStr,
      @JsonProperty(value = ApiNames.SPAN) Integer span,
      @JsonProperty(value = ApiNames.WERKSEINSTELLUNG) Integer werkeinstellung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IDecoderTyp decoderTyp = findDecoderTyp(herstellerStr, bestellNr, false);
    AdressTyp adressTyp = AdressTyp.valueOf(adressTypStr);

    IDecoderTypAdress entity = new DecoderTypAdress(id, decoderTyp, index, adressTyp, span, werkeinstellung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IDecoderTypCV createDecoderTypCV(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
      @JsonProperty(value = ApiNames.BESTELL_NR) String bestellNr,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.CV) Integer cv,
      @JsonProperty(value = ApiNames.MINIMAL) Integer max,
      @JsonProperty(value = ApiNames.MAXIMAL) Integer min,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IDecoderTyp decoderTyp = findDecoderTyp(herstellerStr, bestellNr, false);

    IDecoderTypCV entity = new DecoderTypCV(id, decoderTyp, cv, bezeichnung, cv, min, max, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IDecoderTypFunktion createDecoderTypFunktion(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
      @JsonProperty(value = ApiNames.BESTELL_NR) String bestellNr,
      @JsonProperty(value = ApiNames.REIHE) Integer reihe,
      @JsonProperty(value = ApiNames.NAMEN) String funktion,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.PROGRAMMABLE) Boolean programmable,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IDecoderTyp decoderTyp = findDecoderTyp(herstellerStr, bestellNr, false);

    IDecoderTypFunktion entity = new DecoderTypFunktion(id, decoderTyp, reihe, funktion, bezeichnung, programmable,
        deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IEpoch createEpoch(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IEpoch entity = new Epoch(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IGattung createGattung(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IGattung entity = new Gattung(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IHersteller createHersteller(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.TELEFON) String telefon,
      @JsonProperty(value = ApiNames.URL) String urlStr,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    URL url = new URL(urlStr);

    IHersteller entity = new Hersteller(id, name, bezeichnung, url, telefon, deleted);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IKategorie createKategorie(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IKategorie entity = new Kategorie(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IKupplung createKupplung(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IKupplung entity = new Kupplung(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static ILand createLand(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiPaths.NAME_PARAM_NAME) String name,
      @JsonProperty(value = ApiNames.WAHRUNG) String wahrungStr,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IWahrung wahrung = findWahrung(wahrungStr, false);

    ILand entity = new Land(id, name, bezeichnung, wahrung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static ILicht createLicht(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiPaths.NAME_PARAM_NAME) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    ILicht entity = new Licht(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IMassstab createMassstab(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IMassstab entity = new Massstab(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IMotorTyp createMotorTyp(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IMotorTyp entity = new MotorTyp(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IProdukt createProdukt(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.HERSTELLER) String herstellerStr,
      @JsonProperty(value = ApiNames.BESTELL_NR) String bestellNr,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.UNTER_KATEGORIE) UnterKategorie unterKategorie,
      @JsonProperty(value = ApiNames.MASSSTAB) String massstabStr,
      @JsonProperty(value = ApiNames.SPURWEITE) String spurweiteStr,
      @JsonProperty(value = ApiNames.BETREIBSNUMMER) String betreibsNummer,
      @JsonProperty(value = ApiNames.EPOCH) String epochStr,
      @JsonProperty(value = ApiNames.BAHNVERWALTUNG) String bahnverwaltungStr,
      @JsonProperty(value = ApiNames.GATTUNG) String gattungStr,
      @JsonProperty(value = ApiNames.BAUZEIT) LocalDate bauzeit,
      @JsonProperty(value = ApiNames.ACHSFOLG) String achsfolgStr,
      @JsonProperty(value = ApiNames.VORBILD) String vorbildStr,
      @JsonProperty(value = ApiNames.ANMERKUNG) String anmerkung,
      @JsonProperty(value = ApiNames.SONDERMODELL) String sondermodellStr,
      @JsonProperty(value = ApiNames.AUFBAU) String aufbauStr,
      @JsonProperty(value = ApiNames.LICHT) String lichtStr,
      @JsonProperty(value = ApiNames.KUPPLUNG) String kupplungStr,
      @JsonProperty(value = ApiNames.STEUERUNG) String steuerungStr,
      @JsonProperty(value = ApiNames.DECODER_TYP) DecoderTyp decoderTyp,
      @JsonProperty(value = ApiNames.MOTOR_TYP) String motorTypStr,
      @JsonProperty(value = ApiNames.LANGE) BigDecimal lange,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    // Just see if Jackson can work out the embedded objects...
    IHersteller hersteller = findHersteller(herstellerStr, false);
    IMassstab massstab = findMassstab(massstabStr, false);
    ISpurweite spurweite = findSpurweite(spurweiteStr, false);
    IEpoch epoch = findEpoch(epochStr, false);
    IBahnverwaltung bahnverwaltung = findBahnverwaltung(bahnverwaltungStr, false);
    IGattung gattung = findGattung(gattungStr, false);
    IVorbild vorbild = findVorbild(gattungStr, false);
    IAchsfolg achsfolg = findAchsfolg(achsfolgStr, false);
    ISonderModell sondermodell = findSonderModell(sondermodellStr, false);
    IAufbau aufbau = findAufbau(aufbauStr, false);
    ILicht licht = findLicht(lichtStr, false);
    IKupplung kupplung = findKupplung(kupplungStr, false);
    ISteuerung steuerung = findSteuerung(steuerungStr, false);
    IMotorTyp motorTyp = findMotorTyp(motorTypStr, false);

    IProdukt entity = new Produkt(id,
        hersteller,
        bestellNr,
        bezeichnung,
        unterKategorie,
        massstab,
        spurweite,
        epoch,
        bahnverwaltung,
        gattung,
        betreibsNummer,
        bauzeit,
        vorbild,
        achsfolg,
        anmerkung,
        sondermodell,
        aufbau,
        licht,
        kupplung,
        steuerung,
        decoderTyp,
        motorTyp,
        lange,
        deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  IProduktTeil createProduktTeil(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.PRODUKT) Produkt produkt,
      @JsonProperty(value = ApiNames.TEIL) Produkt teil,
      @JsonProperty(value = ApiNames.ANZAHL) Integer anzahl,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IProduktTeil entity = new ProduktTeil(id, produkt, teil, anzahl, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IProtokoll createProtokoll(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IProtokoll entity = new Protokoll(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static ISonderModell createSonderModell(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    ISonderModell entity = new SonderModell(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static ISpurweite createSpurweite(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    ISpurweite entity = new Spurweite(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static ISteuerung createSteuerung(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    ISteuerung entity = new Steuerung(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IUnterKategorie createUnterKategorie(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.KATEGORIE) String kategorieStr,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IKategorie kategorie = findKategorie(kategorieStr, false);

    return new UnterKategorie(id, kategorie, name, bezeichnung, deleted);
  }

  @JsonCreator(mode=PROPERTIES)
  public static IVorbild createVorbild(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.GATTUNG) IGattung gattung,
      @JsonProperty(value = ApiNames.UNTER_KATEGORIE, required=true) IUnterKategorie unterKategorie,
      @JsonProperty(value = ApiNames.BAHNVERWALTUNG) IBahnverwaltung bahnverwaltung,
      @JsonProperty(value = ApiNames.HERSTELLER) String hersteller,
      @JsonProperty(value = ApiNames.BAUZEIT) LocalDate bauzeit,
      @JsonProperty(value = ApiNames.ANZAHL) Integer anzahl,
      @JsonProperty(value = ApiNames.BETREIBSNUMMER) String betreibsNummer,
      @JsonProperty(value = ApiNames.ANTRIEB) IAntrieb antrieb,
      @JsonProperty(value = ApiNames.ACHSFOLG) IAchsfolg achsfolg,
      @JsonProperty(value = ApiNames.ANFAHRZUGKRAFT) BigDecimal anfahrzugkraft,
      @JsonProperty(value = ApiNames.LEISTUNG) BigDecimal leistung,
      @JsonProperty(value = ApiNames.DIENSTGEWICHT) BigDecimal dienstgewicht,
      @JsonProperty(value = ApiNames.GESCHWINDIGKEIT) Integer geschwindigkeit,
      @JsonProperty(value = ApiNames.LANGE) BigDecimal lange,
      @JsonProperty(value = ApiNames.AUSSERDIENST) LocalDate ausserdienst,
      @JsonProperty(value = ApiNames.DMTREIBRAD) BigDecimal dmTreibrad,
      @JsonProperty(value = ApiNames.DMLAUFRADVORN) BigDecimal dmLaufradVorn,
      @JsonProperty(value = ApiNames.DMLAUFRADHINTEN) BigDecimal dmLaufradHinten,
      @JsonProperty(value = ApiNames.ZYLINDER) Integer zylinder,
      @JsonProperty(value = ApiNames.DMZYLINDER) BigDecimal dmZylinder,
      @JsonProperty(value = ApiNames.KOLBENHUB) BigDecimal kolbenhub,
      @JsonProperty(value = ApiNames.KESSELUBERDRUCK) BigDecimal kesseluberdruck,
      @JsonProperty(value = ApiNames.ROSTFLACHE) BigDecimal rostflache,
      @JsonProperty(value = ApiNames.UBERHITZERFLACHE) BigDecimal uberhitzerflache,
      @JsonProperty(value = ApiNames.WASSERVORRAT) BigDecimal wasservorrat,
      @JsonProperty(value = ApiNames.VERDAMPFUNG) BigDecimal verdampfung,
      @JsonProperty(value = ApiNames.FAHRMOTOREN) Integer fahrmotoren,
      @JsonProperty(value = ApiNames.MOTORBAUART) String motorbauart,
      @JsonProperty(value = ApiNames.LEISTUNGSUBERTRAGUNG) LeistungsUbertragung leistungsUbertragung,
      @JsonProperty(value = ApiNames.REICHWEITE) BigDecimal reichweite,
      @JsonProperty(value = ApiNames.KAPAZITAT) BigDecimal kapazitaet,
      @JsonProperty(value = ApiNames.KLASSE) Integer klasse,
      @JsonProperty(value = ApiNames.SITZPLATZEKL1) Integer sitzPlatzeKL1,
      @JsonProperty(value = ApiNames.SITZPLATZEKL2) Integer sitzPlatzeKL2,
      @JsonProperty(value = ApiNames.SITZPLATZEKL3) Integer sitzPlatzeKL3,
      @JsonProperty(value = ApiNames.SITZPLATZEKL4) Integer sitzPlatzeKL4,
      @JsonProperty(value = ApiNames.AUFBAU) String aufbauten,
      @JsonProperty(value = ApiNames.TRIEBKOPF) Integer triebkopf,
      @JsonProperty(value = ApiNames.MITTELWAGEN) Integer mittelwagen,
      @JsonProperty(value = ApiNames.DREHGESTELLBAUART) String drehgestellbauart,
      @JsonProperty(value = ApiNames.ANMERKUNG) String anmerkung,
      @JsonProperty(value = ApiNames.ABBILDUNG) String abbildungStr,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IVorbild entity = new Vorbild(id, gattung, unterKategorie, bahnverwaltung, hersteller, bauzeit, anzahl, betreibsNummer, antrieb, achsfolg, anmerkung, anfahrzugkraft, leistung, dienstgewicht,
        geschwindigkeit, lange, ausserdienst, dmTreibrad, dmLaufradVorn, dmLaufradHinten, zylinder, dmZylinder, kolbenhub, kesseluberdruck, rostflache, uberhitzerflache,
        wasservorrat, verdampfung, fahrmotoren, anmerkung, leistungsUbertragung, reichweite, kapazitaet, klasse, sitzPlatzeKL1, sitzPlatzeKL2, sitzPlatzeKL3,
        sitzPlatzeKL4, aufbauten, triebkopf, mittelwagen, drehgestellbauart, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IWahrung createWahrung(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DEZIMAL) Integer dezimal,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IWahrung entity = new Wahrung(id, name, bezeichnung, dezimal, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IZug createZug(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.ZUG_TYP) String zugTypStr,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IZugTyp zugTyp = findZugTyp(zugTypStr, false);

    IZug entity = new Zug(id, name, bezeichnung, zugTyp, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IZugConsist createZugConsist(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.ZUG) String zugStr,
      @JsonProperty(value = ApiNames.POSITION) Integer position,
      @JsonProperty(value = ApiNames.ARTIKEL) String artikelStr,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) throws Exception {
    IZug zug = findZug(zugStr, true);
    IArtikel artikel = findArtikel(artikelStr, false);

    IZugConsist entity = new ZugConsist(id,  zug, position, artikel, deleted);

    debug(entity);

    return entity;
  }

  @JsonCreator(mode=PROPERTIES)
  public static IZugTyp createZugTyp(@JsonProperty(value = ApiNames.ID) Long id,
      @JsonProperty(value = ApiNames.NAMEN) String name,
      @JsonProperty(value = ApiNames.BEZEICHNUNG) String bezeichnung,
      @JsonProperty(value = ApiNames.DELETED) Boolean deleted) {
    IZugTyp entity = new ZugTyp(id, name, bezeichnung, deleted);

    debug(entity);

    return entity;
  }

  protected static void debug(IItem<?> message) {
  }

  protected static IAchsfolg findAchsfolg(String achsfolgStr, boolean inflate) { return null; }
  protected static IAufbau findAufbau(String aufbauStr, boolean inflate) { return null; }
  protected static IArtikel findArtikel(String artikelId, boolean inflate) { return null; }
  protected static IBahnverwaltung findBahnverwaltung(String bahnverwaltungStr, boolean inflate) { return null; }
  protected static IDecoder findDecoder(String decoderId, boolean inflate) { return null; };
  protected static IDecoderTyp findDecoderTyp(String herstellerStr, String bestellNr, boolean inflate) { return null; };
  protected static IDecoderTypCV findDecoderTypCV(IDecoderTyp decoderTyp, Integer cvValue, boolean inflate) { return null; };
  protected static IDecoderTypFunktion findDecoderTypFunktion(IDecoderTyp decoderTyp, Integer reihe, String funktion, boolean inflate) { return null; };
  protected static IEpoch findEpoch(String epochStr, boolean inflate) { return null; }
  protected static IGattung findGattung(String gattungStr, boolean inflate) { return null; }
  protected static IHersteller findHersteller(String kupplungStr, boolean inflate) { return null; };
  protected static IKupplung findKupplung(String kupplungStr, boolean inflate) { return null; };
  protected static IKategorie findKategorie(String kategorieStr, boolean inflate) { return null; };
  protected static ILicht findLicht(String lichtStr, boolean inflate) { return null; };
  protected static IMassstab findMassstab(String massstabStr, boolean inflate) { return null; }
  protected static IMotorTyp findMotorTyp(String motorTypStr, boolean inflate) { return null; };
  protected static IProdukt findProdukt(String herstellerStr, String bestellNr, boolean inflate) { return null; };
  protected static IProtokoll findProtokoll(String protokollStr, boolean inflate) { return null; };
  protected static ISonderModell findSonderModell(String sondermodellStr, boolean inflate) { return null; }
  protected static ISpurweite findSpurweite(String spurweiteStr, boolean inflate) { return null; }
  protected static ISteuerung findSteuerung(String steuerungStr, boolean inflate) { return null; };
  protected static IWahrung findWahrung(String wahrungStr, boolean inflate) { return null; };
  protected static IVorbild findVorbild(String gattungStr, boolean inflate) { return null; }
  protected static IZug findZug(String zugStr, boolean inflate) { return null; }
  protected static IZugTyp findZugTyp(String zugTypStr, boolean inflate) { return null; }
}
