package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IDecoderAdress;
import com.linepro.modellbahn.model.impl.DecoderAdress;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IDecoderAdressKey;
import com.linepro.modellbahn.persistence.IDecoderAdressPersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class DecoderAdressPersister extends
    ItemPersister<IDecoderAdress, IDecoderAdressKey> implements IDecoderAdressPersister {

  @Inject
  public DecoderAdressPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, DecoderAdress.class);
  }

  @Override
  void addCriteria(Query query, IDecoderAdressKey key) {
    query.setParameter(ApiNames.DECODER_ID, key.getDecoderId());
    query.setParameter(ApiNames.INDEX, key.getIndex());
  }

  @Override
  void addCriteria(Query query, IDecoderAdress entity) {
    query.setParameter(ApiNames.DECODER_ID, entity.getDecoder().getDecoderId());
    query.setParameter(ApiNames.INDEX, entity.getIndex());
  }

  @Override
  protected String getBusinessKeyQuery() {
    return new StringBuilder("SELECT e FROM ")
        .append(getEntityName())
        .append(" e WHERE e.")
        .append(DBNames.DECODER).append(".").append(DBNames.DECODER_ID)
        .append(" = :")
        .append(ApiNames.DECODER_ID)
        .append(" AND e.").append(DBNames.INDEX)
        .append(" = :")
        .append(ApiNames.INDEX)
        .toString();
  }
}
