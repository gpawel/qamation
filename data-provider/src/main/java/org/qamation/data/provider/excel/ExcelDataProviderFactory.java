package org.qamation.data.provider.excel;


import org.qamation.data.provider.DataProviderFactory;

import java.lang.reflect.Constructor;

/**
 * Created by Pavel on 2017-05-11.
 */
public class ExcelDataProviderFactory extends DataProviderFactory {
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
    public static  <A extends ExcelDataProvider> A createExcelDataProviderInstance(String dataProviderImplClassName, String dataFileName, int tabNumber ) {
        A provider = (A)createInstance(dataProviderImplClassName, dataFileName, tabNumber);
        return provider;
    }
}
