package com.linepro.modellbahn.persistence;

import com.linepro.modellbahn.model.IDecoderTypAdress;
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
public interface IDecoderTypAdressPersister extends IPersister<IDecoderTypAdress> {
    
    /**
     * Adds the entity checking the primary key; fails if the entity all ready exists.
     *
     * @param entity the entity
     * @return the e
     * @throws Exception if the entity all ready exists or there is a DB error.
     */
    IDecoderTypAdress add(IDecoderTypAdress entity) throws Exception;

    /**
     * Finds the entity by primary key.
     *
     * @param id the id
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
    IDecoderTypAdress findById(Long id, boolean eager) throws Exception;

    IDecoderTypAdress findByKey(String name, boolean eager) throws Exception;

    /**
     * Finds the entity by business key.
     *
     * @param hersteller the entity key
     * @param bestellNr
     * @param index
     * @param eager TODO
     * @return the entity
     * @throws Exception if there more than one match or there is a DB error
     */
     IDecoderTypAdress findByKey(String hersteller, String bestellNr, Integer index, boolean eager) throws Exception;

     IDecoderTypAdress findByKey(IDecoderTypAdress entity, boolean eager) throws Exception;

     Long countAll() throws Exception;

     Long countAll(IDecoderTypAdress template) throws Exception;

     Long countAll(IDecoderTypAdress template, Map<String,List<String>> references) throws Exception;

    /**
     * Finds all the entities.
     *
     * @return the list of entities
     * @throws Exception if there is a DB error
     */
    List<IDecoderTypAdress> findAll() throws Exception;

    List<IDecoderTypAdress> findAll(IDecoderTypAdress template) throws Exception;

    /**
     * Finds all entities that have fields with the same values as the template suppled (query by example).
     *
     * @param template the entity to match
     * @return the list of matching entities
     * @throws Exception there is a DB error
     */
    List<IDecoderTypAdress> findAll(IDecoderTypAdress template, Integer startPosition, Integer maxSize) throws Exception;

    List<IDecoderTypAdress> findAll(IDecoderTypAdress template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IDecoderTypAdress update(Long id, IDecoderTypAdress entity) throws Exception;

    /**
     * Updates the specified entity to match the supplied entity.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if the entity does not exist or there is a DB error
     */
    IDecoderTypAdress update(IDecoderTypAdress entity) throws Exception;

    IDecoderTypAdress update(String hersteller, String bestellNr, Integer index, IDecoderTypAdress entity) throws Exception;

    /**
     * Deletes the specified entity by primary key.
     *
     * @param id the entity id
     * @throws Exception if there is a DB error
     */
    void delete(Long id) throws Exception;

    void delete(IDecoderTypAdress entity) throws Exception;

    /**
     * Deletes the specified entity by business key.
     *
     * @param hersteller the entity key
     * @param bestellNr
     * @param index
     * @throws Exception if there is a DB error
     */
    void delete(String hersteller, String bestellNr, Integer index) throws Exception;

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
    void deleteAll(IDecoderTypAdress template) throws Exception;

    /**
     * Adds the entity if it does not exist or updates it if it does.
     *
     * @param entity the new state of the entity.
     * @return the updated entity.
     * @throws Exception if there is a DB error
     */
    IDecoderTypAdress save(IDecoderTypAdress entity) throws Exception;

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
    Class<IDecoderTypAdress> getEntityClass();

    String getEntityName();

    /**
     * Populates any lazy collections for the item
     */
    void populateLazyCollections();

    String getNextId();

    Map<String, Selector> getSelectors();

    IDecoderTypAdress create() throws Exception;
}
