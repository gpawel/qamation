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
public class Storage<T extends DataProvider> {

    private static final Logger log = LoggerFactory.getLogger(Storage.class);
    private static Storage storage = null;
    private static boolean shouldReload = true;

    public synchronized static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

// StandardJMeterEngine.register(storage);

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
        return container.get(key);
    }

    public synchronized void remove(String key) {
        if (container.containsKey(key)) {
            container.remove(key);
        }
    }

    public Set<String> getKeys() {
        return container.keySet();
    }

    public synchronized static void reload() {
        if (shouldReload) {
            log.info("\nRELOADING\n");
            shouldReload = false;
        }
    }

    public static void resetReload() {
        if (!shouldReload) {
            shouldReload = true;
        }
    }

}