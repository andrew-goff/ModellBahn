package com.linepro.modellbahn.model.util;

import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderTyp;

public interface IDecoderCreator {

    IDecoder create(IDecoderTyp decoderTyp) throws Exception;
}
