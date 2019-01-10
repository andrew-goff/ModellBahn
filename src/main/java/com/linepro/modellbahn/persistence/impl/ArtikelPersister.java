package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.impl.Artikel;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IArtikelPersister;
import com.linepro.modellbahn.rest.util.ApiNames;
import javax.inject.Inject;
import javax.persistence.Query;
import org.slf4j.ILoggerFactory;

public class ArtikelPersister extends ItemPersister<IArtikel, String> implements IArtikelPersister {

  @Inject
  public ArtikelPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager) {
    super(sessionManagerFactory, logManager, Artikel.class);
  }

  @Override
  void addCriteria(Query query, String key) {
    query.setParameter(ApiNames.ARTIKEL_ID, key);
  }

  @Override
  void addCriteria(Query query, IArtikel entity) {
    query.setParameter(ApiNames.ARTIKEL_ID, entity.getArtikelId());
  }}
