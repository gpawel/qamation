package org.qamation.webdriver.utils;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pavel.Gouchtchine on 12/29/2016.
 */
public class TimeOutsConfig {

    private final static String DRIVER_IMPLICIT_WAIT_TIMEOUT_MILLIS = "DRIVER_IMPLICIT_WAIT_TIMEOUT_MILLIS";

    private final static String DRIVER_NEW_PAGE_SCRIPT_EXEC_TIMEOUT_MILLIS = "DRIVER_NEW_PAGE_SCRIPT_EXEC_TIMEOUT_MILLIS";
    private final static String DRIVER_NEW_PAGE_LOAD_TIMEOUT_MILLIS = "DRIVER_NEW_PAGE_LOAD_TIMEOUT_MILLIS";

    private final static String DRIVER_CURRENT_PAGE_SCRIPT_EXEC_TIMEOUT_MILLIS = "DRIVER_CURRENT_PAGE_SCRIPT_EXEC_TIMEOUT_MILLIS";
    private final static String DRIVER_CURRENT_PAGE_LOAD_TIMEOUT_MILLIS = "DRIVER_CURRENT_PAGE_LOAD_TIMEOUT_MILLIS";

    private final static String NEW_PAGE_LOAD_SCRIPTS_TIMEOUT_MILLIS = "NEW_PAGE_LOAD_SCRIPTS_TIMEOUT_MILLIS";
    private final static String NEW_PAGE_LOAD_SCRIPTS_INTERVAL_MILLIS = "NEW_PAGE_LOAD_SCRIPTS_INTERVAL_MILLIS";
    private final static String CURRENT_PAGE_LOAD_SCRIPTS_TIMEOUT_MILLIS = "CURRENT_PAGE_LOAD_SCRIPTS_TIMEOUT_MILLIS";
    private final static String CURRENT_PAGE_LOAD_SCRIPTS_INTERVAL_MILLIS = "CURRENT_PAGE_LOAD_SCRIPTS_INTERVAL_MILLIS";

    private final static String NEW_PAGE_CHANGES_TIMEOUT_MILLIS = "NEW_PAGE_CHANGES_TIMEOUT_MILLIS";
    private final static String NEW_PAGE_CHANGES_INTERVAL_MILLIS = "NEW_PAGE_CHANGES_INTERVAL_MILLIS";

    private final static String CURRENT_PAGE_CHANGES_TIMEOUT_MILLIS = "CURRENT_PAGE_CHANGES_TIMEOUT_MILLIS";
    private final static String CURRENT_PAGE_CHANGES_INTERVAL_MILLIS = "CURRENT_PAGE_CHANGES_INTERVAL_MILLIS";

    private static long implicitWaitTimeOutMillis = Long.parseLong(System.getProperty(DRIVER_IMPLICIT_WAIT_TIMEOUT_MILLIS,"15000"));

    private static long newPageScriptExecTimeOutMillis = Long.parseLong(System.getProperty(DRIVER_NEW_PAGE_SCRIPT_EXEC_TIMEOUT_MILLIS,"30000"));
    private static long newPageLoadTimeOutMillis = Long.parseLong(System.getProperty(DRIVER_NEW_PAGE_LOAD_TIMEOUT_MILLIS,"120000"));
    private static long curPageScriptExecTimeOutMillis = Long.parseLong(System.getProperty(DRIVER_CURRENT_PAGE_SCRIPT_EXEC_TIMEOUT_MILLIS,"1000"));
    private static long curPageLoadTimeOutMillis = Long.parseLong(System.getProperty(DRIVER_CURRENT_PAGE_LOAD_TIMEOUT_MILLIS,"15000"));


    private static long newPageLoadScriptsTimeOutMillis = Long.parseLong(System.getProperty(NEW_PAGE_LOAD_SCRIPTS_TIMEOUT_MILLIS,"4000"));
    private static long newPageLoadScriptsIntervalMillis = Long.parseLong(System.getProperty(NEW_PAGE_LOAD_SCRIPTS_INTERVAL_MILLIS,"1000"));
    private static long newPageChangesTimeOutMillis = Long.parseLong(System.getProperty(NEW_PAGE_CHANGES_TIMEOUT_MILLIS,"4000"));
    private static long newPageChangesIntervalMillis = Long.parseLong(System.getProperty(NEW_PAGE_CHANGES_INTERVAL_MILLIS,"1000"));

    private static long curPageLoadScriptsTimeOutMillis = Long.parseLong(System.getProperty(CURRENT_PAGE_LOAD_SCRIPTS_TIMEOUT_MILLIS,"1000"));
    private static long curPageLoadScriptsIntervalMillis = Long.parseLong(System.getProperty(CURRENT_PAGE_LOAD_SCRIPTS_INTERVAL_MILLIS,"300"));
    private static long curPageChangesTimeOutMillis = Long.parseLong(System.getProperty(CURRENT_PAGE_CHANGES_TIMEOUT_MILLIS,"500"));
    private static long curPageChangesIntervalMillis = Long.parseLong(System.getProperty(CURRENT_PAGE_CHANGES_INTERVAL_MILLIS,"200"));

    private static long loadScriptTimeOutMillis;
    private static long loadScriptIntervalMillis;
    private static long pageChangesTimeOutMillis;
    private static long pageChangesIntervalMillis;


    public static void setNewPageTimeOuts(WebDriver driver) {
        setDriverTimeOuts(driver,newPageLoadTimeOutMillis,newPageScriptExecTimeOutMillis,implicitWaitTimeOutMillis);
        loadScriptTimeOutMillis = newPageLoadScriptsTimeOutMillis;
        loadScriptIntervalMillis = newPageLoadScriptsIntervalMillis;
        pageChangesTimeOutMillis = newPageChangesTimeOutMillis;
        pageChangesIntervalMillis = newPageChangesIntervalMillis;
    }

    public static void setCurrentPageTimeOuts(WebDriver driver) {
        setDriverTimeOuts(driver,curPageLoadTimeOutMillis,curPageScriptExecTimeOutMillis,implicitWaitTimeOutMillis);
        loadScriptTimeOutMillis = curPageLoadScriptsTimeOutMillis;
        loadScriptIntervalMillis = curPageLoadScriptsIntervalMillis;
        pageChangesTimeOutMillis = curPageChangesTimeOutMillis;
        pageChangesIntervalMillis = curPageChangesIntervalMillis;
    }

    private static void setDriverTimeOuts(WebDriver driver, long pageLoadTimeOut, long scriptTimeOut, long imlicitWaitTimeOut) {
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().setScriptTimeout(scriptTimeOut, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(imlicitWaitTimeOut,TimeUnit.MILLISECONDS);
    }

    public static long getLoadScriptTimeOutMillis() {
        return loadScriptTimeOutMillis;
    }

    public static long getLoadScriptIntervalMillis() {
        return loadScriptIntervalMillis;
    }

    public static long getPageChangesTimeOutMillis() {
        return pageChangesTimeOutMillis;
    }

    public static long getPageChangesIntervalMillis() {
        return pageChangesIntervalMillis;
    }

    public static long getImplicitWaitTimeOutMillis() {
        return implicitWaitTimeOutMillis;
    }
}
