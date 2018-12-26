package org.qamation.webdriver.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qamation.utils.StringUtils;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Created by Pavel.Gouchtchine on 12/30/2016.
 */
public class IsPageReadyUtils {

    private final static String DOCUMENT_READY_ASYNC_SCRIPT = StringUtils.readFileIntoString("document_ready_async.js");
    private final static String GET_DOCUMENT_CONTENT_ASYNC_SCRIPT = StringUtils.readFileIntoString("get_document_content_async.js");
    private final static String PAGE_CHANGES_OBSERVER_ASYNC_SCRIPT = StringUtils.readFileIntoString("wait_page_changes_stop.js");
    private final static String SCRIPTS_LOADING_STOPED = StringUtils.readFileIntoString("wait_scripts_loading_stops.js");
    private final static String WINDOWS_LAODED_SCRIPT = StringUtils.readFileIntoString("wait_window_load.js");

    public static String getDocumentContent(WebDriver driver) {
        JavascriptExecutor js = WebDriverUtils.getJavaScriptExecutor(driver);
        String content = (String) js.executeAsyncScript(GET_DOCUMENT_CONTENT_ASYNC_SCRIPT);
        return content;
    }

    public static String getDocumentContentMD5(WebDriver driver) {
        String content = getDocumentContent(driver);
        String md5 =  StringUtils.getMD5(content);
        return md5;
    }

    public static boolean isDocumentStateReady(WebDriver driver) {
        Boolean result = waitDocumentStateReady(driver);
        return result.booleanValue();
    }

    public static  Boolean isScriptsLoadingDone(WebDriver driver) {
        JavascriptExecutor js = WebDriverUtils.getJavaScriptExecutor(driver);
        Boolean result = (Boolean) js.executeAsyncScript(SCRIPTS_LOADING_STOPED,
                TimeOutsConfig.getLoadScriptTimeOutMillis(),
                TimeOutsConfig.getLoadScriptIntervalMillis());
        return result;
    }

    public static int isPageChangeStopped(WebDriver driver) {
        JavascriptExecutor js = WebDriverUtils.getJavaScriptExecutor(driver);
        Long result = (Long) js.executeAsyncScript(PAGE_CHANGES_OBSERVER_ASYNC_SCRIPT,
                TimeOutsConfig.getPageChangesTimeOutMillis(),
                TimeOutsConfig.getPageChangesWatchForMaxMillis(),
                TimeOutsConfig.getPageChangesIntervalMillis());
        return result.intValue();
    }

    public static Long waitPageWindowLoaded(WebDriver driver) {
        JavascriptExecutor js = WebDriverUtils.getJavaScriptExecutor(driver);
        Long duration = (Long) js.executeAsyncScript(WINDOWS_LAODED_SCRIPT);
        return duration;
    }

    public static Boolean waitForSpinnerToAppear(WebDriver driver, By spinnerLocator) {
        WebDriverWait wait = new WebDriverWait(driver,
                TimeOutsConfig.getWaitForSpinnerToAppearTimeOutMillis()/1000,
                TimeOutsConfig.getWaitForSpinnerToAppearIntervalMillis());

        Function<WebDriver,WebElement> f = ExpectedConditions.presenceOfElementLocated(spinnerLocator);
        WebElement el = wait.until(f);
        return el != null;
    }

    public static Boolean waitForSpinnerToDisappear(WebDriver driver, By spinnerLocation) {
        long timeOut = TimeOutsConfig.getWaitForSpinnerToDisappearTimeOutMillis() / 1000L;
        long interval = TimeOutsConfig.getWaitForSpinnerToDisappearIntervalMillis();
        WebDriverWait waitSpinnerIsGone = new WebDriverWait(driver, timeOut, interval);

        //ExpectedCondition<Boolean> spinnerDisappearsCondition = ExpectedConditionsUtils.getSpinnerDissapearedCondition(spinnerLocation);

        Function <WebDriver,Boolean> f = ExpectedConditionsUtils.getSpinnerDissapearedCondition(spinnerLocation);
        try  {
            //waitSpinnerIsGone.until(spinnerDisappearsCondition);
            waitSpinnerIsGone.until(f);
            return Boolean.valueOf(true);
        }
        catch (TimeoutException ex) {return Boolean.valueOf(false);}

    }



    private static  Boolean waitDocumentStateReady(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,120,300);
        return wait.until(ExpectedConditionsUtils.getDocumentReadyCondition());
    }




}
