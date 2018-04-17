package org.qamation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by Pavel.Gouchtchine on 12/09/2016.
 */
public class ResourceUtils {
    public static Logger log = LoggerFactory.getLogger(ResourceUtils.class);
    public static URL getURLForResouce(String resourceName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(resourceName);
    }
    public static String getSystemProperty(String property, String defValue) {
        String value = System.getProperty(property);
        if (value == null) {
            log.warn(property+" is not set. It is recommended to set it using -D"+property+" for a start up string.");
            return defValue;
        };
        return value;
    }
}
