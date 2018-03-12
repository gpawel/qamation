package org.qamation.webdriver.utils;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Pavel.Gouchtchine on 12/29/2016.
 */
public class TimeOutsConfig {

    private final static String DRIVER_IMPLICIT_WAIT_TIMEOUT_MILLIS = "DRIVER_IMPLICIT_WAIT_TIMEOUT_MILLIS";

    private final static String DRIVER_SCRIPT_EXEC_TIMEOUT_MILLIS = "DRIVER_SCRIPT_EXEC_TIMEOUT_MILLIS";
    private final static String DRIVER_PAGE_LOAD_TIMEOUT_MILLIS = "DRIVER_PAGE_LOAD_TIMEOUT_MILLIS";

    private final static String LOAD_SCRIPTS_TIMEOUT_MILLIS = "LOAD_SCRIPTS_TIMEOUT_MILLIS";
    private final static String LOAD_SCRIPTS_INTERVAL_MILLIS = "LOAD_SCRIPTS_INTERVAL_MILLIS";

    private final static String PAGE_CHANGES_TIMEOUT_MILLIS = "PAGE_CHANGES_TIMEOUT_MILLIS";
    private final static String PAGE_CHANGES_INTERVAL_MILLIS = "PAGE_CHANGES_INTERVAL_MILLIS";

    private final static String WAIT_FOR_SPINNER_TO_APPEAR_TIME_OUT_MILLIS = "WAIT_FOR_SPINNER_TO_APPEAR_TIME_OUT_MILLIS";
    private final static String WAIT_FOR_SPINNER_TO_APPEAR_INTERVAL_MILLIS = "WAIT_FOR_SPINNER_TO_APPEAR_INTERVAL_MILLIS";
    private final static String WAIT_FOR_SPINNER_TO_DISAPPEAR_TIME_OUT_MILLIS = "WAIT_FOR_SPINNER_TO_DISAPPEAR_TIME_OUT_MILLIS";
    private final static String WAIT_FOR_SPINNER_TO_DISAPPEAR_INTERVAL_MILLIS = "WAIT_FOR_SPINNER_TO_DISAPPEAR_INTERVAL_MILLIS";

    private static long driverImplicitWaitTimeOutMillis = Long.parseLong(System.getProperty(DRIVER_IMPLICIT_WAIT_TIMEOUT_MILLIS,"0"));

    private static long driverScriptExecTimeOutMillis = Long.parseLong(System.getProperty(DRIVER_SCRIPT_EXEC_TIMEOUT_MILLIS,"30000"));
    private static long driverPageLoadTimeOutMillis = Long.parseLong(System.getProperty(DRIVER_PAGE_LOAD_TIMEOUT_MILLIS,"120000"));

    private static long loadScriptTimeOutMillis = Long.parseLong(System.getProperty(LOAD_SCRIPTS_TIMEOUT_MILLIS,"1000"));
    private static long loadScriptIntervalMillis = Long.parseLong(System.getProperty(LOAD_SCRIPTS_INTERVAL_MILLIS,"300"));
    private static long pageChangesTimeOutMillis = Long.parseLong(System.getProperty(PAGE_CHANGES_TIMEOUT_MILLIS,"600"));
    private static long pageChangesIntervalMillis = Long.parseLong(System.getProperty(PAGE_CHANGES_INTERVAL_MILLIS,"400"));

    private static long waitForSpinnerToAppearTimeOutMillis = Long.parseLong(System.getProperty(WAIT_FOR_SPINNER_TO_APPEAR_TIME_OUT_MILLIS,"1000"));
    private static long waitForSpinnerToAppearIntervalMillis = Long.parseLong(System.getProperty(WAIT_FOR_SPINNER_TO_APPEAR_INTERVAL_MILLIS,"300"));

    private static long waitForSpinnerToDisappearTimeOutMillis = Long.parseLong(System.getProperty(WAIT_FOR_SPINNER_TO_DISAPPEAR_TIME_OUT_MILLIS,"300000"));
    private static long waitForSpinnerToDisappearIntervalMillis = Long.parseLong(System.getProperty(WAIT_FOR_SPINNER_TO_DISAPPEAR_INTERVAL_MILLIS,"300"));

