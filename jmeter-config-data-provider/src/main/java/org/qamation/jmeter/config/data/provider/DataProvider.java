package org.qamation.jmeter.config.data.provider;

import java.util.Iterator;


public interface DataProvider {
    public String[] getFieldNames();
    public int getLinesNumber();
    public Iterator<String[]> getIterator();
}
