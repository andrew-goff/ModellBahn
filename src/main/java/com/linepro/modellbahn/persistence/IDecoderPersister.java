package com.linepro.modellbahn.persistence;

import com.linepro.modellbahn.model.IDecoder;
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
public interface IDecoderPersister extends IPersister<IDecoder> {
    
    /**
     * Adds the entity checking the primary decoderId; fails if the entity all ready exists.
     *
     * @param entity the entity
     * @return the e
     * @throws Exception if the entity all ready exists or there is a DB error.
     */
    IDecoder add(IDecoder entity) throws Exception;

    /**
     * Finds the entity by primary decoderId.
     *
     * @param id the id
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    IDecoder findById(Long id, boolean eager) throws Exception;

    /**
     * Finds the entity by business decoderId.
     *
     * @param decoderId the decoderId to scan for only fields annotated with @BusinessKey are considered
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
     IDecoder findByKey(String decoderId, boolean eager) throws Exception;

     IDecoder findByKey(IDecoder entity, boolean eager) throws Exception;

     Long countAll() throws Exception;

     Long countAll(IDecoder template) throws Exception;

     Long countAll(IDecoder template, Map<String,List<String>> references) throws Exception;

    /**
     * Finds all the entities.
     *
     * @return the list of entities
     * @throws Exception if there is a DB error
     */
    List<IDecoder> findAll() throws Exception;

    List<IDecoder> findAll(IDecoder template) throws Exception;

    /**
     * Finds all entities that have fields with the same values as the template suppled (query by example).
     *
     * @param template the entity to match
     * @return the list of matching entities
     * @throws Exception there is a DB error
     */
    List<IDecoder> findAll(IDecoder template, Integer startPosition, Integer maxSize) throws Exception;

    List<IDecoder> findAll(IDecoder template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IDecoder update(Long id, IDecoder entity) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IDecoder update(IDecoder entity) throws Exception;

    IDecoder update(String decoderId, IDecoder entity) throws Exception;

    /**
     * Deletes the specified entity by primary decoderId.
     *
     * @param id the entity id
     * @throws Exception if there is a DB error
     */
    void delete(Long id) throws Exception;

    void delete(IDecoder entity) throws Exception;

    /**
     * Deletes the specified entity by business decoderId.
     *
     * @param decoderId the entity decoderId
     * @throws Exception if there is a DB error
     */
    void delete(String decoderId) throws Exception;

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
    void deleteAll(IDecoder template) throws Exception;

    /**
     * Adds the entity if it does not exist or updates it if it does.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if there is a DB error
     */
    IDecoder save(IDecoder entity) throws Exception;

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
    Class<IDecoder> getEntityClass();

    String getEntityName();

    /**
     * Populates any lazy collections for the item
     */
    void populateLazyCollections();

    String getNextId();

    Map<String, Selector> getSelectors();

    IDecoder create() throws Exception;
}
