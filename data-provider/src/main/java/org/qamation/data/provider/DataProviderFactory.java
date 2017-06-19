package org.qamation.data.provider;

import java.lang.reflect.Constructor;

/**
 * Created by Pavel on 2017-04-23.
 */
public class DataProviderFactory {
    protected static Class<?> getClassForName(String className) {
        try {
            return Class.forName(className);
        }
        catch(Exception e) {
            throw new RuntimeException("Unable to get Class for "+className,e);
        }
    }

    protected static Object createInstance(String className, String fileName) {
        try {
            Class<?> pageClass = getClassForName(className);
            Constructor<?> cons = pageClass.getConstructor(String.class,String.class);
            Object obj = cons.newInstance(className,fileName);
            return obj;
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot create Instance for class "+className,e);
        }
    }

    public static  <A extends DataProvider> A createDataProviderInstance(String dataProviderImplClassName, String dataFileName ) {
        A page = (A)createInstance(dataProviderImplClassName, dataFileName);
        return page;
    }
}
