package com.linepro.modellbahn.persistence.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.collection.spi.PersistentCollection;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.linepro.modellbahn.guice.ISessionManagerFactory;
import com.linepro.modellbahn.model.IItem;

import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.IIdGenerator;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.ISessionManager;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.util.Selector;
import com.linepro.modellbahn.util.SelectorsBuilder;

/**
 * ItemPersister.
 * Basic CRUD DAO for items.
 * @author   $Author$
 * @version  $Id$
 *
 * @param <I> the element type
 */
public abstract class ItemPersister<I extends IItem, K> implements IPersister<I, K> {

    private final ISessionManagerFactory sessionManagerFactory;

    private final Logger logger;

    private final Class<?> entityClass;

    private final Map<String, Selector> selectors;

    private final Map<String, Selector> businessKeys;

    private final Map<String, Selector> collections;

    private final String entityName;

    private final IIdGenerator idGenerator;

    private final String businessKeyQuery;

    private String idKey;
    
    /**
     * Instantiates a new item persister.
     *
     * @param sessionManagerFactory the entity manager
     * @param logManager the log manager
     * @param entityClass the entity class
     */
    public ItemPersister(final ISessionManagerFactory sessionManagerFactory, final ILoggerFactory logManager, final Class<? extends I> entityClass) {
        this.sessionManagerFactory = sessionManagerFactory;
        this.logger = logManager.getLogger(entityClass.getName());
        this.entityClass = (Class<I>) entityClass;
        this.idGenerator = new IdGenerator(sessionManagerFactory);

        businessKeys = new SelectorsBuilder().build(entityClass, BusinessKey.class);
        collections = new SelectorsBuilder().build(entityClass, OneToMany.class);
        selectors = new SelectorsBuilder().build(entityClass, Column.class, JoinColumn.class, OneToMany.class);

        Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
        entityName = entityAnnotation != null ? entityAnnotation.name() : entityClass.getSimpleName();

        StringBuilder queryString = new StringBuilder("SELECT e FROM ")
                .append(getEntityName())
                .append(" e WHERE ");

        int i = 0;

        for (Selector businessKey : getBusinessKeys().values()) {
            if (i == 0) {
                idKey = businessKey.getName();
            } else {
                queryString.append(" AND ");
            }
            queryString.append("e.");
            queryString.append(businessKey.getName());
            queryString.append(" = :");
            queryString.append(businessKey.getName());
            i++;
        }

        businessKeyQuery = queryString.toString();

        debug(getBusinessKeyQuery());
    }

    @Override
    public I add(I entity) throws Exception {
        ISessionManager session = getSession();

        try {
            entity.setDeleted(false);

            session.getEntityManager().persist(entity);

            debug("added: " + entity);

            session.commit();

            return entity;
        } catch (Exception e) {
            error("add error: " + entity, e);
            
            session.rollback();

            throw e;
        }
    }

    @Override
    public I update(Long id, I entity) throws Exception {
        return internalUpdate(id, entity, false);
    }

    @Override
    public I update(K key, I entity) throws Exception {
        return internalUpdate(key, entity, false);
    }

    @Override
    public I save(I entity) throws Exception {
        return internalUpdate(entity, true);
    }

