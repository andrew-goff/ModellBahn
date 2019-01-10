package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.impl.Decoder;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IDecoderPersister;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class DecoderPersister extends ItemPersister<IDecoder, String> implements IDecoderPersister {

  @Inject
  public DecoderPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, Decoder.class);
  }

  @Override
  void addCriteria(Query query, String key) {
    query.setParameter(DBNames.DECODER_ID, key);
  }

  @Override
  void addCriteria(Query query, IDecoder entity) {
    query.setParameter(DBNames.DECODER_ID, entity.getDecoderId());
  }
}
