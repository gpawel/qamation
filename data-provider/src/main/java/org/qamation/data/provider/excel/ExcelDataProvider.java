package org.qamation.data.provider.excel;

import java.util.Iterator;
import org.qamation.data.provider.DataProvider;

public interface ExcelDataProvider extends DataProvider{
    public String[] getFieldNames();
    public int getLinesNumber();
    public Iterator<String[]> getIterator();
}
