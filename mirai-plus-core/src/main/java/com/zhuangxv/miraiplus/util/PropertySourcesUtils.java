package com.zhuangxv.miraiplus.util;

import org.springframework.core.env.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xiaoxu
 */
public class PropertySourcesUtils {

    private PropertySourcesUtils() {
    }

    public static Map<String, Object> getSubProperties(Iterable<PropertySource<?>> propertySources, String prefix) {
        AbstractEnvironment environment = new AbstractEnvironment() {
        };
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            mutablePropertySources.addLast(propertySource);
        }
        return getSubProperties(environment, prefix);
    }

    public static Map<String, Object> getSubProperties(ConfigurableEnvironment environment, String prefix) {
        Map<String, Object> subProperties = new LinkedHashMap<>();
        MutablePropertySources propertySources = environment.getPropertySources();
        String normalizedPrefix = normalizePrefix(prefix);
        Iterator<?> iterator = propertySources.iterator();
        while (true) {
            PropertySource<?> source;
            do {
                if (!iterator.hasNext()) {
                    return Collections.unmodifiableMap(subProperties);
                }
                source = (PropertySource<?>) iterator.next();
            } while (!(source instanceof EnumerablePropertySource));
            String[] propertyNames = ((EnumerablePropertySource<?>) source).getPropertyNames();
            for (String name : propertyNames) {
                if (!subProperties.containsKey(name) && name.startsWith(normalizedPrefix)) {
                    String subName = name.substring(normalizedPrefix.length());
                    if (!subProperties.containsKey(subName)) {
                        Object value = source.getProperty(name);
                        if (value instanceof String) {
                            value = environment.resolvePlaceholders((String) value);
                        }
                        subProperties.put(subName, value);
                    }
                }
            }
        }
    }

    public static String normalizePrefix(String prefix) {
        return prefix.endsWith(".") ? prefix : prefix + ".";
    }

}
