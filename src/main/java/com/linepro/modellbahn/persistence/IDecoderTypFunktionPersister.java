package com.linepro.modellbahn.persistence;

import com.linepro.modellbahn.model.IDecoderTypFunktion;
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
public interface IDecoderTypFunktionPersister extends IPersister<IDecoderTypFunktion> {
    
    /**
     * Adds the entity checking the primary key; fails if the entity all ready exists.
     *
     * @param entity the entity
     * @return the e
     * @throws Exception if the entity all ready exists or there is a DB error.
     */
    IDecoderTypFunktion add(IDecoderTypFunktion entity) throws Exception;

    /**
     * Finds the entity by primary key.
     *
     * @param id the id
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    IDecoderTypFunktion findById(Long id, boolean eager) throws Exception;

    IDecoderTypFunktion findByKey(String name, boolean eager) throws Exception;

    /**
     * Finds the entity by business key.
     *
     * @param hersteller the entity key
     * @param bestellNr
     * @param reihe
     * @param funktion
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
     IDecoderTypFunktion findByKey(String hersteller, String bestellNr, Integer reihe, String funktion, boolean eager) throws Exception;

     IDecoderTypFunktion findByKey(IDecoderTypFunktion entity, boolean eager) throws Exception;

     Long countAll() throws Exception;

     Long countAll(IDecoderTypFunktion template) throws Exception;

     Long countAll(IDecoderTypFunktion template, Map<String,List<String>> references) throws Exception;

    /**
     * Finds all the entities.
     *
     * @return the list of entities
     * @throws Exception if there is a DB error
     */
    List<IDecoderTypFunktion> findAll() throws Exception;

    List<IDecoderTypFunktion> findAll(IDecoderTypFunktion template) throws Exception;

    /**
     * Finds all entities that have fields with the same values as the template suppled (query by example).
     *
     * @param template the entity to match
     * @return the list of matching entities
     * @throws Exception there is a DB error
     */
    List<IDecoderTypFunktion> findAll(IDecoderTypFunktion template, Integer startPosition, Integer maxSize) throws Exception;

    List<IDecoderTypFunktion> findAll(IDecoderTypFunktion template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IDecoderTypFunktion update(Long id, IDecoderTypFunktion entity) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IDecoderTypFunktion update(IDecoderTypFunktion entity) throws Exception;

    IDecoderTypFunktion update(String hersteller, String bestellNr, Integer reihe, String funktion, IDecoderTypFunktion entity) throws Exception;

    /**
     * Deletes the specified entity by primary key.
     *
     * @param id the entity id
     * @throws Exception if there is a DB error
     */
    void delete(Long id) throws Exception;

    void delete(IDecoderTypFunktion entity) throws Exception;

    /**
     * Deletes the specified entity by business key.
     *
     * @param hersteller the entity key
     * @param bestellNr
     * @param reihe
     * @param funktion
     * @throws Exception if there is a DB error
     */
    void delete(String hersteller, String bestellNr, Integer reihe, String funktion) throws Exception;

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
    void deleteAll(IDecoderTypFunktion template) throws Exception;

    /**
     * Adds the entity if it does not exist or updates it if it does.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if there is a DB error
     */
    IDecoderTypFunktion save(IDecoderTypFunktion entity) throws Exception;

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
    Class<IDecoderTypFunktion> getEntityClass();

    String getEntityName();

    /**
     * Populates any lazy collections for the item
     */
    void populateLazyCollections();

    String getNextId();

    Map<String, Selector> getSelectors();

    IDecoderTypFunktion create() throws Exception;
}
