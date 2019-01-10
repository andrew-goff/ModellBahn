package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IProduktTeil;
import com.linepro.modellbahn.model.IProduktTeil;
import com.linepro.modellbahn.model.impl.ProduktTeil;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IProduktTeilPersister;
import com.linepro.modellbahn.persistence.IProduktTeilKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class ProduktTeilPersister extends ItemPersister<IProduktTeil, IProduktTeilKey> implements IProduktTeilPersister {

  @Inject
  public ProduktTeilPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, ProduktTeil.class);
  }

  @Override
  void addCriteria(Query query, IProduktTeilKey key) {
    query.setParameter(ApiNames.HERSTELLER, key.getHersteller());
    query.setParameter(ApiNames.BESTELL_NR, key.getBestellNr());
    query.setParameter(ApiNames.TEIL_HERSTELLER, key.getTeilHersteller());
    query.setParameter(ApiNames.TEIL_BESTELL_NR, key.getTeilBestellNr());
  }

  @Override
  void addCriteria(Query query, IProduktTeil entity) {
    query.setParameter(ApiNames.HERSTELLER, entity.getProdukt().getHersteller().getName());
    query.setParameter(ApiNames.BESTELL_NR, entity.getProdukt().getBestellNr());
    query.setParameter(ApiNames.TEIL_HERSTELLER, entity.getTeil().getHersteller().getName());
    query.setParameter(ApiNames.TEIL_BESTELL_NR, entity.getTeil().getBestellNr());
  }

  @Override
  protected String getBusinessKeyQuery() {
    return new StringBuilder("SELECT e FROM ")
        .append(getEntityName())
        .append(" e WHERE e.")
        .append(DBNames.PRODUKT).append(".").append(DBNames.HERSTELLER).append(".").append(DBNames.NAME)
        .append(" = :")
        .append(ApiNames.HERSTELLER)
        .append(" AND e.").append(DBNames.PRODUKT).append(".").append(DBNames.BESTELL_NR)
        .append(" = :")
        .append(ApiNames.BESTELL_NR)
        .append(" AND e.").append(DBNames.TEIL).append(".").append(DBNames.HERSTELLER).append(".").append(DBNames.NAME)
        .append(" = :")
        .append(ApiNames.TEIL_HERSTELLER)
        .append(" AND e.")
        .append(DBNames.TEIL).append(".").append(".").append(DBNames.BESTELL_NR)
        .append(" = :")
        .append(ApiNames.TEIL_BESTELL_NR)
        .toString();
  }
}
