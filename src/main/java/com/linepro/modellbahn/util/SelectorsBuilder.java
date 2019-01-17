package com.linepro.modellbahn.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.mapping.Collection;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * SelectorsBuilder. Generates a map of selectors by reflection
 * 
 * @author $Author:$
 * @version $Id:$
 */
public class SelectorsBuilder {

    class SelectorsKey {

        private final Class<?> entityClass;

        private final List<Class<? extends Annotation>> annotations;

        SelectorsKey(Class<?> entityClass, List<Class<? extends Annotation>> annotations) {
            this.entityClass = entityClass;
            this.annotations = annotations;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(getAnnotations())
                    .append(getEntityClass())
                    .hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            SelectorsKey other = (SelectorsKey) obj;

            return new EqualsBuilder()
                    .append(getAnnotations(), other.getAnnotations())
                    .append(getEntityClass(), other.getEntityClass())
                    .isEquals();
        }

        List<Class<? extends Annotation>> getAnnotations() {
            return annotations;
        }

        Class<?> getEntityClass() {
            return entityClass;
        }
    }

    private static final Map<SelectorsKey, Map<String, Selector>> cache = Collections.synchronizedMap(new HashMap<>());

    /** The logger. */
    private final Logger logger;

    /**
     * Instantiates a new selectors builder.
     */
    public SelectorsBuilder() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Builds a map of selectors by reflection.
     *
     * @param entityClass
     *            the entity class
     * @param annotations
     *            the annotations to scan for
     * @return the map of selectors
     */
    @SafeVarargs
    public final Map<String, Selector> build(Class<?> entityClass, Class<? extends Annotation>... annotations) {
        List<Class<? extends Annotation>> annotationList = Arrays.asList(annotations);
        SelectorsKey key = new SelectorsKey(entityClass, annotationList);

        Map<String, Selector> selectors = cache.get(key);

        if (selectors == null) {
            selectors = buildSelectors(entityClass, annotationList);

            cache.put(key, selectors);
        }

        return selectors;
    }

    private Map<String, Selector> buildSelectors(Class<?> entityClass,
                                                 List<Class<? extends Annotation>> annotations) {
        Map<String, Selector> selectors = new HashMap<>();

        try {
            for (Method getter : entityClass.getMethods()) {
                for (Class<? extends Annotation> annotation : annotations) {
                    Annotation annotated = getter.getAnnotation(annotation);

                    if (annotated != null) {
                        Method setter = getSetter(getter);

                        String name;

                        if (annotated instanceof JsonGetter) {
                            name = ((JsonGetter) annotated).value();
                        } else {
                            name = getJpaName(getter.getName());
                        }

                        Selector selector = new Selector(name, getter.getReturnType(), getter, setter,
                                Collection.class.isAssignableFrom(getter.getReturnType()));

                        selectors.put(selector.getName(), selector);
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "SelectorsBuilder", e);
        }

        return selectors;
    }

    /**
     * Gets the jpa name. E.g. for getName returns name.
     * We don't do isBooeanName because we use Booleans not bool
     * @param getterName the getter name
     * @return the jpa name
     */
    private String getJpaName(String getterName) {
        StringBuilder sb = new StringBuilder(getterName);
        sb.delete(0, 3);
        sb.replace(0, 1, StringUtils.lowerCase(sb.substring(0, 1)));
        return sb.toString();
    }

    /**
     * Gets the setter corresponding to the getter.
     * 
     * @param getter
     *            the getter
     * @return the setter or null if none exists
     */
    private Method getSetter(Method getter) {
        StringBuilder sb = new StringBuilder(getter.getName());

        sb.replace(0, 1, "s");

        String setterName = sb.toString();

        try {
            return getter.getDeclaringClass().getMethod(setterName, getter.getReturnType());
        } catch (NoSuchMethodException e) {
            //logger.warning("SelectorsBuilder: no setter for " + getter.getName());
        }

        return null;
    }
}
