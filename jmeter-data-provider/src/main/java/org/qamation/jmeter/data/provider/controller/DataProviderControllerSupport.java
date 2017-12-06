package org.qamation.jmeter.data.provider.controller;

import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.DataProviderSupport;
import org.qamation.jmeter.data.provider.GuiData;

public class DataProviderControllerSupport extends DataProviderSupport {

    private boolean continueLoop = true;

    @Override
    protected <T extends DataProvider> void dataFinished(T dataProvider) {
        dataProvider.reset();
        continueLoop = false;
    }

    public boolean next(GuiData guiData, JMeterContext context) {
        String dataProviderName = guiData.getFilename()+ context.getThread().getThreadName();
        putDataIntoJMeterContext(dataProviderName, guiData, context);
        return continueLoop;
    }
}