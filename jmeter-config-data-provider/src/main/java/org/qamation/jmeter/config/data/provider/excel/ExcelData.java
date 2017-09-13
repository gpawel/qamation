package org.qamation.jmeter.config.data.provider.excel;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.testelement.TestElement;
import org.qamation.data.provider.excel.ExcelDataProvider;
import org.qamation.jmeter.config.data.provider.SimpleData;

public class ExcelData extends SimpleData {
    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        ExcelDataProvider excelDataProvider = getExcelDataProvider();
        //isEndReached(excelDataProvider);
    }

    private ExcelDataProvider getExcelDataProvider() {
        return null;
    }
}
