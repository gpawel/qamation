package org.qamation.jmeter.data.provider;

import org.apache.jmeter.threads.JMeterContext;
import org.apache.jorphan.util.JMeterError;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
import org.slf4j.LoggerFactory;

public abstract class DataProviderSupport  {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderSupport.class);

    public synchronized  static <T extends DataProvider> T getDataProvider(String key, GuiData guiData) {
        Storage storage = Storage.getStorage(guiData.getClassNameForStorage());
        T provider = (T) storage.get(key);
        if (provider == null) {
            provider = DataProviderSupport.callDataProviderFactory(guiData);
            storage.put(key,provider);
        }
        //StandardJMeterEngine.register(this);
        return provider;
    }

    public <T extends DataProvider> void putDataIntoJMeterContext(T dataProvider, JMeterContext context) {
        if (dataProvider.hasNext()) {
            String[] dataLine = dataProvider.next();
            String[] headers = dataProvider.getFieldNames();
            for (int i = 0; i < headers.length; i++) {
                context.getVariables().put(headers[i], dataLine[i]);
            }
        }
        else {
            dataFinished (dataProvider);
        }
    }



    private static <T extends DataProvider> T callDataProviderFactory(GuiData guiData) {
        int tabNumber = Integer.parseInt(guiData.getTabNumber());

        if (guiData.isFirstLineHeader()) {
            return DataProviderFactory.createDataProviderInstance(
                    guiData.getDataProviderImplClassName(), guiData.getFilename(), tabNumber);
        } else {
            if (guiData.getFieldNames()==null)
                throw new JMeterError("\n\nWhen Data file does not have header, Field names must be provided.");
            String[] headers = guiData.getFieldNames().split(",");
            return DataProviderFactory.createDataProviderInstance(
                    guiData.getDataProviderImplClassName(), guiData.getFilename(), tabNumber, headers);

        }
    }



    protected abstract <T extends DataProvider> void dataFinished(T dataProvider);


}
