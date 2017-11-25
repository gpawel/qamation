package org.qamation.jmeter.data.provider.controller;

import org.apache.jmeter.threads.JMeterContext;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.DataProviderSupport;
import org.qamation.jmeter.data.provider.GuiData;

public class DataProviderControllerSupport extends DataProviderSupport {


    public DataProviderControllerSupport(GuiData guiData, JMeterContext context) {
        super(guiData, context);
        putDataIntoJMeterContext(guiData.getFilename());
    }

}