package com.linepro.modellbahn.persistence.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.ILoggerFactory;

import com.google.inject.assistedinject.Assisted;
import com.linepro.modellbahn.model.INamedItem;
import com.linepro.modellbahn.persistence.INamedItemPersister;

public class NamedItemPersister<E extends INamedItem> extends ItemPersister<E> implements INamedItemPersister<E> {

    @Inject
    public NamedItemPersister(final EntityManager entityManager, final ILoggerFactory logManager, @Assisted final Class<E> clazz) {
        super(entityManager, logManager, clazz);
        
        ConvertUtils.register(new NamedItemConverter<E>(this), clazz);
    }

    @Override
    public E findByName(String name) throws Exception {
        if (name != null) {
            E template = create();
    
            template.setName(name);
    
            List<E> findAll = super.findAll(template);
            
            if (!findAll.isEmpty()) {
                return findAll.get(0);
            }
        }
        
        return null;
    }

    @Override
    public E find(E entity) throws Exception {
        return findByName(entity.getName());
    }
}