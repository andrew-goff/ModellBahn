package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IZugConsist;
import com.linepro.modellbahn.model.impl.ZugConsist;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IZugConsistKey;
import com.linepro.modellbahn.persistence.IZugConsistPersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class ZugConsistPersister extends ItemPersister<IZugConsist, IZugConsistKey> implements IZugConsistPersister {

  @Inject
  public ZugConsistPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, ZugConsist.class);
  }

  @Override
  void addCriteria(Query query, IZugConsistKey key) {
    query.setParameter(ApiNames.ZUG, key.getZug());
    query.setParameter(ApiNames.POSITION, key.getPosition());
  }

  @Override
  void addCriteria(Query query, IZugConsist entity) {
    query.setParameter(ApiNames.ZUG, entity.getZug().getName());
    query.setParameter(ApiNames.POSITION, entity.getPosition());
  }

  @Override
  protected String getBusinessKeyQuery() {
    return new StringBuilder("SELECT e FROM ")
        .append(getEntityName())
        .append(" e WHERE e.")
        .append(DBNames.ZUG).append(".").append(DBNames.NAME)
        .append(" = :")
        .append(ApiNames.ZUG)
        .append(" AND e.").append(DBNames.POSITION)
        .append(" = :")
        .append(ApiNames.POSITION)
        .toString();
  }
}
