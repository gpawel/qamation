package org.qamation.jmeter.config.data.provider;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;

import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
import org.qamation.jmeter.config.Storage;
import org.slf4j.LoggerFactory;


public class SimpleData extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge,
        TestStateListener   {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SimpleData.class);

    public static final String SHARE_MODE_ALL = "shareMode.all";
    public static final String SHARE_MODE_GROUP = "shareMode.group";
    public static final String SHARE_MODE_THREAD = "shareMode.thread";

    protected String filename;
    protected String dataProviderImplClassName;
    protected String dataLabel;
    protected boolean resetAtEOF;
    protected String shareMode;

    private Storage container;

    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        final JMeterContext context = getThreadContext();
        JMeterVariables threadVars = context.getVariables();
        String key = getKey();
        Storage storage = Storage.getStorage();
        DataProvider dataProvider = getDataProvider(storage,key);

        if (isEndReached(dataProvider)) {
            if (!isResetAtEOF()) {
                //context.getThread().stop();
                throw new JMeterStopThreadException("End of data.");
            }
        }
        Object[] dataLine = dataProvider.getNextLine();
        threadVars.putObject(dataLabel,dataLine);
    }



    private boolean isEndReached(DataProvider dataProvider) {
        int size = dataProvider.getSize();
        int currentIndex = dataProvider.getCurrentLineIndex();
        if (currentIndex == size) return true;
        return false;
    }


    protected DataProvider getDataProvider(Storage storage, String key) {
        DataProvider dataProvider;
        if (storage.hasKey(key)) {
            dataProvider = storage.get(key);
        }
        else {
            dataProvider = DataProviderFactory.createDataProviderInstance(getDataProviderImplClassName(),getFilename());
            storage.put(key,dataProvider);
        }
        return dataProvider;
    }

    protected String getKey() {
        String suffix = getSuffix();
        String key = getFilename() + suffix;
        return key;
    }

    private String getSuffix() {
        final JMeterContext context = getThreadContext();
        String shareMode = getShareMode();
        int modeInt = SimpleDataBeanInfo.getShareModeAsInt(shareMode);
        String suffix;
        switch(modeInt){
            case SimpleDataBeanInfo.SHARE_ALL:
                suffix = "";
                break;
            case SimpleDataBeanInfo.SHARE_GROUP:
                suffix = context.getThreadGroup().getName();
                break;
            case SimpleDataBeanInfo.SHARE_THREAD:
                suffix = context.getThread().getThreadName();
                break;
            default:
                suffix = "";
                break;
        }
        return suffix;
    }


    @Override
    public void testStarted() {
        Storage.resetAtStart();
    }

    @Override
    public void testStarted(String s) {
        testStarted();
    }

    @Override
    public void testEnded() {
        log.info("Test ended");
    }

    @Override
    public void testEnded(String s) {
        testEnded();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDataProviderImplClassName() {
        return dataProviderImplClassName;
    }

    public void setDataProviderImplClassName(String dataProviderImplClassName) {
        this.dataProviderImplClassName = dataProviderImplClassName;
    }

    public boolean isResetAtEOF() {
        return resetAtEOF;
    }

    public void setResetAtEOF(boolean resetAtEOF) {
        this.resetAtEOF = resetAtEOF;
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }
}
