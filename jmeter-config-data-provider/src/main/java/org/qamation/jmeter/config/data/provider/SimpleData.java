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
import org.apache.jorphan.util.JMeterStopThreadException;
import org.apache.log.Logger;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;


public class SimpleData extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge,
        TestStateListener   {
    private static final Logger log = LoggingManager.getLoggerForClass();

    protected String filename;
    protected String dataProviderImplClassName;
    protected String dataSourceName;
    protected boolean resetAtEOF;


    protected DataProvider dataProvider = null;
    protected Object[][] data = null;

    private int cursor;
    private int dataSize;

    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        if (cursor > dataSize) {
            if (resetAtEOF) {
                resetCursor();
            }
            else {
                throw new JMeterStopThreadException("End of data from "+filename+" is reached.");
            }
        }
        final JMeterContext context = getThreadContext();
        JMeterVariables threadVars = context.getVariables();
        threadVars.putObject(dataSourceName,data[cursor]);
        cursor++;
    }

    @Override
    public void testStarted() {
        log.info("test started");
        if (dataProvider == null) {
            setDataProvider();
            resetCursor();
        }
    }

    @Override
    public void testStarted(String s) {
        testStarted();
    }

    @Override
    public void testEnded() {
        log.info("test ended");
        dataProvider.close();
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

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    protected void setDataProvider() {
        dataProvider = DataProviderFactory.createDataProviderInstance(dataProviderImplClassName,filename);
        data = dataProvider.getData();
        dataSize=data.length;
    }

    protected void resetCursor() {
        cursor = 0;
    }
}
