package org.qamation.webdriver.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qamation.utils.StringUtils;

import javax.annotation.Nullable;

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
        JavascriptExecutor js = getJavaScriptExecutor(driver);
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
        JavascriptExecutor js = getJavaScriptExecutor(driver);
        Boolean result = (Boolean) js.executeAsyncScript(SCRIPTS_LOADING_STOPED,
                TimeOutsConfig.getLoadScriptTimeOutMillis(),
                TimeOutsConfig.getLoadScriptIntervalMillis());
        return result;
    }

    public static int isPageChangeStopped(WebDriver driver) {
        JavascriptExecutor js = getJavaScriptExecutor(driver);
        int result = (int) js.executeAsyncScript(PAGE_CHANGES_OBSERVER_ASYNC_SCRIPT,
                TimeOutsConfig.getPageChangesTimeOutMillis(),
                TimeOutsConfig.getPageChangesIntervalMillis());
        return result;
    }

    public static Long waitPageWindowLoaded(WebDriver driver) {
        JavascriptExecutor js = getJavaScriptExecutor(driver);
        Long duration = (Long) js.executeAsyncScript(WINDOWS_LAODED_SCRIPT);
        return duration;
    }

    private static  Boolean waitDocumentStateReady(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,120,300);
        return wait.until(getDocumentReadyCondition());
    }

    private static JavascriptExecutor getJavaScriptExecutor(WebDriver driver) {
        if (driver instanceof JavascriptExecutor) {
            return (JavascriptExecutor) driver;
        } else throw new RuntimeException("Cannot turn this driver into JavaScript");
    }

    private static ExpectedCondition<Boolean> getDocumentReadyCondition() {
        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver drvr) {
                JavascriptExecutor jse = getJavaScriptExecutor(drvr);
                Boolean result = (Boolean)jse.executeAsyncScript(DOCUMENT_READY_ASYNC_SCRIPT);
                return result;
            }
        };
        return condition;
    }


}
