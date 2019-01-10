package com.linepro.modellbahn.rest.util;

import static javax.ws.rs.HttpMethod.GET;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.impl.StaticPersisterFactory;
import com.linepro.modellbahn.util.Selector;
import com.linepro.modellbahn.util.SelectorsBuilder;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * AbstractService.
 * Basic CRUD rest service
 * @author $Author$
 * @version $Id$
 * @param <I> the element interface
 */
public abstract class AbstractItemService<I extends IItem, K> extends AbstractService {

    protected static final List<String> PAGE_FIELDS = Arrays.asList(ApiNames.PAGE_NUMBER, ApiNames.PAGE_SIZE);

    protected static final Integer FIRST_PAGE = 0;

    protected static final Integer DEFAULT_PAGE_SIZE = 30;

    /** The persister. */
    protected final IPersister<I, K> persister;

    /** The entity class. */
    protected final Class<?> entityClass;

    /** The selectors. */
    protected final Map<String, Selector> selectors;

    protected Link apiLink;

    protected Link homeLink;

    protected Link wadlLink;

    protected URI serviceURI;

    /**
     * Instantiates a new abstract service.
     * @param entityClass the entity class
     */
    protected AbstractItemService(final Class<?> entityClass) {
        this.entityClass = entityClass;
        this.persister = StaticPersisterFactory.get().createPersister(entityClass);
        this.selectors = new SelectorsBuilder().build(entityClass, JsonGetter.class);
    }

