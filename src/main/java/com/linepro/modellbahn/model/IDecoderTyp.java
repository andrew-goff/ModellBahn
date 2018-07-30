package com.linepro.modellbahn.model;

import java.math.BigDecimal;
import java.util.Set;

import com.linepro.modellbahn.model.keys.DecoderTypKey;
import com.linepro.modellbahn.model.util.AdressTyp;
import com.linepro.modellbahn.model.util.Konfiguration;

/**
 * IDecoderTyp.
 * @author   $Author$
 * @version  $Id$
 */
public interface IDecoderTyp extends INamedItem<DecoderTypKey> {

    /**
     * Gets the typ.
     *
     * @return the typ
     */
    AdressTyp getAdressTyp();

    /**
     * Sets the typ.
     *
     * @param typ the new typ
     */
    void setAdressTyp(AdressTyp typ);

    /**
     * Gets the hersteller.
     *
     * @return the hersteller
     */
    IHersteller getHersteller();

    /**
     * Sets the hersteller.
     *
     * @param hersteller the new hersteller
     */
    void setHersteller(IHersteller hersteller);

    /**
     * Gets the adressen.
     *
     * @return the adressen
     */
    Integer getAdressen();

    /**
     * Sets the adressen.
     *
     * @param adressen the new adressen
     */
    void setAdressen(Integer adressen);

    /**
     * Gets the i max.
     *
     * @return the i max
     */
    BigDecimal getiMax();

    /**
     * Sets the i max.
     *
     * @param iMax the new i max
     */
    void setiMax(BigDecimal iMax);

    /**
     * Gets the protokoll.
     *
     * @return the protokoll
     */
    IProtokoll getProtokoll();

    /**
     * Sets the protokoll.
     *
     * @param protokoll the new protokoll
     */
    void setProtokoll(IProtokoll protokoll);

    /**
     * Gets the fahrstufe.
     *
     * @return the fahrstufe
     */
    Integer getFahrstufe();

    /**
     * Sets the fahrstufe.
     *
     * @param fahrstufe the new fahrstufe
     */
    void setFahrstufe(Integer fahrstufe);

    /**
     * Gets the sound.
     *
     * @return the sound
     */
    Boolean getSound();

    /**
     * Sets the sound.
     *
     * @param sound the new sound
     */
    void setSound(Boolean sound);

    /**
     * Gets the cv.
     *
     * @return the cv
     */
    Set<IDecoderTypCV> getCVs();

    /**
     * Sets the cv.
     *
     * @param cv the new cv
     */
    void setCVs(Set<IDecoderTypCV> cv);

    /**
     * Gets the funktion.
     *
     * @return the funktion
     */
    Set<IDecoderTypFunktion> getFunktionen();

    /**
     * Sets the funktion.
     *
     * @param funktion the new funktion
     */
    void setFunktionen(Set<IDecoderTypFunktion> funktion);

    /**
     * Gets the konfiguration.
     *
     * @return the konfiguration
     */
    Konfiguration getKonfiguration();

    /**
     * Sets the konfiguration.
     *
     * @param konfiguration the new konfiguration
     */
    void setKonfiguration(Konfiguration konfiguration);

    void addCV(IDecoderTypCV CV);

    void removeCV(IDecoderTypCV CV);

    void addFunktion(IDecoderTypFunktion funktion);

    void removeFunktion(IDecoderTypFunktion funktion);
}