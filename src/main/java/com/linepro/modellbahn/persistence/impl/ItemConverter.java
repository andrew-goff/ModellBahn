package com.linepro.modellbahn.persistence.impl;

import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.persistence.IKey;
import com.linepro.modellbahn.persistence.IKeyGenerator;
import com.linepro.modellbahn.persistence.IPersister;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * NamedItemConverter.
 * Converts from name to NamedItem.
 * @author  $Author:$
 * @version $Id:$
 */
class ItemConverter<E extends IItem<?>> implements Converter {

    /** The persister. */
    private final IPersister<E> persister;

    private final IKeyGenerator keyGenerator;

    /**
     * Instantiates a new named item converter.
     *
     * @param persister the persister
     */
    public ItemConverter(IPersister<E> persister, IKeyGenerator keyGenerator) {
        this.persister = persister;
        this.keyGenerator = keyGenerator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(Class<T> type, Object value) {
        try {
            if (StringUtils.isNotBlank(value.toString())) {
                Class<? extends IItem<?>> entityClass = getPersister().getEntityClass();

                if (entityClass.isAssignableFrom(type)) {
                    IItem<?> entity = getPersister().findByKey(keyGenerator.getKey(value), false);

                    if (entity != null) {
                        return (T) entity;
                    }

                    throw new IllegalArgumentException(value + " does not exist");
                }

                throw new IllegalArgumentException(type + " not supported");
            }
        } catch (Exception e) {
            throw new ConversionException(e);
        }
        
        return null;
    }

    private IPersister<E> getPersister() {
        return persister;
    }
}