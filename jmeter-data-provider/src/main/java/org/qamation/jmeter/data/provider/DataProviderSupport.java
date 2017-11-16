package org.qamation.jmeter.data.provider;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jorphan.util.JMeterError;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class DataProviderSupport implements TestStateListener {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderSupport.class);

    protected GuiData guiData;
    protected JMeterContext context;
    protected String key = null;

    public DataProviderSupport(GuiData guiData, JMeterContext context) {
        this.guiData = guiData;
        this.context = context;
    }


    public <T extends DataProvider> T getDataProvider(String key) {
        T provider = (T) context.getVariables().getObject(key);
        this.key = key;
        if (provider == null) {
            provider = callDataProviderFactory();
            context.getVariables().putObject(key,provider);
        }
        StandardJMeterEngine.register(this);
        return provider;
    }

    public <T extends DataProvider> void putDataIntoJMeterContext(String key) {
        T dataProvider = getDataProvider(key);
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

    // @Override//
    public <T extends DataProvider> T callDataProviderFactory() {
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

    public <T extends DataProvider> void reset() {
        if (this.key == null) return;
        if (guiData.isResetAtTestStart()) {
            if (context == null) throw new JMeterStopThreadException("Context is null at the reset()");
            T provider = (T) context.getVariables().getObject(this.key);
            if (provider == null) throw new JMeterStopThreadException("Data provider is null for key="+key);
            provider.reset();
        }
    }

    @Override
    public void testStarted() {
        log.info("test starting");
        reset();
    }

    @Override
    public void testStarted(String s) {
        testStarted();
    }

    @Override
    public void testEnded() {


    }

    @Override
    public void testEnded(String s) {

    }

    //public abstract <T extends DataProvider> T callDataProviderFactory();
}
