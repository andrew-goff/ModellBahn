package com.linepro.modellbahn.util;

import com.linepro.modellbahn.model.util.IDecoderCreator;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import javax.inject.Inject;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.linepro.modellbahn.model.IAchsfolg;
import com.linepro.modellbahn.model.IAntrieb;
import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IAufbau;
import com.linepro.modellbahn.model.IBahnverwaltung;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IEpoch;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.IKategorie;
import com.linepro.modellbahn.model.IKupplung;
import com.linepro.modellbahn.model.ILicht;
import com.linepro.modellbahn.model.IMassstab;
import com.linepro.modellbahn.model.IMotorTyp;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.IProtokoll;
import com.linepro.modellbahn.model.ISonderModell;
import com.linepro.modellbahn.model.ISpurweite;
import com.linepro.modellbahn.model.ISteuerung;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.IVorbild;
import com.linepro.modellbahn.model.IWahrung;
import com.linepro.modellbahn.model.IZug;
import com.linepro.modellbahn.model.IZugTyp;
import com.linepro.modellbahn.model.impl.Achsfolg;
import com.linepro.modellbahn.model.impl.Antrieb;
import com.linepro.modellbahn.model.impl.Artikel;
import com.linepro.modellbahn.model.impl.Aufbau;
import com.linepro.modellbahn.model.impl.Bahnverwaltung;
import com.linepro.modellbahn.model.impl.Decoder;
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





import com.linepro.modellbahn.model.util.AdressTyp;
import com.linepro.modellbahn.model.util.DecoderCreator;
import com.linepro.modellbahn.model.util.Konfiguration;
import com.linepro.modellbahn.model.util.LeistungsUbertragung;
import com.linepro.modellbahn.model.util.Status;
import com.linepro.modellbahn.persistence.IKey;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.IPersisterFactory;

public class DBPopulator {

    private final IPersisterFactory persisterFactory;

    private final Logger logger;

    @Inject
    public DBPopulator(IPersisterFactory persisterFactory, ILoggerFactory logManger) {
        this.persisterFactory = persisterFactory;
        this.logger = logManger.getLogger(getClass().getName());
    }

    public void populate() {
        logger.info("Start population");

        populateAchsfolg();
        populateAntrieb();
        populateAufbau();
        populateBahnverwaltung();
        populateEpoch();
        populateGattung();
        populateHersteller();
        populateKupplung();
        populateLicht();
        populateMassstab();
        populateMotorTyp();
        populateProtokoll();
        populateSonderModell();
        populateSpurweite();
        populateSteuerung();

        populateKategorie();

        populateWahrung();
        populateLand();

        populateDecoderTyp();

        populateVorbild();

        populateDecoder();

        populateProdukt();

        populateArtikel();

        populateZugTyp();

        populateZug();
    }

    protected void dump() {
        dump(Achsfolg.class);
        dump(Antrieb.class);
        dump(Aufbau.class);
        dump(Bahnverwaltung.class);
        dump(Epoch.class);
        dump(Gattung.class);
        dump(Hersteller.class);
        dump(Kupplung.class);
        dump(Licht.class);
        dump(Massstab.class);
        dump(MotorTyp.class);
        dump(Protokoll.class);
        dump(SonderModell.class);
        dump(Spurweite.class);
        dump(Steuerung.class);

        dump(Kategorie.class);

        dump(Wahrung.class);
        dump(Land.class);

        dump(DecoderTyp.class);

        dump(Vorbild.class);

        dump(Decoder.class);

        dump(Produkt.class);
        dump(ProduktTeil.class);

        dump(Artikel.class);

        dump(ZugTyp.class);
        dump(Zug.class);
        dump(ZugConsist.class);
    }

    private Achsfolg addAchsfolg(String namen, String bezeichnung) {
        return save(new Achsfolg(null, namen, bezeichnung, false));
    }

    private void populateAchsfolg() {
        addAchsfolg("1CD32T35", "(1'C)D 3'2'T35");
        addAchsfolg("1C1H2T", "1'C 1' h2t");
        addAchsfolg("1CH23T16", "1'C h2 3T16");
        addAchsfolg("1D1H2T", "1'D 1' h2t");
        addAchsfolg("1E1H323T28", "1'E 1' h3 2'3'T28");
        addAchsfolg("1EH222T26KAB5", "1'E h2 2'2T26 KAB5");
        addAchsfolg("1E1H2T", "1'E1' h2t");
        addAchsfolg("2C1H322T26", "2'C 1' h3 2'2'T26");
        addAchsfolg("2C122T26", "2'C1' 2'2'T26");
        addAchsfolg("2C1H322T40", "2'C1' h3 2'2'T40");
        addAchsfolg("A1DM", "A1 dm");
        addAchsfolg("AADM", "AA dm");
        addAchsfolg("BDRN", "B drn");
        addAchsfolg("BH2T", "B h2t");
        addAchsfolg("B2DH22222BDH", "B'2' dh 2'2' 2'2' 2'B' dh");
        addAchsfolg("BBDE", "B'B' de");
        addAchsfolg("BBDH", "B'B' dh");
        addAchsfolg("BB", "B'B'");
        addAchsfolg("BO2E", "Bo'2' e");
        addAchsfolg("BOBO22BOBO", "Bo'Bo'+2'2'+Bo'Bo'");
        addAchsfolg("BOBOBOBOBOBOBOBO", "Bo'Bo'+Bo'Bo'+Bo'Bo'+Bo'Bo'");
        addAchsfolg("CDH", "C dh");
        addAchsfolg("CH2T", "C h2t");
        addAchsfolg("CCDH", "C'C' dh");
        addAchsfolg("COCOW6GF", "Co'Co' w6gf");
        addAchsfolg("COCO", "Co'Co'");
        addAchsfolg("DH2T", "D h2t");
        addAchsfolg("DDH4VT", "D'D h4vt");
    }

    private Antrieb addAntrieb(String namen, String bezeichnung) {
        return save(new Antrieb(null, namen, bezeichnung, false));
    }

    private void populateAntrieb() {
        addAntrieb("AKKU", "Akku");
        addAntrieb("DAMPF", "Dampf");
        addAntrieb("DIESEL", "Diesel");
        addAntrieb("ELEKTRO", "Elektro");
        addAntrieb("DRUCKLUFT", "Druckluft");
    }

    private Artikel addArtikel(IProdukt produkt, LocalDate kaufdatum, IWahrung wahrung, BigDecimal preis, Integer stuck,
            ISteuerung steuerung, IMotorTyp motorTyp, ILicht licht, IKupplung kupplung, IDecoder decoder, String bezeichnung,
            String anmerkung, String beladung, Status status) {

        return save(new Artikel(null, produkt,  kaufdatum,  wahrung,  preis,  stuck,
            steuerung,  motorTyp,  licht,  kupplung,  decoder,
            persisterFactory.createPersister(Artikel.class).getNextId(),  bezeichnung,  anmerkung,
            beladung,  status, false));
    }

    private void populateArtikel() {
        IProdukt produkt = findProdukt("MARKLIN", "3000");
        IWahrung wahrung = findWahrung("HKD");
        ISteuerung steuerung = findSteuerung("FRU");
        IMotorTyp motorTyp = findMotorTyp("SFCM");
        ILicht licht = findLicht("L1V");
        IKupplung kupplung = findKupplung("RELEX");

        addArtikel(produkt, LocalDate.of(1967,1,1), wahrung, BigDecimal.valueOf(100.0), 1,
                steuerung, motorTyp, licht, kupplung, null,
                "", null, null, Status.GEKAUFT);
    }

    private Aufbau addAufbau(String namen, String bezeichnung) {
        return save(new Aufbau(null, namen, bezeichnung, false));
    }

    private void populateAufbau() {
        addAufbau("LK", "Fahrgestell der Lok aus Metall");
        addAufbau("LMK", "Fahrgestell und vorwiegender Aufbau der Loks aus Metall");
        addAufbau("LM", "Fahrgestell und Aufbau der Lokomotive aus Metall");
        addAufbau("WK", "Fahrgestell des Wagens aus Metall");
        addAufbau("WMK", "Überwiegender Teil des Wagenaufbaus aus Metall");
        addAufbau("WM", "Fahrgestell und Aufbau des Wagens aus Metall");
        addAufbau("LKM", "Überwiegender Teil des Lokaufbaues aus Metall");
    }

    private Bahnverwaltung addBahnverwaltung(String namen, String bezeichnung) {
        return save(new Bahnverwaltung(null, namen, bezeichnung, false));
    }

