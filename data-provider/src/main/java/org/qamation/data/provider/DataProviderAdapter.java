package org.qamation.data.provider;


import org.qamation.excel.utils.ExcelReader;

/**
 * Created by Pavel on 2017-05-14.
 */
public class DataProviderAdapter implements DataProvider {
    //private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderAdapter.class);
    protected ExcelReader provider;
    protected Object[][] data;
    protected String fileName;

    public DataProviderAdapter(String className, String fileName) {
        //TODO COPY ORIGINAL FILE INTO A WORKING FILE
        this.fileName = fileName;
        this.provider = ExcelReader.createExcelReader(fileName,0);
        this.data = provider.getData();
    }

    @Override
    public Object[][] getData() {
        return data;
    }

    @Override
    public void finalize() {
        data = null;
        provider = null;
    }
}
