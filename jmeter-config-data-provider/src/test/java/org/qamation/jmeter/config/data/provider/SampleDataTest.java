package org.qamation.jmeter.config.data.provider;


import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.qamation.jmeter.apache.junit.JMeterTestCase;


/**
 * Created by Pavel on 2017-06-05.
 */
public class SampleDataTest extends JMeterTestCase {
    private JMeterVariables threadVars;

    @Before
    public void setUp() {
        JMeterContext jmcx = JMeterContextService.getContext();
        jmcx.setVariables(new JMeterVariables());
        threadVars = jmcx.getVariables();
        threadVars.put("b", "value");
    }

    @After
    public void tearDown() {
        SimpleData data = new SimpleData();
        data.setDataLabel("DATA");
        data.setDataProviderImplClassName("org.qamation.jmeter.config.data.provider.ExcelToDataProviderAdapter");
        data.setFilename("D:/QAMATION/documentation/Examples/SimpleDataProvider/Simple_Excel_Data.xlsx");
        data.setResetAtEOF(true);
        data.setShareMode("All threads");

    }

    @Test
    public void iterateThroughDataRows() {
        SimpleData data = new SimpleData();
        data.setDataLabel("DATA");
        data.setDataProviderImplClassName("org.qamation.jmeter.config.data.provider.ExcelToDataProviderAdapter");
        data.setFilename("D:/QAMATION/documentation/Examples/SimpleDataProvider/Simple_Excel_Data.xlsx");
        data.setResetAtEOF(true);
        data.setShareMode("All threads");

    }
}