    /**
     * Internal update.
     *
     * @param entity the entity
     * @param addOrUpdate the add or update
     * @return the e
     * @throws Exception the exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private I internalUpdate(Long id, I entity, boolean addOrUpdate) throws Exception {
        ISessionManager session = getSession();

        try {
            I found = internalFindByKey(session, key, false);
            
            if (found == null && !addOrUpdate) {
                return null;
            }

            I result;
            
            if (found == null) {
                // Save the new entity
                result = entity;
            } else {
                result = found;

                // Copy updated values into existing entity
                for (Selector selector : getSelectors().values()) {
                    Object value = selector.getGetter().invoke(entity);

                    if (value instanceof Collection) {
                        ((Collection) selector.getGetter().invoke(found)).addAll((Collection) value);
                    } else if (value != null) {
                        selector.getSetter().invoke(result, value);
                    }
                }
            }

            result = inflate(session.getEntityManager().merge(result), true);

            debug((found == null ? "added" : "updated") + ": " + result);

            session.commit();

            return result;
        } catch (Exception e) {
            error("Update error: " + entity, e);

            session.rollback();

            throw e;
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        ISessionManager session = getSession();
            try {

            I found = findById(id, false);

            session.getEntityManager().remove(found);

            debug("deleted: " + id);

            session.commit();
        } catch (Exception e) {
            error("delete error : " + id, e);
            
            session.rollback();

            throw e;
        }
    }

    public void delete(I entity) throws Exception {
        delete(entity);
    }

    @Override
    public void delete(K key) throws Exception {
        ISessionManager session = getSession();

        try {
            I found = internalFindByKey(session, key, false);

            session.getEntityManager().remove(found);
            
            debug("deleted: " + key);

            session.commit();
        } catch (Exception e) {
            error("delete error: " + key, e);
            
            session.rollback();

            throw e;
        }
    }

    @Override
    public void deleteAll() throws Exception {
        deleteAll(null);
    }

    @Override
    public void deleteAll(I template) throws Exception {
        ISessionManager session = getSession();

        try {
            CriteriaBuilder builder = session.getEntityManager().getCriteriaBuilder();
            CriteriaDelete<I> query = builder.createCriteriaDelete(getEntityClass());
            Root<I> root = query.from(getEntityClass());

            List<Predicate> predicates = getConditions(builder, root, template, getSelectors(), Collections.emptyMap(), null);

            if (!predicates.isEmpty()) {
                query.where(predicates.toArray(new Predicate[] {}));
            }

            session.getEntityManager().createQuery(query).executeUpdate();

            debug("deleteAll: " + template);

            session.commit();
        } catch (Exception e) {
            error("deleteAll error: " + template, e);

            session.rollback();
            
            throw e;
        }
    }

    @Override
    public I findById(Long id, boolean eager) throws Exception {
        ISessionManager session = getSession();

        try {
            I result = null;
            
            if (id != null) {
                result = inflate(session.getEntityManager().find(getEntityClass(), id), eager);
            }

            debug("findById found: " + result);
    
            session.commit();

            return result;
        } catch (Exception e) {
            error("findById error: " + id, e);

            session.rollback();
            
            throw e;
       }
    }

    @Override
    public I findByKey(K key, boolean eager) throws Exception {
        ISessionManager session = getSession();

        try {
            Query query = session.getEntityManager().createQuery(getBusinessKeyQuery());

            addCriteria(query, key);
            
            return internalFindByKey(query, eager);
        } catch (Exception e) {
            error("findByKey error: " + key, e);

            session.rollback();
           
            throw e;
        }
    }

    private I internalFindByKey(Query query, boolean eager) throws Exception {
        @SuppressWarnings("unchecked")
        List<I> results = (List<I>) query.getResultList();
        
        if (results.size() > 1) {
            throw new NonUniqueResultException();
        }
        
        I result = null;
        
        if (results.size() == 1) {
            result = inflate(results.get(0), eager);
        }
        
        debug("findByKey found: " + result);

        return result;
    }

    @Override
    public Long countAll(I template) throws Exception {
        return countAll(null, Collections.emptyMap());
    }

    @Override
    public Long countAll(I template, Map<String,List<String>> references) throws Exception {
        ISessionManager session = getSession();

        try {
            CriteriaBuilder builder = session.getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
            Root<?> root = countQuery.from(getEntityClass());
            countQuery.select(builder.count(root));

            List<Predicate> predicates = getConditions(builder, root, template, getSelectors(), references, countQuery);

            if (!predicates.isEmpty()) {
                countQuery.where(predicates.toArray(new Predicate[] {}));
            }

            Long result = session.getEntityManager().createQuery(countQuery).getSingleResult();

            debug("countAll found: " + result);

            session.commit();

            return result;
        } catch (Exception e) {
            error("countAll error: " + template, e);

            session.rollback();
            
            throw e;
        }
    }

    @Override
    public List<I> findAll() throws Exception {
        return findAll(null);
    }

    @Override
    public List<I> findAll(I template) throws Exception {
        return findAll(template, null, null);
    }

    @Override
    public List<I> findAll(I template, Integer startPosition, Integer maxSize) throws Exception {
        return findAll(template, Collections.emptyMap(), startPosition, maxSize);
    }

    @Override
    public List<I> findAll(I template, Map<String,List<String>> references, Integer startPosition, Integer maxSize) throws Exception {
        return findAll(template, references, getSelectors(), startPosition, maxSize);
    }

    private List<I> findAll(I template, Map<String,List<String>> references, Map<String, Selector> selectors, Integer startPosition, Integer maxResult) throws Exception {
        ISessionManager session = getSession();

        try {
            CriteriaBuilder builder = session.getEntityManager().getCriteriaBuilder();
            CriteriaQuery<I> criteria = builder.createQuery(getEntityClass());
            Root<I> root = criteria.from(getEntityClass());

            List<Predicate> predicates = getConditions(builder, root, template, selectors, references, criteria);

            if (!predicates.isEmpty()) {
                criteria.select(root).where(predicates.toArray(new Predicate[] {}));
            } else {
                criteria.select(root);
            }

            TypedQuery<I> query = session.getEntityManager().createQuery(criteria);

            if (startPosition != null) {
                query.setFirstResult(startPosition * maxResult);
            }

            if (maxResult != null) {
                query.setMaxResults(maxResult);
            }

            List<I> result = query.getResultList();

            debug("findAll found: " + result);

            session.commit();

            return result;
        } catch (Exception e) {
            error("findAll error: " + template, e);
            
            session.rollback();

            throw e;
        }
    }

    /**
     * Gets the conditions.
     *
     * @param builder the builder
     * @param root the root
     * @param template the template
     * @return the conditions
     * @throws Exception the exception
     */
    private List<Predicate> getConditions(CriteriaBuilder builder, Root<I> root, I template, Map<String, Selector> selectors,
                                          Map<String,List<String>> references, CriteriaQuery criteria) throws Exception {
        List<Predicate> predicates = new ArrayList<>();

        if (template != null) {
            for (Selector selector : selectors.values()) {
                Object value = selector.getGetter().invoke(template);

                if (value != null && !(value instanceof Collection)) {
                    value = value instanceof IItem ? ((IItem) value).getId() : value;
                    Path<Object> field = root.get(selector.getName());
                    Predicate predicate = builder.equal(field, value);
                    predicates.add(predicate);
                }
            }
        }

        if (!references.isEmpty()) {
            for (String key : references.keySet()) {
                Join<?,?> join = root.join(key);
                Expression<String> exp = join.get(DBNames.NAME);
                Predicate predicate = exp.in(references.get(key));
                predicates.add(predicate);
            }
        }

        return predicates;
    }