    public static void setDriverTimeOuts(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(driverPageLoadTimeOutMillis, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().setScriptTimeout(driverScriptExecTimeOutMillis, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(driverImplicitWaitTimeOutMillis,TimeUnit.MILLISECONDS);
    }

    public static long getDriverImplicitWaitTimeOutMillis() {
        return driverImplicitWaitTimeOutMillis;
    }

    public static void setDriverImplicitWaitTimeOutMillis(long driverImplicitWaitTimeOutMillis) {
        TimeOutsConfig.driverImplicitWaitTimeOutMillis = driverImplicitWaitTimeOutMillis;
    }

    public static long getDriverScriptExecTimeOutMillis() {
        return driverScriptExecTimeOutMillis;
    }

    public static void setDriverScriptExecTimeOutMillis(long driverScriptExecTimeOutMillis) {
        TimeOutsConfig.driverScriptExecTimeOutMillis = driverScriptExecTimeOutMillis;
    }

    public static long getDriverPageLoadTimeOutMillis() {
        return driverPageLoadTimeOutMillis;
    }

    public static void setDriverPageLoadTimeOutMillis(long driverPageLoadTimeOutMillis) {
        TimeOutsConfig.driverPageLoadTimeOutMillis = driverPageLoadTimeOutMillis;
    }

    public static long getLoadScriptTimeOutMillis() {
        return loadScriptTimeOutMillis;
    }

    public static void setLoadScriptTimeOutMillis(long loadScriptTimeOutMillis) {
        TimeOutsConfig.loadScriptTimeOutMillis = loadScriptTimeOutMillis;
    }

    public static long getLoadScriptIntervalMillis() {
        return loadScriptIntervalMillis;
    }

    public static void setLoadScriptIntervalMillis(long loadScriptIntervalMillis) {
        TimeOutsConfig.loadScriptIntervalMillis = loadScriptIntervalMillis;
    }

    public static long getPageChangesTimeOutMillis() {
        return pageChangesTimeOutMillis;
    }

    public static void setPageChangesTimeOutMillis(long pageChangesTimeOutMillis) {
        TimeOutsConfig.pageChangesTimeOutMillis = pageChangesTimeOutMillis;
    }

    public static long getPageChangesIntervalMillis() {
        return pageChangesIntervalMillis;
    }

    public static void setPageChangesIntervalMillis(long pageChangesIntervalMillis) {
        TimeOutsConfig.pageChangesIntervalMillis = pageChangesIntervalMillis;
    }

    public static long getWaitForSpinnerToAppearTimeOutMillis() {
        return waitForSpinnerToAppearTimeOutMillis;
    }

    public static void setWaitForSpinnerToAppearTimeOutMillis(long waitForSpinnerToAppearTimeOutMillis) {
        TimeOutsConfig.waitForSpinnerToAppearTimeOutMillis = waitForSpinnerToAppearTimeOutMillis;
    }

    public static void setWaitForSpinnerToAppearIntervalMillis(long waitForSpinnerToAppearIntervalMillis) {
        TimeOutsConfig.waitForSpinnerToAppearIntervalMillis = waitForSpinnerToAppearIntervalMillis;
    }

    public static long getWaitForSpinnerToAppearIntervalMillis() {
        return waitForSpinnerToAppearIntervalMillis;
    }


    public static long getWaitForSpinnerToDisappearTimeOutMillis() {
        return waitForSpinnerToDisappearTimeOutMillis;
    }

    public static void setWaitForSpinnerToDisappearTimeOutMillis(long waitForSpinnerToDisappearTimeOutMillis) {
        TimeOutsConfig.waitForSpinnerToDisappearTimeOutMillis = waitForSpinnerToDisappearTimeOutMillis;
    }

    public static long getWaitForSpinnerToDisappearIntervalMillis() {
        return waitForSpinnerToDisappearIntervalMillis;
    }

    public static void setWaitForSpinnerToDisappearIntervalMillis(long waitForSpinnerToDisappearIntervalMillis) {
        TimeOutsConfig.waitForSpinnerToDisappearIntervalMillis = waitForSpinnerToDisappearIntervalMillis;
    }
}
