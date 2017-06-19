package org.qamation.jmeter.config.data.provider;

import org.qamation.data.provider.DataProvider;
import org.qamation.excel.utils.ExcelReader;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Pavel on 2017-05-14.
 */
public class ExcelToDataProviderAdapter implements DataProvider {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelToDataProviderAdapter.class);
    protected ExcelReader reader;
    protected Object[][] data;
    protected String fileName;


    public ExcelToDataProviderAdapter(String className, String fileName) {
        this(className,fileName,0);
    }

    public ExcelToDataProviderAdapter(String className, String fileName, int sheetIndex) {
        //TODO COPY ORIGINAL FILE INTO A WORKING FILE
        this.fileName = fileName;
        this.reader = ExcelReader.createExcelReader(fileName,sheetIndex);
        this.data = reader.getData();
    }

    @Override
    public Object[][] getData() {
        return data;
    }

    @Override
    public void finalize() {
        data = null;
        reader = null;
    }
}