    private void populateBahnverwaltung() {
        addBahnverwaltung("AAE", "AAE");
        addBahnverwaltung("ADTRANZ", "ABB Daimler-Benz Transportation (ADtranz)");
        addBahnverwaltung("AL", "Anhaltische Leopoldsbahn (AL)");
        addBahnverwaltung("ALNO", "ALNO Küchen");
        addBahnverwaltung("AMTRAK", "American and Track (AMTRAK)");
        addBahnverwaltung("AR", "Alaska Railroad (ARR)");
        addBahnverwaltung("ATSF", "Atchison, Topeka and Santa Fe Railway (AT+SF)");
        addBahnverwaltung("AVE", "Alta Velocidad Española (AVE)");
        addBahnverwaltung("AVG", "Albtal-Verkehrs-Gesellschaft mbH (AVG)");
        addBahnverwaltung("ALASKA", "Alaska Railroad (ARR)");
        addBahnverwaltung("ALUSUISSE", "Schweizerische Aluminium AG (Alusuisse)");
        addBahnverwaltung("BA", "Boston and Albany Railway (B+A)");
        addBahnverwaltung("BO", "Baltomore and Ohio Railway (B+0)");
        addBahnverwaltung("BBO", "Österreichischen Bundesbahnen (ÖBB)");
        addBahnverwaltung("BHE", "Bebra-Hanauer Eisenbahn (BHE)");
        addBahnverwaltung("BLS", "Bern–Lötschberg–Simplon railway (BLS)");
        addBahnverwaltung("BN", "Burlington Northern Railroad (BN)");
        addBahnverwaltung("BVG", "Berliner Verkehrsbetriebe (BVG)");
        addBahnverwaltung("BuH", "Ruhrkohle AG Bahn und Hafen GmbH (RAG/BuH)");
        addBahnverwaltung("CG", "Columbus and Greenville Railway (C+G)");
        addBahnverwaltung("CBQ", "Chicago, Burlington and Quincy Railroad (CBQ)");
        addBahnverwaltung("CFL", "Société Nationale des Chemins de Fer Luxembourgeois (CFL)");
        addBahnverwaltung("CFV3V", "Chemin de Fer à Vapeur des 3 Vallées (CFV3V)");
        addBahnverwaltung("CIWL", "Compagnie Internationale des Wagons-Lits (CIWL)");
        addBahnverwaltung("CN", "Canadian National Railway (CN)");
        addBahnverwaltung("CONNEX", "CONNEX GmbH");
        addBahnverwaltung("DAB", "Dortmunder Actien-Brauerei (DAB)");
        addBahnverwaltung("DBAG", "Deutschen Bahn AG (DB AG)");
        addBahnverwaltung("DBAUTOZUG", "Deutschen Bahn AG AutoZug (DB AutoZug)");
        addBahnverwaltung("DBCARGO", "Deutschen Bahn AG Cargo (DB Cargo)");
        addBahnverwaltung("DBMUSEUM", "Deutschen Bundesbahn Museum (DB Museum)");
        addBahnverwaltung("DB", "Deutschen Bundesbahn (DB)");
        addBahnverwaltung("DBDR", "Deutschen Reichsbahn (DB/DR)");
        addBahnverwaltung("DBP", "Deutschen Bahnpost (DBP)");
        addBahnverwaltung("DL", "Dixie Line (DL)");
        addBahnverwaltung("DRDDR", "Deutschen Reichsbahn (DDR)");
        addBahnverwaltung("DR", "Deutschen Reichsbahn (DR)");
        addBahnverwaltung("DRB", "Deutschen Reichsbahn (DRB)");
        addBahnverwaltung("DRGGVBAY", "Deutschen Reichsbahn Gesellschaft (DRG)");
        addBahnverwaltung("DRG", "Deutschen Reichsbahn Gesellschaft (DRG)");
        addBahnverwaltung("DRGW", "Denver and Rio Grande Western Railroad (DRGW)");
        addBahnverwaltung("DSB", "Danske Statsbaner (DSB)");
        addBahnverwaltung("DSG", "Deutsche Schlafwagen und Speisewagengesellschaft (DSG)");
        addBahnverwaltung("EBOE", "Elmshorn-Barmstedt-Oldesloer Eisenbahn (EBOE)");
        addBahnverwaltung("ESG", "Eisenbahn-Service GmbH (ESG)");
        addBahnverwaltung("EST", " Société Nationale des Chemins de Fer Français (SNCF)");
        addBahnverwaltung("EVB", "Eisenbahnen und Verkehrsbetriebe Elbe-Weser GmbH (EVB)");
        addBahnverwaltung("EMD", "GM Electro Motive Division (EMD)");
        addBahnverwaltung("EUH", "„Eisenbahn + Häfen“");
        addBahnverwaltung("FSCARGO", "Ferrovie dello Stato Italiane Cargo (FS Cargo)");
        addBahnverwaltung("FS", "Ferrovie dello Stato Italiane (FS)");
        addBahnverwaltung("GATX", "GATX Corporation (GATX)");
        addBahnverwaltung("GBAG", "GB Netz der Deutschen Bahn AG (GBAG)");
        addBahnverwaltung("GBADSTE", "Großherzoglich Badische Staatseisenbahn (G.Bad.St.E)");
        addBahnverwaltung("GKM", "Grosskraftwerk Mannheim AG (GKM)");
        addBahnverwaltung("GOSTB", "Großherzoglich Oldenburgische Staatseisenbahn (G.O.St.B)");
        addBahnverwaltung("GSW", "Great Southwest Railroad (GSW)");
        addBahnverwaltung("GVB", "Gruppenverwaltung Bayern (GVB)");
        addBahnverwaltung("GHMFFE", "Großherzoglich Mecklenburgische Friedrich-Franz-Eisenbahn (MFFE)");
        addBahnverwaltung("HBS", "Herzoglich Braunschweigische Staatseisenbahn (HBS)");
        addBahnverwaltung("HEG", "Hersfelder Eisenbahn Gesellschaft mbH (HEG)");
        addBahnverwaltung("HNSTB", "Herzoglich Nassauische Staatsbahn (HNSt.B)");
        addBahnverwaltung("HANSA", "Hansabahn Dortmund");
        addBahnverwaltung("HENKEL", "Fa Henkel KGaA, Düsseldorf");
        addBahnverwaltung("ICG", "Illonois Central Railway (ICG)");
        addBahnverwaltung("IGE", "Internationalen Gesellschaft für Eisenbahnverkehr (IGE)");
        addBahnverwaltung("ILMEBAHN", "Ilmebahn GmbH");
        addBahnverwaltung("KBAYSTE", "Königlich Bayerischen Staatseisenbahnen (K.Bay.St.E)");
        addBahnverwaltung("KHSTB", "Königlich Hannöversche Staatseisenbahnen (K.H.St.B)");
        addBahnverwaltung("KPEV", "Königlich Preußische Eisenbahn-Verwaltung (K.P.E.V)");
        addBahnverwaltung("KPStE", "Königlich Preußische Staatseisenbahnen (K.P.St.E)");
        addBahnverwaltung("KB", "Deutschen Bundesbahn (DB)");
        addBahnverwaltung("KEG", "Karsdorfer Eisenbahngesellschaft GmbH (KEG)");
        addBahnverwaltung("KH", "Kraftwerk Herne");
        addBahnverwaltung("KLVM", "KLVM");
        addBahnverwaltung("KPUGHSTE", "Königlich Preußische und Großherzoglich Hessischen Staatseisenbahnen (K.Pu.G.H.St.E");
        addBahnverwaltung("KSACHSSTE", "Königlich Sächsische Staatseisenbahnen (K.Sächs.St.E)");
        addBahnverwaltung("KWSTE", "Königlich Württembergischen Staatseisenbahnen (K.W.St.E)");
        addBahnverwaltung("LAG", "Lokalbahn Aktien-Gesellschaft (LAG)");
        addBahnverwaltung("LMS", "London, Midland and Scottish Railway (LMS)");
        addBahnverwaltung("LNER", "London Northeast Railway (LNER)");
        addBahnverwaltung("LSE", "Luzern–Stans–Engelberg-Bahn (LSE)");
        addBahnverwaltung("MAV", "Magyar Államvasutak(MAV)");
        addBahnverwaltung("MFFE", "Großherzoglich Mecklenburgische Friedrich-Franz-Eisenbahn (MFFE)");
        addBahnverwaltung("MILW", "Chicago, Milwaukee, St Paul and Pacific Railroad (MILW)");
        addBahnverwaltung("MKO", "Museumseisenbahn Küstenbahn Ostfriesland (MKO)");
        addBahnverwaltung("MWB", "Mittelweserbahn GmbH (MWB)");
        addBahnverwaltung("MAKIESAG", "Firma „Makies“ AG");
        addBahnverwaltung("NB", "NordseeBahn (NB)");
        addBahnverwaltung("NEG", "Norddeutsche Eisenbahngesellschaft Niebüll GmbH (NEG)");
        addBahnverwaltung("NH", "New York, New Haven and Hartford Railroad (NH)");
        addBahnverwaltung("NL", "P and O Nedlloyd (NL)");
        addBahnverwaltung("NOHAB", "Nydqvist und Holm AB (NOHAB)");
        addBahnverwaltung("NSCARGO", "Nederlandse Spoorweggen Cargo (NS Cargo)");
        addBahnverwaltung("NS", "Nederlandse Spoorweggen (NS)");
        addBahnverwaltung("NSB", "Norske Statsbaner (NSB)");
        addBahnverwaltung("NYC", "New York Central Railroad (NYC)");
        addBahnverwaltung("NORDLINGEN", "das Bayerische Eisenbahnmuseum (Nördlingen)");
        addBahnverwaltung("OBB", "Österreichischen Bundesbahnen (ÖBB)");
        addBahnverwaltung("OHB", "Österreichischen Bundesbahnen (ÖBB)");
        addBahnverwaltung("ONR", "Ontario Northland Railway (ONR)");
        addBahnverwaltung("ONRAIL", "On Rail Gesellschaft für Eisenbahnausrüstung und Zubehör mbH (On Rail)");
        addBahnverwaltung("OPEL", "Opel AG");
        addBahnverwaltung("ORBECHAV", "Transport Vallée de Joux, Yverdon-les-bains et Sainte Croix (Travys)");
        addBahnverwaltung("PLE", "Pittsburgh and Lake Erie Railroad (P+LE)");
        addBahnverwaltung("PSTE", "Preußische Staatseisenbahnen (P.St.E)");
        addBahnverwaltung("PEG", "Prignitzer Eisenbahn-Gesellschaft (PEG)");
        addBahnverwaltung("PRR", "Pennsylvania Railroad (PRR)");
        addBahnverwaltung("PERSIL", "Fa Henkel KGaA, Düsseldorf");
        addBahnverwaltung("PFALZB", "Pfälzische Eisenbahnen (Pfalz.B)");
        addBahnverwaltung("PRIVAT", "Privatbahn");
        addBahnverwaltung("R", "Rutland Railroad (R)");
        addBahnverwaltung("RAG", "Ruhrkohle AG (RAG)");
        addBahnverwaltung("RCT", "Royal Corps of Transport (RCT)");
        addBahnverwaltung("REL", "Reichseisenbahn Elsaß-Lothringen (REL)");
        addBahnverwaltung("RENFE", "Red Nacional de Ferrocarriles Españoles (RENFE)");
        addBahnverwaltung("RAILBOUW", "Railbouw Leerdam Maatschappij");
        addBahnverwaltung("SBBCARGO", "Schweizerischen Bundesbahnen Cargo (SBB Cargo)");
        addBahnverwaltung("SBB", "Schweizerischen Bundesbahnen (SBB/CFF/FFS)");
        addBahnverwaltung("SCF", "Southern Central Freight Railroad (SCF)");
        addBahnverwaltung("SECO", "SECO Rail (SECO)");
        addBahnverwaltung("SJ", "Statens Järnvägar (SJ)");
        addBahnverwaltung("SKW", "Werkseisenbahn SKW Trostberg");
        addBahnverwaltung("SNCB", "Société Nationale des Chemins de Fer Belges (SNCB/NMBS)");
        addBahnverwaltung("SNCF", "Société Nationale des Chemins de Fer Français (SNCF)");
        addBahnverwaltung("SOB", "SüdOstBayernBahn (SOB)");
        addBahnverwaltung("SOO", "Soo Line Railroad (SOO)");
        addBahnverwaltung("SP", "Southern Pacific Railroad (SP)");
        addBahnverwaltung("SZD", "Sovetskie železnye dorogi (SžD)");
        addBahnverwaltung("SEEHKIEL", "Seehaven Kiel");
        addBahnverwaltung("SIEMENS", "Siemens AG");
        addBahnverwaltung("SOM", "State of Maine (SoM)");
        addBahnverwaltung("STORA", "Trafikaktiebolaget Grängesberg–Oxelösunds Järnvägar (Stora)");
        addBahnverwaltung("SUDDZUCKER", "Südzucker AG");
        addBahnverwaltung("TN", "Texas and Northern Railway (T+N)");
        addBahnverwaltung("TP", "Texas and Pacific Railway Company (T+P)");
        addBahnverwaltung("TAG", "Tegernsee-Bahn");
        addBahnverwaltung("TAGAB", "Tågåkeriet i Bergslagen AB (TÅGAB)");
        addBahnverwaltung("TGOJ", "Trafikaktiebolaget Grängesberg–Oxelösunds Järnvägar (TGOJ)");
        addBahnverwaltung("TKAB", "Tågkompaniet AB (TKAB)");
        addBahnverwaltung("TMR", "Texas Mexican Railway (TM)");
        addBahnverwaltung("TSO", "Travaux du Sud-Ouest (TSO)");
        addBahnverwaltung("TW", "Texas Western Railroad (TW)");
        addBahnverwaltung("TEGERNSEE", "„Tegernsee-Bahn“");
        addBahnverwaltung("UEF", "Ulmer Eisenbahnfreunde (UEF)");
        addBahnverwaltung("UP", "Union Pacific Railroad (UP)");
        addBahnverwaltung("USTC", "United States Transport Corps (USTC)");
        addBahnverwaltung("VTG", "VTG AG (VTG)");
        addBahnverwaltung("VW", "Volkswagen AG (VW)");
        addBahnverwaltung("WEG", "Württembergische Eisenbahngesellschaft (WEG)");
        addBahnverwaltung("WLE", "Westfälische Landes-Eisenbahn GmbH (WLE)");
        addBahnverwaltung("WP", "Western Pacific Railroad (WP)");
        addBahnverwaltung("WIEBE", "Wiebe Gleisbau GmbH (Wiebe)");
    }

    private void populateDecoder() {
        IPersister<IDecoder> persister = persisterFactory.createPersister(IDecoder.class);

        IDecoderCreator creator = new DecoderCreator(persister);

        IDecoderTyp decoderTyp = findDecoderTyp("ESU", "62400");

        try {
            creator.create(decoderTyp);
        } catch (Exception e) {
            logger.error("Failed to save decoder: " + e.getMessage(), e);
        }
    }

    /**
     * Populate decoder typ.
     */
    private void populateDecoderTyp() {
        IProtokoll delta = findProtokoll("DELTA");
        IProtokoll fx = findProtokoll("FX");
        IProtokoll mm = findProtokoll("MM");
        IProtokoll mfx = findProtokoll("MFX");
        IProtokoll weiche = findProtokoll("WEICHE");

        IHersteller digitalbahn = findHersteller("DIGITALBAHN");
        IHersteller esu = findHersteller("ESU");
        IHersteller marklin = findHersteller("MARKLIN");
        IHersteller uhlenbrock = findHersteller("UHLENBROCK");

        addDSD2010(weiche, digitalbahn);

        addLokPilotM4(mfx, esu, "61600", "LokPilot M4");
        addLokPilotM4(mfx, esu, "61601", "LokPilot M4 21MTC");
        addLokSoundM4(mfx, esu, "62400", "LokSound M4");
        addLokSoundM4(mfx, esu, "62499", "LokSound M4 21MTC");
        addLokPilotFX(mm, esu, "52620", "LokPilot FX");
        addLokPilotFX(mm, esu, "52621", "LokPilot FX 21MTC");
        addSwitchPilot(weiche, esu, "51800", "SwitchPilot");
        addSwitchPilot(weiche, esu, "51820", "SwitchPilot 2");
        addSwitchPilotServo(weiche, esu);

        addMarklinSoundDecoder(mfx, marklin, "103787", "103787");
        
        addMarklinDELTADecoder(mm, marklin, "49940", "49940");
        addMarklinDELTADecoder(mm, marklin, "49961", "49961");
        addMarklinDELTADecoder(delta, marklin, "602850", "602850");
        addMarklinDELTADecoder(delta, marklin, "603999", "603999");
        addMarklinDELTADecoder(mm, marklin, "606174", "606174");
        addMarklinDELTADecoder(delta, marklin, "6603", "Delta Modul");
        addMarklinDELTADecoder(delta, marklin, "66031", "Delta Modul mit Zusatzfunktion");
        addMarklinDELTADecoder(delta, marklin, "66032", "Delta Modul mit automatischer Systemerkennung");
        addMarklinDELTADecoder(delta, marklin, "670040", "670040");
    
        add46715(fx, marklin);
        add60760(fx, marklin);
        add115798(fx, marklin);
        add150436(fx, marklin);
        add219574(fx, marklin);
        add602756(fx, marklin);
        add608862(fx, marklin);
        add611105(fx, marklin);
        add611754(fx, marklin);

        add115166(mfx, marklin);
        add115673(mfx, marklin);
        add116836(mfx, marklin);
        add123572(mfx, marklin);
        add140131(mfx, marklin);
        add148924(mfx, marklin);
        add156787(mfx, marklin);
        add162946(mfx, marklin);
        add169274(mfx, marklin);
        add253201(mfx, marklin);
        add269706(mfx, marklin);
        add39970(mfx, marklin);
        add60902(mfx, marklin);
        add611077(mfx, marklin);

        add209394(mm, marklin);
        add42973(mm, marklin);
        add49960(mm, marklin);
        add606896(mm, marklin);
        add608825(mm, marklin);
    
        addWeicheDecoder(weiche, marklin, "74460", "Einbau-Digital-Decoder");
    
        addDrehscheibendekoder(weiche, marklin);
    
        addUhlenbrock67900(mm, uhlenbrock);
    }

    private DecoderTyp addDecoderTyp(IHersteller hersteller, IProtokoll protokoll, String bestellNr, String bezeichnung, Boolean sound, Konfiguration konfiguration) {
        return save(new DecoderTyp(null, hersteller, protokoll, bestellNr, bezeichnung, sound, konfiguration, false));
    }
    
    private void addAdress(IDecoderTyp decoderTyp, Integer index, AdressTyp adressTyp, Integer span, Integer werkseinstellung) {
        decoderTyp.addAdress(new DecoderTypAdress(null, decoderTyp, index, adressTyp, span, werkseinstellung, false));
    }
    
    private void addCV(IDecoderTyp decoderTyp, Integer cv, String bezeichnung, Integer minimal, Integer maximal, Integer werkseinstellung) {
        decoderTyp.addCV(new DecoderTypCV(null, decoderTyp, cv, bezeichnung, minimal, maximal, werkseinstellung, false));
    }

    private void addFunktion(IDecoderTyp decoderTyp, Integer reihe, String funktion, String bezeichnung, Boolean programmable) {
        decoderTyp.addFunktion(new DecoderTypFunktion(null, decoderTyp, reihe, funktion, bezeichnung, programmable, false));
    }
    
    private DecoderTyp add60760(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "60760", "Hochleistungsdecoder", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung/Bremsverzögerung", 1, 63, 16);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);

        return update(decoderTyp);
    }

    private DecoderTyp add46715(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "46715", "46715", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        addFunktion(decoderTyp, 0, "F1", "Kranhaus drehen", false);
        addFunktion(decoderTyp, 0, "F2", "Kranausleger Heben heben", false);
        addFunktion(decoderTyp, 0, "F3", "Haken heben", false);

        return update(decoderTyp);
    }

