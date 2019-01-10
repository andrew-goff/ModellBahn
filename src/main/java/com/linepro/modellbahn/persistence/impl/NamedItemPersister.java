package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.INamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.INamedItemPersister;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class NamedItemPersister<E extends INamedItem> extends ItemPersister<E, String> implements
    INamedItemPersister<E> {

  @Inject
  public NamedItemPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager, Class<?> entityClass) {
    super(sessionManagerFactory, logManager, entityClass);
  }

  @Override
  void addCriteria(Query query, String key) {
    query.setParameter(DBNames.NAME, key);
  }

  @Override
  void addCriteria(Query query, E entity) {
    query.setParameter(DBNames.NAME, entity.getName());
  }
}
