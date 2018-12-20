package org.qamation.data.provider;


import java.lang.reflect.Constructor;

/**
 * Created by Pavel on 2017-05-11.
 */
public class DataProviderFactory  {

    public static  <A extends DataProvider> A createDataProviderInstance(String dataProviderImplClassName, String dataFileName ) {
        A provider = (A)createInstance(dataProviderImplClassName, dataFileName);
        return provider;
    }


    public static  <A extends DataProvider> A createDataProviderInstance(String dataProviderImplClassName, String dataFileName, int tabNumber ) {
        A provider = (A)createInstance(dataProviderImplClassName, dataFileName, tabNumber);
        return provider;
    }

    public static  <A extends DataProvider> A createDataProviderInstance(String dataProviderImplClassName, String dataFileName, int tabNumber, String[] header ) {
        A provider = (A)createInstance(dataProviderImplClassName, dataFileName, tabNumber, header);
        return provider;
    }

    public static  <A extends DataProvider> A createDataProviderInstance(String dataProviderImplClassName, String dataFileName, String[] header ) {
        A provider = (A)createInstance(dataProviderImplClassName, dataFileName, header);
        return provider;
    }

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
            e.printStackTrace();
            throw new RuntimeException("Cannot create ExcelDataProvider instance for file "+fileName+" using class: "+className,e);
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
            e.printStackTrace();
            throw new RuntimeException("Cannot create ExcelDataProvider instance for file "+fileName+" using class: "+className,e);
        }
    }

    protected  static Object createInstance(String dataProviderImplClassName, String dataFileName) {
        return createInstance(dataProviderImplClassName,dataFileName,0);
    }

    protected  static Object createInstance(String dataProviderImplClassName, String dataFileName, String[] header) {
        return createInstance(dataProviderImplClassName,dataFileName,0,header);
    }


}
