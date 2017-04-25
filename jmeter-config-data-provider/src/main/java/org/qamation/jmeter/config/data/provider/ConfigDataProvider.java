package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.util.Iterator;


public class ConfigDataProvider extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge,
        TestStateListener,
        DataProvider {
    private static final Logger log = LoggingManager.getLoggerForClass();
    protected String filename;
    protected String dataProviderImplClassName;
    protected DataProvider dataProvider = null;





    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        if (dataProvider == null) {
            dataProvider = DataProviderFactory.createDataProviderInstance(dataProviderImplClassName,filename);
        }

    }

    @Override
    public void testStarted() {
        log.info("test started");
    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testEnded() {
        log.info("test ended");
    }

    @Override
    public void testEnded(String s) {
        testEnded();
    }

    @Override
    public String[] getFieldNames() {
        return new String[0];
    }

    @Override
    public int getLinesNumber() {
        return 0;
    }

    @Override
    public Iterator<String> getIterator() {
        return null;
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
}
