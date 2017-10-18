package org.qamation.jmeter.config.data.provider.simple;




import org.apache.jmeter.threads.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
import org.qamation.jmeter.apache.junit.JMeterTestCase;
import org.qamation.jmeter.config.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Pavel on 2017-06-05.
 */
public class SampleDataTest extends JMeterTestCase {

    private JMeterVariables threadVars;
    private String fileName = "Simple_Excel_Data.xlsx";
    private String dataProviderImplClassName = "org.qamation.data.excelReader.DataProviderAdapter";



    private static final Logger log = LoggerFactory.getLogger(SampleDataTest.class);
    @Before
    public void setUp() {
        JMeterContext jmcx = JMeterContextService.getContext();
        jmcx.setVariables(new JMeterVariables());
        threadVars = jmcx.getVariables();
        threadVars.put("b", "value");
        fileName = this.getClass().getClassLoader().getResource(fileName).getPath();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void createDataContainer() {
        Storage storage = Storage.getStorage();
                //Storage.getStorage(fileName,dataProviderImplClassName);

        DataProvider dp;
        Object[][] data;
        if (storage.hasKey(fileName)) {
            dp = storage.get(fileName);
            data = dp.getData();
        }
        else {
            dp = DataProviderFactory.createDataProviderInstance(dataProviderImplClassName, fileName);
            storage.put(fileName,dp);
            data = storage.get(fileName).getData();
        }
        Assert.assertNotNull(data);
        Assert.assertTrue(data.length>0);
    }

    @Test
    public void createSimpleDataTest() {
        SimpleData data = createSimpleData(dataProviderImplClassName,fileName, "DATA",true,SimpleData.SHARE_MODE_ALL);
        Assert.assertNotNull(data);
        Assert.assertEquals("DATA",data.getDataLabel());
        Assert.assertEquals(true,data.isResetAtEOF());
        Assert.assertEquals(SimpleData.SHARE_MODE_ALL,data.getShareMode());
    }

    // @Test will not work without starting up gui less Jmeter. need to learn how.
    public void setDifferentShareMode() {
        SimpleData forAll = createSimpleData(dataProviderImplClassName,fileName, "forAll",true,SimpleData.SHARE_MODE_ALL);
        SimpleData forThread = createSimpleData(dataProviderImplClassName,fileName, "forThread",true,SimpleData.SHARE_MODE_THREAD);
        forAll.iterationStart(null);
        forThread.iterationStart(null);
        String[] forAllDataLine = (String []) threadVars.getObject("forAll");
        String[] forThreadData = (String []) threadVars.getObject("forThread");
        Assert.assertEquals("A 11",forAllDataLine[0]);
        Assert.assertEquals("A 11",forThreadData[0]);


    }



    private SimpleData createSimpleData(String dataProviderImplClassName, String fileName, String label,boolean resetAtEOF, String shareMode) {
        SimpleData data = new SimpleData();
        data.setDataLabel(label);
        data.setDataProviderImplClassName(dataProviderImplClassName);
        data.setFilename(fileName);
        data.setResetAtEOF(resetAtEOF);
        data.setShareMode(shareMode);
        return data;
    }
}

    /*
    @Test
    public void createDifferentThreads() {
        //SimpleData data = createSimpleData(dataProviderImplClassName,fileName,"DATA", true,SimpleData.SHARE_MODE_ALL);
        //JMeterContextService.addTotalThreads(3);
        JMeterThread thread = new JMeterThread();

        JMeterContext context = JMeterContextService.getContext();
        Assert.assertNotNull(context);
        StandardJMeterEngine jmeterEngine = context.getEngine();
        Assert.assertNotNull(jmeterEngine);
        AbstractThreadGroup threadGroup_1 = context.getThreadGroup();
        AbstractThreadGroup threadGroup_2 = context.getThreadGroup();
        Assert.assertNotEquals(threadGroup_1,threadGroup_2);

        Assert.assertNotNull(threadGroup_1);
        JMeterThread thread_1 = threadGroup_1.addNewThread(100, jmeterEngine);
        Assert.assertNotNull(thread_1);
        JMeterThread thread_2 = threadGroup_1.addNewThread(100,jmeterEngine);
        Assert.assertNotNull(thread_2);
        Assert.assertNotEquals(thread_1,thread_2);

    }
*/