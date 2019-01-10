package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderAdress;
import com.linepro.modellbahn.model.IDecoderCV;
import com.linepro.modellbahn.model.IDecoderFunktion;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypAdress;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.IProduktTeil;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.IVorbild;
import com.linepro.modellbahn.model.impl.ZugConsist;
import com.linepro.modellbahn.persistence.IDecoderFunktionPersister;
import com.linepro.modellbahn.persistence.IDecoderTypFunktionPersister;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.ILoggerFactory;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.INamedItem;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.IPersisterFactory;

/**
 * A factory for creating Persister objects.
 */
public class PersisterFactory implements IPersisterFactory {

    /** The entity manager. */
    private final ISessionManagerFactory sessionManagerFactory;

    /** The log manager. */
    private final ILoggerFactory logManager;

    /** The persisters. */
    private final Map<Class<?>,IPersister<?,?>> persisters = new HashMap<>();
    
    /**
     * Instantiates a new persister factory.
     *
     * @param sessionManagerFactory the session manager
     * @param logManager the log manager
     */
    @Inject
    public PersisterFactory(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
        this.sessionManagerFactory = sessionManagerFactory;
        this.logManager = logManager;
    }

    @Override
    public synchronized <E extends IItem> IPersister<?, ?> createPersister(Class<E> entityClass) {
        IPersister<?, ?> persister = persisters.get(entityClass);
        
        if (persister == null) {
            if (IArtikel.class.isAssignableFrom(entityClass)) {
                persister = new ArtikelPersister(sessionManagerFactory, logManager);
            } else if (IDecoder.class.isAssignableFrom(entityClass)) {
                persister = new DecoderPersister(sessionManagerFactory, logManager);
            } else if (IDecoderAdress.class.isAssignableFrom(entityClass)) {
                persister = new DecoderAdressPersister(sessionManagerFactory, logManager);
            } else if (IDecoderCV.class.isAssignableFrom(entityClass)) {
                persister = new DecoderCVPersister(sessionManagerFactory, logManager);
            } else if (IDecoderFunktion.class.isAssignableFrom(entityClass)) {
                persister = new DecoderFunktionPersister(sessionManagerFactory, logManager);
            } else if (IDecoderTyp.class.isAssignableFrom(entityClass)) {
                persister = new DecoderTypPersister(sessionManagerFactory, logManager);
            } else if (IDecoderTypAdress.class.isAssignableFrom(entityClass)) {
                persister = new DecoderTypAdressPersister(sessionManagerFactory, logManager);
            } else if (IDecoderTypCV.class.isAssignableFrom(entityClass)) {
                persister = new DecoderTypCVPersister(sessionManagerFactory, logManager);
            } else if (IDecoderTypFunktion.class.isAssignableFrom(entityClass)) {
                persister = new DecoderTypFunktionPersister(sessionManagerFactory, logManager);
            } else if (IProdukt.class.isAssignableFrom(entityClass)) {
                persister = new ProduktPersister(sessionManagerFactory, logManager);
            } else if (IProduktTeil.class.isAssignableFrom(entityClass)) {
                persister = new ProduktTeilPersister(sessionManagerFactory, logManager);
            } else if (IUnterKategorie.class.isAssignableFrom(entityClass)) {
                persister = new UnterKategoriePersister(sessionManagerFactory, logManager);
            } else if (IVorbild.class.isAssignableFrom(entityClass)) {
                persister = new VorbildPersister(sessionManagerFactory, logManager);
            } else if (ZugConsist.class.isAssignableFrom(entityClass)) {
                persister = new ZugConsistPersister(sessionManagerFactory, logManager);
            } else if (INamedItem.class.isAssignableFrom(entityClass)) {
                persister = new NamedItemPersister<>(sessionManagerFactory, logManager, entityClass);
            }

            persisters.put(entityClass, persister);
        }
        
        return persister;
    }
}

