package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.impl.DecoderTypFunktion;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IDecoderTypFunktionKey;
import com.linepro.modellbahn.persistence.IDecoderTypFunktionPersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class DecoderTypFunktionPersister extends ItemPersister<IDecoderTypFunktion, IDecoderTypFunktionKey> implements IDecoderTypFunktionPersister {

  @Inject
  public DecoderTypFunktionPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, DecoderTypFunktion.class);
  }

  @Override
  void addCriteria(Query query, IDecoderTypFunktionKey key) {
    query.setParameter(ApiNames.HERSTELLER, key.getHersteller());
    query.setParameter(ApiNames.BESTELL_NR, key.getBestellNr());
    query.setParameter(ApiNames.REIHE, key.getReihe());
    query.setParameter(ApiNames.FUNKTION, key.getFunktion());
  }

  @Override
  void addCriteria(Query query, IDecoderTypFunktion entity) {
    query.setParameter(ApiNames.HERSTELLER, entity.getDecoderTyp().getHersteller().getName());
    query.setParameter(ApiNames.BESTELL_NR, entity.getDecoderTyp().getBestellNr());
    query.setParameter(ApiNames.REIHE, entity.getReihe());
    query.setParameter(ApiNames.FUNKTION, entity.getFunktion());
  }

  @Override
  protected String getBusinessKeyQuery() {
    return new StringBuilder("SELECT e FROM ")
        .append(getEntityName())
        .append(" e WHERE e.")
        .append(DBNames.DECODER_TYP).append(".").append(DBNames.HERSTELLER).append(".").append(DBNames.NAME)
        .append(" = :")
        .append(ApiNames.HERSTELLER)
        .append(" AND e.").append(DBNames.DECODER_TYP).append(".").append(DBNames.BESTELL_NR)
        .append(" = :")
        .append(ApiNames.BESTELL_NR)
        .append(" AND e.").append(DBNames.REIHE)
        .append(" = :")
        .append(ApiNames.REIHE)
        .append(" AND e.").append(DBNames.FUNKTION)
        .append(" = :")
        .append(ApiNames.FUNKTION)
        .toString();
  }
}
