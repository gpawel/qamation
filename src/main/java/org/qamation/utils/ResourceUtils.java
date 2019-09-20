package org.qamation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

/**
 * Created by Pavel.Gouchtchine on 12/09/2016.
 */
public class ResourceUtils {
    public static Logger log = LoggerFactory.getLogger(ResourceUtils.class);
    public static String PROPERTIES_FILE_SUFFIX = ".properties";

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

    public static void loadProperties(String path) {
        loadAllProperties(path);
    }

    public static void loadProperties() {
        loadAllProperties(System.getProperty("user.dir"));
    }

    private static void loadAllProperties(String propertiesPath) {

        String[] paths = FileUtils.listFilesInFolder(propertiesPath);
        for (String p: paths) {
            if (p.endsWith(".properties") || p.endsWith("PROPERTIES") ) FileUtils.loadPropertiesFile(p);
        }
    }










}