    private DecoderTyp addWeicheDecoder(IProtokoll weiche, IHersteller marklin, String bestellNr, String bezeichnung) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, weiche, bestellNr, bezeichnung, false, Konfiguration.SWITCH);

        addAdress(decoderTyp, 1, AdressTyp.WEICHE, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 255, null);

        return update(decoderTyp);
    }

    private DecoderTyp addDrehscheibendekoder(IProtokoll weiche, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, weiche, "7687", "Drehscheibendekoder", false, Konfiguration.LINK);

        addAdress(decoderTyp, 1, AdressTyp.WEICHE, 1, 16);

        addCV(decoderTyp, 1, "Adresse", 14, 15, 14);

        addFunktion(decoderTyp, 0, "F0", "End", false);
        addFunktion(decoderTyp, 0, "F1", "Input", false);
        addFunktion(decoderTyp, 0, "F2", "Clear", false);
        addFunktion(decoderTyp, 0, "F3", "180", false);
        addFunktion(decoderTyp, 0, "F4", "Step >", false);
        addFunktion(decoderTyp, 0, "F5", "Step <", false);
        addFunktion(decoderTyp, 0, "F6", "Spoke 1", false);
        addFunktion(decoderTyp, 0, "F7", "Spoke 2", false);
        addFunktion(decoderTyp, 0, "F8", "Spoke 3", false);
        addFunktion(decoderTyp, 0, "F9", "Spoke 3", false);
        addFunktion(decoderTyp, 0, "F10", "Spoke 4", false);
        addFunktion(decoderTyp, 0, "F11", "Spoke 5", false);
        addFunktion(decoderTyp, 0, "F12", "Spoke 6", false);
        addFunktion(decoderTyp, 0, "F13", "Spoke 7", false);
        addFunktion(decoderTyp, 0, "F14", "Spoke 8", false);
        addFunktion(decoderTyp, 0, "F15", "Spoke 9", false);

        return update(decoderTyp);
    }

    private DecoderTyp addUhlenbrock67900(IProtokoll mm, IHersteller uhlenbrock) {
        DecoderTyp decoderTyp = addDecoderTyp(uhlenbrock, mm, "67900", "67900", false, Konfiguration.SWITCH);

        addAdress(decoderTyp, 1, AdressTyp.MM, 1, 8);

        addCV(decoderTyp, 1, "Adresse", 1, 127, 3);
        addCV(decoderTyp, 2, "Minimale Geschwindigkeit", 1, 63, 5);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, 2);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, 2);
        addCV(decoderTyp, 5, "Maximale Geschwindigkeit", 1, 63, 20);
        addCV(decoderTyp, 6, "Maximale Motorspannung", 1, 255, 64);
        addCV(decoderTyp, 7, "Softwareversion", null, null, null);
        addCV(decoderTyp, 8, "Herstellerkennung", null, null, 85);
        addCV(decoderTyp, 17, "Lange Lokadresse MSB", 192, 231, 199);
        addCV(decoderTyp, 18, "Lange Lokadresse LSB", 0, 255, 208);
        addCV(decoderTyp, 20, "Konfiguration beider Motoren nach DCC-Norm", 0, 33, 0);
        addCV(decoderTyp, 49, "Decoder-Konfiguration", 0, 195, 0);
        addCV(decoderTyp, 65, "Motorola Programmierung Reihe", 0, 255, 0);
        addCV(decoderTyp, 67, "Maximale Geschwindigkeit für Taste 1", 0, 255, 40);
        addCV(decoderTyp, 68, "Maximale Geschwindigkeit für Taste 2", 0, 255, 40);
        addCV(decoderTyp, 69, "Maximale Geschwindigkeit für Taste 3", 0, 255, 50);
        addCV(decoderTyp, 70, "Maximale Geschwindigkeit für Taste 4", 0, 255, 50);
        addCV(decoderTyp, 71, "Anfahrverzögerung für Taste 1", 0, 255, 5);
        addCV(decoderTyp, 72, "Anfahrverzögerung für Taste 2", 0, 255, 5);
        addCV(decoderTyp, 73, "Anfahrverzögerung für Taste 3", 0, 255, 5);
        addCV(decoderTyp, 74, "Anfahrverzögerung für Taste 4", 0, 255, 5);
        addCV(decoderTyp, 75, "Bremsverzögerung für Taste 1", 0, 255, 1);
        addCV(decoderTyp, 76, "Bremsverzögerung für Taste 2", 0, 255, 1);
        addCV(decoderTyp, 77, "Bremsverzögerung für Taste 3", 0, 255, 1);
        addCV(decoderTyp, 78, "Bremsverzögerung für Taste 4", 0, 255, 1);
        addCV(decoderTyp, 79, "Maximale Motorspannung im Analogbetrieb", 0, 255, 180);
        addCV(decoderTyp, 98, "Zeitbegrenztes Einschalten der Ausgänge A1 + A2", 0, 3, 3);
        addCV(decoderTyp, 99, "Maximale Einschaltzeit in Sekunden", 0, 255, 45);

        addFunktion(decoderTyp, 0, "F0", "Kranhaken/Laufkatze", false);
        addFunktion(decoderTyp, 0, "F1", "F1", false);
        addFunktion(decoderTyp, 0, "F2", "F2", false);
        addFunktion(decoderTyp, 0, "F3", "F3", false);
        addFunktion(decoderTyp, 0, "F4", "F4", false);

        return update(decoderTyp);
    }
    
    private DecoderTyp addMarklinDELTADecoder(IProtokoll mm, IHersteller marklin, String bestellNr, String bezeichnung) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mm, bestellNr, bezeichnung, false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DELTA, 1, 80);
        
        addCV(decoderTyp, 1, "Adresse", 1, 80, 11);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);

        return update(decoderTyp);
    }

    private DecoderTyp addSwitchPilotServo(IProtokoll weiche, IHersteller esu) {
        DecoderTyp decoderTyp = addDecoderTyp(esu, weiche, "51802", "SwitchPilot Servo", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.WEICHE, 1, 4);

        addCV(decoderTyp, 1, "Decoderadresse 1, LSB", 1, 63, 1);
        addCV(decoderTyp, 7, "Versionsnummer", null, null, 153);
        addCV(decoderTyp, 8, "Herstellerkennung", null, null, 151);
        addCV(decoderTyp, 9, "Decoderadresse 1, MSB", 0, 7, 0);
        addCV(decoderTyp, 28, "RailCom Konfiguration", 0, 6, 0);
        addCV(decoderTyp, 29, "Konfigurationsregister", 128, 136, 128);
        addCV(decoderTyp, 37, "Servo 1, Drehgeschwindigkeit", 1, 63, 15);
        addCV(decoderTyp, 38, "Servo 1, Stellung „A“", 1, 63, 24);
        addCV(decoderTyp, 39, "Servo 1, Stellung „B“", 1, 63, 40);
        addCV(decoderTyp, 40, "Servo 2, Drehgeschwindigkeit", 1, 63, 15);
        addCV(decoderTyp, 41, "Servo 2, Stellung „A“", 1, 63, 24);
        addCV(decoderTyp, 42, "Servo 2, Stellung „B“", 1, 63, 40);
        addCV(decoderTyp, 43, "Servo 3, Drehgeschwindigkeit", 1, 63, 15);
        addCV(decoderTyp, 44, "Servo 3, Stellung „A“", 1, 63, 24);
        addCV(decoderTyp, 45, "Servo 3, Stellung „B“", 1, 63, 40);
        addCV(decoderTyp, 46, "Servo 4, Drehgeschwindigkeit", 1, 63, 15);
        addCV(decoderTyp, 47, "Servo 4, Stellung „A“", 1, 63, 24);
        addCV(decoderTyp, 48, "Servo 4, Stellung „B“", 1, 63, 40);

        addFunktion(decoderTyp, 0, "S1", "Servo 1", false);
        addFunktion(decoderTyp, 0, "S2", "Servo 2", false);
        addFunktion(decoderTyp, 0, "S3", "Servo 3", false);
        addFunktion(decoderTyp, 0, "S4", "Servo 4", false);

        return update(decoderTyp);
    }

    private DecoderTyp addSwitchPilot(IProtokoll weiche, IHersteller esu, String bestellNr, String bezeichnung) {
        DecoderTyp decoderTyp = addDecoderTyp(esu, weiche, bestellNr, bezeichnung, false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.WEICHE, 1, 8);
        addAdress(decoderTyp, 2, AdressTyp.WEICHE, 1, 2);

        addCV(decoderTyp, 1, "Decoderadresse 1, LSB", 1, 63, 1);
        addCV(decoderTyp, 3, "Konfiguration Ausgang 1", 0, 64, 8);
        addCV(decoderTyp, 4, "Konfiguration Ausgang 2", 0, 64, 8);
        addCV(decoderTyp, 5, "Konfiguration Ausgang 3", 0, 64, 8);
        addCV(decoderTyp, 6, "Konfiguration Ausgang 4", 0, 64, 8);
        addCV(decoderTyp, 7, "Versionsnummer", null, null, 115);
        addCV(decoderTyp, 8, "Herstellerkennung", null, null, 151);
        addCV(decoderTyp, 9, "Decoderadresse 1, MSB", 0, 7, 0);
        addCV(decoderTyp, 28, "RailCom Konfiguration", 0, 6, 0);
        addCV(decoderTyp, 29, "Konfigurationsregister", 128, 136, 128);
        addCV(decoderTyp, 33, "Funktionsausgangsstatus", 0, 255, null);
        addCV(decoderTyp, 34, "„Zoom“-Konfiguration", 0, 15, 0);
        addCV(decoderTyp, 35, "Decoderadresse 2, LSB", 1, 63, 1);
        addCV(decoderTyp, 36, "Decoderadresse 2, MSB", 0, 8, 8);
        addCV(decoderTyp, 37, "Servo 1, Drehgeschwindigkeit", 1, 63, 15);
        addCV(decoderTyp, 38, "Servo 1, Stellung „A“", 1, 63, 24);
        addCV(decoderTyp, 39, "Servo 1, Stellung „B“", 1, 63, 40);
        addCV(decoderTyp, 40, "Servo 2, Drehgeschwindigkeit", 1, 63, 15);
        addCV(decoderTyp, 41, "Servo 2, Stellung „A“", 1, 63, 24);
        addCV(decoderTyp, 42, "Servo 2, Stellung „B“", 1, 63, 40);

        addFunktion(decoderTyp, 0, "K1", "Funktion 1", false);
        addFunktion(decoderTyp, 0, "K2", "Funktion 2", false);
        addFunktion(decoderTyp, 0, "K3", "Funktion 3", false);
        addFunktion(decoderTyp, 0, "K4", "Funktion 4", false);
        addFunktion(decoderTyp, 0, "S1", "Servo 1", false);
        addFunktion(decoderTyp, 0, "S2", "Servo 2", false);

        return update(decoderTyp);
    }

    private DecoderTyp addLokPilotFX(IProtokoll mm, IHersteller esu, String bestellNr, String bezeichnung) {
        DecoderTyp decoderTyp = addDecoderTyp(esu, mm, bestellNr, bezeichnung, false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);
        addAdress(decoderTyp, 2, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        addFunktion(decoderTyp, 0, "F0", "Funktion 0", false);
        addFunktion(decoderTyp, 0, "F1", "Funktion 1", false);
        addFunktion(decoderTyp, 0, "F2", "Funktion 2", false);
        addFunktion(decoderTyp, 0, "F3", "Funktion 3", false);
        addFunktion(decoderTyp, 0, "F4", "Funktion 4", false);

        return update(decoderTyp);
    }

    private DecoderTyp addLokSoundM4(IProtokoll mfx, IHersteller esu, String bestellNr, String bezeichnung) {
        DecoderTyp decoderTyp = addDecoderTyp(esu, mfx, bestellNr, bezeichnung, true, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);
        addAdress(decoderTyp, 2, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 3);
        addCV(decoderTyp, 2, "Anfahrverzögerung", 1, 63, 3);
        addCV(decoderTyp, 3, "Beschleunigungszeit", 1, 63, 16);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, 12);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, 63);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 53, "Regelungsreferenz", 1, 63, 56);
        addCV(decoderTyp, 54, "Lastregelung Param. K", 1, 63, 32);
        addCV(decoderTyp, 55, "Lastregelung Param. L", 1, 63, 24);
        addCV(decoderTyp, 56, "Regelungseinfluss", 1, 63, 63);
        addCV(decoderTyp, 57, "Geräuschmodus 1", 1, 63, 10);
        addCV(decoderTyp, 58, "Geräuschmodus 2", 1, 63, 58);
        addCV(decoderTyp, 59, "Fahrgeräusch", 1, 63, 32);
        addCV(decoderTyp, 60, "Fahrgeräusch", 1, 63, 55);
        addCV(decoderTyp, 63, "Geräuschlautstärke", 1, 63, 63);
        addCV(decoderTyp, 64, "Bremssoundschwelle", 1, 63, 7);
        addCV(decoderTyp, 73, "Speicheroptionen", 0, 7, 3);
        addCV(decoderTyp, 74, "Märklin Addresse 2", 1, 63, null);
        addCV(decoderTyp, 75, "Märklin Addresse 2", 1, 80, 4);
        addCV(decoderTyp, 78, "Anfahrspannung Analog AC", 1, 63, 25);
        addCV(decoderTyp, 79, "Höchstgeschwindigkeit Analog AC", 1, 63, 63);

        addFunktion(decoderTyp, 0, "F0", "Funktion 0", false);
        addFunktion(decoderTyp, 0, "F1", "Funktion 1", false);
        addFunktion(decoderTyp, 0, "F2", "Funktion 2", false);
        addFunktion(decoderTyp, 0, "F3", "Funktion 3", false);
        addFunktion(decoderTyp, 0, "F4", "Funktion 4", false);
        addFunktion(decoderTyp, 0, "F5", "Funktion 5", false);
        addFunktion(decoderTyp, 0, "F6", "Funktion 6", false);
        addFunktion(decoderTyp, 0, "F7", "Funktion 7", false);
        addFunktion(decoderTyp, 0, "F8", "Funktion 8", false);
        addFunktion(decoderTyp, 0, "F9", "Funktion 9", false);
        addFunktion(decoderTyp, 0, "F10", "Funktion 10", false);
        addFunktion(decoderTyp, 0, "F11", "Funktion 11", false);
        addFunktion(decoderTyp, 0, "F12", "Funktion 12", false);
        addFunktion(decoderTyp, 0, "F13", "Funktion 13", false);
        addFunktion(decoderTyp, 0, "F14", "Funktion 14", false);
        addFunktion(decoderTyp, 0, "F15", "Funktion 15", false);

        return update(decoderTyp);
    }

    private DecoderTyp addLokPilotM4(IProtokoll mfx, IHersteller esu, String bestellNr, String bezeichnung) {
        DecoderTyp decoderTyp = addDecoderTyp(esu, mfx, bestellNr, bezeichnung, false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 3);
        addCV(decoderTyp, 2, "Anfahrverzögerung", 1, 63, 3);
        addCV(decoderTyp, 3, "Beschleunigungszeit", 1, 63, 16);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, 12);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, 63);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 53, "Regelungsreferenz", 1, 63, 45);
        addCV(decoderTyp, 54, "Lastregelung Param. K", 1, 63, 32);
        addCV(decoderTyp, 55, "Lastregelung Param. L", 1, 63, 24);
        addCV(decoderTyp, 56, "Regelungseinfluss", 1, 63, 63);
        addCV(decoderTyp, 73, "Speicheroptionen", 0, 7, 3);
        addCV(decoderTyp, 75, "Märklin Addresse 2", 1, 80, 4);
        addCV(decoderTyp, 78, "Anfahrspannung Analog AC", 1, 63, 25);
        addCV(decoderTyp, 79, "Höchstgeschwindigkeit Analog AC", 1, 63, 63);

        addFunktion(decoderTyp, 0, "F0", "Funktion 0", false);

        return update(decoderTyp);
    }

    private DecoderTyp addMarklinSoundDecoder(IProtokoll mfx, IHersteller marklin, String bestellNr, String bezeichnung) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, bestellNr, bezeichnung, false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 10);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Spitzensignal", false);
        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Rauchgenerator", false);
        addFunktion(decoderTyp, 0, "F2", "Lokpfeife", false);
        addFunktion(decoderTyp, 0, "F3", "Fahrgeräusch", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Luftpumpe", false);
        addFunktion(decoderTyp, 0, "F6", "Triebwerksbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F7", "Bremsenquietschen aus", false);
        addFunktion(decoderTyp, 0, "F8", "Rangierpfeife", false);
        addFunktion(decoderTyp, 0, "F9", "Dampf ablassen", false);

        return update(decoderTyp);
    }
    private DecoderTyp add115798(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "115798", "115798", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 255, null);
        addCV(decoderTyp, 2, "Anfahrverzögerung/Bremsverzögerung", 1, 31, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung/Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        return update(decoderTyp);
    }

    private DecoderTyp add150436(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "150436", "150436", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 255, 38);
        addCV(decoderTyp, 3, "Anfahrverzögerung/Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);

        return update(decoderTyp);
    }

    private DecoderTyp add219574(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "219574", "219574", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 45);
        addCV(decoderTyp, 2, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Rauchgenerator", false);
        addFunktion(decoderTyp, 0, "F11", "Schüttelrost", false);
        addFunktion(decoderTyp, 0, "F2", "Lokpfeife", false);
        addFunktion(decoderTyp, 0, "F3", "Dampftriebwerk", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Luftpumpe", false);
        addFunktion(decoderTyp, 0, "F6", "Feuerschein - Feuerbüchse", false);
        addFunktion(decoderTyp, 0, "F7", "Bremsenquietschen", false);
        addFunktion(decoderTyp, 0, "F8", "Rangierpfiff", false);
        addFunktion(decoderTyp, 0, "F9", "Dampf ablassen", false);
        addFunktion(decoderTyp, 0, "F10", "Kohleschaufeln", false);

        return update(decoderTyp);
    }

    private DecoderTyp add602756(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "602756", "602756", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 2, "Lautstärke", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Schleuderrad Geräusch", false);
        addFunktion(decoderTyp, 0, "F2", "Schleuderrad", false);
        addFunktion(decoderTyp, 0, "F3", "Pfeife", false);
        addFunktion(decoderTyp, 0, "F4", "Signalstreckenlampen", false);

        return update(decoderTyp);
    }

    private DecoderTyp add608862(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "608862", "608862", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 10);
        addCV(decoderTyp, 2, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Maschinenraumbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F2", "Stromabnehmer vorn", false);
        addFunktion(decoderTyp, 0, "F3", "Stromabnehmer hinten", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);

        return update(decoderTyp);
    }

    private DecoderTyp add611105(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "611105", "611105", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 71);
        addCV(decoderTyp, 2, "Bedienung festgelegt", 0, 1, 0);
        addCV(decoderTyp, 3, "Baggerschaufel Zeitbegrenzung", 0, 1, 0);

        addFunktion(decoderTyp, 0, "F0", "Kohleschaufe schließen", false);
        addFunktion(decoderTyp, 0, "F1", "Laufgestell Motoren", false);
        addFunktion(decoderTyp, 0, "F2", "Führerhaus Beleuchtung", false);
        addFunktion(decoderTyp, 0, "F3", "Kohleschaufe Heben", false);
        addFunktion(decoderTyp, 0, "F4", "Führerhaus Drehen", false);

        return update(decoderTyp);
    }

    private DecoderTyp add611754(IProtokoll fx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, fx, "611754", "611754", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 2, "Anfahrverzögerung/Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F2", "Telex-Kupplung", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);

        return update(decoderTyp);
    }

    private DecoderTyp add115166(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "115166", "115166", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung / Innenbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Schlusslicht ausschalten", false);
        addFunktion(decoderTyp, 0, "F2", "Dieselmotor und Bremse", false);
        addFunktion(decoderTyp, 0, "F3", "Signalhorn", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Bremsenquietschen", false);
        addFunktion(decoderTyp, 0, "F6", "Türe Zu", false);
        addFunktion(decoderTyp, 0, "F7", "Glocke", false);
        addFunktion(decoderTyp, 0, "F8", "Abfahrtspfiff", false);

        return update(decoderTyp);
    }

    private DecoderTyp add115673(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "115673", "115673", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 64);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, 63);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, 63);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, 63);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, null);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, 63);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Rauchgenerator", false);
        addFunktion(decoderTyp, 0, "F2", "Betriebsgeräusch", false);
        addFunktion(decoderTyp, 0, "F3", "Pfeife", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Luftpumpe", false);
        addFunktion(decoderTyp, 0, "F6", "Kohleschaufeln", false);
        addFunktion(decoderTyp, 0, "F7", "Glocke", false);
        addFunktion(decoderTyp, 0, "F8", "Dampf ablassen", false);
        addFunktion(decoderTyp, 0, "F9", "Bremsenquietschen", false);
        addFunktion(decoderTyp, 0, "F10", "Schüttelrost", false);

        return update(decoderTyp);
    }

    private DecoderTyp add116836(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "116836", "116836", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 70);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F2", "Betriebsgeräusch", false);
        addFunktion(decoderTyp, 0, "F3", "Signalhorn", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Bremsenquietschen", false);
        addFunktion(decoderTyp, 0, "F6", "Metallsäge", false);
        addFunktion(decoderTyp, 0, "F7", "Hämmern", false);
        addFunktion(decoderTyp, 0, "F8", "Winkelschleifer", false);
        addFunktion(decoderTyp, 0, "F9", "Elektroschweißen", false);
        addFunktion(decoderTyp, 0, "F10", "Schleifbock", false);

        return update(decoderTyp);
    }

    private DecoderTyp add123572(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "123572", "123572", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 0, 80, 42);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 0, 63, 63);
        addCV(decoderTyp, 4, "Bremsverzögerung", 0, 63, 63);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 0, 63, 63);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, null);
        addCV(decoderTyp, 63, "Lautstärke", 0, 63, 63);


        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung / Innenbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F2", "Bahnhofsansage", false);
        addFunktion(decoderTyp, 0, "F3", "Signalhorn", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);

        return update(decoderTyp);
    }

    private DecoderTyp add140131(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "140131", "140131", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung / Schlusslicht", false);
        addFunktion(decoderTyp, 0, "F1", "Schlusslicht ausschalten", false);
        addFunktion(decoderTyp, 0, "F2", "Betriebsgeräusch", false);
        addFunktion(decoderTyp, 0, "F3", "Signalhorn", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Druckluft ablassen", false);
        addFunktion(decoderTyp, 0, "F6", "Bremsenquietschen", false);

        return update(decoderTyp);
    }

    private DecoderTyp add148924(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "148924", "148924", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 2, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);


        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Schlusslicht ausschalten", false);
        addFunktion(decoderTyp, 0, "F2", "Betriebsgeräusch", false);
        addFunktion(decoderTyp, 0, "F3", "Horn", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Druckluft ablassen", false);
        addFunktion(decoderTyp, 0, "F6", "Bremsenquietschen", false);
        addFunktion(decoderTyp, 0, "F7", "Führerstandsbeleuchtung vorn", false);
        addFunktion(decoderTyp, 0, "F8", "Führerstandsbeleuchtung hinten", false);

        return update(decoderTyp);
    }

    private DecoderTyp add156787(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "156787", "156787", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 49);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Innenbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F2", "Begrüßungs-Ansage", false);
        addFunktion(decoderTyp, 0, "F3", "Pfeife", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Innenbeleuchtung dimmen", false);

        return update(decoderTyp);
    }

    private DecoderTyp add162946(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "162946", "162946", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 11);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Innenbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F2", "Betriebsgeräusch ", false);
        addFunktion(decoderTyp, 0, "F3", "Horn", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Bremsenquietschen", false);
        addFunktion(decoderTyp, 0, "F6", "Bahnhofsansage", false);
        addFunktion(decoderTyp, 0, "F7", "Türe zu", false);
        addFunktion(decoderTyp, 0, "F8", "Schaffnerpfiff", false);
        addFunktion(decoderTyp, 0, "F9", "Hilfsdiesel", false);
        addFunktion(decoderTyp, 0, "F10", "Lüfter", false);
        addFunktion(decoderTyp, 0, "F11", "Kompressor", false);
        addFunktion(decoderTyp, 0, "F12", "Überdruckventil", false);
        addFunktion(decoderTyp, 0, "F13", "Druckluft ablassen", false);

        return update(decoderTyp);
    }

    private DecoderTyp add169274(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "169274", "169274", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 0, 80, 43);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 0, 63, 63);
        addCV(decoderTyp, 4, "Bremsverzögerung", 0, 63, 63);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 0, 63, 63);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, null);
        addCV(decoderTyp, 63, "Lautstärke", 0, 63, 63);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Tischlampen", false);
        addFunktion(decoderTyp, 0, "F2", "Betriebsgeräusch", false);
        addFunktion(decoderTyp, 0, "F3", "Horn", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);
        addFunktion(decoderTyp, 0, "F5", "Bremsenquietschen", false);
        addFunktion(decoderTyp, 0, "F6", "Schaffnerpfiff", false);
        addFunktion(decoderTyp, 0, "F7", "Türen", false);
        addFunktion(decoderTyp, 0, "F8", "Bahnhofsansage", false);
        addFunktion(decoderTyp, 0, "F9", "Rangierpfiff", false);

        return update(decoderTyp);
    }

    private DecoderTyp add253201(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "253201", "253201", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Innenbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F1", "Start / Stopp", false);
        addFunktion(decoderTyp, 0, "F2", "Pause", false);
        addFunktion(decoderTyp, 0, "F3", "ein Lied vor", false);
        addFunktion(decoderTyp, 0, "F4", "ein Lied zurück", false);
        addFunktion(decoderTyp, 0, "F5", "Lauter", false);
        addFunktion(decoderTyp, 0, "F6", "Leiser", false);
        addFunktion(decoderTyp, 0, "F7", "Lichtorgel an / aus", false);
        addFunktion(decoderTyp, 0, "F8", "Barbeleuchtung an / aus", false);
        addFunktion(decoderTyp, 0, "F9", "Strom führende Kupplung", false);
        addFunktion(decoderTyp, 0, "F10", "Stroboskop", false);
        addFunktion(decoderTyp, 0, "F11", "Umgebungsgeräusch 1", false);
        addFunktion(decoderTyp, 0, "F12", "Umgebungsgeräusch 2", false);
        addFunktion(decoderTyp, 0, "F13", "Betriebsgeräusch 1", false);
        addFunktion(decoderTyp, 0, "F14", "Betriebsgeräusch 2", false);
        addFunktion(decoderTyp, 0, "F15", "Betriebsgeräusch 3", false);

        return update(decoderTyp);
    }

    private DecoderTyp add269706(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "269706", "269706", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 50, "Protokolle", 1, 15, 15);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Spitzensignal", false);
        addFunktion(decoderTyp, 0, "F1", "Innenbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F2", "Fahrgeräusch", false);
        addFunktion(decoderTyp, 0, "F3", "Signalton", false);
        addFunktion(decoderTyp, 0, "F4", "Direktsteuerung", false);
        addFunktion(decoderTyp, 0, "F5", "Bremsenquietschen aus", false);
        addFunktion(decoderTyp, 0, "F6", "Spitzensignal Lokseite 2", false);
        addFunktion(decoderTyp, 0, "F7", "Schaffnerpfiff", false);
        addFunktion(decoderTyp, 0, "F8", "Spitzensignal Lokseite 1", false);
        addFunktion(decoderTyp, 0, "F9", "Türenschließen", false);
        addFunktion(decoderTyp, 0, "F10", "Schienenstoß", false);
        addFunktion(decoderTyp, 0, "F11", "Bahnhofsansage", false);
        addFunktion(decoderTyp, 0, "F12", "Dialog", false);
        addFunktion(decoderTyp, 0, "F13", "Dialog", false);
        addFunktion(decoderTyp, 0, "F14", "Dialog", false);
        addFunktion(decoderTyp, 0, "F15", "Dialog ", false);

        return update(decoderTyp);
    }

    private DecoderTyp add39970(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "39970", "39970", true, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);

        addFunktion(decoderTyp, 0, "F1", "Arbeitsbühne heben", false);
        addFunktion(decoderTyp, 0, "F2", "Arbeitsbühne schwenken", false);
        addFunktion(decoderTyp, 0, "F3", "Stromabnehmer", false);
        addFunktion(decoderTyp, 0, "F4", "Initialisierung", false);

        return update(decoderTyp);
    }

    private DecoderTyp add60902(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "60902", "Hochleistungselektronik", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 2, "Anfahrverzögerung", 1, 63, 3);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);

        return update(decoderTyp);
    }

    private DecoderTyp add611077(IProtokoll mfx, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, mfx, "611077", "611077", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung/Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Anfahrverzögerung/Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);

        return update(decoderTyp);
    }

    private DecoderTyp add209394(IProtokoll protokoll, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, protokoll, "209394", "209394", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", null, null, 54);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);

        return update(decoderTyp);
    }

    private DecoderTyp add42973(IProtokoll protokoll, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, protokoll, "42973", "42973", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, 63);

        addFunktion(decoderTyp, 0, "F2", "Pantograf", false);
        addFunktion(decoderTyp, 0, "F3", "Geräusch einer Schaffner", false);

        return update(decoderTyp);
    }


    private DecoderTyp add49960(IProtokoll protokoll, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, protokoll, "49960", "49960", true, Konfiguration.SWITCH);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);

        addFunktion(decoderTyp, 0, "F1", "Meßbereich", false);
        addFunktion(decoderTyp, 0, "F2", "Meßbereich", false);
        addFunktion(decoderTyp, 0, "F3", "Maßeinheit", false);
        addFunktion(decoderTyp, 0, "F4", "Anzeigen", false);

        return update(decoderTyp);
    }


    private DecoderTyp add606896(IProtokoll protokoll, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, protokoll, "606896", "606896", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, null);
        addCV(decoderTyp, 2, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);

        addFunktion(decoderTyp, 0, "F0", "Strinbeleuchtung", false);
        addFunktion(decoderTyp, 0, "F4", "ABV", false);

        return update(decoderTyp);
    }

    private DecoderTyp add608825(IProtokoll protokoll, IHersteller marklin) {
        DecoderTyp decoderTyp = addDecoderTyp(marklin, protokoll, "608825", "608825", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.DIGITAL, 1, 1);

        addCV(decoderTyp, 1, "Adresse", 1, 80, 39);
        addCV(decoderTyp, 2, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 3, "Anfahrverzögerung", 1, 63, null);
        addCV(decoderTyp, 4, "Bremsverzögerung", 1, 63, null);
        addCV(decoderTyp, 5, "Höchstgeschwindigkeit", 1, 63, null);
        addCV(decoderTyp, 8, "Rückstellen auf Serienwerte", null, null, 8);
        addCV(decoderTyp, 63, "Lautstärke", 1, 63, null);

        return update(decoderTyp);
    }

    private DecoderTyp addDSD2010(IProtokoll weiche, IHersteller digitalbahn) {
        DecoderTyp decoderTyp = addDecoderTyp(digitalbahn, weiche, "DSD2010", "Drehscheibendekoder", false, Konfiguration.CV);

        addAdress(decoderTyp, 1, AdressTyp.WEICHE, 1, 16);

        addCV(decoderTyp, 1, "48 / 24 Positions", 0, 1, 1);
        addCV(decoderTyp, 2, "DCC / Motorola", 0, 1, 1);
        addCV(decoderTyp, 3, "Position specification", 0, 1, 0);

        addFunktion(decoderTyp, 0, "K0", "Licht", false);
        addFunktion(decoderTyp, 0, "K1", "Step", false);
        addFunktion(decoderTyp, 0, "K2", "Turn", false);
        addFunktion(decoderTyp, 0, "K3", "Richtung", false);
        addFunktion(decoderTyp, 0, "K4", "Spoke 1", false);
        addFunktion(decoderTyp, 0, "K5", "Spoke 2", false);
        addFunktion(decoderTyp, 0, "K6", "Spoke 3", false);
        addFunktion(decoderTyp, 0, "K7", "Spoke 3", false);
        addFunktion(decoderTyp, 0, "K8", "Spoke 4", false);
        addFunktion(decoderTyp, 0, "K9", "Spoke 5", false);
        addFunktion(decoderTyp, 0, "K10", "Spoke 6", false);
        addFunktion(decoderTyp, 0, "K11", "Spoke 7", false);
        addFunktion(decoderTyp, 0, "K12", "Spoke 8", false);
        addFunktion(decoderTyp, 0, "K13", "Spoke 9", false);
        addFunktion(decoderTyp, 0, "K14", "Spoke 10", false);
        addFunktion(decoderTyp, 0, "K15", "Spoke 11", false);

        return update(decoderTyp);
    }

    private Epoch addEpoch(String namen, String bezeichnung) {
        return save(new Epoch(null, namen, bezeichnung, false));
    }

    private void populateEpoch() {
        addEpoch("I", "I : 1835 - 1920");
        addEpoch("II", "II : 1920 - 1950");
        addEpoch("III", "III : 1949 - 1970");
        addEpoch("IV", "IV : 1965 - 1990");
        addEpoch("V", "V : 1990 - 2006");
        addEpoch("VI", "VI : 2006 -");
        addEpoch("IA", "Ia : 1835 - 1875");
        addEpoch("IB", "Ib : 1875 - 1895");
        addEpoch("IC", "Ic : 1895 - 1910");
        addEpoch("ID", "Id : 1910 - 1920");
        addEpoch("IIA", "IIa : 1920 - 1925");
        addEpoch("IIB", "IIb : 1925 - 1937");
        addEpoch("IIC", "IIc : 1937 - 1950");
        addEpoch("IIIA", "IIIa : 1949 - 1956");
        addEpoch("IIIB", "IIIb : 1956 - 1970");
        addEpoch("IVA", "IVa : 1965 - 1970");
        addEpoch("IVB", "IVb : 1970 – 1980");
        addEpoch("IVC", "IVc : 1980 – 1990");
        addEpoch("VA", "Va : 1990 - 1994");
        addEpoch("VB", "Vb : 1994 - 2000");
        addEpoch("VC", "Vc : 2000 - 2006");
    }

    private Gattung addGattung(String namen, String bezeichnung) {
        return save(new Gattung(null, namen, bezeichnung, false));
    }

    private void populateGattung() {
        addGattung("AB3YGE", "AB3YGE : AB3yg");
        addGattung("ADUMH101", "ADümh 101");
        addGattung("ARDUMZ106", "ARDümz 106");
        addGattung("APUMH121", "Apümh 121");
        addGattung("AVMZ206", "Avmz 206");
        addGattung("AVUMH111", "Avümh 111");
        addGattung("AUM203", "Aüm 203");
        addGattung("B3YGE", "B3yg");
        addGattung("B4YGE", "B4yg");
        addGattung("BA115", "BA 115");
        addGattung("BD3YGE", "BD3yg");
        addGattung("BR03", "BR 03");
        addGattung("BR03.10", "BR 03.10");
        addGattung("BR103", "BR 103");
        addGattung("BR111", "BR 111");
        addGattung("BR151", "BR 151");
        addGattung("BR211", "BR 211");
        addGattung("BR216", "BR 216");
        addGattung("BR220", "BR 220");
        addGattung("BR230", "BR 230");
        addGattung("BR24", "BR 24");
        addGattung("BR260", "BR 260");
        addGattung("BR321", "BR 321");
        addGattung("BR45", "BR 45");
        addGattung("BR50", "BR 50");
        addGattung("BR53", "BR 53");
        addGattung("BR601", "BR 601");
        addGattung("BR64", "BR 64");
        addGattung("BR701", "BR 701");
        addGattung("BR81", "BR 81");
        addGattung("BR85", "BR 85");
        addGattung("BR86", "BR 86");
        addGattung("BR89.0", "BR 89.0");
        addGattung("BR96", "BR 96");
        addGattung("BR98.3", "BR 98.3");
        addGattung("BT10", "BT 10");
        addGattung("BTMM51", "BTmm 51");
        addGattung("BI18T", "Bi 18t");
        addGattung("BI", "Bi");
        addGattung("DBYG546", "DByg 546");
        addGattung("DHG700C", "DHG 700C");
        addGattung("ELD4", "ELD4");
        addGattung("ET403", "ET 403");
        addGattung("ET91", "ET 91");
        addGattung("ELU061", "El-u 061");
        addGattung("F7", "F7");
        addGattung("G10", "G 10");
        addGattung("GL", "Gl");
        addGattung("GMHS53", "Gmhs 53");
        addGattung("GR20", "Gr 20");
        addGattung("GS210", "Gs 210");
        addGattung("H10", "H 10");
        addGattung("ICRA10", "ICR-A10");
        addGattung("ICRB10", "ICR-B10");
        addGattung("IBDLPS383", "Ibdlps 383");
        addGattung("ICHQRS377", "Ichqrs 377");
        addGattung("KBS443", "Kbs 443");
        addGattung("KKLM505", "Kklm 505");
        addGattung("LAAE540", "Laae 540");
        addGattung("MANNSCHAFT376", "Mannschaftswagen 376");
        addGattung("NS6400", "NS 6400");
        addGattung("OOTZ50", "OOtz 50");
        addGattung("OM", "Om „Breslau“");
        addGattung("OM12", "Om 12");
        addGattung("OTMM70", "Otmm 70");
        addGattung("PW90HZL", "Pw 90 HzL");
        addGattung("PWGPR14", "Pwg Pr 14");
        addGattung("PWGS41", "Pwgs 41");
        addGattung("PWI28", "Pwi 28");
        addGattung("PWIWU13", "Pwi Wü 13");
        addGattung("R02", "R 02");
        addGattung("RLMMPS651", "Rlmmps 651");
        addGattung("RLMMS58", "Rlmms 58");
        addGattung("RLMMS", "Rlmms");
        addGattung("RLMMSO56", "Rlmmso 56");
        addGattung("RS684", "Rs 684");
        addGattung("SSH71", "SSH 71");
        addGattung("SSYM", "SSym „Köln“");
        addGattung("SAMMS709", "Samms 709");
        addGattung("SCHOTTERWAGEN166", "Schotterwagen 166");
        addGattung("SM24", "Sm 24");
        addGattung("TEHS50", "Tehs 50");
        addGattung("TKO02", "Tko 02");
        addGattung("UCS", "Ucs");
        addGattung("V200", "V 200");
        addGattung("V80", "V 80");
        addGattung("VS98", "VS 98");
        addGattung("VT75", "VT 75");
        addGattung("VT95", "VT 95");
        addGattung("VT98", "VT 98");
        addGattung("VIEH", "Viehtransport");
        addGattung("WGMH824", "WGmh 824");
        addGattung("WRMZ135", "WRmz 135");
        addGattung("WRUMH131", "WRümh 131");
        addGattung("X05", "X05 „Erfurt“");
        addGattung("ZCES", "Zces");
        addGattung("UM312", "üm 312");
        addGattung("UM313", "üm 313");
    }

    private URL getURL(String url) {
        try {
            if (url != null ) return new URL(url);
        } catch (MalformedURLException e) {
            logger.error("Invalid url: " + url, e);
        }
        return null;
    }

    private Hersteller addHersteller( String name, String bezeichnung, String url, String telefon) {
        return save(new Hersteller(null, name, bezeichnung, getURL(url), telefon, false));
    }

    private void populateHersteller() {
        addHersteller("AVAGO", "Avago Technologies", "https://www.broadcom.com/", null);
        addHersteller("4MFOR", "4MFOR", "https://www.maerklin.de/", "+49 (0) 71 61 608-0");
        addHersteller("ARTITEC", "Artitec", "http://www.artitec.nl", null);
        addHersteller("AUHAGEN", "Auhagen", "https://auhagen.de/", null);
        addHersteller("BK", "Bochmann und Kochendörfer", null, null);
        addHersteller("BRAWA", "Brawa", "https://www.brawa.de/", null);
        addHersteller("BUSCH", "Busch Model", "https://www.busch-model.info/", null);
        addHersteller("DCC", "DCC Supplies", "https://www.dccsupplies.com/", null);
        addHersteller("DELUXE", "Deluxe Materials", "https://deluxematerials.co.uk/", null);
        addHersteller("DIGITALBAHN", "Digital-Bahn", "https://www.digital-bahn.de/", null);
        addHersteller("DIGITRAINS", "DIGITRAINS Ltd", "https://www.digitrains.co.uk/", null);
        addHersteller("DIOTEC", "Diotec Semiconductor AG", "https://diotec.com/en/home.html", null);
        addHersteller("ERBERT", "Erbert Signale", "http://www.erbert-signale.de/", null);
        addHersteller("ESU", "Electronic Solutions Ulm", "http://www.esu.eu/", null);
        addHersteller("EVERGREEN", "Evergreen Scale Models", "https://evergreenscalemodels.com/", null);
        addHersteller("FAIRCHILD", "Fairchild Semiconductors", null, null);
        addHersteller("FALLER", "Faller", "https://www.faller.de/", null);
        addHersteller("FLEISCHMANN", "Fleischmann", "https://www.fleischmann.de/", null);
        addHersteller("GASSNER", "Gaßner Beschriftungen", "https://www.gassner-beschriftungen.de/", null);
        addHersteller("GAUGEMASTER", "Gauge Master", "http://www.gaugemaster.com/index.html", null);
        addHersteller("HEICO", "Heico Modell", "http://www.heico-modell.de/", null);
        addHersteller("HERKAT", "Herkat", "http://www.herkat.de/", null);
        addHersteller("HERPA", "Herpa", "https://www.herpa.de/", null);
        addHersteller("HUMBROL", "Humbrol", "https://www.humbrol.com/uk-en/", null);
        addHersteller("JORDAN", "Jordan Modellbau", "http://www.jordan-modellbau.de/", null);
        addHersteller("KIBRI", "Kibri", "https://viessmann-modell.com/", null);
        addHersteller("KINGBRIGHT", "Kingbright", "https://kingbright-europe.de/", null);
        addHersteller("KKPMO", "KK Produkcja", "http://www.kkpmo.com/", null);
        addHersteller("KNIPEX", "KNIPEX", "https://www.knipex.com/nc/en/home/", null);
        addHersteller("KUHN", "Kühn Digital", "http://www.kuehn-digital.de/", null);
        addHersteller("LILIPUT", "Liliput", "http://liliput.de/", null);
        addHersteller("LIMA", "Lima", "https://www.hornby.com/", null);
        addHersteller("LDT", "Littfinski Daten Technik", "https://www.ldt-infocenter.com/", null);
        addHersteller("LUX", "LUX-Modellbau", "https://www.lux-modellbau.de/", null);
        addHersteller("MAQUETT", "Maquett", "https://www.maquett.nl/", null);
        addHersteller("MARKLIN", "Märklin", "https://www.maerklin.de/", "+49 (0) 71 61 608-0");
        addHersteller("MEHANO", "Mehano", "http://www.mehano.si/", null);
        addHersteller("MERTEN", "Merten", "https://www.preiser.de/", null);
        addHersteller("NOCH", "Noch", "https://www.noch.de/", null);
        addHersteller("OMRON", "Omron", "https://www.omron.com/", null);
        addHersteller("POLA", "POLA", "https://www.faller.de/", null);
        addHersteller("PREISER", "Preiser", "https://www.preiser.de/", null);
        addHersteller("PROSES", "Proses", "https://proses.com/prestashop/", null);
        addHersteller("RATIO", "Ratio", "https://peco-uk.com/pages/ratio", null);
        addHersteller("REDLINE", "Red Line", "http://www.redline.com/", null);
        addHersteller("REVELL", "Revell", "https://www.revell.de/", null);
        addHersteller("RIVAROSSI", "Rivarossi", "https://www.hornby.com/uk-en/shop/brands/rivarossi-h0-1-87.html", null);
        addHersteller("ROCO", "Roco", "https://www.roco.cc/en/home/index.html", null);
        addHersteller("SCHUCO", "Schuco", "https://www.schuco.de/", null);
        addHersteller("SEUTHE", "Seuthe Dampf", "http://seuthe-dampf.de/", null);
        addHersteller("TAIWAN", "Taiwan Semiconductor", null, null);
        addHersteller("TAMIYA", "Tamiya", "https://tamiya.com", null);
        addHersteller("TAMS", "Tams Elektronik", "https://tams-online.de/de_DE", null);
        addHersteller("TOWERPRO", "Tower Pro", "http://www.towerpro.com.tw/", null);
        addHersteller("TRIX", "Trix", "https://www.trix.de/", "+49 (0) 71 61 608-0");
        addHersteller("TRUOPTO", "TruOpto", "https://www.rapidonline.com/brands/truopto", null);
        addHersteller("UHLENBROCK", "Uhlenbrock", "https://www.uhlenbrock.de/", null);
        addHersteller("UNBEKANT", "", null, null);
        addHersteller("VIESSMANN", "Viessmann Modell", "https://viessmann-modell.com/", null);
        addHersteller("WALTHERS", "Walthers", "https://www.faller.de/", null);
        addHersteller("WEINERT", "Weinert Modellbau", "https://weinert-modellbau.de/", null);
        addHersteller("WIKING", "Wiking", "https://wiking.de/", null);
        addHersteller("WOODLAND", "Woodland Scenics", "https://woodlandscenics.woodlandscenics.com/", null);
        addHersteller("ZIMO", "Zimo", "http://www.zimo.at", null);
    }

    private Kategorie addKategorie(String name, String bezeichnung) {
        return save(new Kategorie(null, name, bezeichnung, false));
    }

    private void addUnterKategorie(IKategorie kategorie, String name, String bezeichnung) {
        kategorie.addUnterKategorie(new UnterKategorie(null, kategorie, name, bezeichnung, false));
    }

    private void populateKategorie() {
        Kategorie kategorie = addKategorie("AUSGESTALTUNG", "Ausgestaltung");

        addUnterKategorie(kategorie, "AUSGESTALTUNG", "Ausgestaltung");
        addUnterKategorie(kategorie, "BLUHMEN", "Blühmen");
        addUnterKategorie(kategorie, "BAUME", "Bäume");
        addUnterKategorie(kategorie, "BUSCHE", "Büsche");
        addUnterKategorie(kategorie, "FIGUREN", "Figuren");
        addUnterKategorie(kategorie, "HECKEN", "Hecken");
        addUnterKategorie(kategorie, "LADEGUT", "Ladegut");
        addUnterKategorie(kategorie, "PFLANZEN", "Pflanzen");
        addUnterKategorie(kategorie, "ZEICHEN", "Zeichen");
        addUnterKategorie(kategorie, "ZAUNE", "Zäune");

        update(kategorie);

        kategorie = addKategorie("BELEUCHTUNG", "Beleuchtung");

        addUnterKategorie(kategorie, "BELEUCHTUNG", "Beleuchtung");
        addUnterKategorie(kategorie, "GLUEHLAMPE", "Gluehlampe");
        addUnterKategorie(kategorie, "INNENBELEUCHTUNG", "Innenbeleuchtung");
        addUnterKategorie(kategorie, "LEUCHTEINSATZ", "Leuchteinsatz");
        addUnterKategorie(kategorie, "STROMFUHRENDEKUPPLUNGEN", "Stromführendekupplungen");
        addUnterKategorie(kategorie, "STROMZUFUHRUNG", "Stromzuführung");
        addUnterKategorie(kategorie, "ZUGSCHLUSSBELEUCHTUNG", "Zugschlussbeleuchtung");

        update(kategorie);

        kategorie = addKategorie("DECODER", "Decoder");

        addUnterKategorie(kategorie, "DECODER", "Decoder");
        addUnterKategorie(kategorie, "LAUTSPRECHER", "Lautsprecher");

        update(kategorie);

        kategorie = addKategorie("ERSATZTEIL", "Ersatzteil");

        addUnterKategorie(kategorie, "ANKER", "Anker");
        addUnterKategorie(kategorie, "BESCHWERUNG", "Beschwerung");
        addUnterKategorie(kategorie, "DREHGESTELLRAHMEN", "Drehgestellrahmen");
        addUnterKategorie(kategorie, "DREHGESTELL", "Drehgestell");
        addUnterKategorie(kategorie, "ENTSTORDROSSEL", "Entstördrossel");
        addUnterKategorie(kategorie, "ERSATZTEIL", "Ersatzteil");
        addUnterKategorie(kategorie, "FEDER", "Feder");
        addUnterKategorie(kategorie, "FELDMAGNET", "Feldmagnet");
        addUnterKategorie(kategorie, "FENSTER", "Fenster");
        addUnterKategorie(kategorie, "GRUNDPLATTE", "Grundplatte");
        addUnterKategorie(kategorie, "HAFTREIFEN", "Haftreifen");
        addUnterKategorie(kategorie, "HALTEPLATTE", "Halteplatte");
        addUnterKategorie(kategorie, "INNENEINRICHTUNG", "Inneneinrichtung");
        addUnterKategorie(kategorie, "ISOLIERUNG", "Isolierung");
        addUnterKategorie(kategorie, "KABEL", "Kabel");
        addUnterKategorie(kategorie, "KABELKLEMMEN", "Kabelklemmen");
        addUnterKategorie(kategorie, "KLAPPE", "Klappe");
        addUnterKategorie(kategorie, "KOHLBURSTEN", "Kohlbursten");
        addUnterKategorie(kategorie, "KUPPELSTANGE", "Kuppelstange");
        addUnterKategorie(kategorie, "KUPPLUNG", "Kupplung");
        addUnterKategorie(kategorie, "KUPPLUNGSDEICHSEL", "Kupplungsdeichsel");
        addUnterKategorie(kategorie, "KUPPLUNGSHAKEN", "Kupplungshaken");
        addUnterKategorie(kategorie, "KUPPLUNGSKINEMATIK", "Kupplungskinematik");
        addUnterKategorie(kategorie, "KUPPLUNGSSCHACHT", "Kupplungsschacht");
        addUnterKategorie(kategorie, "KURZKUPPLUNG", "Kurzkupplung");
        addUnterKategorie(kategorie, "LEITSCHAUFEL", "Leitschaufel");
        addUnterKategorie(kategorie, "LOKUMBAUSATZE", "Lokumbausätze");
        addUnterKategorie(kategorie, "MASSEFEDER", "Massefeder");
        addUnterKategorie(kategorie, "MESSINGBLECH", "Messingblech");
        addUnterKategorie(kategorie, "MOTORSCHILD", "Motorschild");
        addUnterKategorie(kategorie, "MUTTER", "Mutter");
        addUnterKategorie(kategorie, "PANTOGRAPH", "Pantograph");
        addUnterKategorie(kategorie, "PRALLPLATTE", "Prallplatte");
        addUnterKategorie(kategorie, "PUFFER", "Puffer");
        addUnterKategorie(kategorie, "RAD", "Rad");
        addUnterKategorie(kategorie, "RELAIS", "Relais");
        addUnterKategorie(kategorie, "RELEXKUPPLUNG", "Relexkupplung");
        addUnterKategorie(kategorie, "SCHALTSFEDER", "Schaltsfeder");
        addUnterKategorie(kategorie, "SCHLEIFER", "Schleifer");
        addUnterKategorie(kategorie, "SCHRAUBE", "Schraube");
        addUnterKategorie(kategorie, "SCHRAUBENKUPPLUNG", "Schraubenkupplung");
        addUnterKategorie(kategorie, "SENKSCHRAUBE", "Senkschraube");
        addUnterKategorie(kategorie, "STANGE", "Stange");
        addUnterKategorie(kategorie, "TELEX", "Telex");
        addUnterKategorie(kategorie, "TRAEGER", "Traeger");
        addUnterKategorie(kategorie, "WAGENBODEN", "Wagenboden");
        addUnterKategorie(kategorie, "WEICHENFEDER", "Weichenfeder");
        addUnterKategorie(kategorie, "ZUGFEDER", "Zugfeder");
        addUnterKategorie(kategorie, "ZYLINDERSCHRAUBE", "Zylinderschraube");

        update(kategorie);

        kategorie = addKategorie("FAHRZEUG", "Fahrzeug");

        addUnterKategorie(kategorie, "FAHRZEUG", "Fahrzeug");

        update(kategorie);

        kategorie = addKategorie("GEBAUDE", "Gebaüde");

        addUnterKategorie(kategorie, "BEKOHLUNG", "Bekohlung");
        addUnterKategorie(kategorie, "BOCKKRAN", "Bockkrän");
        addUnterKategorie(kategorie, "DREHSCHEIBE", "Drehscheibe");
        addUnterKategorie(kategorie, "GEBAUDE", "Gebaüde");

        update(kategorie);

        kategorie = addKategorie("GLEISMATERIEL", "Gleismateriel");

        addUnterKategorie(kategorie, "GLEISMATERIEL", "Gleismateriel");

        update(kategorie);

        kategorie = addKategorie("LANDSCHAFTSBAU", "Landschaftsbau");

        addUnterKategorie(kategorie, "LANDSCHAFTSBAU", "Landschaftsbau");

        update(kategorie);

        kategorie = addKategorie("LOKOMOTIV", "Lokomotiv");

        addUnterKategorie(kategorie, "LOKOMOTIV", "Lokomotiv");
        addUnterKategorie(kategorie, "AKKU", "Akku");
        addUnterKategorie(kategorie, "DAMPF", "Dampf");
        addUnterKategorie(kategorie, "DIESEL", "Diesel");
        addUnterKategorie(kategorie, "ELEKTRO", "Elektro");

        update(kategorie);

        kategorie = addKategorie("OBERLEITUNG", "Oberleitung");

        addUnterKategorie(kategorie, "OBERLEITUNG", "Oberleitung");

        update(kategorie);

        kategorie = addKategorie("SET", "Set");

        addUnterKategorie(kategorie, "SET", "Set");

        update(kategorie);

        kategorie = addKategorie("SIGNALTECHNIK", "Signaltechnik");

        addUnterKategorie(kategorie, "SIGNALBIRNE", "Signalbirne");
        addUnterKategorie(kategorie, "SIGNALTECHNIK", "Signaltechnik");

        update(kategorie);

        kategorie = addKategorie("SONSTIGES", "Sonstiges");

        addUnterKategorie(kategorie, "SONSTIGES", "Sonstiges");

        update(kategorie);

        kategorie = addKategorie("STEUERUNGSTECHNIK", "Steuerungstechnik");

        addUnterKategorie(kategorie, "STEUERUNGSTECHNIK", "Steuerungstechnik");
        addUnterKategorie(kategorie, "STROMVERSORGUNG", "Stromversorgung");

        update(kategorie);

        kategorie = addKategorie("TREIBWAGEN", "Treibwagen");

        addUnterKategorie(kategorie, "BEIWAGEN", "Beiwagen");
        addUnterKategorie(kategorie, "MITTELWAGEN", "Mittelwagen");
        addUnterKategorie(kategorie, "STEURWAGEN", "Steurwagen");
        addUnterKategorie(kategorie, "TREIBWAGEN", "Treibwagen");

        update(kategorie);

        kategorie = addKategorie("WAGEN", "Wagen");

        addUnterKategorie(kategorie, "ABTEIL", "Abteilwagen");
        addUnterKategorie(kategorie, "AUSSICHTS", "Aussichtswagen");
        addUnterKategorie(kategorie, "AUTOTRANSPORT", "Autotransportwagen");
        addUnterKategorie(kategorie, "BAHNDIENST", "Bahndienstwagen");
        addUnterKategorie(kategorie, "BANANEN", "Bananenwagen");
        addUnterKategorie(kategorie, "BAR", "Barwagen");
        addUnterKategorie(kategorie, "BEHALTERTRAG", "Behältertragwagen");
        addUnterKategorie(kategorie, "BIER", "Bierwagen");
        addUnterKategorie(kategorie, "CARBIDFLASCHEN", "Carbid-Flaschenwagen");
        addUnterKategorie(kategorie, "CONTAINERTRAG", "Containertragwagen");
        addUnterKategorie(kategorie, "DOPPELSTOCK", "Doppelstockwagen");
        addUnterKategorie(kategorie, "DREHSCHEMEL", "Drehschemelwagen");
        addUnterKategorie(kategorie, "FAHRRADTRANSPORT", "Fahrradtransportwagen");
        addUnterKategorie(kategorie, "FLACH", "Flachwagen");
        addUnterKategorie(kategorie, "GAS", "Gaswagen");
        addUnterKategorie(kategorie, "GEDECKTERGUTER", "Gedeckter Güterwagen");
        addUnterKategorie(kategorie, "GEPACK", "Gepäckwagen");
        addUnterKategorie(kategorie, "GESELLSCHAFTS", "Gesellschaftswagen");
        addUnterKategorie(kategorie, "GROSSRAUM", "Großraumwagen");
        addUnterKategorie(kategorie, "GUTER", "Güterwagen");
        addUnterKategorie(kategorie, "GUTERZUGBEGLEIT", "Güterzugbegleitwagen");
        addUnterKategorie(kategorie, "HOCHBORD", "Hochbordwagen");
        addUnterKategorie(kategorie, "KESSEL", "Kesselwagen");
        addUnterKategorie(kategorie, "KRAN", "Kränwagen");
        addUnterKategorie(kategorie, "KUHL", "Kühlwagen");
        addUnterKategorie(kategorie, "MANNSCHAFTS", "Mannschaftswagen");
        addUnterKategorie(kategorie, "MESSE", "Messewagen");
        addUnterKategorie(kategorie, "NAHVERKEHRS", "Nahverkehrswagen");
        addUnterKategorie(kategorie, "NIEDERBORD", "Niederbordwagen");
        addUnterKategorie(kategorie, "ROLLDACH", "Rolldachwagen");
        addUnterKategorie(kategorie, "SCHIEBEWAND", "Schiebewandwagen");
        addUnterKategorie(kategorie, "SCHNEEPFLUG", "Schneepflug");
        addUnterKategorie(kategorie, "SCHOTTER", "Schotterwagen");
        addUnterKategorie(kategorie, "SCHWERLAST", "Schwerlastwagen");
        addUnterKategorie(kategorie, "SCHUTTGUTKIPP", "Schüttgut-Kippwagen");
        addUnterKategorie(kategorie, "SEITENENTLADE", "Seitenentladewagen");
        addUnterKategorie(kategorie, "SILO", "Silowagen");
        addUnterKategorie(kategorie, "SONDERFAHRZEUG", "Sonderfahrzeug");
        addUnterKategorie(kategorie, "SPEISE", "Speisewagen");
        addUnterKategorie(kategorie, "TASCHEN", "Taschenwagen");
        addUnterKategorie(kategorie, "TIEFLADE", "Tiefladewagen");
        addUnterKategorie(kategorie, "TORPEDOPFANNEN", "Torpedopfannenwagen");
        addUnterKategorie(kategorie, "UMBAU", "Umbauwagen");
        addUnterKategorie(kategorie, "VERSCHLAG", "Verschlagwagen");
        addUnterKategorie(kategorie, "VIEH", "Viehwagen");
        addUnterKategorie(kategorie, "WAGEN", "Wagen");
        addUnterKategorie(kategorie, "WEIHNACHTS", "Weihnachtswagen");
        addUnterKategorie(kategorie, "WEIN", "Weinwagen");

        update(kategorie);

        Kategorie werkzeug = addKategorie("WERKZEUG", "Werkzeug");

        addUnterKategorie(werkzeug, "BUCHER", "Bücher");
        addUnterKategorie(werkzeug, "FARBE", "Farbe");
        addUnterKategorie(werkzeug, "KLEB", "Kleb");
        addUnterKategorie(werkzeug, "WERKZEUG", "Werkzeug");

        save(werkzeug);

        Kategorie zubehor = addKategorie("ZUBEHOR", "Zubehör");

        addUnterKategorie(zubehor, "BESCHRIFTIGUNG", "Beschriftigung");
        addUnterKategorie(zubehor, "ZUBEHOR", "Zubehör");

        save(zubehor);
    }

    private Kupplung addKupplung(String name, String bezeichnung) {
        return save(new Kupplung(null, name, bezeichnung, false));
    }

    private void populateKupplung() {
        addKupplung("RELEX", "Relex Kupplung");
        addKupplung("KK", "Märklin-Kurzkupplungen mit Drehpunkt");
        addKupplung("NEM", "Märklin-Kurzkupplungen in Norm-Aufnahme mit Drehpunkt");
        addKupplung("NEMKK", "Märklin-Kurzkupplungen in Norm-Aufnahme mit Kulissenführung");
        addKupplung("SFKK", "Märklin-Kurzkupplungen in Norm-Aufnahme mit Stromfürhrender Kulissenführung");
        addKupplung("TELEX", "Telex Kupplung");
    }

    private Land addLand(String name, String bezeichnung, IWahrung wahrung) {
        return save(new Land(null, name, bezeichnung, wahrung, false));
    }

    private void populateLand() {
        IWahrung aud = findWahrung("AUD");
        IWahrung eur = findWahrung("EUR");
        IWahrung gbp = findWahrung("GBP");
        IWahrung usd = findWahrung("USD");
        
        addLand("AU", "Australien", aud);
        addLand("BE", "Belgien", eur);
        addLand("DE", "Deutschland", eur);
        addLand("FR", "Frankreich", eur);
        addLand("IT", "Italien", eur);
        addLand("NL", "Niederland", eur);
        addLand("UK", "Vereinigtes Königreich", gbp);
        addLand("US", "Vereinigte Staaten", usd);
    }

    private Licht addLicht(String namen, String bezeichnung) {
        return save(new Licht(null, namen, bezeichnung, false));
    }
   
    private void populateLicht() {
       addLicht("L1V", "Einfach-Spitzensignal vorne");
       addLicht("L1W", "Einfach-Spitzensignal mit der Fahrtrichtung wechselnd.");
       addLicht("L2V", "Zweilicht-Spitzensignal vorne");
       addLicht("L2L2", "Zweilicht-Spitzensignal vorne und hinten");
       addLicht("L2W", "Zweilicht-Spitzensignal mit der Fahrtrichtung wechselnd");
       addLicht("L3V", "Dreilicht-Spitzensignal vorne");
       addLicht("L3W", "Dreilicht-Spitzensignal mit der Fahrtrichtung wechselnd");
       addLicht("L4W", "Vierlicht-Spitzensignal mit der Fahrtrichtung wechselnd");
       addLicht("R1H", "Ein rotes Schlusslicht");
       addLicht("R2H", "Zwei rote Schlusslichter");
       addLicht("L2R2W", "Zweilicht-Spitzensignal und zwei rote Schlusslichter mit der Fahrtrichtung wechselnd");
       addLicht("L3R1W", "Dreilicht-Spitzensignal und ein rotes Schlusslicht mit der Fahrtrichtung wechselnd");
       addLicht("L3R2W", "Dreilicht-Spitzensignal und zwei rote Schlusslichter mit der Fahrtrichtung wechselnd");
       addLicht("L3L1W", "Dreilicht-Spitzensignal und ein weißes Schlusslicht mit der Fahrtrichtung wechselnd");
       addLicht("L3L2W", "Dreilicht-Spitzensignal und zwei weißes Schlusslicht mit der Fahrtrichtung wechselnd");
    }

    private Massstab addMassstab(String name, String bezeichnung) {
        return save(new Massstab(null, name, bezeichnung, false));
    }

    private void populateMassstab() {
        addMassstab("0", "0 : 1:45 32mm");
        addMassstab("0E", "0e : 1:45 16,5mm");
        addMassstab("0I", "0i : 1:45 12mm");
        addMassstab("0M", "0m : 1:45 22,5mm");
        addMassstab("0P", "0p : 1:45 9mm");
        addMassstab("1IN", "1\" : 1:12 121mm");
        addMassstab("1", "1 : 1:32 45mm");
        addMassstab("1E", "1e : 1:32 22,5mm");
        addMassstab("1I", "1i : 1:32 16,5mm");
        addMassstab("1M", "1m : 1:32 32mm");
        addMassstab("1N3", "13 : 1:32 28,6mm");
        addMassstab("1P", "1p : 1:32 12mm");
        addMassstab("F", "F : 1:20.32 70.64mm");
        addMassstab("FN3", "Fn3 : 1:20.32 45mm");
        addMassstab("H0", "H0 : 1:87 16,5mm");
        addMassstab("H0E", "H0e : 1:87 9mm");
        addMassstab("H0I", "H0i : 1:87 6,5mm (H0f)");
        addMassstab("H0M", "H0m : 1:87 12mm");
        addMassstab("H0P", "H0p : 1:87 4,5mm");
        addMassstab("HON2", "HOn2 : 1:87.1 7mm");
        addMassstab("II", "II : 1:22.5 63,5mm");
        addMassstab("IIE", "IIe : 1:22.5 32mm");
        addMassstab("III-NEM", "Iii (NEM) : 1:22.5 22,5mm");
        addMassstab("III", "III : 1:16 89mm");
        addMassstab("IIIE", "IIIe : 1:16 45mm");
        addMassstab("IIII-NMRA", "IIIi (NMRA) : 1:16 32mm (3/4\")");
        addMassstab("IIIM", "IIIm : 1:16 63,5mm");
        addMassstab("IIIP", "IIIp : 1:16 22,5mm");
        addMassstab("IIM", "IIm : 1:22.5 45mm");
        addMassstab("IIP", "IIp : 1:22.5 16,5mm");
        addMassstab("N", "N : 1:160 9mm");
        addMassstab("NE", "Ne : 1:160 4,5mm (Nn2)");
        addMassstab("NM", "Nm : 1:160 6,5mm (Nn3)");
        addMassstab("O", "O : 1:48 31.75mm");
        addMassstab("ON2", "On2 : 1:48 12,7mm");
        addMassstab("ON3", "On3 : 1:48 19,4mm ");
        addMassstab("ON30", "On30 : 1:48 16,5mm");
        addMassstab("OO", "OO : 1:76.2 19,05mm");
        addMassstab("S", "S : 1:64 22,5mm");
        addMassstab("SE", "Se : 1:64 12mm");
        addMassstab("SI", "Si : 1:64 9mm");
        addMassstab("SM", "Sm : 1:64 16,5mm");
        addMassstab("SN3", "Sn3 : 1:64 14,3mm");
        addMassstab("Sp", "Sp : 1:64 6,5mm");
        addMassstab("TT", "TT : 1:120 12mm");
        addMassstab("TTE", "TTe : 1:120 6,5mm");
        addMassstab("TTI", "TTi : 1:120 4,5mm");
        addMassstab("TTM", "TTm : 1:120 9mm");
        addMassstab("V", "V : 1:11 127mm");
        addMassstab("VE", "Ve : 1:11 63,5mm");
        addMassstab("VI", "Vi : 1:11 45mm");
        addMassstab("VII", "VII : 1:8 184mm");
        addMassstab("VIIE", "VIIe : 1:8 89mm");
        addMassstab("VIII", "VIIi : 1:8 63,5mm");
        addMassstab("VIIM", "VIIm : 1:8 127mm");
        addMassstab("VIIP", "VIIp : 1:8 45mm");
        addMassstab("VM", "Vm : 1:11 89mm");
        addMassstab("VP", "Vp : 1:11 32mm");
        addMassstab("X", "X : 1:5.5 260mm");
        addMassstab("XE", "Xe : 1:5.5 127mm");
        addMassstab("XI", "Xi : 1:5.5 89mm");
        addMassstab("XM", "Xm : 1:5.5 184mm");
        addMassstab("XP", "Xp : 1:5.5 63,5mm");
        addMassstab("Z", "Z : 1:220 6,5mm");
        addMassstab("ZM", "Zm : 1:220 4,5mm");
    }

    private MotorTyp addMotorTyp(String name, String bezeichnung) {
        return save(new MotorTyp(null, name, bezeichnung, false));
    }

    private void populateMotorTyp() {
        addMotorTyp("CSINUS", "C-Sinus");
        addMotorTyp("KSINUS", "C-Sinus Kompakt");
        addMotorTyp("DCM", "Trommelkollektor");
        addMotorTyp("DREHSTROM", "Drehstrom");
        addMotorTyp("GLOCKENANKER", "Glockenanker");
        addMotorTyp("HLM", "5-Stern (DCM)");
        addMotorTyp("HLMMS1-7", "5-Stern (MS1-7z)");
        addMotorTyp("HLMMS1-8", "5-Stern (MS1-8z)");
        addMotorTyp("HLMMS2-7", "5-Stern (MS2-7z)");
        addMotorTyp("HLMMS2-8", "5-Stern (MS2-8z)");
        addMotorTyp("HLMS", "5-Stern (SFCM)");
        addMotorTyp("LFCMMS1-7", "Scheibenkollektor (MS1-7z)");
        addMotorTyp("LFCMMS1-8", "Scheibenkollektor (MS1-8z)");
        addMotorTyp("LFCMMS2-7", "Scheibenkollektor (MS2-7z)");
        addMotorTyp("LFCMMS2-8", "Scheibenkollektor (MS2-8z)");
        addMotorTyp("SDSINUS", "SoftDriveSinus");
        addMotorTyp("SFCM", "Scheibenkollektor (klein)");
    }

    private Produkt addProdukt(IHersteller hersteller, String bestellNr, String bezeichnung, IUnterKategorie unterKategorie,
            IMassstab massstab, ISpurweite spurweite, IEpoch epoch, IBahnverwaltung bahnverwaltung, IGattung gattung,
            String betreibsnummer, LocalDate bauzeit, IVorbild vorbild, IAchsfolg achsfolg, String anmerkung,
            ISonderModell sondermodel, IAufbau aufbau, ILicht licht, IKupplung kupplung, ISteuerung steuerung,
            IDecoderTyp decoderTyp, IMotorTyp motorTyp, BigDecimal lange) {
        return save(new Produkt(null,  hersteller,  bestellNr,  bezeichnung,  unterKategorie,
                 massstab,  spurweite,  epoch,  bahnverwaltung,  gattung,
                 betreibsnummer,  bauzeit,  vorbild,  achsfolg,  anmerkung,
                 sondermodel,  aufbau,  licht,  kupplung,  steuerung,
                 decoderTyp,  motorTyp,  lange, false));
    }

    @SuppressWarnings("unused")
    private void addTeil(IProdukt produkt, IProdukt teil, Integer anzahl) {
        produkt.addTeil(new ProduktTeil(null, produkt, teil, anzahl, false));
    }

    private void populateProdukt() {
        IHersteller hersteller = findHersteller("MARKLIN");
        ISteuerung steuerung = findSteuerung("FRU");
        IMotorTyp motorTyp = findMotorTyp("SFCM");
        ILicht licht = findLicht("L1V");
        IKupplung kupplung = findKupplung("RELEX");
        IUnterKategorie unterKategorie = findUnterKategorie("LOKOMOTIV" ,"DAMPF");
        IMassstab massstab = findMassstab("H0");
        ISpurweite spurweite = findSpurweite("H0");
        IEpoch epoch = findEpoch("III");
        IBahnverwaltung bahnverwaltung = findBahnverwaltung("DB");
        IGattung gattung = findGattung("BR89.0");
        IVorbild vorbild = findVorbild("BR89.0");
        IAchsfolg achsfolg = findAchsfolg("CH2T");
        IAufbau aufbau = findAufbau("LK");

        addProdukt(hersteller, "3000", "BR 89.0", unterKategorie, massstab,
            spurweite, epoch, bahnverwaltung, gattung, "89 028",
            LocalDate.of(1907,1,1), vorbild, achsfolg, null,
            null, aufbau, licht, kupplung, steuerung,
            null, motorTyp, BigDecimal.valueOf(11.0));
    }

    private Protokoll addProtokoll(String name, String bezeichnung) {
        return save(new Protokoll(null, name, bezeichnung, false));
    }

    private void populateProtokoll() {
        addProtokoll("DELTA", "Märklin DELTA");
        addProtokoll("FX", "Märklin fx");
        addProtokoll("MFX", "Märklin mfx");
        addProtokoll("DCC", "DCC");
        addProtokoll("MM", "Märklin Motorola");
        addProtokoll("WEICHE", "Märklin Motorola Weiche");
    }

    private SonderModell addSonderModell(String name, String bezeichnung) {
        return save(new SonderModell(null, name, bezeichnung, false));
    }

    private void populateSonderModell() {
        addSonderModell("MM", "Märklin Magazin");
        addSonderModell("INSIDER", "Märklin Insider");
        addSonderModell("MHI", "Märklin Handler Initiative");
        addSonderModell("JAHRES", "Märklin Magazin Jahreswagen");
        addSonderModell("KC", "Märklin Kids Club Jahreswagen");
        addSonderModell("EINMALIGE", "Einmalige Serien");
        addSonderModell("MUSEUM", "Museumswagen");
        addSonderModell("WEIHNACHT", "Weihnachtswagen");
        addSonderModell("SONDER", "SonderModell");
    }

    private Spurweite addSpurweite(String name, String bezeichnung) {
        return save(new Spurweite(null, name, bezeichnung, false));
    }

    private void populateSpurweite() {
        addSpurweite("0", "0 : 32mm");
        addSpurweite("TT", "TT : 12mm");
        addSpurweite("H0", "H0 : 16,5mm");
        addSpurweite("N", "N : 9mm");
        addSpurweite("Z", "Z : 6,5mm");
        addSpurweite("I", "I : 45mm");
        addSpurweite("S", "S : 22,5mm");
        addSpurweite("II", "II : 64mm");
        addSpurweite("III", "III : 89mm");
        addSpurweite("V", "V : 127mm");
        addSpurweite("VII", "VII : 184mm");
        addSpurweite("X", "X : 260mm");
    }

    private Steuerung addSteuerung(String name, String bezeichnung) {
        return save(new Steuerung(null, name, bezeichnung, false));
    }

    private void populateSteuerung() {
        addSteuerung("DIGITAL", "digital");
        addSteuerung("FRU", "Fahrrichtungsumschalter");
        addSteuerung("USE", "Umschaltelektronik");
    }

    private Vorbild addVorbild(IGattung gattung, IUnterKategorie unterKategorie, IBahnverwaltung bahnverwaltung, String hersteller, LocalDate bauzeit,
            Integer anzahl, String betreibsNummer, IAntrieb antrieb, IAchsfolg achsfolg, String bezeichnung, BigDecimal anfahrzugkraft,
            BigDecimal leistung, BigDecimal dienstgewicht, Integer geschwindigkeit, BigDecimal lange, LocalDate ausserdienst,
            BigDecimal dmTreibrad, BigDecimal dmLaufradVorn, BigDecimal dmLaufradHinten, Integer zylinder, BigDecimal dmZylinder,
            BigDecimal kolbenhub, BigDecimal kesselueberdruck, BigDecimal rostflaeche, BigDecimal ueberhitzerflaeche, BigDecimal wasservorrat,
            BigDecimal verdampfung, Integer fahrmotoren, String motorbauart, LeistungsUbertragung leistungsUbertragung, BigDecimal reichweite, BigDecimal kapazitaet, Integer klasse, Integer sitzPlatzeKL1,
            Integer sitzPlatzeKL2, Integer sitzPlatzeKL3, Integer sitzPlatzeKL4, String aufbauten, Integer triebkoepfe, 
            Integer mittelwagen, String drehgestellbauart) {
        return save(new Vorbild(null, gattung, unterKategorie, bahnverwaltung, hersteller, bauzeit,
                 anzahl,  betreibsNummer,  antrieb,  achsfolg,  bezeichnung,  anfahrzugkraft,
                 leistung,  dienstgewicht,  geschwindigkeit,  lange,  ausserdienst,
                 dmTreibrad,  dmLaufradVorn,  dmLaufradHinten,  zylinder,  dmZylinder,
                 kolbenhub,  kesselueberdruck,  rostflaeche,  ueberhitzerflaeche,  wasservorrat,
                 verdampfung,  fahrmotoren,  motorbauart,  leistungsUbertragung,  reichweite,  kapazitaet,  klasse,  sitzPlatzeKL1,
                 sitzPlatzeKL2,  sitzPlatzeKL3,  sitzPlatzeKL4,  aufbauten, triebkoepfe,  mittelwagen, drehgestellbauart, false));
    }

    private void populateVorbild() {
        IGattung gattung = findGattung("BR89.0");
        IUnterKategorie unterKategorie = findUnterKategorie("LOKOMOTIV" ,"DAMPF");
        IAntrieb antrieb = findAntreib("DAMPF");
        IBahnverwaltung bahnverwaltung = findBahnverwaltung("DB");
        IAchsfolg achsfolg = findAchsfolg("CH2T");
        String hersteller = "Henschel";
        LocalDate bauzeit = LocalDate.of(1934, 1, 1);
        Integer anzahl = 10;
        String  betreibsNummer = "89 006"; 
        BigDecimal anfahrzugkraft = null;
        BigDecimal leistung = new BigDecimal("385.0");
        BigDecimal dienstgewicht = new BigDecimal("46.6");
        Integer geschwindigkeit = 45;
        BigDecimal lange = new BigDecimal("9.6");
        LocalDate ausserdienst = LocalDate.of(1962, 1, 1);
        BigDecimal dmTreibrad = new BigDecimal("1.1");
        BigDecimal dmLaufradVorn = null;
        BigDecimal dmLaufradHinten = null;
        Integer zylinder = 2;
        BigDecimal dmZylinder = new BigDecimal("0.42");
        BigDecimal kolbenhub = new BigDecimal("0.55");
        BigDecimal kesselueberdruck = new BigDecimal("14");
        BigDecimal rostflaeche = new BigDecimal("1.42");
        BigDecimal ueberhitzerflaeche = new BigDecimal("24.1");
        BigDecimal wasservorrat = new BigDecimal("4.5");
        BigDecimal verdampfung = new BigDecimal("69.86");
        Integer fahrmotoren = null;
        String motorbauart = null;
        LeistungsUbertragung leistungsUbertragung = null;
        BigDecimal reichweite = null;
        BigDecimal kapazitaet = null;
        Integer klasse = null;
        Integer sitzPlatzeKL1 = null;
        Integer sitzPlatzeKL2 = null;
        Integer sitzPlatzeKL3 = null;
        Integer sitzPlatzeKL4 = null;
        String aufbauten = null;
        Integer triebkoepfe = null;
        Integer mittelwagen = null;
        String drehgestellbauart = null;
 
        addVorbild(gattung, unterKategorie, bahnverwaltung, hersteller, bauzeit,
                anzahl, betreibsNummer, antrieb, achsfolg, "BR 89.0", anfahrzugkraft,
                leistung, dienstgewicht, geschwindigkeit, lange, ausserdienst,
                dmTreibrad, dmLaufradVorn,  dmLaufradHinten,  zylinder,  dmZylinder,
                kolbenhub, kesselueberdruck, rostflaeche, ueberhitzerflaeche, wasservorrat,
                verdampfung, fahrmotoren, motorbauart, leistungsUbertragung,
                reichweite, kapazitaet, klasse, sitzPlatzeKL1, sitzPlatzeKL2, sitzPlatzeKL3, 
                sitzPlatzeKL4, aufbauten, triebkoepfe, mittelwagen, drehgestellbauart);
    }

    private Wahrung addWahrung(String name, String bezeichnung, Integer decimals) {
        return save(new Wahrung(null, name, bezeichnung, decimals, false));
    }

    private void populateWahrung() {
        addWahrung("AUD", "Australian Dollar", 2);
        addWahrung("DEM", "Deutschemark", 2);
        addWahrung("EUR", "Euro", 2);
        addWahrung("GBP", "Pound Serling", 2);
        addWahrung("HKD", "Hong Kong Dollar", 2);
        addWahrung("USD", "US Dollar", 2);
    }

    private Zug addZug(String name, String bezeichnung, IZugTyp zugTyp) {
        return save(new Zug(null, name, bezeichnung, zugTyp, false));
    }

    private void addConsist(IZug zug, Integer position, IArtikel artikel) {
        zug.addConsist(new ZugConsist(null, zug, position, artikel, false));
    }

    private void populateZug() {
        IZugTyp zugTyp = findZugTyp("TEE");

        Zug zug = addZug("BAVARIA", "TEE „Bavaria“", zugTyp);

        addConsist(zug, 1, null);
    }

    private ZugTyp addZugTyp(String name, String bezeichnung) {
        return save(new ZugTyp(null, name, bezeichnung, false));
    }

    private void populateZugTyp() {
        addZugTyp("GUTTERZUG", "Gütterzug");
        addZugTyp("NAHVEKERS", "Nahvekerszug");
        addZugTyp("BAHNDIENST", "Bahndienstzug");
        addZugTyp("IR", "Interregiozug");
        addZugTyp("IC", "Intercityzug");
        addZugTyp("TEE", "TEE Zug");
        addZugTyp("MILITAR", "Militär Zug");
    }

    private <E extends IItem> E findByKey(IKey key, Class<E> entityClass) {
        try {
            IPersister<?, ?> persister = persisterFactory.createPersister(entityClass);

            return persister.findByKey(key, false);
        } catch (Exception e) {
            logger.error("Error finding " + key, e);
        }
        
        return null;
    }

    private <E extends IItem> E findName(String name, Class<E> entityClass) {
        return findByKey(new NameKey(name), entityClass);
    }

    private IAchsfolg findAchsfolg(String name) {

      return findName(name, Achsfolg.class);
    }

    private IAntrieb findAntreib(String name) {
        return findName(name, Antrieb.class);
    }

    private IAufbau findAufbau(String name) {
        return findName(name, Aufbau.class);
    }

    private IBahnverwaltung findBahnverwaltung(String name) {
        return findName(name, Bahnverwaltung.class);
    }

    protected IDecoder findDecoder(String name) {
        return findName(name, Decoder.class);
    }

    private IDecoderTyp findDecoderTyp(String hersteller, String bestellNr) {
        return findByKey(new DecoderTypKey(findHersteller(hersteller), bestellNr), DecoderTyp.class);
    }

    private IEpoch findEpoch(String name) {
        return findName(name, Epoch.class);
    }

    private IGattung findGattung(String name) {
        return findName(name, Gattung.class);
    }

    private IHersteller findHersteller(String name) {
        return findName(name, Hersteller.class);
    }

    private IKategorie findKategorie(String name) {
        return findName(name, Kategorie.class);
    }


    private IKupplung findKupplung(String name) {
        return findName(name, Kupplung.class);
    }

    private ILicht findLicht(String name) {
        return findName(name, Licht.class);
    }

    private IMassstab findMassstab(String name) {
        return findName(name, Massstab.class);
    }

    private IMotorTyp findMotorTyp(String name) {
        return findName(name, MotorTyp.class);
    }

    private IProdukt findProdukt(String hersteller, String bestellNr) {
        return findByKey(new ProduktKey(findHersteller(hersteller), bestellNr), Produkt.class);
    }

    private IProtokoll findProtokoll(String name) {
        return findName(name, Protokoll.class);
    }

    protected ISonderModell findSonderModell(String name) {
        return findName(name, SonderModell.class);
    }

    private ISpurweite findSpurweite(String name) {
        return findName(name, Spurweite.class);
    }

    private ISteuerung findSteuerung(String name) {
        return findName(name, Steuerung.class);
    }

    private IUnterKategorie findUnterKategorie(String kategorie, String unterKategorie) {
        return findByKey(new UnterKategorieKey(findKategorie(kategorie), unterKategorie), UnterKategorie.class);
    }

    private IVorbild findVorbild(String name) {
        return findByKey(new VorbildKey(findGattung(name)), Vorbild.class);
    }

    private IWahrung findWahrung(String name) {
        return findName(name, Wahrung.class);
    }

    private IZugTyp findZugTyp(String name) {
        return findByKey(new NameKey(name), ZugTyp.class);
    }
    
    private <E extends IItem> E save(E item) {
        try {
            @SuppressWarnings("unchecked")
            IPersister<E> persister = (IPersister<E>) persisterFactory.createPersister(item.getClass());

            return persister.save(item);
        } catch (Exception e) {
            logger.error("Failed to save " + item + " : " + e.getMessage(), e);
        }
        
        return null;
    }

    private <E extends IItem> E update(E item) {
        try {
            @SuppressWarnings("unchecked")
            IPersister<E> persister = (IPersister<E>) persisterFactory.createPersister(item.getClass());

            return persister.update(item);
        } catch (Exception e) {
            logger.error("Failed to save " + item + " : " + e.getMessage(), e);
        }
        
        return null;
    }

    private <E extends IItem> void dump(Class<E> entityClass) {
        try {
            IPersister<E> persister = persisterFactory.createPersister(entityClass);
        
            int i = 0;
    
            for (E item : persister.findAll()) {
                i++;
                System.out.println(item);
            }
            
            logger.debug("dumped: " + i + " " + entityClass.getSimpleName());
        } catch (Exception e) {
            logger.error("dump error: " + entityClass.getSimpleName());
        }
    }
}