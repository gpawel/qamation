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

    private final static String INCLUDE_WAIT_FOR_PAGE_CHANGES = "INCLUDE_WAIT_FOR_PAGE_CHANGES";
    private final static String PAGE_CHANGES_TIMEOUT_MILLIS = "PAGE_CHANGES_TIMEOUT_MILLIS";
    private final static String PAGE_CHANGES_WATCH_FOR_MAX_MILLIS = "PAGE_CHANGES_WATCH_FOR_MAX_MILLIS";
    private final static String PAGE_CHANGES_INTERVAL_MILLIS = "PAGE_CHANGES_INTERVAL_MILLIS";



    private final static String INCLUDE_WAIT_FOR_SPINNER_TO_APPEAR = "INCLUDE_WAIT_FOR_SPINNER_TO_APPEAR";
    private final static String WAIT_FOR_SPINNER_TO_APPEAR_TIME_OUT_MILLIS = "WAIT_FOR_SPINNER_TO_APPEAR_TIME_OUT_MILLIS";
    private final static String WAIT_FOR_SPINNER_TO_APPEAR_INTERVAL_MILLIS = "WAIT_FOR_SPINNER_TO_APPEAR_INTERVAL_MILLIS";



    private final static String INCLUDE_WAIT_FOR_SPINNER_TO_DISAPPEAR = "INCLUDE_WAIT_FOR_SPINNER_TO_DISAPPEAR";
    private final static String WAIT_FOR_SPINNER_TO_DISAPPEAR_TIME_OUT_MILLIS = "WAIT_FOR_SPINNER_TO_DISAPPEAR_TIME_OUT_MILLIS";
    private final static String WAIT_FOR_SPINNER_TO_DISAPPEAR_INTERVAL_MILLIS = "WAIT_FOR_SPINNER_TO_DISAPPEAR_INTERVAL_MILLIS";


    private final static String INCLUDE_IS_PAGE_READY_CONDITION="INCLUDE_IS_PAGE_READY_CONDITION";
    private final static String IS_PAGE_READY_CONDITION_TIME_OUT_MILLIS = "IS_PAGE_READY_CONDITION_TIME_OUT_MILLIS";
    private final static String IS_PAGE_READY_CONDITION_INTERVAL_MILLIS = "IS_PAGE_READY_CONDITION_INTERVAL_MILLIS";

    private final static String INCLUDE_EXPLICIT_PAUSE="INCLUDE_EXPLICIT_PAUSE";
    private final static String EXPLICIT_PAUSE_MILLIS="EXPLICIT_PAUSE_MILLIS";

    private static long driverImplicitWaitTimeOutMillis = getLongProperty(DRIVER_IMPLICIT_WAIT_TIMEOUT_MILLIS,"0");

    private static long driverScriptExecTimeOutMillis = getLongProperty(DRIVER_SCRIPT_EXEC_TIMEOUT_MILLIS,"30000");
    private static long driverPageLoadTimeOutMillis = getLongProperty(DRIVER_PAGE_LOAD_TIMEOUT_MILLIS,"120000");

    private static long loadScriptTimeOutMillis = getLongProperty(LOAD_SCRIPTS_TIMEOUT_MILLIS,"1000");
    private static long loadScriptIntervalMillis = getLongProperty(LOAD_SCRIPTS_INTERVAL_MILLIS,"300");





    private static boolean includePageChanges = getBooleanProperty(INCLUDE_WAIT_FOR_PAGE_CHANGES,"true");
    private static long pageChangesTimeOutMillis = getLongProperty(PAGE_CHANGES_TIMEOUT_MILLIS,"600");
    private static long pageChangesWatchForMaxMillis = getLongProperty(PAGE_CHANGES_WATCH_FOR_MAX_MILLIS,"5000");
    private static long pageChangesIntervalMillis = getLongProperty(PAGE_CHANGES_INTERVAL_MILLIS,"400");


    private static boolean includeWaitForSpinnerToAppear = getBooleanProperty(INCLUDE_WAIT_FOR_SPINNER_TO_APPEAR,"false");
    private static long waitForSpinnerToAppearTimeOutMillis = getLongProperty(WAIT_FOR_SPINNER_TO_APPEAR_TIME_OUT_MILLIS,"1000");
    private static long waitForSpinnerToAppearIntervalMillis = getLongProperty(WAIT_FOR_SPINNER_TO_APPEAR_INTERVAL_MILLIS,"300");


    private static boolean includeWaitForSpinnerToDisappear = getBooleanProperty(INCLUDE_WAIT_FOR_SPINNER_TO_DISAPPEAR,"false");
    private static long waitForSpinnerToDisappearTimeOutMillis = getLongProperty(WAIT_FOR_SPINNER_TO_DISAPPEAR_TIME_OUT_MILLIS,"300000");
    private static long waitForSpinnerToDisappearIntervalMillis = getLongProperty(WAIT_FOR_SPINNER_TO_DISAPPEAR_INTERVAL_MILLIS,"300");


    private static boolean includeIsPageReadyCondition = getBooleanProperty(INCLUDE_IS_PAGE_READY_CONDITION,"false");
    private static long isPageReadyConditionTimeOutMillis = getLongProperty(IS_PAGE_READY_CONDITION_TIME_OUT_MILLIS,"5000");
    private static long isPageReadyConditionIntervalMillis = getLongProperty(IS_PAGE_READY_CONDITION_INTERVAL_MILLIS,"200");

    private static boolean includeExplicitPause = getBooleanProperty(INCLUDE_EXPLICIT_PAUSE,"false");
    private static long explicitPause = getLongProperty(EXPLICIT_PAUSE_MILLIS,"0");

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





    public static boolean getIncludePageChanges(){return includePageChanges;}
    public static void setIncludeWaitForPageChanges(boolean includePageChanges) {
        TimeOutsConfig.includePageChanges = includePageChanges;
    }
    public static long getPageChangesTimeOutMillis() {return pageChangesTimeOutMillis;}
    public static void setPageChangesTimeOutMillis(long pageChangesTimeOutMillis) {
        TimeOutsConfig.pageChangesTimeOutMillis = pageChangesTimeOutMillis;
    }
    public static long getPageChangesIntervalMillis() {
        return pageChangesIntervalMillis;
    }
    public static void setPageChangesIntervalMillis(long pageChangesIntervalMillis) {
        TimeOutsConfig.pageChangesIntervalMillis = pageChangesIntervalMillis;
    }
    public static long getPageChangesWatchForMaxMillis() {return pageChangesWatchForMaxMillis;}
    public static void setPageChangesWatchForMaxMillis (long pageChangesWatchForMaxMillis) {
        TimeOutsConfig.pageChangesWatchForMaxMillis = pageChangesWatchForMaxMillis;
    }








    public static boolean getIncludeWaitForSpinnerToAppear() {
        return includeWaitForSpinnerToAppear;
    }
    public static void setIncludeWaitForSpinnerToAppear(boolean includeWaitForSpinnerToAppear) {
        TimeOutsConfig.includeWaitForSpinnerToAppear = includeWaitForSpinnerToAppear;
    }

    public static long getWaitForSpinnerToAppearTimeOutMillis() {return waitForSpinnerToAppearTimeOutMillis;}
    public static void setWaitForSpinnerToAppearTimeOutMillis(long waitForSpinnerToAppearTimeOutMillis) {
        TimeOutsConfig.waitForSpinnerToAppearTimeOutMillis = waitForSpinnerToAppearTimeOutMillis;
    }
    public static void setWaitForSpinnerToAppearIntervalMillis(long waitForSpinnerToAppearIntervalMillis) {
        TimeOutsConfig.waitForSpinnerToAppearIntervalMillis = waitForSpinnerToAppearIntervalMillis;
    }
    public static long getWaitForSpinnerToAppearIntervalMillis() {return waitForSpinnerToAppearIntervalMillis;}









    public static boolean getIncludeWaitForSpinnerToDisappear() {
        return includeWaitForSpinnerToDisappear;
    }

    public static void setIncludeWaitForSpinnerToDisappear(boolean includeWaitForSpinnerToDisappear) {
        TimeOutsConfig.includeWaitForSpinnerToDisappear = includeWaitForSpinnerToDisappear;
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

    // ============================================================================================================
    public static boolean getIncludeIsPageReadyCondition() {
        return includeIsPageReadyCondition;
    }

    public static void setIncludeIsPageReadyCondition(boolean includeIsPageReadyCondition) {
        TimeOutsConfig.includeIsPageReadyCondition = includeIsPageReadyCondition;
    }

    public static long getIsPageReadyConditionTimeOutMillis() {
        return isPageReadyConditionTimeOutMillis;
    }

    public static void setIsPageReadyConditionTimeOutMillis(long isPageReadyConditionTimeOutMillis) {
        TimeOutsConfig.isPageReadyConditionTimeOutMillis = isPageReadyConditionTimeOutMillis;
    }

    public static long getIsPageReadyConditionIntervalMillis() {
        return isPageReadyConditionIntervalMillis;
    }

    public static void setIsPageReadyConditionIntervalMillis(long isPageReadyConditionIntervalMillis) {
        TimeOutsConfig.isPageReadyConditionIntervalMillis = isPageReadyConditionIntervalMillis;
    }
    // ============================================================================================================
    private static long getLongProperty(String propName, String defValue) {
        return Long.parseLong(System.getProperty(propName,defValue));
    }

    private static boolean getBooleanProperty(String propName, String defValue) {
        return Boolean.parseBoolean(System.getProperty(propName,defValue) );
    }
    // ============================================================================================================

    public static boolean getIncludeExplicitPause() {
        return includeExplicitPause;
    }

    public static void setIncludeExplicitPause(boolean includeExplicitPause) {
        TimeOutsConfig.includeExplicitPause = includeExplicitPause;
    }

    public static long getExplicitPause() {
        return explicitPause;
    }

    public static void setExplicitPause(long explicitPause) {
        TimeOutsConfig.explicitPause = explicitPause;
    }
}
