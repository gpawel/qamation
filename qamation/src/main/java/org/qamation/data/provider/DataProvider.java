package org.qamation.data.provider;

import java.util.Iterator;

/**
 * Created by Pavel on 2017-05-11.
 */
public interface DataProvider extends Iterator<String[]> {
    int getNumberOfLines();
    String[] getFieldNames();
    String[] getLine(int lineIndex);
    Object[][] getDataAsArray();
    Iterator<Object[]> getDataAsIterator();
    void reset();
    void reload();
    void close();
}
