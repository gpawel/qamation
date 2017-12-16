package org.qamation.jmeter.data.provider.config;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jorphan.util.JMeterError;
import org.slf4j.LoggerFactory;


public class ExcelDataConfig extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge,
        ConfigGuiData {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelDataConfig.class);
    private DataProviderConfigSupport support;

    protected String filename;
    protected String dataProviderImplClassName;
    protected boolean resetAtEOF;
    protected String shareMode;
    protected String tabNumber;
    protected String fieldNames;
    protected boolean isFirstLineHeader;


    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        JMeterContext context = JMeterContextService.getContext();
        if (!isFirstLineHeader && fieldNames.isEmpty()) {
            throw new JMeterError("Field names not provided.");
        }
        log.info("iteration start by thread: " + context.getThread().getThreadName());
        this.support = new DataProviderConfigSupport();
        support.iterationStart(this, context);
    }


    @Override
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String getDataProviderImplClassName() {
        return dataProviderImplClassName;
    }

    public void setDataProviderImplClassName(String dataProviderImplClassName) {
        this.dataProviderImplClassName = dataProviderImplClassName;
    }

    @Override
    public boolean isResetAtEOF() {
        return resetAtEOF;
    }

    public void setResetAtEOF(boolean resetAtEOF) {
        this.resetAtEOF = resetAtEOF;
    }

    @Override
    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }


    @Override
    public String getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(String tabNumber) {
        this.tabNumber = tabNumber;
    }

    @Override
    public String getFieldNames() {
        return fieldNames;
    }

    @Override
    public boolean isFirstLineHeader() {
        return getIsFirstLineHeader();
    }

    @Override
    public String getClassNameForStorage() {
        return ExcelDataConfig.class.getCanonicalName();
    }

    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }


    public boolean getIsFirstLineHeader() {
        return isFirstLineHeader;
    }

    public void setIsFirstLineHeader(boolean firstLineHeader) {
        this.isFirstLineHeader = firstLineHeader;
    }
}
