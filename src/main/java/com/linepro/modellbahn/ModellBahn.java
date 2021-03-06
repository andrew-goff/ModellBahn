package com.linepro.modellbahn;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;

import javax.validation.Validation;
import org.glassfish.grizzly.http.server.HttpServer;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.google.inject.assistedinject.Assisted;
import com.linepro.modellbahn.jersey.ServerBuilder;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.rest.util.ModellBahnConfiguration;
import com.linepro.modellbahn.util.DBPopulator;
import com.linepro.modellbahn.util.StaticContentFinder;

import io.swagger.jaxrs.config.BeanConfig;

/**
 * ModellBahn. The ModellBahn application
 * 
 * @author $Author:$
 * @version $Id:$
 */
public class ModellBahn implements IModellBahn {
    
    public static final String JERSEY_SERVLET_CONTEXT_PATH = "";
    
    public static final String JSP_CLASSPATH_ATTRIBUTE = "org.apache.catalina.jsp_classpath";

    /** The logger. */
    private final Logger logger;

    /** The populator. */
    private final DBPopulator populator;

    /** The base uri. */
    private final URI baseUri;

    /**
     * Instantiates a new modell bahn.
     *
     * @param loggerFactory
     *            the logger factory
     * @param populator
     *            the populator
     * @param baseUri
     *            the base uri
     */
    @Inject
    public ModellBahn(ILoggerFactory loggerFactory, DBPopulator populator, @Assisted URI baseUri,
            @Assisted Collection<String> staticRoots, @Assisted String storeRoot) {
        this.logger = loggerFactory.getLogger(getClass().getName());
        this.populator = populator;
        this.baseUri = baseUri;

        StaticContentFinder.getFinder().addPaths(staticRoots);
        StaticContentFinder.getStore().setBaseUri(baseUri);
        StaticContentFinder.getStore().setStoreRoot(storeRoot);
    }

    @Override
    public void run() {
        try {
            logger.info(String.format("Application starting: %s", baseUri.toString()));

            populator.populate();

            ModellBahnConfiguration configuration = new ModellBahnConfiguration();

            BeanConfig swaggerConfig = new BeanConfig();
            swaggerConfig.setVersion(ApiPaths.VERSION);
            swaggerConfig.setSchemes(new String[] { baseUri.getScheme() });
            swaggerConfig.setHost(baseUri.getHost());
            swaggerConfig.setBasePath(ApiPaths.API_ROOT);
            swaggerConfig.setResourcePackage(configuration.getPackages());
            swaggerConfig.setScan(true);

            configuration.register(baseUri);

            HttpServer server = new ServerBuilder().getServer(baseUri, configuration);

            //configuration.configureJsp(server);

            Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

            Validation.byDefaultProvider().configure();


            server.start();

            logger.info("Application started with Static content served on {}{}\n" +
                    "Static content from {}; Filestore from {}\n" +
                    "API served on {}{}\n" +
                    "WADL available at {}/{}\n" +                    
                    "Swagger served on {}{}{} to {}{}swagger/index.html\n" +
                    "Press CTRL^C (SIGINT) to terminate.",
                    baseUri, ApiPaths.WEB_ROOT, 
                    StaticContentFinder.getFinder().getAbsolutePaths(), StaticContentFinder.getStore().getStoreRoot(),
                    baseUri, ApiPaths.API_ROOT,
                    baseUri, ApiPaths.APPLICATION_WADL,
                    baseUri, ApiPaths.SWAGGER_ROOT, ApiPaths.SWAGGER_RESOURCE,
                    baseUri, ApiPaths.WEB_ROOT);

            Thread.currentThread().join();

            logger.info("Shutting server down..");
        } catch (Exception ex) {
            logger.error("Application start failed: ", ex);
        }
    }
}