package org.qamation.data.provider;


import java.lang.reflect.Constructor;

/**
 * Created by Pavel on 2017-05-11.
 */
public class DataProviderFactory  {

    protected static Class<?> getClassForName(String className) {
        try {
            return Class.forName(className);
        }
        catch(Exception e) {
            throw new RuntimeException("Unable to get Class for "+className,e);
        }
    }

    protected static Object createInstance(String className, String fileName, int tabNumber) {
        try {
            Class<?> clss = getClassForName(className);
            Constructor<?> cons = clss.getConstructor(String.class, int.class);
            Object obj = cons.newInstance(fileName,tabNumber);
            return obj;
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot create ExcelDataProvider instance for class "+className,e);
        }
    }

    protected static Object createInstance(String className, String fileName, int tabNumber, String[] header) {
        try {
            Class<?> clss = getClassForName(className);
            Constructor<?> cons = clss.getConstructor(String.class, int.class, String[].class);
            Object obj = cons.newInstance(fileName,tabNumber,header);
            return obj;
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot create ExcelDataProvider instance for class "+className,e);
        }
    }

    public static  <A extends DataProvider> A createExcelDataProviderInstance(String dataProviderImplClassName, String dataFileName, int tabNumber ) {
        A provider = (A)createInstance(dataProviderImplClassName, dataFileName, tabNumber);
        return provider;
    }

    public static  <A extends DataProvider> A createExcelDataProviderInstance(String dataProviderImplClassName, String dataFileName, int tabNumber, String[] header ) {
        A provider = (A)createInstance(dataProviderImplClassName, dataFileName, tabNumber, header);
        return provider;
    }

}