    @ApiResponses({@ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response get(Long id) {
        try {
            logGet(getEntityClassName() + ": " + id);

            I entity = getPersister().findById(id, true);

            if (entity == null) {
                return getResponse(notFound());
            }

            return getResponse(ok(), entity, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

    }

    @ApiResponses({@ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response get(K key) {
        try {
            logGet(getEntityClassName() + ": " + key);

            I entity = getPersister().findByKey(key, true);

            if (entity == null) {
                return getResponse(notFound());
            }

            return getResponse(ok(), entity, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

    }

    @ApiResponses({@ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response get(I entity) {
        try {
            logGet(getEntityClassName() + ": " + entity);

            I result = getPersister().findByKey(entity, true);

            if (result == null) {
                return getResponse(notFound());
            }

            return getResponse(ok(), result, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    /**
     * Adds a new entity.
     * @param entity the entity
     * @return the response
     */
    @ApiResponses({@ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response add(I entity) {
        try {
            logPost(getEntityClassName() + ": " + entity);

            I result = getPersister().add(entity);

            return getResponse(created(), result, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @ApiResponses({@ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response update(Long id, I entity) {
        try {
            logPut(getEntityClassName() + ": " + id + ", " +entity);

            I result = getPersister().update(id, entity);

            if (result == null) {
                return getResponse(notFound());
            }

            return getResponse(accepted(), result, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @ApiResponses({@ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response update(K key, I entity) {
        try {
            logPut(getEntityClassName() + ": " + key + ", " + entity);

            I result = getPersister().update(key, entity);

            if (result == null) {
                return getResponse(notFound());
            }

            return getResponse(accepted(), result, true, true);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    @ApiResponses({@ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response delete(Long id) {
        try {
            logDelete(getEntityClassName() + ": " + id);

            getPersister().delete(id);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

    }

    @ApiResponses({@ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response delete(K key) {
        try {
            logDelete(getEntityClassName() + ": " + key);

            getPersister().delete(key);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }

    }

    @ApiResponses({@ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response delete(I entity) {
        try {
            logDelete(getEntityClassName() + ": " + entity);

            getPersister().delete(entity);

            return getResponse(noContent());
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    /**
     * Searches for entities (by example using Uri query parameters).
     * @param info the info
     * @return the response
     */
    @ApiResponses({@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    protected Response search(UriInfo info) {
        return search(info, info.getQueryParameters());
    }

    protected Response search(UriInfo info, MultivaluedMap<String, String> queryParameters) {
        try {
            I template = getTemplate(queryParameters);

            logGet(getEntityClassName() + ": " + template);

            Integer pageNumber = null;
            Integer pageSize = null;
            Integer maxPage = null;

            String pageNumberStr = queryParameters.getFirst(ApiNames.PAGE_NUMBER);
            String pageSizeStr = queryParameters.getFirst(ApiNames.PAGE_SIZE);

            if (pageNumberStr != null || pageSizeStr != null) {
                pageNumber = NumberUtils.toInt(pageNumberStr, FIRST_PAGE);
                pageSize = NumberUtils.toInt(pageSizeStr, DEFAULT_PAGE_SIZE);

                Long rowCount = getPersister().countAll(template);

                maxPage = new Double(Math.floor(rowCount.doubleValue() / pageSize.doubleValue())).intValue();
            }

            @SuppressWarnings("unchecked")
            List<IItem> entities = (List<IItem>) getPersister().findAll(template, pageNumber, pageSize);

            if (entities.isEmpty()) {
                return getResponse(noContent());
            }

            List<Link> navigation = getNavLinks(info, pageNumber, pageSize, maxPage);

            return getResponse(ok(), entities, true, true, navigation);
        } catch (Exception e) {
            return getResponse(serverError(e));
        }
    }

    protected List<Link> getNavLinks(UriInfo info, Integer pageNumber, Integer pageSize, Integer maxPage) {
        List<Link> navigation = new ArrayList<>();

        if (pageNumber != null) {
            if (pageNumber > 0) {
                navigation.add(getPageLink(info, pageNumber - 1, pageSize, ApiNames.PREVIOUS));
            }

            if (pageNumber < maxPage) {
                navigation.add(getPageLink(info, pageNumber + 1, pageSize, ApiNames.NEXT));
            }
        }

        return navigation;
    }

    protected Link getPageLink(UriInfo info, Integer pageNumber, Integer pageSize, String rel) {
        // copy any non paging query parameters
        UriBuilder uri = info.getAbsolutePathBuilder();
        MultivaluedMap<String, String> queryParameters = info.getQueryParameters();

        queryParameters.forEach((k, v) -> {
            if (!PAGE_FIELDS.contains(k)) {
                v.forEach(x -> uri.queryParam(k, x));
            }
        });

        uri.queryParam(ApiNames.PAGE_NUMBER, pageNumber.toString());
        uri.queryParam(ApiNames.PAGE_SIZE, pageSize.toString());

        Builder link = Link.fromUri(uri.toString());

        return link.rel(rel).type(GET).build();
    }

    /**
     * Createa a template for search / delete from query parameters.
     * @param queryParameters the query parameters
     * @return the template
     * @throws Exception if reflection fails
     */
    protected I getTemplate(MultivaluedMap<String, String> queryParameters) throws Exception {
        I template = getPersister().create();

        if (!queryParameters.isEmpty()) {
            for (String name : queryParameters.keySet()) {
                Object value = queryParameters.getFirst(name);

                Selector selector = getSelectors().get(name);

                if (selector != null) {
                    selector.getSetter().invoke(template,
                            ConvertUtils.convert(value, selector.getGetter().getReturnType()));
                }
            }
        }

        return template;
    }

    /**
     * Gets the persister.
     * @return the persister
     */
    protected IPersister<I, K> getPersister() {
        return persister;
    }

    /**
     * Gets the entity class.
     * @return the entity class
     */
    protected Class<?> getEntityClass() {
        return entityClass;
    }

    protected String getEntityClassName() {
        return entityClass.getSimpleName();
    }

    /**
     * Gets the selectors.
     * @return the selectors
     */
    protected Map<String, Selector> getSelectors() {
        return selectors;
    }

    /**
     * Make link.
     * @param uri the uri
     * @param path the path
     * @param rel the rel
     * @param method the method
     * @return the link
     */
    protected Link makeLink(URI uri, String path, String rel, String method) {
        return Link.fromUri(UriBuilder.fromUri(uri).path(path).build()).rel(rel).type(method).build();
    }

    protected Response getResponse(ResponseBuilder builder, IItem entity, boolean update, boolean delete) {
        entity.addLinks(getServiceURI(), update, delete);
        return getResponse(builder.entity(entity));
    }

    protected Response getResponse(ResponseBuilder builder, List<IItem> entities, boolean update, boolean delete,
            List<Link> navigation) {
        for (IItem entity : entities) {
            entity.addLinks(getServiceURI(), update, delete);
        }

        return getResponse(builder.entity(new ListWithLinks<>(entities, navigation)));
    }

    /**
     * Gets the response.
     * @param builder the builder
     * @return the response
     */
    protected Response getResponse(ResponseBuilder builder) {
        return builder.links(getHomeLink(), getApiLink(), getWADLLink()).build();
    }

    protected URI getServiceURI() {
        if (serviceURI == null) {
            serviceURI = getUriInfo().getBaseUriBuilder().path(getClass()).build();
        }

        return serviceURI;
    }

    protected Link getApiLink() {
        if (apiLink == null) {
            apiLink = makeLink(getUriInfo().getBaseUri(), ApiPaths.API_ROOT, ApiNames.API, GET);
        }

        return apiLink;
    }

    protected Link getHomeLink() {
        if (homeLink == null) {
            homeLink = makeLink(getUriInfo().getBaseUri(), "", ApiNames.HOME, GET);
        }

        return homeLink;
    }

    protected Link getWADLLink() {
        if (wadlLink == null) {
            wadlLink = makeLink(getUriInfo().getBaseUri(), ApiPaths.APPLICATION_WADL, ApiNames.WADL, GET);
        }

        return wadlLink;
    }
}
