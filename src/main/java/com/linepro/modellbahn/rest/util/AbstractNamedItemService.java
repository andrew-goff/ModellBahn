package com.linepro.modellbahn.rest.util;

import static javax.ws.rs.HttpMethod.GET;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.INamedItem;
import com.linepro.modellbahn.persistence.INamedItemPersister;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.impl.StaticPersisterFactory;
import com.linepro.modellbahn.util.Selector;
import com.linepro.modellbahn.util.SelectorsBuilder;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

/**
 * AbstractService.
 * Basic CRUD rest service 
 * @author $Author$
 * @version $Id$
 * 
 * @param <I> the element interface
 * @param <E> the element type
 */
public abstract class AbstractNamedItemService<I extends IItem> extends AbstractItemService<I> {

  /**
   * Instantiates a new abstract service.
   *
   * @param entityClass the entity class
   */
  protected AbstractNamedItemService(final Class<?> entityClass) {
    super(entityClass);
  }

  @ApiResponses({
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  public Response get(String name) {
    return super.get(getPersister().findByKey(name, true));
  }

  /**
   * Adds a new entity.
   *
   * @param entity the entity
   * @return the response
   */
  @ApiResponses({
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  public Response add(I entity) {
    try {
      logPost(getEntityClassName() + ": " + entity);

      I result = getPersister().add(entity);

      return getResponse(created(), result, true, true);
    } catch (Exception e) {
      return getResponse(serverError(e));
    }
  }

  @SuppressWarnings("unchecked")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  public Response update(Long id, I entity) {
    try {
      logPut(id + ": " + id);

      I result = getPersister().update(id, entity);

      if (result == null) {
        return getResponse(notFound());
      }

      return getResponse(accepted(), result, true, true);
    } catch (Exception e) {
      return getResponse(serverError(e));
    }
  }

  @SuppressWarnings("unchecked")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  public Response update(String name, I entity) {

    try {
      logPut(name + ": " + entity);

      I result = getPersister().update(name, entity);

      if (result == null) {
        return getResponse(notFound());
      }

      return getResponse(accepted(), result, true, true);
    } catch (Exception e) {
      return getResponse(serverError(e));
    }
  }

  @SuppressWarnings("unchecked")
  @ApiResponses({
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  public Response delete(Long id) {
    return super.delete(id);
  }

  @SuppressWarnings("unchecked")
  @ApiResponses({
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  public Response delete(String name) {
    try {
      logDelete(getEntityClassName() + ": " + name);

      getPersister().delete(name);

      return getResponse(noContent());
    } catch (Exception e) {
      return getResponse(serverError(e));
    }
  }

  /**
   * Searches for entities (by example using Uri query parameters).
   *
   * @param info the info
   * @return the response
   */
  @ApiResponses({
      @ApiResponse(code = 204, message = "No Content"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  public Response search(UriInfo info) {
    return search(info, info.getQueryParameters());
  }



  /**
   * Gets the persister.
   *
   * @return the persister
   */
  protected INamedItemPersister<I> getPersister() {
    return persister;
  }
}
