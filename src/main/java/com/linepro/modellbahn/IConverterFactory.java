package com.linepro.modellbahn;

import com.linepro.modellbahn.model.IItem;

/**
 * A factory for creating IPersister objects.
 * @author   $Author$
 * @version  $Id$
 */
public interface IConverterFactory {
    
    <E extends IItem> void registerConverter(Class<E> clazz);
}
