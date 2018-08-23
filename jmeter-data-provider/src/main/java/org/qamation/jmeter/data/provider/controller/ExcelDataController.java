package org.qamation.jmeter.data.provider.controller;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.NextIsNullException;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.DataProviderSupport;
import org.qamation.jmeter.data.provider.GuiData;
import org.qamation.jmeter.data.provider.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ExcelDataController extends GenericController
        implements TestBean, GuiData, TestStateListener {

    private static final Logger log = LoggerFactory.getLogger(ExcelDataController.class);

    private static final long serialVersionUID = 253L;


    protected String filename;
    protected String dataProviderImplClassName;
    protected boolean resetAtEOF;// = true;
    protected String tabNumber;// = "0";
    protected String fieldNames;// = "";
    protected boolean isFirstLineHeader;// = true;


    public ExcelDataController() {
        //log.info("\n\nExcelDataController constructor is called\n\n");
    }



    @Override
    public Sampler next() {
        initProperties();
        if (isFirst()) {
            readDataLine();
        }
        return super.next();
    }

    @Override
    protected Sampler nextIsNull() throws NextIsNullException  {
        reInitialize();
        if (isEndOfLoop()) {
            //setDone(true);
            return null;
        }
        return next();

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

    @Override
    public String getClassNameForStorage() {
        return ExcelDataController.class.getCanonicalName();
    }

    private void initProperties() {
        filename = getPropertyAsString("filename");
        dataProviderImplClassName = getPropertyAsString("dataProviderImplClassName");
        resetAtEOF = getPropertyAsBoolean("resetAtEOF");
        tabNumber = getPropertyAsString("tabNumber");
        fieldNames = getPropertyAsString("fieldNames");
        isFirstLineHeader = getPropertyAsBoolean("isFirstLineHeader");
    }

    private <T extends DataProvider > void readDataLine() {
        DataProviderControllerSupport support = new DataProviderControllerSupport();
        boolean hasMore =  support.readNextDataPortion(this,JMeterContextService.getContext());
        if (hasMore) {
        }
        else {
            T dataProvider = getDataProvider();
            dataProvider.reset();
            readDataLine();
        }
    }

    private <T extends DataProvider > boolean isEndOfLoop() {
        T provider = getDataProvider();
        return !provider.hasNext();
    }

    private <T extends DataProvider > T getDataProvider() {
        DataProviderControllerSupport support = new DataProviderControllerSupport();
        String dataProviderName = support.getDataProviderName(this,JMeterContextService.getContext());
        T provider = DataProviderSupport.getDataProvider(dataProviderName,this);
        return provider;
    }

    @Override
    public void testStarted() {
        Storage.reload(getClassNameForStorage());
    }

    @Override
    public void testStarted(String s) {
        Storage.reload(getClassNameForStorage());
    }

    @Override
    public void testEnded() {

    }

    @Override
    public void testEnded(String s) {
    }
}
