package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IDecoderTypAdress;
import com.linepro.modellbahn.model.impl.DecoderTypAdress;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IDecoderTypAdressPersister;
import com.linepro.modellbahn.persistence.IDecoderTypAdressKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class DecoderTypAdressPersister extends
    ItemPersister<IDecoderTypAdress, IDecoderTypAdressKey> implements IDecoderTypAdressPersister {

  @Inject
  public DecoderTypAdressPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, DecoderTypAdress.class);
  }

  @Override
  void addCriteria(Query query, IDecoderTypAdressKey key) {
    query.setParameter(ApiNames.HERSTELLER, key.getHersteller());
    query.setParameter(ApiNames.BESTELL_NR, key.getBestellNr());
    query.setParameter(ApiNames.INDEX, key.getIndex());
  }

  @Override
  void addCriteria(Query query, IDecoderTypAdress entity) {
    query.setParameter(ApiNames.HERSTELLER, entity.getDecoderTyp().getHersteller().getName());
    query.setParameter(ApiNames.BESTELL_NR, entity.getDecoderTyp().getBestellNr());
    query.setParameter(ApiNames.INDEX, entity.getIndex());
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
        .append(" AND e.").append(DBNames.INDEX)
        .append(" = :")
        .append(ApiNames.INDEX)
        .toString();
  }
}
