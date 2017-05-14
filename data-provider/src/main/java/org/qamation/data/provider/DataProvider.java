package org.qamation.data.provider;

/**
 * Created by Pavel on 2017-05-11.
 */
public interface DataProvider {
    Object[][] getData();
    public void close();
}
