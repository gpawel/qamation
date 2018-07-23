package org.qamation.webdriver.utils;

public class PageLoadTimer {
    private long start;
    private long end;
    private boolean running;

    public PageLoadTimer() {
        start = 0;
        end = 0;
        running = false;
    }

    public void start() {
        running = true;
        start = System.currentTimeMillis();
        end=0;
    }

    public void stop() {
        if (running) {
            end = System.currentTimeMillis();
            running = false;
        }
        else throw new RuntimeException("PageLoadTimer is not started");
    }
    public long getDuration() {
        if (start == 0) throw new RuntimeException("Time has not been started");
        if (end == 0) end = System.currentTimeMillis();
        return end - start;
    }

    public boolean isRunning() {
        return running;
    }
}
