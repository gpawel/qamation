package org.qamation.jmeter.config.data.provider.excel;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testbeans.gui.GenericTestBeanCustomizer;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.sikuli.guide.Run;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.ResourceBundle;

/**
 * Created by Pavel.Gouchtchine on 03/10/2017.
 */
public class ExcelDataProvider extends ConfigTestElement implements TestBean, LoopIterationListener, NoConfigMerge, TestStateListener {
    private static final Logger log = LoggingManager.getLoggerForClass();
    private String filename;
    private String lastname;


    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        log.info("iteration started");
    }

    @Override
    public void testStarted() {
        log.info("test started");
    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testEnded() {
        log.info("test ended");
    }

    @Override
    public void testEnded(String s) {

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
