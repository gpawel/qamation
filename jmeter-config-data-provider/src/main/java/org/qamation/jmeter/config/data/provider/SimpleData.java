package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.config.CSVDataSetBeanInfo;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testbeans.gui.GenericTestBeanCustomizer;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.apache.log.Logger;
import org.qamation.data.provider.DataProvider;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.SimpleBeanInfo;
import java.util.ResourceBundle;


public class SimpleData extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge,
        TestStateListener   {
    private static final Logger log = LoggingManager.getLoggerForClass();

    protected String filename;
    protected String dataProviderImplClassName;
    protected String dataLabel;
    protected boolean resetAtEOF;


    private DataProviderContainer container;




    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {

        final JMeterContext context = getThreadContext();
        log.info("Thread Name: "+context.getThread().getThreadName());
        log.info("Thread Group: "+context.getThreadGroup().getName());
        JMeterVariables threadVars = context.getVariables();
        container = DataProviderContainer.getDataProviderContainer(filename,dataProviderImplClassName);
        log.info("Cursor: "+container.getCursor());
        Object[] dataLine = container.getNextDataLine(resetAtEOF);
        threadVars.putObject(dataLabel,dataLine);

    }



    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String s) {
        testStarted();
    }

    @Override
    public void testEnded() {
        log.info("Test ended");
        container = DataProviderContainer.getDataProviderContainer(filename,dataProviderImplClassName);
        container.getDataProvider().close();
    }

    @Override
    public void testEnded(String s) {
        testEnded();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDataProviderImplClassName() {
        return dataProviderImplClassName;
    }

    public void setDataProviderImplClassName(String dataProviderImplClassName) {
        this.dataProviderImplClassName = dataProviderImplClassName;
    }

    public boolean isResetAtEOF() {
        return resetAtEOF;
    }

    public void setResetAtEOF(boolean resetAtEOF) {
        this.resetAtEOF = resetAtEOF;
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }


}
