package org.qamation.data.provider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.Iterator;

public class DataProviderFactoryTest {
    private static final Logger log = LoggerFactory.getLogger(DataProviderFactoryTest.class);
    ClassLoader classLoader;
    URL bookXURL;
    URL bookSURL;
    @Before
    public void setUp() {
        classLoader = Thread.currentThread().getContextClassLoader();
        bookXURL = classLoader.getResource("Book2.xlsx");
        bookSURL = classLoader.getResource("Book1.xls");
    }
    @After
    public void tearDown() {}

    @Test
    public void testFactoryJustFile() {
        DataProvider provider = DataProviderFactory
                .createDataProviderInstance(
                        DataProviderExcelAdapter.class.getName(),bookXURL.getFile());
        Assert.assertNotNull(provider);

        String[] header = provider.getFieldNames();
        while (provider.hasNext()) {
            String [] line = provider.next();
            System.out.println("======= Start Line =======");
            for(int i = 0; i < line.length; i++) {
                System.out.print(header[i]+" = "+line[i]+"; ");
            }
            System.out.println();
            System.out.println("======= End Line =======");

        }
    }

    @Test
    public void testFactoryFileAndTabNumber() {
        DataProvider provider = DataProviderFactory
                .createDataProviderInstance(
                        DataProviderExcelAdapter.class.getName(),bookXURL.getFile(),1);
        Assert.assertNotNull(provider);
        String[] header = provider.getFieldNames();
        while (provider.hasNext()) {
            String [] line = provider.next();
            System.out.println("======= Start Line =======");
            for(int i = 0; i < line.length; i++) {
                System.out.print(header[i]+" = "+line[i]+"; ");
            }
            System.out.println();
            System.out.println("======= End Line =======");

        }
    }

    @Test
    public void testFactoryFileAndTabNumberAndHeader() {
        String[] header = new String[] {"V1","V2","V3"};
        DataProvider provider = DataProviderFactory
                .createDataProviderInstance(
                        DataProviderExcelAdapter.class.getName(),bookXURL.getFile(),1,header);
        Assert.assertNotNull(provider);

        while (provider.hasNext()) {
            String [] line = provider.next();
            System.out.println("======= Start Line =======");
            for(int i = 0; i < line.length; i++) {
                System.out.print(header[i]+" = "+line[i]+"; ");
            }
            System.out.println();
            System.out.println("======= End Line =======");

        }
    }

    @Test
    public void testFactoryFileAndHeader() {
        String[] header = new String[] {"V1","V2","V3","V4"};
        DataProvider provider = DataProviderFactory
                .createDataProviderInstance(
                        DataProviderExcelAdapter.class.getName(),bookXURL.getFile(),header);
        Assert.assertNotNull(provider);

        while (provider.hasNext()) {
            String [] line = provider.next();
            System.out.println("======= Start Line =======");
            for(int i = 0; i < line.length; i++) {
                System.out.print(header[i]+" = "+line[i]+"; ");
            }
            System.out.println();
            System.out.println("======= End Line =======");

        }
    }
}
