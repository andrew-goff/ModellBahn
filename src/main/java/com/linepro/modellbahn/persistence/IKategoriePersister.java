package com.linepro.modellbahn.persistence;

import com.linepro.modellbahn.model.IKategorie;
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
public interface IKategoriePersister extends IPersister<IKategorie> {
    
    /**
     * Adds the entity checking the primary key; fails if the entity all ready exists.
     *
     * @param entity the entity
     * @return the e
     * @throws Exception if the entity all ready exists or there is a DB error.
     */
    IKategorie add(IKategorie entity) throws Exception;

    /**
     * Finds the entity by primary key.
     *
     * @param id the id
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    IKategorie findById(Long id, boolean eager) throws Exception;

    /**
     * Finds the entity by business kategorie.
     *
     * @param kategorie the kategorie to scan for only fields annotated with @BusinessKey are considered
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
     IKategorie findByKey(String kategorie, boolean eager) throws Exception;

     IKategorie findByKey(IKategorie entity, boolean eager) throws Exception;

     Long countAll() throws Exception;

     Long countAll(IKategorie template) throws Exception;

     Long countAll(IKategorie template, Map<String,List<String>> references) throws Exception;

    /**
     * Finds all the entities.
     *
     * @return the list of entities
     * @throws Exception if there is a DB error
     */
    List<IKategorie> findAll() throws Exception;

    List<IKategorie> findAll(IKategorie template) throws Exception;

    /**
     * Finds all entities that have fields with the same values as the template suppled (query by example).
     *
     * @param template the entity to match
     * @return the list of matching entities
     * @throws Exception there is a DB error
     */
    List<IKategorie> findAll(IKategorie template, Integer startPosition, Integer maxSize) throws Exception;

    List<IKategorie> findAll(IKategorie template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IKategorie update(Long id, IKategorie entity) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IKategorie update(IKategorie entity) throws Exception;

    IKategorie update(String kategorie, IKategorie entity) throws Exception;

    /**
     * Deletes the specified entity by primary kategorie.
     *
     * @param id the entity id
     * @throws Exception if there is a DB error
     */
    void delete(Long id) throws Exception;

    void delete(IKategorie entity) throws Exception;

    /**
     * Deletes the specified entity by business kategorie.
     *
     * @param kategorie the entity kategorie
     * @throws Exception if there is a DB error
     */
    void delete(String kategorie) throws Exception;

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
    void deleteAll(IKategorie template) throws Exception;

    /**
     * Adds the entity if it does not exist or updates it if it does.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if there is a DB error
     */
    IKategorie save(IKategorie entity) throws Exception;

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
    Class<IKategorie> getEntityClass();

    String getEntityName();

    /**
     * Populates any lazy collections for the item
     */
    void populateLazyCollections();

    String getNextId();

    Map<String, Selector> getSelectors();

    IKategorie create() throws Exception;
}
