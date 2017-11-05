package org.qamation.data.provider;

import org.qamation.excel.utils.ExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;
import java.util.function.Consumer;


public class DataProviderExcelAdapter implements DataProvider {
    private static final Logger log = LoggerFactory.getLogger(DataProviderExcelAdapter.class);
    protected ExcelReader excelReader;
    protected String fileName;
    private int sheetIndex;
    private Iterator<String[]> iterator;


    public DataProviderExcelAdapter(String fileName) {
        this(fileName,0);
    }

    public DataProviderExcelAdapter(String fileName, int sheetIndx) {
        this.fileName = fileName;
        this.excelReader = new ExcelReader(fileName,sheetIndx);
        reset();
        // we assume the excel has header
    }

    public DataProviderExcelAdapter(String fileName, String[] header) {
        this(fileName,0,header);
    }

    public DataProviderExcelAdapter(String fileName, int sheetIndex, String[] header) {
        this.fileName = fileName;
        this.sheetIndex = sheetIndex;
        this.excelReader = new ExcelReader(fileName,sheetIndex,header);
        reset();
    }


    @Override
    public int getNumberOfLines() {
        return this.excelReader.getNumberOfLinesInActiveWorkSheet();
    }

    @Override
    public String[] getFieldNames() {
        return this.excelReader.getFieldNames();
    }

    @Override
    public String[] getLine(int lineIndex) {
        return new String[0];
    }

    @Override
    public void reset() {
        this.iterator = null;
        this.iterator = excelReader.getIterator();
    }

    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    public String[] next() {
        return iterator.next();
    }


}
