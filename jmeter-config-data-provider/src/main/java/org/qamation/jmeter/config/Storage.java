package org.qamation.jmeter.config;

import org.qamation.data.provider.DataProvider;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Pavel on 2017-05-19.
 */
public class Storage<T extends DataProvider>{

    private static final Logger log = LoggerFactory.getLogger(Storage.class);
    private static Storage storage = null;

    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

    synchronized public static void resetAtStart() {
        Storage storage = getStorage();
        storage.reset();
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

    public synchronized void closeAll() {
        Set<String> keys = container.keySet();
        for (String key : keys) {
            if (container.containsKey(key)) {
                T provider = (T) container.get(key);
                provider.close();
            }
        }
    }

    public synchronized void removeAll() {
        Set<String> keys = container.keySet();
        keys = container.keySet();
        for (String key: keys) {
            container.remove(key);
        }
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
