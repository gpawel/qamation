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

    public boolean hasKey(String key) {
        return container.containsKey(key);
    }

    public void put (String key, T t) {
        container.put(key, t);
    }

    public T get (String key) {
        return (T)container.get(key);
    }

    public void reset() {
        Set<String> keys = container.keySet();
        if (keys.size()>0) {
            for (String key : keys) {
                T provider = (T) container.get(key);
                provider.reset();
            }
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
