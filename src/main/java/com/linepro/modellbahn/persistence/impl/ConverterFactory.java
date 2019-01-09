package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.IConverterFactory;
import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderAdress;
import com.linepro.modellbahn.model.IDecoderCV;
import com.linepro.modellbahn.model.IDecoderFunktion;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypAdress;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.IGattung;
import com.linepro.modellbahn.model.IHersteller;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.IKategorie;
import com.linepro.modellbahn.model.INamedItem;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.IProduktTeil;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.impl.Decoder;
import com.linepro.modellbahn.model.impl.DecoderTyp;
import com.linepro.modellbahn.model.impl.Gattung;
import com.linepro.modellbahn.model.impl.Hersteller;
import com.linepro.modellbahn.model.impl.Kategorie;
import com.linepro.modellbahn.model.impl.Produkt;
import com.linepro.modellbahn.model.impl.Vorbild;














import com.linepro.modellbahn.persistence.IKey;
import com.linepro.modellbahn.persistence.IKeyGenerator;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.IPersisterFactory;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.ILoggerFactory;

/**
 * A factory for creating Persister objects.
 */
public class ConverterFactory implements IConverterFactory {

    /** The entity manager. */
    private final IPersisterFactory persisterFactory;

    /** The log manager. */
    private final ILoggerFactory logManager;

    /**
     * Instantiates a new persister factory.
     *
     * @param persisterFactory the persister factory
     * @param logManager the log manager
     */
    @Inject
    public ConverterFactory(final IPersisterFactory persisterFactory, final ILoggerFactory logManager) {
        this.persisterFactory = persisterFactory;
        this.logManager = logManager;
    }

    @Override
    public <E extends IItem> void registerConverter(Class<E> entityClass) {
        IPersister<E> persister = persisterFactory.createPersister(entityClass);

        IKeyGenerator generator = getGenerator(entityClass);

        if (generator != null) {
            ConvertUtils.register(new ItemConverter(persister, generator), entityClass);
        }
    }

