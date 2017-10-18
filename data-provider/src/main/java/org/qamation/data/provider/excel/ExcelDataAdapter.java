package org.qamation.data.provider.excel;

import org.qamation.data.provider.DataProviderAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;


public class ExcelDataAdapter extends DataProviderAdapter implements ExcelDataProvider {
    private static final Logger log = LoggerFactory.getLogger(ExcelDataAdapter.class);
    private int sheetIndex;
    private Iterator <String[]> iterator;



    public ExcelDataAdapter(String fileName, int sheetIndx) {
        super(fileName);
        this.sheetIndex = sheetIndx;
        this.currentIndex = 1;
        this.iterator = excelReader.iterator();
    }



    public String[] getFieldNames() {
        return excelReader.getFieldNames();
    }

    public int getDataSize() {
        return getSize()-1;
    }

    public Iterator<String[]> getIterator() {
        return this.iterator;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    @Override
    public synchronized void reset() {
        currentIndex = 1;
        iterator = excelReader.iterator();
    }


}
