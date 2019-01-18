// module 'types.js'
'use strict';

const extractName = (entity) => { return entity.name; };
const extractBezeichnung = (entity) => { return new Option( extractName(entity), entity.bezeichnung, entity.abbildung) };

const ACHSFOLG_DROP = new DropDown(apiRoot() + 'achsfolg', extractName, extractBezeichnung);
const ADRESS_TYP_DROP = new DropDown(apiRoot() + 'enums/adressTyp', extractName, extractBezeichnung);
const ANTRIEB_DROP = new DropDown(apiRoot() + 'antrieb', extractName, extractBezeichnung);
const ARTIKEL_DROP = new DropDown(apiRoot() + 'artikel', (entity) => { return entity.artikelId; }, extractBezeichnung);
const AUFBAU_DROP = new DropDown(apiRoot() + 'aufbau', extractName, extractBezeichnung);
const BAHNVERWALTUNG_DROP = new DropDown(apiRoot() + 'bahnverwaltung', extractName, extractBezeichnung);
const DECODER_DROP = new DropDown(apiRoot() + 'decoder', (entity) => { return entity.decoderId; }, extractBezeichnung);
const EPOCH_DROP = new DropDown(apiRoot() + 'epoch', extractName, extractBezeichnung);
const GATTUNG_DROP = new DropDown(apiRoot() + 'gattung', extractName, extractBezeichnung);
const HERSTELLER_DROP = new DropDown(apiRoot() + 'hersteller', extractName, extractBezeichnung);
const KONFIGURATION_DROP = new DropDown(apiRoot() + 'enums/konfiguration', extractName, extractBezeichnung);
const KUPPLUNG_DROP = new DropDown(apiRoot() + 'kupplung', extractName, extractBezeichnung);
const LICHT_DROP = new DropDown(apiRoot() + 'licht', extractName, extractBezeichnung);
const MASSSTAB_DROP = new DropDown(apiRoot() + 'massstab', extractName, extractBezeichnung);
const MOTOR_TYP_DROP = new DropDown(apiRoot() + 'motorTyp', extractName, extractBezeichnung);
const PROTOKOLL_DROP = new DropDown(apiRoot() + 'protokoll', extractName, extractBezeichnung);
const SONDERMODELL_DROP = new DropDown(apiRoot() + 'sonderModell', extractName, extractBezeichnung);
const SPURWEITE_DROP = new DropDown(apiRoot() + 'spurweite', extractName, extractBezeichnung);
const STATUS_DROP = new DropDown(apiRoot() + 'enums/status', extractName, extractBezeichnung);
const STECKER_DROP = new DropDown(apiRoot() + 'enums/stecker', extractName, extractBezeichnung);
const STEUERUNG_DROP = new DropDown(apiRoot() + 'steuerung', extractName, extractBezeichnung);
const VORBILD_DROP = new DropDown(apiRoot() + 'vorbild', (entity) => { return entity.gattung.name; }, (entity) => { return new Option(entity.gattung.name, entity.bezeichnung); });
const WAHRUNG_DROP = new DropDown(apiRoot() + 'wahrung', extractName, extractBezeichnung);
const ZUG_TYP_DROP = new DropDown(apiRoot() + 'zugTyp', extractName, extractBezeichnung);

const extractProduktValue = (entity) => { return entity.hersteller.name + '/' + entity.bestellNr ; };
const extractProduktOption = (entity) => { return new Option( extractProduktValue(entity), entity.hersteller.bezeichnung + ' - ' + entity.bestellNr); };
const extractKategorieValue = (entity) => { return entity.kategorie.name + '/' + entity.name; };
const extractKategorieOption = (entity) => { return new Option( extractKategorieValue(entity), entity.kategorie.bezeichnung + ' - ' + entity.bezeichnung); };

const DECODER_TYP_DROP = new DropDown(apiRoot() + 'decoderTyp', extractProduktValue, extractProduktOption);
const PRODUKT_DROP = new DropDown(apiRoot() + 'produkt', extractProduktValue, extractProduktOption);
const UNTER_KATEGORIE_DROP = new DropDown(apiRoot() + 'kategorie/unterKategorien', extractKategorieValue, extractKategorieOption);

