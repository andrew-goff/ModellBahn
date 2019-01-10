package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IVorbild;
import com.linepro.modellbahn.model.impl.Vorbild;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IVorbildPersister;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class VorbildPersister extends ItemPersister<IVorbild, String> implements IVorbildPersister {

  @Inject
  public VorbildPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, Vorbild.class);
  }

  @Override
  void addCriteria(Query query, String key) {
    query.setParameter(DBNames.GATTUNG_ID, key);
  }

  @Override
  void addCriteria(Query query, IVorbild entity) {
    query.setParameter(DBNames.GATTUNG_ID, entity.getGattung().getName());
  }
}
