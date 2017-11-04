package org.qamation.jmeter.data.provider.config;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;

import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.qamation.data.provider.DataProvider;
import org.slf4j.LoggerFactory;


public class SimpleDataConfig extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SimpleDataConfig.class);

    protected String filename;
    protected String dataProviderImplClassName;
    protected boolean resetAtEOF;
    protected boolean resetAtTestStart;
    protected String shareMode;
    protected int tabNumber;
    protected String fieldNames;
    protected boolean firstLineIsHeaer;


    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        log.info("iteration start by thread: " + JMeterContextService.getContext().getThread().getThreadName());
        DataProvider dataProvider = getDataProvider();
        if (hasMore(dataProvider)) {
            Object[] dataLine = dataProvider.getNextLine();
            final JMeterContext context = getThreadContext();
            JMeterVariables threadVars = context.getVariables();
            threadVars.putObject(dataLabel, dataLine);
        }
        stopIfRequired();
    }


    public <T extends DataProvider> T callDataProviderFactory() {
        return DataProviderFactory.createDataProviderInstance(getDataProviderImplClassName(), getFilename());
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

    public boolean isResetAtTestStart() {
        return resetAtTestStart;
    }

    public void setResetAtTestStart(boolean resetAtTestStart) {
        this.resetAtTestStart = resetAtTestStart;
    }

    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }


}
