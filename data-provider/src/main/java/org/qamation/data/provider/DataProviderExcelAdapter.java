package org.qamation.data.provider;

import org.qamation.excel.utils.ExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;


public class DataProviderExcelAdapter implements DataProvider {
    private static final Logger log = LoggerFactory.getLogger(DataProviderExcelAdapter.class);
    protected ExcelReader excelReader;
    protected String fileName;
    protected int currentIndex;
    private int sheetIndex;
    private Iterator <String[]> iterator;

    public DataProviderExcelAdapter(String fileName) {
        this(fileName,0);
    }

    public DataProviderExcelAdapter(String fileName, int sheetIndx) {
        this.fileName = fileName;
        this.excelReader = new ExcelReader(fileName,sheetIndx);
        this.currentIndex = 1;
    }

    public DataProviderExcelAdapter(String fileName, String[] header) {

    }

    public DataProviderExcelAdapter(String fileName, int sheetIndex, String[] header) {

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
    //
    // private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderExcelAdapter.class);





    @Override
    public Object[][] getData() {
        return data;
    }

    @Override
    public int getCurrentLineIndex() {
        return currentIndex;
    }

    @Override
    public int getNumberOfLines() {
        return 0;
    }

    @Override
    public int getNumberOfFields() {
        return 0;
    }

    @Override
    public String[] getFields() {
        return new String[0];
    }

    @Override
    public Object[] getLine(int i) {
        return data[i];
    }

    @Override
    public synchronized Object[] getNextLine() {
        if (currentIndex == data.length) reset();
        return data[currentIndex++];
    }

    @Override
    public synchronized void reset() {
        currentIndex = 0;
    }

    @Override
    public int getSize() {
        return data.length;
    }

    @Override
    public  synchronized void close() {
        try {
            if (excelReader != null ) {
                excelReader.closeWorkBook();
                excelReader = null;
                data = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}
