package org.qamation.jmeter.config.data.provider;

import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;

/**
 * Created by Pavel on 2017-04-23.
 */
public class DataProviderFactory {
    public static Object createInstance(String className, String fileName) {
        try {
            Class<?> pageClass = Class.forName(className);
            Constructor<?> cons = pageClass.getConstructor(String.class);
            Object obj = cons.newInstance(fileName);
            return obj;
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot create DataProvider for class "+className,e);
        }
    }

    public static  <A extends DataProvider> A createDataProviderInstance(String dataProviderImplClassName, String dataFileName ) {
        A page = (A)createInstance(dataProviderImplClassName, dataFileName);
        return page;
    }
}
