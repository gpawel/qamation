package org.qamation.excel.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Iterator;

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

    @Test
    public void readAllLinesAddHeader() {
        String[] header = new String[] {"Field 1","Field 2","Field 3","Field 4"};
        ExcelReader reader = new ExcelReader(bookXURL.getFile(),header);
        Iterator<String[]> itrtr = reader.getIterator();
        while (itrtr.hasNext()) {
            String [] line = itrtr.next();
            System.out.println("======= Start Line =======");
            for(int i = 0; i < line.length; i++) {
                System.out.print(header[i]+" = "+line[i]+"; ");
            }
            System.out.println();
            System.out.println("======= End Line =======");

        }
    }

    @Test
    public void readAllLinesWithHeader() {
        ExcelReader reader = new ExcelReader(bookXURL.getFile(),1);
        String[] header = reader.getFieldNames();
        Iterator<String[]> itrtr = reader.getIterator();
        while (itrtr.hasNext()) {
            String [] line = itrtr.next();
            System.out.println("======= Start Line =======");
            for(int i = 0; i < line.length; i++) {
                System.out.print(header[i]+" = "+line[i]+"; ");
            }
            System.out.println();
            System.out.println("======= End Line =======");

        }
    }
}
