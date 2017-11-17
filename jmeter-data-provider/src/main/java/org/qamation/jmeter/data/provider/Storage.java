package org.qamation.jmeter.data.provider;


import org.apache.jmeter.engine.StandardJMeterEngine;
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

    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
            //StandardJMeterEngine.register(this);
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

    public void put(String key, T t) {
        container.put(key, t);
    }

    public  T get(String key) {
        return  container.get(key);
    }


    public  void remove(String key) {
        if (container.containsKey(key)) {
            container.remove(key);
        }
    }

    public  Set<String> getKeys() {
        return container.keySet();
    }

}