const ABBILDUNG = new IMGColumn('Abbildung', 'abbildung', (entity) => { return entity.abbildung; }, Editable.UPDATE, false);
const ACHSFOLG = new DropDownColumn('Achsfolg', 'achsfolg', (entity) => { return entity.achsfolg ? entity.achsfolg.name : undefined; }, (entity, value) => { entity.achsfolg = value; }, ACHSFOLG_DROP, Editable.UPDATE, false);
const ADRESS_TYP = new DropDownColumn('Typ', 'typ', (entity) => { return entity.adressTyp; }, (entity, value) => { entity.adressTyp = value; }, ADRESS_TYP_DROP, Editable.UPDATE, true);
const ANLEITUNGEN = new PDFColumn('Anleitungen', 'anleitungen', (entity) => { return entity.anleitungen; }, Editable.UPDATE, false);
const ANMERKUNG = new TextColumn('Anmerkung', 'anmerkung', (entity) => { return entity.anmerkung; }, (entity, value) => { entity.anmerkung = value; }, Editable.UPDATE, false, 30);
const ANTRIEB = new DropDownColumn('Antrieb', 'antrieb', (entity) => { return entity.antrieb ? entity.antrieb.name : undefined; }, (entity, value) => { entity.antrieb = value; }, ANTRIEB_DROP, Editable.UPDATE, false);
const ANFAHRZUGKRAFT = new NumberColumn('Anfahrzugkraft', 'anfahrzugkraft', (entity) => { return entity.anfahrzugkraft; }, (entity, value) => { entity.anfahrzugkraft = value; }, Editable.UPDATE, false, 300000, 1);
const ANZAHL = new NumberColumn('Anzahl', 'anzahl', (entity) => { return entity.anzahl; }, (entity, value) => { entity.anzahl = value; }, Editable.UPDATE, false, 300000, 1);
const ARTIKEL = new DropDownColumn('Artikel', 'artikel', (entity) => { return entity.artikel ? entity.artikel.artikelId : undefined; }, (entity, value) => { entity.artikelId = value; }, ARTIKEL_DROP, Editable.UPDATE, false);
const ARTIKEL_ID = new TextColumn('Artikel', 'artikelId', (entity) => { return entity.artikelId; }, (entity, value) => { entity.artikelId = value; }, Editable.NEVER, false, 5);
const AUFBAU = new DropDownColumn('Aufbau', 'aufbau', (entity) => { return entity.aufbau ? entity.aufbau.name : undefined; }, (entity, value) => { entity.aufbau = value; }, AUFBAU_DROP, Editable.UPDATE, false);
const AUSSERDIENST = new DateColumn('Außerdienst', 'ausserdienst', (entity) => { return entity.ausserdienst; }, (entity, value) => { entity.ausserdienst = value; }, Editable.UPDATE, false);
const BAHNVERWALTUNG = new DropDownColumn('Bahnverwaltung', 'bahnverwaltung', (entity) => { return entity.bahnverwaltung ? entity.bahnverwaltung.name : undefined; }, (entity, value) => { entity.bahnverwalung = value; }, BAHNVERWALTUNG_DROP, Editable.UPDATE, false);
const BAUZEIT = new DateColumn('Bauzeit', 'bauzeit', (entity) => { return entity.bauzeit; }, (entity, value) => { entity.bauzeit = value; }, Editable.UPDATE, false);
const BELADUNG = new TextColumn('Beladung', 'beladung', (entity) => { return entity.beladung; }, (entity, value) => { entity.beladung = value; }, Editable.UPDATE, false, 30);
const BESTELL_NR = new TextColumn('Bestell Nr', 'bestellNr', (entity) => { return entity.bestellNr; }, (entity, value) => { entity.bestellNr = value; }, Editable.ADD, true, 10);
const BETREIBSNUMMER = new TextColumn('Betreibsnummer', 'betreibsnummer', (entity) => { return entity.betreibsnummer; }, (entity, value) => { entity.betreibsnummer = value; }, Editable.UPDATE, false, 30);
const BEZEICHNUNG = new TextColumn('Bezeichnung', 'bezeichnung', (entity) => { return entity.bezeichnung; }, (entity, value) => { entity.bezeichnung = value; }, Editable.UPDATE, false, 50);
const CV = new NumberColumn('CV', 'cv', (entity) => { return entity.cv; }, (entity, value) => { entity.cv = value; }, Editable.ADD, true, 127, 1);
const DEZIMAL = new NumberColumn('Dezimal', 'dezimal', (entity) => { return entity.dezimal; }, (entity, value) => { entity.dezimal = value; }, Editable.UPDATE, false, 3, 0);
const DECODER = new DropDownColumn('Decoder', 'decoder', (entity) => { return entity.decoder ? entity.decoder.decoderId : undefined; }, (entity, value) => { entity.decoderId = value; }, DECODER_DROP, Editable.UPDATE, false);
const DECODER_ID = new TextColumn('Decoder', 'decoderId', (entity) => { return entity.decoderId; }, (entity, value) => { entity.decoderId = value; }, Editable.NEVER, false, 5);
const DECODER_TYP = new DropDownColumn('Decoder Typ', 'decoderTyp', (entity) => { return entity.decoderTyp ? extractProduktValue(entity.decoderTyp) : undefined; }, (entity, value) => { let parts = value.split('/'); entity.decoderTyp.hersteller = parts[0]; entity.decoderTyp.bestellNr = parts[1]; }, DECODER_TYP_DROP, Editable.UPDATE, false);
const DELETED = new BoolColumn('Deleted', 'deleted', (entity) => { return entity.deleted; }, (entity, value) => { entity.deleted = value; }, Editable.UPDATE, false);
const DIENSTGEWICHT = new NumberColumn('Dienstgewicht', 'dienstgewicht', (entity) => { return entity.dienstgewicht; }, (entity, value) => { entity.dienstgewicht = value; }, Editable.UPDATE, false, 999, 1);
const DMLAUFRADHINTEN = new NumberColumn('Laufrad Hinten', 'dmLaufradHinten', (entity) => { return entity.dmLaufradHinten; }, (entity, value) => { entity.dmLaufradHinten = value; }, Editable.UPDATE, false, 3000, 1);
const DMLAUFRADVORN = new NumberColumn('Laufrad Vorn', 'dmLaufradVorn', (entity) => { return entity.dmLaufradVorn; }, (entity, value) => { entity.dmLaufradVorn = value; }, Editable.UPDATE, false, 3000, 1);
const DMTREIBRAD = new NumberColumn('Treibrad', 'dmTreibrad', (entity) => { return entity.dmTreibrad; }, (entity, value) => { entity.dmTreibrad = value; }, Editable.UPDATE, false, 3000, 1);
const DMZYLINDER = new NumberColumn('Zylinder', 'dmZylinder', (entity) => { return entity.dmZylinder; }, (entity, value) => { entity.dmZylinder = value; }, Editable.UPDATE, false, 3000, 1);
const DREHGESTELLBAUART = new TextColumn('Drehgestell', 'drehgestellBauart', (entity) => { return entity.drehgestellBauart; }, (entity, value) => { entity.drehgestellBauart = value; }, Editable.UPDATE, false, 30);
const EPOCH = new DropDownColumn('Epoch', 'epoch', (entity) => { return entity.epoch ? entity.epoch.name : undefined; }, (entity, value) => { entity.epoch = value; }, EPOCH_DROP, Editable.UPDATE, false);
const EXPLOSIONSZEICHNUNG = new PDFColumn('Explosionszeichnung', 'explosionszeichnung', (entity) => { return entity.explosionszeichnung; }, Editable.UPDATE, false);
const FAHRMOTOREN = new NumberColumn('Fahrmotoren', 'fahrmotoren', (entity) => { return entity.fahrmotoren; }, (entity, value) => { entity.fahrmotoren = value; }, Editable.UPDATE, false, 5, 1);
const FAHRSTUFE = new NumberColumn('Fahrstufe', 'fahrstufe', (entity) => { return entity.fahrstufe; }, (entity, value) => { entity.fahrstufe = value; }, Editable.UPDATE, false, 127, 1);
const FUNKTION = new TextColumn('Funktion', 'funktion', (entity) => { return entity.funktion; }, (entity, value) => { entity.funktion = value; }, Editable.ADD, true, 3);
const GATTUNG = new DropDownColumn('Gattung', 'gattung', (entity) => { return entity.gattung ? entity.gattung.name : undefined; }, (entity, value) => { entity.gattung = value; }, GATTUNG_DROP, Editable.UPDATE, false);
const GERAUSCH = new BoolColumn('Geräusch', 'gerausch', (entity) => { return entity.sound; }, (entity, value) => { entity.sound = value; }, Editable.UPDATE, true);
const GESCHWINDIGKEIT = new NumberColumn('Geschwindigkeit', 'geschwindigkeit', (entity) => { return entity.geschwindigkeit; }, (entity, value) => { entity.geschwindigkeit = value; }, Editable.UPDATE, false, 300, 1);
const HERSTELLER = new AutoCompleteColumn('Hersteller', 'hersteller', (entity) => { return entity.hersteller ? entity.hersteller.name : undefined.name; }, (entity, value) => { entity.hersteller = value; }, HERSTELLER_DROP, Editable.UPDATE, true);
const I_MAX = new NumberColumn('I Max', 'iMax', (entity) => { return entity.iMax; }, (entity, value) => { entity.iMax = value; }, Editable.UPDATE, false, 1000, 1);
const INDEX = new NumberColumn('Index', 'index', (entity) => { return entity.index; }, (entity, value) => { entity.index = value; }, Editable.ADD, true, 3, 0);
const KAPAZITAT = new NumberColumn('Kapazität', 'kapazitat', (entity) => { return entity.kapazitat; }, (entity, value) => { entity.kapazitat = value; }, Editable.UPDATE, false, 3000, 1, 2);
const KAUFDATUM = new DateColumn('Kaufdatum', 'kaufdatum', (entity) => { return entity.kaufdatum; }, (entity, value) => { entity.kaufdatum = value; }, Editable.UPDATE, false);
const KESSELUBERDRUCK = new NumberColumn('Kesselüberdruck', 'kesseluberdruck', (entity) => { return entity.kesseluberdruck; }, (entity, value) => { entity.kesseluberdruck = value; }, Editable.UPDATE, false, 3000, 0, 2);
const KLASSE = new NumberColumn('Klasse', 'klasse', (entity) => { return entity.klasse; }, (entity, value) => { entity.klasse = value; }, Editable.UPDATE, false, 4, 0);
const KOLBENHUB = new NumberColumn('Kolbenhub', 'kolbenhub', (entity) => { return entity.kolbenhub; }, (entity, value) => { entity.kolbenhub = value; }, Editable.UPDATE, false, 3000, 1, 2);
const KONFIGURATION = new DropDownColumn('Konfiguration', 'konfiguration', (entity) => { return entity.konfiguration ; }, (entity, value) => { entity.konfiguration = value; }, KONFIGURATION_DROP, Editable.UPDATE, true);
const KUPPLUNG = new DropDownColumn('Kupplung', 'kupplung', (entity) => { return entity.kupplung ? entity.kupplung.name : undefined; }, (entity, value) => { entity.kupplung = value; }, KUPPLUNG_DROP, Editable.UPDATE, false);
const LANGE = new NumberColumn('Länge', 'lange', (entity) => { return entity.lange; }, (entity, value) => { entity.lange = value; }, Editable.UPDATE, false, 50, 1, 2);
const LEISTUNG = new NumberColumn('Leistung', 'leistung', (entity) => { return entity.leistung; }, (entity, value) => { entity.leistung = value; }, Editable.UPDATE, false, 10000, 0, 2);
const LEISTUNGSUBERTRAGUNG = new NumberColumn('Leistungsübertragung', 'leistungsubertragung', (entity) => { return entity.leistungsubertragung; }, (entity, value) => { entity.leistungsubertragung = value; }, Editable.UPDATE, false, 10000, 0, 2);
const LICHT = new DropDownColumn('Licht', 'licht', (entity) => { return entity.licht ? entity.licht.name : undefined; }, (entity, value) => { entity.licht = value; }, LICHT_DROP, Editable.UPDATE, false);
const MASSSTAB = new DropDownColumn('Maßstab', 'massstab', (entity) => { return entity.massstab ? entity.massstab.name : undefined; }, (entity, value) => { entity.massstab = value; }, MASSSTAB_DROP, Editable.UPDATE, false);
const MAXIMAL = new NumberColumn('Maximal', 'maximal', (entity) => { return entity.maximal; }, (entity, value) => { entity.maximal = value; }, Editable.UPDATE, false, 30);
const MINIMAL = new NumberColumn('Minimal', 'minimal', (entity) => { return entity.minimal; }, (entity, value) => { entity.minimal = value; }, Editable.UPDATE, false, 30);
const MITTELWAGEN = new NumberColumn('Mittelwagen', 'mittelwagen', (entity) => { return entity.mittelwagen; }, (entity, value) => { entity.mittelwagen = value; }, Editable.UPDATE, false, 30, 0);
const MOTOR_TYP = new DropDownColumn('MotorTyp', 'motorTyp', (entity) => { return entity.motorTyp ? entity.motorTyp.name : undefined; }, (entity, value) => { entity.motorTyp = value; }, MOTOR_TYP_DROP, Editable.UPDATE, false);
const MOTORBAUART = new TextColumn('Motorbauart', 'motorbauart', (entity) => { return entity.motorbauart; }, (entity, value) => { entity.motorbauart = value; }, Editable.UPDATE, false, 30);
const NAMEN = new TextColumn('Namen', 'name', (entity) => { return entity.name; }, (entity, value) => { entity.name = value; }, Editable.ADD, true, 30, '^[A-Z0-9.]+$');
const POSITION = new NumberColumn('Position', 'position', (entity) => { return entity.position; }, (entity, value) => { entity.position = value; }, Editable.UPDATE, false, 30, 0);
const PREIS = new NumberColumn('Preis', 'preis', (entity) => { return entity.preis; }, (entity, value) => { entity.preis = value; }, Editable.UPDATE, false, undefined, 0, 2);
const PRODUKT = new DropDownColumn('Produkt', 'produkt', (entity) => { return entity.produkt ? extractProduktValue(entity.produkt) : undefined; }, (entity, value) => { let parts = value.split('/'); entity.produkt.hersteller = parts[0]; entity.produkt.bestellNr = parts[1]; }, PRODUKT_DROP, Editable.UPDATE, false);
const PROGRAMMABLE = new BoolColumn('Programmable', 'programmable', (entity) => { return entity.programmable; }, (entity, value) => { entity.programmable = value; }, Editable.UPDATE, true);
const PROTOKOLL = new DropDownColumn('Protokoll', 'protokoll', (entity) => { return entity.protokoll ? entity.protokoll.name : undefined; }, (entity, value) => { entity.protokoll = value; }, PROTOKOLL_DROP, Editable.UPDATE, true);
const REICHWEITE = new NumberColumn('Reichweite', 'reichweite', (entity) => { return entity.reichweite; }, (entity, value) => { entity.reichweite = value; }, Editable.UPDATE, false, 3000, 0);
const REIHE = new NumberColumn('Reihe', 'reihe', (entity) => { return entity.reihe; }, (entity, value) => { entity.reihe = value; }, Editable.ADD, true, 1, 0);
const ROSTFLACHE = new NumberColumn('Rostfläche', 'rostflache', (entity) => { return entity.rostflache; }, (entity, value) => { entity.rostflache = value; }, Editable.UPDATE, false, 3000, 0, 2);
const SITZPLATZEKL1 = new NumberColumn('SitzplatzeKL1', 'sitzplatzeKL1', (entity) => { return entity.sitzplatzeKL1; }, (entity, value) => { entity.sitzplatzeKL1 = value; }, Editable.UPDATE, false, 300, 0);
const SITZPLATZEKL2 = new NumberColumn('SitzplatzeKL2', 'sitzplatzeKL2', (entity) => { return entity.sitzplatzeKL2; }, (entity, value) => { entity.sitzplatzeKL2 = value; }, Editable.UPDATE, false, 300, 0);
const SITZPLATZEKL3 = new NumberColumn('SitzplatzeKL3', 'sitzplatzeKL3', (entity) => { return entity.sitzplatzeKL3; }, (entity, value) => { entity.sitzplatzeKL3 = value; }, Editable.UPDATE, false, 300, 0);
const SITZPLATZEKL4 = new NumberColumn('SitzplatzeKL4', 'sitzplatzeKL4', (entity) => { return entity.sitzplatzeKL4; }, (entity, value) => { entity.sitzplatzeKL4 = value; }, Editable.UPDATE, false, 300, 0);
const SONDERMODELL = new DropDownColumn('Sonder Modell', 'sonderModell', (entity) => { return entity.sonderModell ? entity.sonderModell.name : undefined; }, (entity, value) => { entity.sonderModell = value; }, SONDERMODELL_DROP, Editable.UPDATE, false);
const SPAN = new NumberColumn('Span', 'span', (entity) => { return entity.span; }, (entity, value) => { entity.span = value; }, Editable.UPDATE, true, 16, 1);
const SPURWEITE = new DropDownColumn('Spurweite', 'spurweite', (entity) => { return entity.spurweite ? entity.spurweite.name : undefined; }, (entity, value) => { entity.spurweite = value; }, SPURWEITE_DROP, Editable.UPDATE, false);
const STATUS = new DropDownColumn('Status', 'status', (entity) => { return entity.status; }, (entity, value) => { entity.status = value; }, STATUS_DROP, Editable.UPDATE, false);
const STECKER = new DropDownColumn('Stecker', 'stecker', (entity) => { return entity.stecker; }, (entity, value) => { entity.stecker = value; }, STECKER_DROP, Editable.UPDATE, false);
const STEUERUNG = new DropDownColumn('Steuerung', 'steuerung', (entity) => { return entity.steuerung ? entity.steuerung.name : undefined; }, (entity, value) => { entity.steuerung = value; }, STEUERUNG_DROP, Editable.UPDATE, false);
const STUCK = new NumberColumn('Stück', 'stuck', (entity) => { return entity.stuck; }, (entity, value) => { entity.stuck = value; }, Editable.UPDATE, false, 300, 0);
const TELEFON = new PhoneColumn('Telefon', 'telefon', (entity) => { return entity.telefon; }, (entity, value) => { entity.telefon = value; }, Editable.UPDATE, false);
const TRIEBKOPF = new NumberColumn('Triebköpfe', 'triebkopf', (entity) => { return entity.triebkopf; }, (entity, value) => { entity.triebkopf = value; }, Editable.UPDATE, false, 2, 0);
const UBERHITZERFLACHE = new NumberColumn('Überhitzerfläche', 'uberhitzerflache', (entity) => { return entity.uberhitzerflache; }, (entity, value) => { entity.uberhitzerflache = value; }, Editable.UPDATE, false, 3000, 0, 2);
const UNTER_KATEGORIE = new DropDownColumn('Kategorie', 'unterKategorie', (entity) => { return entity.unterKategorie ? extractKategorieValue(entity.unterKategorie) : undefined; }, (entity, value) => { let parts = value.split('/'); entity.unterKategorie.kategorie = parts[0]; entity.unterKategorie.name = parts[1]; }, UNTER_KATEGORIE_DROP, Editable.UPDATE, true);
const URL = new URLColumn('Url', 'url', (entity) => { return entity.url; }, (entity, value) => { entity.url = value; }, Editable.UPDATE, false);
const VERDAMPFUNG = new NumberColumn('Verdampfung', 'verdampfung', (entity) => { return entity.verdampfung; }, (entity, value) => { entity.verdampfung = value; }, Editable.UPDATE, false, 3000, 0, 2);
const VORBILD = new DropDownColumn('Vorbild', 'vorbild', (entity) => { return entity.vorbild ? entity.vorbild.gattung.name : undefined; }, (entity, value) => { entity.vorbild = value; }, VORBILD_DROP, Editable.UPDATE, false);
const WAHRUNG = new DropDownColumn('Wahrung', 'wahrung', (entity) => { return entity.wahrung ? entity.wahrung.name : undefined; }, (entity, value) => { entity.wahrung = value; }, WAHRUNG_DROP, Editable.UPDATE, false);
const WASSERVORRAT = new NumberColumn('Wasservorrat', 'wasservorrat', (entity) => { return entity.wasservorrat; }, (entity, value) => { entity.wasservorrat = value; }, Editable.UPDATE, false, 3000, 0, 2);
const WERKSEINSTELLUNG = new NumberColumn('Werkseinstellung', 'werkseinstellung', (entity) => { return entity.werkseinstellung; }, (entity, value) => { entity.werkseinstellung = value; }, Editable.UPDATE, true, 65535, 1);
const WERT = new NumberColumn('Wert', 'wert', (entity) => { return entity.wert; }, (entity, value) => { entity.wert = value; }, Editable.UPDATE, false, 65535, 1);
const ZUG = new TextColumn('Zug', 'zug', (entity) => { return entity.zug; }, (entity, value) => { entity.zug = value; }, Editable.UPDATE, false, 30);
const ZUG_TYP = new DropDownColumn('Typ', 'typ', (entity) => { return entity.zugTyp.name; }, (entity, value) => { entity.zugTyp = value; }, ZUG_TYP_DROP, Editable.UPDATE, false);
const ZYLINDER = new NumberColumn('Zylinder', 'sylinder', (entity) => { return entity.zylinder; }, (entity, value) => { entity.zylinder = value; }, Editable.UPDATE, false, 100, 1);
