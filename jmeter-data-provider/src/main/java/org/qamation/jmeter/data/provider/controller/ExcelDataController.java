package org.qamation.jmeter.data.provider.controller;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContextService;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.DataProviderSupport;
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
    protected boolean resetAtEOF;// = true;
    protected String tabNumber;// = "0";
    protected String fieldNames;// = "";
    protected boolean isFirstLineHeader;// = true;


    public ExcelDataController() {
        //DataProvider dataProvider = support.getDataProvider();
    }

    @Override
    public Sampler next() {
        initProperties();
        support = new DataProviderControllerSupport(this, JMeterContextService.getContext());
        support.putDataIntoJMeterContext(filename);
        return super.next();
    }



    @Override
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



    public String getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(String tabNumber) {
        this.tabNumber = tabNumber;
    }


    public String getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String fieldNames) {
        this.fieldNames = fieldNames;
    }


    public boolean getIsFirstLineHeader() {
        return isFirstLineHeader;
    }

    public void setIsFirstLineHeader(boolean firstLineHeader) {
        isFirstLineHeader = firstLineHeader;
    }

    @Override
    public boolean isFirstLineHeader() {
        return isFirstLineHeader;
    }

    private void initProperties() {
        filename = getPropertyAsString("filename");
        dataProviderImplClassName = getPropertyAsString("dataProviderImplClassName");
        resetAtEOF = getPropertyAsBoolean("resetAtEOF");
        tabNumber = getPropertyAsString("tabNumber");
        fieldNames = getPropertyAsString("fieldNames");
        isFirstLineHeader = getPropertyAsBoolean("isFirstLineHeader");
    }
}
