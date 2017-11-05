package org.qamation.jmeter.data.provider;

import org.apache.jmeter.threads.JMeterContext;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.utils.Storage;
import org.slf4j.LoggerFactory;

public abstract class DataProviderSupport {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderSupport.class);

    protected GuiData guiData;
    protected JMeterContext context;

    public DataProviderSupport(GuiData guiData, JMeterContext context) {
        this.guiData = guiData;
        this.context = context;
    }

    public void resetIfRequired(DataProvider provider) {

            provider.reset();

    }

    public <T extends DataProvider> T getDataProvider(String key) {
        Storage<T> storage = Storage.getStorage();
        if (storage.hasKey(key)) {
            return storage.get(key);
        } else {
            T dataProvider = callDataProviderFactory();
            storage.put(key, dataProvider);
            return dataProvider;
        }
    }

    public void putDataIntoJMeterContext(String key) {
        DataProvider dataProvider = getDataProvider(key);
        if (dataProvider.hasNext()) {
            String[] dataLine = dataProvider.next();
            String[] headers = dataProvider.getFieldNames();
            for (int i = 0; i < headers.length; i++) {
                context.getVariables().put(headers[i], dataLine[i]);
            }
            return;
        }
        if (guiData.isResetAtEOF()) dataProvider.reset();
        else throw new JMeterStopThreadException("End of data.");
    }


    public abstract <T extends DataProvider> T callDataProviderFactory();
}
