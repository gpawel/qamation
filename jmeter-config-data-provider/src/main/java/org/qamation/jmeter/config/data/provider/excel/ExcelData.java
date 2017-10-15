package org.qamation.jmeter.config.data.provider.excel;

import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.excel.ExcelDataProvider;
import org.qamation.data.provider.excel.ExcelDataProviderFactory;
import org.qamation.jmeter.config.data.provider.AbstractData;
import org.slf4j.LoggerFactory;
public class ExcelData extends AbstractData {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExcelData.class);

    protected int tabNumber=0;



    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        log.info("iteration start by thread: "+ JMeterContextService.getContext().getThread().getThreadName());
        ExcelDataProvider excelDataProvider = getDataProvider();
        isEndReached(excelDataProvider);
        Object[] dataLine = excelDataProvider.getNextLine();
        final JMeterContext context = getThreadContext();
        JMeterVariables threadVars = context.getVariables();
        threadVars.putObject(dataLabel, dataLine);
    }

    @Override
    public <T extends DataProvider> T callFactory() {
        return ExcelDataProviderFactory.createExcelDataProviderInstance(getDataProviderImplClassName(), getFilename(), tabNumber);
    }



    public int getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(int tabNumber) {
        this.tabNumber = tabNumber;
    }


}
