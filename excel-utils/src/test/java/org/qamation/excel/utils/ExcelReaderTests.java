package org.qamation.excel.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

/**
 * Created by Pavel on 2017-05-22.
 */
public class ExcelReaderTests {
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
    public void readAllLines() {
        ExcelReader reader = new ExcelReader(bookXURL.getFile(),0);
        Object[][] data = reader.getData();
        for (int i=0; i<data.length; i++) {
            System.out.println("============================");
            for(int j=0; j<data[i].length; j++) {
                System.out.println("data["+i+"]["+j+"]="+data[i][j]);
            }
        }
    }
}
