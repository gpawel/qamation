package org.qamation.jmeter.config.data.provider.excel;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.log.Logger;
import org.apache.jorphan.logging.LoggingManager;
/**
 * Created by Pavel.Gouchtchine on 03/10/2017.
 */
public class ExcelDataProvider extends ConfigTestElement implements TestBean, LoopIterationListener, TestStateListener {
    private static final Logger log = LoggingManager.getLoggerForClass();
    private String excelFileName;

    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {
        log.info("Iteration started with event:\n"+loopIterationEvent.toString());

    }

    @Override
    public void testStarted() {
        log.info("Test Started");
    }

    @Override
    public void testStarted(String s) {
        log.info("Parameter: "+s);
        testStarted();
    }

    @Override
    public void testEnded() {
        log.info("Test Ended.");
    }

    @Override
    public void testEnded(String s) {
        log.info("Parameter: "+s);
        testEnded();
    }

    public String getExcelFileName() {
        return excelFileName;
    }

    public void setExcelFileName(String excelFileName) {
        this.excelFileName = excelFileName;
    }
}
