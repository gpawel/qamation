package org.qamation.jmeter.data.provider;


import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.testelement.TestStateListener;
import org.qamation.data.provider.DataProvider;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Pavel on 2017-05-19.
 */
public class Storage<T extends DataProvider> implements TestStateListener {

    private static final Logger log = LoggerFactory.getLogger(Storage.class);
    private static Storage storage = null;

    public synchronized static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
            StandardJMeterEngine.register(storage);
        }
        return storage;
    }


    private ConcurrentHashMap<String, T> container = null;

    private Storage() {
        container = new ConcurrentHashMap<String, T>();
    }

    public boolean hasKey(String key) {
        return container.containsKey(key);
    }

    public synchronized void put(String key, T t) {
        container.put(key, t);
    }

    public synchronized T get(String key) {
        return  container.get(key);
    }

    public synchronized void remove(String key) {
        if (container.containsKey(key)) {
            container.remove(key);
        }
    }

    public  Set<String> getKeys() {
        return container.keySet();
    }

    @Override
    public void testStarted() {
        if (container.isEmpty()) return;
        Set<String> keys = getKeys();
        for (String key: keys) {
            T dataProvider = container.get(key);
            dataProvider.reload();
        }

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
        testEnded();
    }
}