package com.linepro.modellbahn.model.util;

import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypAdress;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.impl.Decoder;
import com.linepro.modellbahn.model.impl.DecoderAdress;
import com.linepro.modellbahn.model.impl.DecoderCV;
import com.linepro.modellbahn.model.impl.DecoderFunktion;
import com.linepro.modellbahn.persistence.IDecoderPersister;
import com.linepro.modellbahn.persistence.IPersister;

public class DecoderCreator implements IDecoderCreator {

    private final IDecoderPersister persister;

    public DecoderCreator(IDecoderPersister persister) {
        this.persister = persister;
    }

    @Override
    public IDecoder create(IDecoderTyp decoderTyp) throws Exception {
        IDecoder decoder = new Decoder(null, decoderTyp, decoderTyp.getProtokoll(), persister.getNextId(), decoderTyp.getBezeichnung(), decoderTyp.getFahrstufe(), false);
        
        decoder = persister.add(decoder);

        for (IDecoderTypAdress adress : decoderTyp.getAdressen()) {
            decoder.addAdress(new DecoderAdress(null, decoder, adress.getIndex(), adress.getAdressTyp(), adress.getAdress(), false));
        }

        for (IDecoderTypCV cv : decoderTyp.getCVs()) {
            decoder.addCV(new DecoderCV(null, decoder, cv, cv.getWerkseinstellung(), false));
        }

        for (IDecoderTypFunktion funktion : decoderTyp.getFunktionen()) {
            decoder.addFunktion(new DecoderFunktion(null, decoder, funktion, funktion.getBezeichnung(), false));
        }

        decoder = persister.update(decoder);

        return decoder;
    }
}
