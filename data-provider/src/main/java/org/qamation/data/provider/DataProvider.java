package org.qamation.data.provider;

import java.util.Iterator;

/**
 * Created by Pavel on 2017-05-11.
 */
public interface DataProvider {
    public int getNumberOfLines();
    public String[] getFieldNames();
    public String[] getLine(int lineIndex);
    public Iterator<String[]> getIterator();

}
