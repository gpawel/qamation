package org.qamation.jmeter.config.data.provider;

import org.qamation.data.provider.DataProvider;
import org.qamation.excel.utils.ExcelReader;

import java.io.IOException;

/**
 * Created by Pavel on 2017-05-14.
 */
public class ExcelSimpleDataAdapter implements DataProvider {

    protected ExcelReader reader;
    protected Object[][] data;
    protected String fileName;

    public ExcelSimpleDataAdapter (String className, String fileName) {

        new ExcelSimpleDataAdapter(className,fileName,0);
    }

    public ExcelSimpleDataAdapter(String className, String fileName, int sheetIndex) {
        //TODO COPY ORIGINAL FILE INTO A WORKING FILE
        this.fileName = fileName;
        data = reader.getData();
        reader = ExcelReader.createExcelReader(fileName,sheetIndex);
    }

    @Override
    public Object[][] getData() {
        return data;
    }

    @Override
    public void close() {
        data = null;
        try {
            reader.closeWorkBook();
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to close Excel Workbook from "+fileName);
        }
    }
}
