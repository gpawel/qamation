package org.qamation.jmeter.data.provider;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.ThreadListener;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.util.JMeterError;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
import org.slf4j.LoggerFactory;

import java.util.Set;

public abstract class DataProviderSupport  {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderSupport.class);

    protected GuiData guiData;
    protected JMeterContext context;

    public synchronized  static <T extends DataProvider> T getDataProvider(String key, GuiData guiData) {
        Storage storage = Storage.getStorage();
        T provider = (T) storage.get(key);
        if (provider == null) {
            provider = DataProviderSupport.callDataProviderFactory(guiData);
            storage.put(key,provider);
        }
        //StandardJMeterEngine.register(this);
        return provider;
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

    public DataProviderSupport(GuiData guiData, JMeterContext context) {
        this.guiData = guiData;
        this.context = context;
    }


    public <T extends DataProvider> void putDataIntoJMeterContext(String providerName) {
        T dataProvider = DataProviderSupport.getDataProvider(providerName, guiData);
        if (dataProvider.hasNext()) {
            String[] dataLine = dataProvider.next();
            String[] headers = dataProvider.getFieldNames();
            for (int i = 0; i < headers.length; i++) {
                context.getVariables().put(headers[i], dataLine[i]);
            }
            return;
        }
        if (guiData.isResetAtEOF()) {
            dataProvider.reset();
            putDataIntoJMeterContext(providerName);
        }
        else throw new JMeterStopThreadException("End of data.");
    }


}
