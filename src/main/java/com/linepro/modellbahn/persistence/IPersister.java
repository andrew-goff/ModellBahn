package com.linepro.modellbahn.persistence;

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
 * @param <I> the element type
 */
public interface IPersister<I extends IItem<?>> {
    
    /**
     * Adds the entity checking the primary key; fails if the entity all ready exists.
     *
     * @param entity the entity
     * @return the e
     * @throws Exception if the entity all ready exists or there is a DB error.
     */
    I add(I entity) throws Exception;

    /**
     * Finds the entity by primary key.
     *
     * @param id the id
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    I findById(Long id, boolean eager) throws Exception;

    I findByKey(Long id, boolean eager) throws Exception;

    I findByKey(String name, boolean eager) throws Exception;

    /**
     * Finds the entity by business key.
     *
     * @param key the key to scan for only fields annotated with @BusinessKey are considered
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
     I findByKey(IKey key, boolean eager) throws Exception;

     I findByKey(I entity, boolean eager) throws Exception;

     Long countAll() throws Exception;

     Long countAll(I template) throws Exception;

     Long countAll(I template, Map<String,List<String>> references) throws Exception;

    /**
     * Finds all the entities.
     *
     * @return the list of entities
     * @throws Exception if there is a DB error
     */
    List<I> findAll() throws Exception;

    List<I> findAll(I template) throws Exception;

    /**
     * Finds all entities that have fields with the same values as the template suppled (query by example).
     *
     * @param template the entity to match
     * @return the list of matching entities
     * @throws Exception there is a DB error
     */
    List<I> findAll(I template, Integer startPosition, Integer maxSize) throws Exception;

    List<I> findAll(I template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity ignoring null fields.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity ist nicht vorhanden or there is a DB error
     */
    I merge(Long id, I entity) throws Exception;

    I merge(IKey key, I entity) throws Exception;

    /**
     * Updates the specified entity which must be under db control.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity ist nicht vorhanden or there is a DB error
     */
    I update(I entity) throws Exception;

    /**
     * Deletes the specified entity by primary key.
     *
     * @param id the entity id
     * @throws Exception if there is a DB error
     */
    void delete(Long id) throws Exception;

    void delete(I entity) throws Exception;

    /**
     * Deletes the specified entity by business key.
     *
     * @param key the entity key
     * @throws Exception if there is a DB error
     */
    void delete(IKey key) throws Exception;

    /**
     * Deletes all entities.
     *
     * @throws Exception if there is a DB error
     */
    void deleteAll() throws Exception;

    /**
     * Deletes all entities that have fields with the same values as the template suppled (delete by example).
     *
     * @param template the entity to match
     * @throws Exception if there is a DB error
     */
    void deleteAll(I template) throws Exception;

    /**
     * Adds the entity if it ist nicht vorhanden or updates it if it does.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if there is a DB error
     */
    I save(I entity) throws Exception;

    /**
     * Populate lazy collection.
     *
     * @param collection the collection
     */
    void populateLazyCollection(Collection<?> collection);

    /**
     * Gets the entity class.
     * @return the class
     */
    Class<?> getEntityClass();

    String getEntityName();

    String getNextId();

    Map<String, Selector> getSelectors();

    I create() throws Exception;
}
