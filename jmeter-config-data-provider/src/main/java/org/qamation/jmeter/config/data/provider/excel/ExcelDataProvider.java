package org.qamation.jmeter.config.data.provider.excel;



import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.qamation.jmeter.config.data.provider.DataProvider;

import java.io.IOException;
import java.util.Iterator;
import org.qamation.excel.utils.ExcelReader;


public class ExcelDataProvider implements DataProvider, TestStateListener {
    private static final Logger log = LoggingManager.getLoggerForClass();
    private ExcelReader excelReader;


    public ExcelDataProvider (String fileName) {
        excelReader = ExcelReader.createExcelReader(fileName,0);
    }

    public ExcelDataProvider(String fileName, int sheetIndex) {
        excelReader = ExcelReader.createExcelReader(fileName,sheetIndex);
    }

    @Override
    public String[] getFieldNames() {
        return excelReader.getFieldNames();
    }

    @Override
    public int getLinesNumber() {
        return excelReader.getNmberOfLinesInActiveWorkSheet();
    }

    @Override
    public Iterator<String> getIterator() {
        return excelReader.iterator();
    }

    public String getFilename() {
        return excelReader.getFileName();
    }

    public int getSheetIndex() {
        return excelReader.getActiveSheetIndex();
    }


    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String host) {

    }

    @Override
    public void testEnded() {
        try {
            excelReader.closeWorkBook();
        } catch (IOException e) {
            String m = "Unable to close Excel workbook";
            log.error(m);
            throw new RuntimeException(m,e);
        }
    }

    @Override
    public void testEnded(String host) {
        testEnded();
    }
}
