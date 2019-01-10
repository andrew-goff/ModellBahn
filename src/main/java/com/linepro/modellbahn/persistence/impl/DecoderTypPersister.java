package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.impl.Artikel;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IDecoderTypKey;
import com.linepro.modellbahn.persistence.IDecoderTypPersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class DecoderTypPersister extends ItemPersister<IDecoderTyp, IDecoderTypKey> implements IDecoderTypPersister {

  @Inject
  public DecoderTypPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, Artikel.class);
  }

  @Override
  void addCriteria(Query query, IDecoderTypKey key) {
    query.setParameter(ApiNames.HERSTELLER, key.getHersteller());
    query.setParameter(ApiNames.BESTELL_NR, key.getBestellNr());
  }

  @Override
  void addCriteria(Query query, IDecoderTyp entity) {
    query.setParameter(ApiNames.HERSTELLER, entity.getHersteller().getName());
    query.setParameter(ApiNames.BESTELL_NR, entity.getBestellNr());
  }

  @Override
  protected String getBusinessKeyQuery() {
    return new StringBuilder("SELECT e FROM ")
        .append(getEntityName())
        .append(" e WHERE e.")
        .append(DBNames.HERSTELLER).append(".").append(DBNames.NAME)
        .append(" = :")
        .append(ApiNames.HERSTELLER)
        .append(" AND e.").append(DBNames.BESTELL_NR)
        .append(" = :")
        .append(ApiNames.BESTELL_NR)
        .toString();
  }
}
