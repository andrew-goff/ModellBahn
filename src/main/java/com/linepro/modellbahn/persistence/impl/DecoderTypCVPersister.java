package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IDecoderTypCVKey;
import com.linepro.modellbahn.persistence.IDecoderTypCVPersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class DecoderTypCVPersister extends ItemPersister<IDecoderTypCV, IDecoderTypCVKey> implements
    IDecoderTypCVPersister {

  @Inject
  public DecoderTypCVPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, IDecoderTypCV.class);
  }

  @Override
  void addCriteria(Query query, IDecoderTypCVKey key) {
    query.setParameter(ApiNames.HERSTELLER, key.getHersteller());
    query.setParameter(ApiNames.BESTELL_NR, key.getBestellNr());
    query.setParameter(ApiNames.CV, key.getCV());
  }

  @Override
  void addCriteria(Query query, IDecoderTypCV entity) {
    query.setParameter(ApiNames.HERSTELLER, entity.getDecoderTyp().getHersteller().getName());
    query.setParameter(ApiNames.BESTELL_NR, entity.getDecoderTyp().getBestellNr());
    query.setParameter(ApiNames.CV, entity.getCv());
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
        .append(" AND e.").append(DBNames.CV)
        .append(" = :")
        .append(ApiNames.CV)
        .toString();
  }
}
