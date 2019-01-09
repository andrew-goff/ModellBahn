package com.linepro.modellbahn.persistence;

import com.linepro.modellbahn.model.IArtikel;
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
public interface IArtikelPersister extends IPersister<IArtikel> {
    
    /**
     * Adds the entity checking the primary key; fails if the entity all ready exists.
     *
     * @param entity the entity
     * @return the e
     * @throws Exception if the entity all ready exists or there is a DB error.
     */
    IArtikel add(IArtikel entity) throws Exception;

    /**
     * Finds the entity by primary key.
     *
     * @param id the id
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    IArtikel findById(Long id, boolean eager) throws Exception;

    IArtikel findIArtikelId(String artikelId, boolean eager) throws Exception;

    /**
     * Finds the entity by business key.
     *
     * @param key the key to scan for only fields annotated with @BusinessKey are considered
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    IArtikel findByKey(IKey key, boolean eager) throws Exception;

    IArtikel findByKey(IArtikel entity, boolean eager) throws Exception;

     Long countAll() throws Exception;

     Long countAll(IArtikel template) throws Exception;

     Long countAll(IArtikel template, Map<String,List<String>> references) throws Exception;

    /**
     * Finds all the entities.
     *
     * @return the list of entities
     * @throws Exception if there is a DB error
     */
    List<IArtikel> findAll() throws Exception;

    List<IArtikel> findAll(IArtikel template) throws Exception;

    /**
     * Finds all entities that have fields with the same values as the template suppled (query by example).
     *
     * @param template the entity to match
     * @return the list of matching entities
     * @throws Exception there is a DB error
     */
    List<IArtikel> findAll(IArtikel template, Integer startPosition, Integer maxSize) throws Exception;

    List<IArtikel> findAll(IArtikel template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IArtikel update(Long id, IArtikel entity) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IArtikel update(IArtikel entity) throws Exception;

    IArtikel update(IKey key, IArtikel entity) throws Exception;

    /**
     * Deletes the specified entity by primary key.
     *
     * @param id the entity id
     * @throws Exception if there is a DB error
     */
    void delete(Long id) throws Exception;

    void delete(IArtikel entity) throws Exception;

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
    void deleteAll(IArtikel template) throws Exception;

    /**
     * Adds the entity if it does not exist or updates it if it does.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if there is a DB error
     */
    IArtikel save(IArtikel entity) throws Exception;

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
    Class<IArtikel> getEntityClass();

    String getEntityName();

    /**
     * Populates any lazy collections for the item
     */
    void populateLazyCollections();

    String getNextId();

    Map<String, Selector> getSelectors();

    IArtikel create() throws Exception;
}
