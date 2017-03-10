package org.qamation.jmeter.config.data.provider.excel;


import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestStateListener;

/**
 * Created by Pavel.Gouchtchine on 03/10/2017.
 */
public class ExcelDataProvider extends ConfigTestElement implements TestBean, LoopIterationListener, TestStateListener {
    @Override
    public void iterationStart(LoopIterationEvent loopIterationEvent) {

    }

    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testEnded() {

    }

    @Override
    public void testEnded(String s) {

    }
}
