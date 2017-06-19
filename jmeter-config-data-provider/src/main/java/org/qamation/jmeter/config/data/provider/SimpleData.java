package org.qamation.jmeter.config.data.provider;


import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;

import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.LoggerFactory;


public class SimpleData extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge,
        TestStateListener   {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SimpleData.class);

    protected String filename;
    protected String dataProviderImplClassName;
    protected String dataLabel;
    protected boolean resetAtEOF;
    protected String shareMode;


    private DataProviderContainer container;




    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {

        final JMeterContext context = getThreadContext();
        JMeterVariables threadVars = context.getVariables();
        String suffix = getSuffix(context);
        container = DataProviderContainer.getDataProviderContainer(filename,dataProviderImplClassName,suffix);
        log.info("Cursor: "+container.getCursor());
        Object[] dataLine = container.getNextDataLine(resetAtEOF);
        threadVars.putObject(dataLabel,dataLine);

    }

    private String getSuffix(JMeterContext context) {
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
        DataProviderContainer.resetAtStart();
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
