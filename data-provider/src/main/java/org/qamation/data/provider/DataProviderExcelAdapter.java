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
    private int sheetIndex = 0;
    private Iterator<String[]> iterator;
    private String[] header = null;


    public DataProviderExcelAdapter(String fileName) {
        this(fileName, 0);
    }

    public DataProviderExcelAdapter(String fileName, int sheetIndx) {
        this.fileName = fileName;
        this.sheetIndex = sheetIndx;
        this.excelReader = new ExcelReader(fileName, sheetIndx);

        // we assume the excel has header
    }

    public DataProviderExcelAdapter(String fileName, String[] header) {
        this(fileName, 0, header);
    }

    public DataProviderExcelAdapter(String fileName, int sheetIndex, String[] header) {
        this.fileName = fileName;
        this.sheetIndex = sheetIndex;
        this.header = header;
        this.excelReader = new ExcelReader(fileName, sheetIndex, header);
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
        return new String[lineIndex];
    }

    @Override
    public Object[][] getDataAsArray() {
        return excelReader.getData();
    }

    @Override
    public Iterator<Object[]> getDataAsIterator() {
        Iterator<String[]> et = excelReader.getIterator();
        return new Iterator<Object[]>() {
            @Override
            public boolean hasNext() {
                return et.hasNext();
            }

            @Override
            public Object[] next() {
                return et.next();
            }
        };
    }

    @Override
    public void reset() {
        this.iterator = null;
        this.iterator = setIterator();
    }

    @Override
    public void reload() {
        close();
        if (header == null)
            this.excelReader = new ExcelReader(fileName, sheetIndex);
        else
            this.excelReader = new ExcelReader(fileName, sheetIndex, header);
    }

    @Override
    public void close() {
        try {
            //log.info("closing "+excelReader.getFileName()+" workbook");
            excelReader.closeWorkBook();
            iterator = null;

        } catch (Exception e) {
            e.printStackTrace();
            String message = "Unable to close DataProvider for " + excelReader.getFileName() + " with original file name " + excelReader.getOriginalFileName() + " failed.";
            log.error(message);
            throw new RuntimeException(message, e);
        }
    }

    public boolean hasNext() {
        if (this.iterator == null) this.iterator = setIterator();
        return this.iterator.hasNext();
    }

    public String[] next() {
        if (this.iterator == null) this.iterator = setIterator();
        return iterator.next();
    }

    private Iterator<String[]> setIterator() {
        return excelReader.getIterator();
    }


}
