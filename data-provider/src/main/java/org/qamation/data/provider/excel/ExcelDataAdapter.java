package org.qamation.data.provider.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;


public class ExcelDataAdapter  implements ExcelDataProvider {
    private static final Logger log = LoggerFactory.getLogger(ExcelDataAdapter.class);
    private int sheetIndex;

    protected ExcelDataProvider provider;
    protected String fileName;

    public ExcelDataAdapter(String className, String fileName, int sheetIncex) {
        this.sheetIndex = sheetIncex;
        this.fileName = fileName;
        provider = ExcelDataProviderFactory.createExcelDataProviderInstance(className,fileName,sheetIncex);
    }

    @Override
    public String[] getFieldNames() {
        return provider.getFieldNames();
    }

    @Override
    public int getLinesNumber() {
        return provider.getLinesNumber();
    }

    @Override
    public Iterator<String[]> getIterator() {
        return provider.getIterator();
    }

    public String getFilename() {
        return fileName;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    @Override
    public Object[][] getData() {
        return provider.getData();
    }


}
