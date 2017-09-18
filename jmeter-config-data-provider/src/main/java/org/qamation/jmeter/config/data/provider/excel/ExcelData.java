package org.qamation.jmeter.config.data.provider.excel;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.testelement.TestElement;
import org.qamation.data.provider.excel.ExcelDataProvider;
import org.qamation.jmeter.config.data.provider.SimpleData;
import org.slf4j.LoggerFactory;

public class ExcelData extends SimpleData {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelData.class);
    protected int tabNumber=0;

    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        //ExcelDataProvider excelDataProvider = getExcelDataProvider();
        //isEndReached(excelDataProvider);
        log.info("\nITERATION STARTED\n");
    }

    private ExcelDataProvider getExcelDataProvider() {
        return null;
    }

    public int getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(int tabNumber) {
        this.tabNumber = tabNumber;
    }
}
