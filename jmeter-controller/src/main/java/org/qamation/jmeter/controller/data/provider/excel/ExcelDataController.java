package org.qamation.jmeter.controller.data.provider.excel;

import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.testbeans.TestBean;
import org.qamation.data.provider.excel.ExcelDataProvider;
import org.qamation.data.provider.excel.ExcelDataProviderFactory;
import org.qamation.jmeter.utils.storage.data.provider.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ExcelDataController extends GenericController
        implements TestBean {

    private static final Logger log = LoggerFactory.getLogger(ExcelDataController.class);

    private static final long serialVersionUID = 253L;

    private String fileName;
    private String dataProviderImplClassName;
    private int tabNumber;

    private ExcelDataProvider provider;

    public ExcelDataController() {
        //provider = getExcelDataProvider();
    }

    private ExcelDataProvider getExcelDataProvider() {
        Storage <ExcelDataProvider> storage = Storage.getStorage();
        if (storage.hasKey(fileName)) {
            return storage.get(fileName);
        }
        else {
            ExcelDataProvider dataProvider =
                    ExcelDataProviderFactory.createExcelDataProviderInstance(
                            dataProviderImplClassName, fileName,tabNumber
                    );
            storage.put(fileName,dataProvider);
            return dataProvider;
        }
    }

    public String getExcelFileName() {
        return fileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.fileName = excelFileName;
    }

    public String getExcelDataProviderImpClassName() {
        return dataProviderImplClassName;
    }

    public void setExcelDataProviderImpClassName(String excelDataProviderImpClassName) {
        this.dataProviderImplClassName = excelDataProviderImpClassName;
    }

    public int getExcelTab() {
        return tabNumber;
    }

    public void setExcelTab(int excelTab) {
        this.tabNumber = excelTab;
    }
}
