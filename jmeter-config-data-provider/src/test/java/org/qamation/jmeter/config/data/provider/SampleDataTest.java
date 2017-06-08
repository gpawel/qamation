package org.qamation.jmeter.config.data.provider;


import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.qamation.jmeter.apache.junit.JMeterTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Pavel on 2017-06-05.
 */
public class SampleDataTest extends JMeterTestCase {
    private JMeterVariables threadVars;
    private String fileName = "D:/QAMATION/documentation/Examples/SimpleDataProvider/Simple_Excel_Data.xlsx";
    private String dataProviderImplClassName = "org.qamation.jmeter.config.data.provider.ExcelToDataProviderAdapter";

    private static final Logger log = LoggerFactory.getLogger(SampleDataTest.class);
    @Before
    public void setUp() {
        JMeterContext jmcx = JMeterContextService.getContext();
        jmcx.setVariables(new JMeterVariables());
        threadVars = jmcx.getVariables();
        threadVars.put("b", "value");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void createDataContainer() {
        DataProviderContainer container = DataProviderContainer.getDataProviderContainer(fileName,dataProviderImplClassName);
        Object [][] data =  container.getData();
    }

    @Test
    public void createSimpleData() {
        SimpleData data = new SimpleData();
        data.setDataLabel("DATA");
        data.setDataProviderImplClassName(dataProviderImplClassName);
        data.setFilename(fileName);
        data.setResetAtEOF(true);
        data.setShareMode("All threads");

    }
}
