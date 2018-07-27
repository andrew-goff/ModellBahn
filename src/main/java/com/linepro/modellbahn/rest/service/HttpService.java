package com.linepro.modellbahn.rest.service;

import java.io.File;
import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.linepro.modellbahn.rest.util.AbstractService;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.StaticContentFinder;

@Path(ApiPaths.WEB_ROOT)
public class HttpService extends AbstractService {

    private static final String WEB_ROOT = StringUtils.stripStart(ApiPaths.WEB_ROOT, "//");

    public HttpService() {
    }

    @GET
    @Path("{docPath:.*}")
    @Produces({MediaType.WILDCARD})
    public Response getFolder(@PathParam("docPath") String docPath) {
        URI requested = uriInfo.getBaseUri().relativize(uriInfo.getRequestUri());

        String stripedPath = StringUtils.removeStart(requested.getPath(), WEB_ROOT);

        String path = StringUtils.isBlank(stripedPath) ? "index.html" :stripedPath;

        info("GET " + path);

        File file = StaticContentFinder.get().findFile(path);

        if (file != null) {
            return Response.ok(file).build();
        }

        return Response.noContent().build();
    }
}