package org.qamation.web.page;

public interface PageRedyTimer extends IsReady {
    public void resetTimer();
    public void addPageReadyTime(long duration);
    public long getPageReadyTime();
}
