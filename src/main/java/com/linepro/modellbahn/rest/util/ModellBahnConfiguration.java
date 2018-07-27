package com.linepro.modellbahn.rest.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.linepro.modellbahn.jersey.LoggingFilter;
import com.linepro.modellbahn.jersey.SecurityRequestFilter;

/**
 * ModellBahnConfiguration.
 * Jersey configuration for the ModellBahn application 
 * @author  $Author:$
 * @version $Id:$
 */
@ApplicationPath("/")
public class ModellBahnConfiguration extends ResourceConfig {

    /** The Constant PACKAGES. */
    private static final String[] PACKAGES = { "com.linepro.modellbahn.rest" };

    /**
     * Instantiates a new modell bahn configuration.
     */
    @Inject
    public ModellBahnConfiguration() {
        packages(PACKAGES);
        
        property(JsonGenerator.PRETTY_PRINTING, true);

        // MVC.
        //property(MvcFeature.TEMPLATE_BASE_PATH, "/jsp");
        property(ServerProperties.MONITORING_ENABLED, true);
        property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);
        property(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, true);

        property(ServerProperties.TRACING, "ALL");
        property(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
        
        property(ServletProperties.FILTER_CONTEXT_PATH, "/ModellBahn"); 
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/web/**/*");
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "//web//**//*");

        /*
        property(ClassNamesResourceConfig.PROPERTY_CLASSNAMES, "");
        property(ClasspathResourceConfig.PROPERTY_CLASSPATH, "");
        property(FeaturesAndProperties.FEATURE_DISABLE_XML_SECURITY, "");
        property(FeaturesAndProperties.FEATURE_FORMATTED, "");
        property(FeaturesAndProperties.FEATURE_PRE_1_4_PROVIDER_PRECEDENCE, "");
        property(FeaturesAndProperties.FEATURE_XMLROOTELEMENT_PROCESSING, "");
        property(JSONMarshaller.FORMATTED, "");
        property(LoggingFilter.FEATURE_LOGGING_DISABLE_ENTITY, "");
        property(PackagesResourceConfig.PROPERTY_PACKAGES, "");
        property(ResourceConfig.FEATURE_CANONICALIZE_URI_PATH, "");
        property(ResourceConfig.FEATURE_DISABLE_WADL, "");
        property(ResourceConfig.FEATURE_IMPLICIT_VIEWABLES, "");
        property(ResourceConfig.FEATURE_MATCH_MATRIX_PARAMS, "");
        property(ResourceConfig.FEATURE_NORMALIZE_URI, "");
        property(ResourceConfig.FEATURE_REDIRECT, "");
        property(ResourceConfig.FEATURE_TRACE, "");
        property(ResourceConfig.FEATURE_TRACE_PER_REQUEST, "");
        property(ResourceConfig.PROPERTY_CONTAINER_NOTIFIER, "");
        property(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS, "");
        property(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, "");
        property(ResourceConfig.PROPERTY_DEFAULT_RESOURCE_COMPONENT_PROVIDER_FACTORY_CLASS, "");
        property(ResourceConfig.PROPERTY_LANGUAGE_MAPPINGS, "");
        property(ResourceConfig.PROPERTY_MEDIA_TYPE_MAPPINGS, "");
        property(ResourceConfig.PROPERTY_RESOURCE_FILTER_FACTORIES, "");
        property(ResourceConfig.PROPERTY_WADL_GENERATOR_CONFIG, "");
        property(ServletProperties.APPLICATION_CONFIG_CLASS, "");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, true);
        property(ServletProperties.GLASSFISH_DEFAULT_ERROR_PAGE_RESPONSE, "");
        property(ServletProperties.JSP_TEMPLATES_BASE_PATH, "");
        property(ServletProperties.PROPERTY_FILTER_CONTEXT_PATH, "");
        property(ServletProperties.PROPERTY_WEB_PAGE_CONTENT_REGEX, "");
        property(ServletProperties.RESOURCE_CONFIG_CLASS, "");
        property(WebComponent.APPLICATION_CONFIG_CLASS, "");
        property(WebComponent.JSP_TEMPLATES_BASE_PATH, "");
        property(WebComponent.RESOURCE_CONFIG_CLASS, "");
        */

        register(LoggingFilter.class);
        register(SecurityRequestFilter.class);

        //register(MvcFeature.class);
        //register(JspMvcFeature.class);

        // Logging.
        //register(new LoggingFeature(getLogger(), Level.FINEST, LoggingFeature.Verbosity.PAYLOAD_ANY, 8192));

        // Tracing support.
        //register(DeclarativeLinkingFeature.class);
    }
    
    /**
     * Gets the logger.
     *
     * @return the logger
     */
    protected Logger getLogger() {
        Logger logger = java.util.logging.Logger.getLogger("ModellBahn");
        logger.setLevel(Level.FINEST);

        //SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        
        return logger;
    }
}