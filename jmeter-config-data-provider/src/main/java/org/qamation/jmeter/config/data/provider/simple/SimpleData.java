package org.qamation.jmeter.config.data.provider.simple;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;

import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
import org.qamation.jmeter.config.Storage;
import org.qamation.jmeter.config.data.provider.AbstractData;
import org.slf4j.LoggerFactory;


public class SimpleData extends AbstractData

{
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SimpleData.class);

    protected String dataLabel;

    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        log.info("iteration start by thread: " + JMeterContextService.getContext().getThread().getThreadName());
        DataProvider dataProvider = getDataProvider();
        isEndReached(dataProvider);
        Object[] dataLine = dataProvider.getNextLine();
        final JMeterContext context = getThreadContext();
        JMeterVariables threadVars = context.getVariables();
        threadVars.putObject(dataLabel, dataLine);
    }

    @Override
    public <T extends DataProvider> T callDataProviderFactory() {
        return DataProviderFactory.createDataProviderInstance(getDataProviderImplClassName(), getFilename());
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }


}
