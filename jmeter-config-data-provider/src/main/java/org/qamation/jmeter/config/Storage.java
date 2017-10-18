package org.qamation.jmeter.config;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.qamation.data.provider.DataProvider;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Pavel on 2017-05-19.
 */
public class Storage<T extends DataProvider>
implements TestStateListener
{

    private static final Logger log = LoggerFactory.getLogger(Storage.class);
    private static Storage storage = null;

    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
        }
        StandardJMeterEngine.register(storage);
        return storage;
    }


    private  HashMap<String,Object> container = null;

    private Storage() {
        container = new HashMap<String, Object>();
    }

    public synchronized boolean hasKey(String key) {
        return container.containsKey(key);
    }

    public synchronized void put (String key, T t) {
        container.put(key, t);
    }

    public synchronized T get (String key) {
        return (T)container.get(key);
    }

    public synchronized void reset() {
        Set<String> keys = container.keySet();
        if (keys.size()>0) {
            for (String key : keys) {
                T provider = (T) container.get(key);
                provider.reset();
            }
        }
    }

    public synchronized void remove(String key) {
        if (container.containsKey(key)) {
            container.remove(key);
        }
    }

    public synchronized Set<String> getKeys() {
        return container.keySet();
    }

    @Override
    public void testStarted() {
        log.info("test starting");
        reset();
    }

    @Override
    public void testStarted(String s) {
        testStarted();
    }

    @Override
    public void testEnded() {


    }

    @Override
    public void testEnded(String s) {

    }




















    /*public Object[] getNextDataLine(boolean shouldReset) {
        if (cursor > dataProvider.getData().length-1) {
            if (shouldReset) {
                resetData();
            }
            else {
                throw new JMeterStopThreadException("End of "+fileName+" is reached");
            }
        }
        return dataProvider.getData()[cursor++];
    }*/


}
