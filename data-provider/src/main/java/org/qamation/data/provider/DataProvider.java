package org.qamation.data.provider;

/**
 * Created by Pavel on 2017-05-11.
 */
public interface DataProvider {
    public Object[][] getData();
    public int getCurrentLineIndex();
    public Object[] getLine(int i);
    public Object[] getNextLine();
    public void reset();
    public int getSize();
    public void close();
}
