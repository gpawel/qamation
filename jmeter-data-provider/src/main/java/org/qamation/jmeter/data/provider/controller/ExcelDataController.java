package org.qamation.jmeter.data.provider.controller;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContextService;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.GuiData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ExcelDataController extends GenericController
        implements TestBean, GuiData {

    private static final Logger log = LoggerFactory.getLogger(ExcelDataController.class);

    private static final long serialVersionUID = 253L;
    private DataProviderControllerSupport support;

    protected String filename;
    protected String dataProviderImplClassName;
    protected boolean resetAtEOF;
    protected boolean resetAtTestStart;
    protected String shareMode;
    protected String tabNumber;
    protected String fieldNames;
    protected boolean isFirstLineHeader;


    public ExcelDataController() {
        support = new DataProviderControllerSupport(this, JMeterContextService.getContext());
        //DataProvider dataProvider = support.getDataProvider();
    }


    @Override
    public <T extends DataProvider> T callDataProviderFactory() {
        return null;
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
    public boolean isResetAtTestStart() {
        return resetAtTestStart;
    }

    public void setResetAtTestStart(boolean resetAtTestStart) {
        this.resetAtTestStart = resetAtTestStart;
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

    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }

    @Override
    public boolean isFirstLineHeader() {
        return isFirstLineHeader;
    }

    public void setFirstLineHeader(boolean firstLineHeader) {
        isFirstLineHeader = firstLineHeader;
    }
}
