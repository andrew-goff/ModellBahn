package com.linepro.modellbahn.persistence;

import com.linepro.modellbahn.model.IUnterKategorie;
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
public interface IUnterKategoriePersister extends IPersister<IUnterKategorie> {
    
    /**
     * Adds the entity checking the primary key; fails if the entity all ready exists.
     *
     * @param entity the entity
     * @return the e
     * @throws Exception if the entity all ready exists or there is a DB error.
     */
    IUnterKategorie add(IUnterKategorie entity) throws Exception;

    /**
     * Finds the entity by primary key.
     *
     * @param id the id
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    IUnterKategorie findById(Long id, boolean eager) throws Exception;

    IUnterKategorie findByKey(String name, boolean eager) throws Exception;

    /**
     * Finds the entity by business key.
     *
     * @param kategorie the key to scan for only fields annotated with @BusinessKey are considered
     * @param unterKategorie
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
     IUnterKategorie findByKey(String kategorie, String unterKategorie, boolean eager) throws Exception;

     IUnterKategorie findByKey(IUnterKategorie entity, boolean eager) throws Exception;

     Long countAll() throws Exception;

     Long countAll(IUnterKategorie template) throws Exception;

     Long countAll(IUnterKategorie template, Map<String,List<String>> references) throws Exception;

    /**
     * Finds all the entities.
     *
     * @return the list of entities
     * @throws Exception if there is a DB error
     */
    List<IUnterKategorie> findAll() throws Exception;

    List<IUnterKategorie> findAll(IUnterKategorie template) throws Exception;

    /**
     * Finds all entities that have fields with the same values as the template suppled (query by example).
     *
     * @param template the entity to match
     * @return the list of matching entities
     * @throws Exception there is a DB error
     */
    List<IUnterKategorie> findAll(IUnterKategorie template, Integer startPosition, Integer maxSize) throws Exception;

    List<IUnterKategorie> findAll(IUnterKategorie template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IUnterKategorie update(Long id, IUnterKategorie entity) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IUnterKategorie update(IUnterKategorie entity) throws Exception;

    IUnterKategorie update(String kategorie, String unterKategorie, IUnterKategorie entity) throws Exception;

    /**
     * Deletes the specified entity by primary key.
     *
     * @param id the entity id
     * @throws Exception if there is a DB error
     */
    void delete(Long id) throws Exception;

    void delete(IUnterKategorie entity) throws Exception;

    /**
     * Deletes the specified entity by business key.
     *
     * @param kategorie the entity key
     * @param unterKategorie
     * @throws Exception if there is a DB error
     */
    void delete(String kategorie, String unterKategorie) throws Exception;

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
    void deleteAll(IUnterKategorie template) throws Exception;

    /**
     * Adds the entity if it does not exist or updates it if it does.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if there is a DB error
     */
    IUnterKategorie save(IUnterKategorie entity) throws Exception;

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
    Class<IUnterKategorie> getEntityClass();

    String getEntityName();

    /**
     * Populates any lazy collections for the item
     */
    void populateLazyCollections();

    String getNextId();

    Map<String, Selector> getSelectors();

    IUnterKategorie create() throws Exception;
}