    IKeyGenerator getGenerator(Class<?> entityClass) {

        if (IArtikel.class.isAssignableFrom(entityClass)) {
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) {
                    return new ArtikelKey(value.toString());
                }
            };
        } else if (IDecoder.class.isAssignableFrom(entityClass)) {
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) {
                    return new DecoderKey(value.toString());
                }
            };
        } else if (IDecoderAdress.class.isAssignableFrom(entityClass)) {
            final IPersister<Decoder> lookup = persisterFactory.createPersister(Decoder.class);
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IDecoder decoder = lookup.findByKey(new DecoderKey(values.get(0)), false);
                    return new DecoderAdressKey(decoder, Integer.valueOf(values.get(1)));
                }
            };
        } else if (IDecoderCV.class.isAssignableFrom(entityClass)) {
            final IPersister<Decoder> lookup = persisterFactory.createPersister(Decoder.class);
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IDecoder decoder = lookup.findByKey(new DecoderKey(values.get(0)), false);
                    return new DecoderCVKey(decoder, Integer.valueOf(values.get(1)));
                }
            };
        } else if (IDecoderFunktion.class.isAssignableFrom(entityClass)) {
            final IPersister<Decoder> lookup = persisterFactory.createPersister(Decoder.class);
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IDecoder decoder = lookup.findByKey(new DecoderKey(values.get(0)), false);
                    return new DecoderFunktionKey(decoder, values.get(1));
                }
            };
        } else if (IDecoderTyp.class.isAssignableFrom(entityClass)) {
            final IPersister<Hersteller> lookup = persisterFactory.createPersister(Hersteller.class);
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IHersteller hersteller = lookup.findByKey(new NameKey(values.get(0)), false);
                    return new DecoderTypKey(hersteller, values.get(1));
                }
            };
        } else if (IDecoderTypAdress.class.isAssignableFrom(entityClass)) {
            final IPersister<Hersteller> lookup = persisterFactory.createPersister(Hersteller.class);
            final IPersister<DecoderTyp> decoderTypLookup = persisterFactory.createPersister(DecoderTyp.class);
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IHersteller hersteller = lookup.findByKey(new NameKey(values.get(0)), false);
                    IDecoderTyp decoderTyp = decoderTypLookup.findByKey(new DecoderTypKey(hersteller, values.get(1)), false);
                    return new DecoderTypAdressKey(decoderTyp, Integer.valueOf(values.get(2)));
                }
            };
        } else if (IDecoderTypCV.class.isAssignableFrom(entityClass)) {
            final IPersister<Hersteller> lookup = persisterFactory.createPersister(Hersteller.class);
            final IPersister<DecoderTyp> decoderTypLookup = persisterFactory.createPersister(DecoderTyp.class);
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IHersteller hersteller = lookup.findByKey(new NameKey(values.get(0)), false);
                    IDecoderTyp decoderTyp = decoderTypLookup.findByKey(new DecoderTypKey(hersteller, values.get(1)), false);
                    return new DecoderTypCVKey(decoderTyp, Integer.valueOf(values.get(2)));
                }
            };
        } else if (IDecoderTypFunktion.class.isAssignableFrom(entityClass)) {
            final IPersister<Hersteller> lookup = persisterFactory.createPersister(Hersteller.class);
            final IPersister<DecoderTyp> decoderTypLookup = persisterFactory.createPersister(DecoderTyp.class);
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IHersteller hersteller = lookup.findByKey(new NameKey(values.get(0)), false);
                    IDecoderTyp decoderTyp = decoderTypLookup.findByKey(new DecoderTypKey(hersteller, values.get(1)), false);
                    return new DecoderTypFunktionKey(decoderTyp, Integer.valueOf(values.get(2)), values.get(3));
                }
            };
        } else if (IProdukt.class.isAssignableFrom(entityClass)) {
            final IPersister<Hersteller> lookup = persisterFactory.createPersister(Hersteller.class);
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IHersteller hersteller = lookup.findByKey(new NameKey(values.get(0)), false);
                    return new ProduktKey(hersteller, values.get(1));
                }
            };
        } else if (IProduktTeil.class.isAssignableFrom(entityClass)) {
            final IPersister<Hersteller> lookup = persisterFactory.createPersister(Hersteller.class);
            final IPersister<Produkt> produktLookup = persisterFactory.createPersister(Produkt.class);
            return  new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    List<String> values = (List<String>) value;
                    IHersteller hersteller = lookup.findByKey(new NameKey(values.get(0)), false);
                    Produkt produkt = produktLookup.findByKey(new ProduktKey(hersteller, values.get(1)), false);
                    hersteller = lookup.findByKey(new NameKey(values.get(2)), false);
                    Produkt teil = produktLookup.findByKey(new ProduktKey(hersteller, values.get(3)), false);
                    return new ProduktTeilKey(produkt, teil);
                }
            };
        } else if (IUnterKategorie.class.isAssignableFrom(entityClass)) {
            final IPersister<Kategorie> lookup = persisterFactory.createPersister(Kategorie.class);
            return  new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    IKategorie kategorie = lookup.findByKey(new NameKey(value.toString()), false);
                    return new UnterKategorieKey(kategorie, value.toString());
                }
            };
        } else if (Vorbild.class.isAssignableFrom(entityClass)) {
            final IPersister<Gattung> lookup = persisterFactory.createPersister(Gattung.class);
            return  new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) throws Exception {
                    IGattung gattung = lookup.findByKey(new NameKey(value.toString()), false);
                    return new VorbildKey(gattung);
                }
            };
        } else if (INamedItem.class.isAssignableFrom(entityClass)) {
            return new IKeyGenerator() {
                @Override
                public IKey getKey(Object value) {
                    return new NameKey(value.toString());
                }
            };
        }

        return null;
    }
}
