package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.util.Iterator;


public class ConfigDataProvider extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge,
        TestStateListener,
        DataProvider {
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

    @Override
    public String[] getFieldNames() {
        return new String[0];
    }

    @Override
    public int getLinesNumber() {
        return 0;
    }

    @Override
    public Iterator<String> getIterator() {
        return null;
    }
}