    @Override
    public void populateLazyCollection(Collection<?> collection) {
        ISessionManager session = getSession();
        
        collection.size();
        
        session.commit();
    }

    @Override
    public void populateLazyCollections() {
        
    }

    /** The logger. */ /**
     * Gets the logger.
     *
     * @return the logger
     */
    private Logger getLogger() {
        return logger;
    }


    /**
     * Info.
     *
     * @param message the message
     */
    private void debug(String message) {
        getLogger().debug(message);
    }

    /**
     * Error.
     *
     * @param message the message
     * @param e the e
     * @throws Exception the exception
     */
    private void error(String message, Exception e) throws Exception {
        getLogger().error(message, e);

        throw e;
    }

    /**
     * Info.
     *
     * @param message the message
     */
    protected void info(String message) {
        getLogger().info(message);
    }

    
    /** The entity class. */
    @Override
    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    /** The selectors. */
    @Override
    public Map<String, Selector> getSelectors() {
        return selectors;
    }

    /**
     * Creates the.
     *
     * @return the e
     * @throws Exception if we are naughty
     */
    @Override
    public I create() throws Exception {
        return (I) getEntityClass().newInstance();
    }

    /**
     * You can't easily override FetchType.LAZY so touch each collection to fill it in as required
     * only goes one level deep.
     * @param entity the entity to inflate
     * @param eager true if inflation is required
     * @return the same entity you passed in with it's lazy collections populated
     * @throws Exception if there is a DB error
     */
    private I inflate(I entity, boolean eager) throws Exception {
        if (eager && entity != null) {
            for (Selector selector : getCollections().values()) {
                Collection<?> collection = (Collection<?>) selector.getGetter().invoke(entity);
                
                if (collection instanceof PersistentCollection) {
                    ((PersistentCollection) collection).forceInitialization();
                }
            }
        }
        
        return entity;
    }

    private ISessionManager getSession() {
        SessionManager sessionManager = getSessionManagerFactory().create();
        sessionManager.begin();
        return sessionManager;
    }

    @Override
    public String getNextId() {
        return getIdGenerator().getNextId(getEntityName(), getIdKey());
    }

    abstract void addCriteria(Query query, K key);

    abstract void addCriteria(Query query, I entity);

    /** The entity manager. */
    protected ISessionManagerFactory getSessionManagerFactory() {
        return sessionManagerFactory;
    }

    /** The business keys. */
    protected Map<String, Selector> getBusinessKeys() {
        return businessKeys;
    }

    /** Collections on the object */
    protected Map<String, Selector> getCollections() {
        return collections;
    }

    protected IIdGenerator getIdGenerator() {
        return idGenerator;
    }

    protected String getBusinessKeyQuery() {
        return businessKeyQuery;
    }

    protected String getIdKey() {
        return idKey;
    }
}