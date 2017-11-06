package org.qamation.jmeter.data.provider.config;




import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.threads.*;
import org.apache.jorphan.collections.HashTree;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.apache.junit.JMeterTestCase;

import org.qamation.jmeter.data.provider.GuiData;
import org.qamation.jmeter.data.provider.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


/**
 * Created by Pavel on 2017-06-05.
 */
public class ExcelDataConfigTest extends JMeterTestCase {

    private JMeterVariables threadVars;
    private String dataProviderImplClassName = "org.qamation.data.provider.DataProviderExcelAdapter";

    ClassLoader classLoader;
    URL bookXURL;
    URL bookSURL;

    ExcelDataConfig config;
    JMeterContext context;


    private static final Logger log = LoggerFactory.getLogger(ExcelDataConfigTest.class);
    @Before
    public void setUp() {
        context = JMeterContextService.getContext();
        //context.setVariables(new JMeterVariables());
        //context.setThread(new JMeterThread(new HashTree(false),));
        threadVars = context.getVariables();
        threadVars.put("b", "value");
        classLoader = Thread.currentThread().getContextClassLoader();
        bookXURL = classLoader.getResource("Book2.xlsx");
        bookSURL = classLoader.getResource("Book1.xls");
        config = createExcelDataConfig(
                bookXURL.getFile(),
                dataProviderImplClassName,
                true,
                true,
                "shareMode.all",
                1,
                "",
                true
                );
    }



    @After
    public void tearDown() {
    }

    @Test
    public void readFromDataProvider() {
        config.iterationStart(new LoopIterationEvent(config,0));
        Assert.assertEquals("asdfas",context.getVariables().get("Value 1"));

    }

    private ExcelDataConfig createExcelDataConfig(
            String fileName,
            String className,
            boolean resetAtEOF,
            boolean resetAtTestStart,
            String shareMode,
            int tabNumber,
            String fieldNames,
            boolean isFirstLineHeader
            ) {
        ExcelDataConfig config = new ExcelDataConfig();
        config.setFilename(fileName);
        config.setDataProviderImplClassName(className);
        config.setResetAtEOF(resetAtEOF);
        config.setResetAtTestStart(resetAtTestStart);
        config.setShareMode(shareMode);
        config.setTabNumber(tabNumber);
        config.setFieldNames(fieldNames);
        config.setIsFirstLineHeader(isFirstLineHeader);
        return config;
    }






}

    /*
    @Test
    public void createDifferentThreads() {
        //ExcelDataConfig data = createSimpleData(dataProviderImplClassName,fileName,"DATA", true,ExcelDataConfig.SHARE_MODE_ALL);
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