package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IDecoderFunktion;
import com.linepro.modellbahn.model.impl.DecoderFunktion;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IDecoderFunktionKey;
import com.linepro.modellbahn.persistence.IDecoderFunktionPersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class DecoderFunktionPersister extends
    ItemPersister<IDecoderFunktion, IDecoderFunktionKey> implements IDecoderFunktionPersister {

  @Inject
  public DecoderFunktionPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, DecoderFunktion.class);
  }

  @Override
  void addCriteria(Query query, IDecoderFunktionKey key) {
    query.setParameter(ApiNames.DECODER_ID, key.getDecoderId());
    query.setParameter(ApiNames.REIHE, key.getReihe());
    query.setParameter(ApiNames.FUNKTION, key.getFunktion());
  }

  @Override
  void addCriteria(Query query, IDecoderFunktion entity) {
    query.setParameter(ApiNames.DECODER_ID, entity.getDecoder().getDecoderId());
    query.setParameter(ApiNames.REIHE, entity.getFunktion().getReihe());
    query.setParameter(ApiNames.FUNKTION, entity.getFunktion());
  }

  @Override
  protected String getBusinessKeyQuery() {
    return new StringBuilder("SELECT e FROM ")
        .append(getEntityName())
        .append(" e WHERE e.")
        .append(DBNames.DECODER).append(".").append(DBNames.DECODER_ID)
        .append(" = :")
        .append(ApiNames.DECODER_ID)
        .append(" AND e.").append(DBNames.REIHE)
        .append(" = :")
        .append(ApiNames.REIHE)
        .append(" AND e.").append(DBNames.FUNKTION)
        .append(" = :")
        .append(ApiNames.FUNKTION)
        .toString();
  }
}
