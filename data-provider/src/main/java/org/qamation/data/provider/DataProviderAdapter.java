package org.qamation.data.provider;


import org.qamation.excel.utils.ExcelReader;
import org.qamation.utils.FileUtils;

import java.io.IOException;

/**
 * Created by Pavel on 2017-05-14.
 */
public class DataProviderAdapter implements DataProvider {
    //private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderAdapter.class);
    protected ExcelReader provider;
    protected Object[][] data;
    protected String fileName;
    protected int currentIndex;

    public DataProviderAdapter(String fileName) {
        //TODO COPY ORIGINAL FILE INTO A WORKING FILE
        this.fileName = fileName;
        this.provider = new ExcelReader(fileName,0);
        this.data = provider.getData();
        this.currentIndex = 0;
    }

    @Override
    public Object[][] getData() {
        return data;
    }

    @Override
    public int getCurrentLineIndex() {
        return currentIndex;
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
            if (provider != null ) {
                provider.closeWorkBook();
                provider = null;
                data = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
