package org.qamation.jmeter.data.provider;

import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.utils.Storage;
import org.slf4j.LoggerFactory;

public abstract class DataProviderSupport
         {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderSupport.class);



    public DataProviderSupport (
            String fileName,
            String dataProviderImplClass,
            int tabNumber,
            String[] header

    ) {}






    protected < P extends DataProvider > boolean hasMore(P provider) {
        int size = provider.getSize();
        int currentIndex = provider.getCurrentLineIndex();
        if (currentIndex < size) {
            return  true;
        }
        return false;
    }

    protected void stopIfRequired() {
        if (!isResetAtEOF()) {
            throw new JMeterStopThreadException("End of data.");
        }
    }

    protected <T extends DataProvider > T getDataProvider() {
        Storage<T> storage = Storage.getStorage();
        String key = getKey();
        T dataProvider;
        if (storage.hasKey(key)) {
            dataProvider = storage.get(key);
        } else {
            dataProvider = callDataProviderFactory();
            storage.put(key, dataProvider);
        }
        return dataProvider;
    }




    //public abstract <T extends DataProvider> T callDataProviderFactory();
}
