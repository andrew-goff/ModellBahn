package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.impl.UnterKategorie;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IUnterKategorieKey;
import com.linepro.modellbahn.persistence.IUnterKategoriePersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class UnterKategoriePersister extends
    ItemPersister<IUnterKategorie, IUnterKategorieKey> implements IUnterKategoriePersister {

  @Inject
  public UnterKategoriePersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, UnterKategorie.class);
  }

  @Override
  void addCriteria(Query query, IUnterKategorieKey key) {
    query.setParameter(ApiNames.KATEGORIE, key.getKategorie());
    query.setParameter(ApiNames.UNTER_KATEGORIE, key.getUnterKategorie());
  }

  @Override
  void addCriteria(Query query, IUnterKategorie entity) {
    query.setParameter(ApiNames.KATEGORIE, entity.getKategorie().getName());
    query.setParameter(ApiNames.UNTER_KATEGORIE, entity.getName());
  }

  @Override
  protected String getBusinessKeyQuery() {
    return new StringBuilder("SELECT e FROM ")
        .append(getEntityName())
        .append(" e WHERE e.")
        .append(DBNames.KATEGORIE).append(".").append(DBNames.NAME)
        .append(" = :")
        .append(ApiNames.KATEGORIE)
        .append(" AND e.").append(DBNames.NAME)
        .append(" = :")
        .append(ApiNames.UNTER_KATEGORIE)
        .toString();
  }
}
