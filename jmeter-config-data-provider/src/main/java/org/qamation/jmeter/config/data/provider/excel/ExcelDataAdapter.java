package org.qamation.jmeter.config.data.provider.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import org.qamation.jmeter.config.data.provider.ExcelToDataProviderAdapter;


public class ExcelDataAdapter extends ExcelToDataProviderAdapter implements ExcelDataProvider {
    private static final Logger log = LoggerFactory.getLogger(ExcelDataAdapter.class);
    private int sheetIndex;

    public ExcelDataAdapter(String className, String fileName, int sheetIncex) {
        super(className,fileName,sheetIncex);
    }


    @Override
    public String[] getFieldNames() {
        return reader.getFieldNames();
    }

    @Override
    public int getLinesNumber() {
        return reader.getNmberOfLinesInActiveWorkSheet();
    }

    @Override
    public Iterator<String[]> getIterator() {
        return reader.iterator();
    }

    public String getFilename() {
        return reader.getFileName();
    }

    public int getSheetIndex() {
        return reader.getActiveSheetIndex();
    }

    @Override
    public Object[][] getData() {
        return data;
    }


}
