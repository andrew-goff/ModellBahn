package com.linepro.modellbahn.persistence;

import com.linepro.modellbahn.model.INamedItem;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.util.Selector;

/**
 * IPersister.
 * A data access object for the specified element
 * @author   $Author$
 * @version  $Id$
 * @param <E> the element type
 */
public interface INamedItemPersister<E extends INamedItem> extends IPersister<E, String> {}
