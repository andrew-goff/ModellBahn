package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IDecoderCV;
import com.linepro.modellbahn.model.impl.DecoderCV;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IDecoderCVKey;
import com.linepro.modellbahn.persistence.IDecoderCVPersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class DecoderCVPersister extends ItemPersister<IDecoderCV, IDecoderCVKey> implements IDecoderCVPersister {

  @Inject
  public DecoderCVPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, DecoderCV.class);
  }

  @Override
  void addCriteria(Query query, IDecoderCVKey key) {
    query.setParameter(ApiNames.DECODER_ID, key.getDecoderId());
    query.setParameter(ApiNames.CV, key.getCV());
  }

  @Override
  void addCriteria(Query query, IDecoderCV entity) {
    query.setParameter(ApiNames.DECODER_ID, entity.getDecoder().getDecoderId());
    query.setParameter(ApiNames.CV, entity.getCv().getCv());
  }

  @Override
  protected String getBusinessKeyQuery() {
    return new StringBuilder("SELECT e FROM ")
        .append(getEntityName())
        .append(" e WHERE e.")
        .append(DBNames.DECODER).append(".").append(DBNames.DECODER_ID)
        .append(" = :")
        .append(ApiNames.DECODER_ID)
        .append(" AND e.").append(DBNames.CV)
        .append(" = :")
        .append(ApiNames.CV)
        .toString();
  }
}
