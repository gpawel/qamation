package org.qamation.jmeter.config.data.provider.excel;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Created by Pavel.Gouchtchine on 03/10/2017.
 */
public class ExcelDataProvider extends ConfigTestElement implements TestBean, LoopIterationListener, TestStateListener {
    private static final Logger log = LoggingManager.getLoggerForClass();
    private String filename;
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
}
