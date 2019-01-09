package com.linepro.modellbahn.persistence;

import com.linepro.modellbahn.model.IVorbild;
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
 */
public interface IVorbildPersister extends IPersister<IVorbild> {
    
    /**
     * Adds the entity checking the primary gattung; fails if the entity all ready exists.
     *
     * @param entity the entity
     * @return the e
     * @throws Exception if the entity all ready exists or there is a DB error.
     */
    IVorbild add(IVorbild entity) throws Exception;

    /**
     * Finds the entity by primary gattung.
     *
     * @param id the id
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    IVorbild findById(Long id, boolean eager) throws Exception;

    /**
     * Finds the entity by business gattung.
     *
     * @param gattung the gattung to scan for only fields annotated with @BusinessKey are considered
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
     IVorbild findByKey(String gattung, boolean eager) throws Exception;

     IVorbild findByKey(IVorbild entity, boolean eager) throws Exception;

     Long countAll() throws Exception;

     Long countAll(IVorbild template) throws Exception;

     Long countAll(IVorbild template, Map<String,List<String>> references) throws Exception;

    /**
     * Finds all the entities.
     *
     * @return the list of entities
     * @throws Exception if there is a DB error
     */
    List<IVorbild> findAll() throws Exception;

    List<IVorbild> findAll(IVorbild template) throws Exception;

    /**
     * Finds all entities that have fields with the same values as the template suppled (query by example).
     *
     * @param template the entity to match
     * @return the list of matching entities
     * @throws Exception there is a DB error
     */
    List<IVorbild> findAll(IVorbild template, Integer startPosition, Integer maxSize) throws Exception;

    List<IVorbild> findAll(IVorbild template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IVorbild update(Long id, IVorbild entity) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IVorbild update(IVorbild entity) throws Exception;

    IVorbild update(String gattung, IVorbild entity) throws Exception;

    /**
     * Deletes the specified entity by primary gattung.
     *
     * @param id the entity id
     * @throws Exception if there is a DB error
     */
    void delete(Long id) throws Exception;

    void delete(IVorbild entity) throws Exception;

    /**
     * Deletes the specified entity by business gattung.
     *
     * @param gattung the entity gattung
     * @throws Exception if there is a DB error
     */
    void delete(String gattung) throws Exception;

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
    void deleteAll(IVorbild template) throws Exception;

    /**
     * Adds the entity if it does not exist or updates it if it does.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if there is a DB error
     */
    IVorbild save(IVorbild entity) throws Exception;

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
    Class<IVorbild> getEntityClass();

    String getEntityName();

    /**
     * Populates any lazy collections for the item
     */
    void populateLazyCollections();

    String getNextId();

    Map<String, Selector> getSelectors();

    IVorbild create() throws Exception;
}
