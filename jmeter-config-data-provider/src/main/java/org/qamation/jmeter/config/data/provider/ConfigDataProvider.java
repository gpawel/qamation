package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
import org.qamation.jmeter.config.data.provider.excel.ExcelDataProvider;

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
    protected ExcelDataProvider dataProvider = null;
    protected Iterator<String[]> dataIterator=null;





    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        final JMeterContext context = getThreadContext();
        if (dataProvider == null) {
            dataProvider = DataProviderFactory.createDataProviderInstance(dataProviderImplClassName,filename);
            dataIterator = dataProvider.getIterator();
        }
        if (!dataIterator.hasNext()) {
            dataIterator = dataProvider.getIterator();
        }
        String[] dataLine = dataIterator.next();
        JMeterVariables threadVars = context.getVariables();

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

    @Override
    public Object[][] getData() {
        return new Object[0][];
    }
}
