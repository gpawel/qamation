package org.qamation.webdriver.utils;

import org.qamation.utils.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;


/**
 * Created by pavel.gouchtchine on 12/14/2016.
 */
public class ExpectedConditionsUtils {
    public final static String JS_EXECUTOR_TIME_OUT_SYS_PROP = "JS_EXECUTOR_TIMEOUT_MILLS";
    public final static String PAGE_MUTATIONS_TIME_OUT_SYS_PROP = "PAGE_MUTATIONS_TIME_OUT_MILLS";
    public final static String PATE_MUTATIONS_INTERVAL_SYS_PROP = "PAGE_MUTATIONS_INTERVAL_MILLS";

    private final static String DOCUMENT_READY_ASYNC_SCRIPT = StringUtils.readFileIntoString("document_ready_async.js");
    private final static String GET_DOCUMENT_CONTENT_ASYNC_SCRIPT = StringUtils.readFileIntoString("get_document_content_async.js");
    private final static String MUTATIONS_OBSERVER_ASYNC_SCRIPT = StringUtils.readFileIntoString("wait_page_changes_stop.js");
    private final static String SCRIPTS_LOADING_STOPED = StringUtils.readFileIntoString("wait_scripts_loading_stops.js");


    public static ExpectedCondition<Boolean> documentReadyAsync() {
        return createAsyncJSCondition(DOCUMENT_READY_ASYNC_SCRIPT);
    }

    public static ExpectedCondition<Boolean> browserContentStoppedChangingCondition() {
        ExpectedCondition<Boolean> cond = new ExpectedCondition<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver drvr) {
                JavascriptExecutor js = getJSExecutor(drvr);
                String md5_1 = getPageContentMD5(js);
                long sleepIntervalMils = getMillsecondsFromSystemProperties(PATE_MUTATIONS_INTERVAL_SYS_PROP);
                sleep(sleepIntervalMils);
                String md5_2 = getPageContentMD5(js);
                if (md5_1.equals(md5_2)) return true;
                return false;
            }
        };
        return cond;
    }

    public static ExpectedCondition<Long> pageMutationsStoped(final WebElement element) {
        ExpectedCondition condition = new ExpectedCondition<Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable WebDriver drvr) {
                JavascriptExecutor jse = getJSExecutor(drvr);
                long mutationsTimeOut = getMillsecondsFromSystemProperties(PAGE_MUTATIONS_TIME_OUT_SYS_PROP);
                long mutationsInterval = getMillsecondsFromSystemProperties(PATE_MUTATIONS_INTERVAL_SYS_PROP);
                Long result = (Long)jse.executeAsyncScript(MUTATIONS_OBSERVER_ASYNC_SCRIPT,mutationsTimeOut,mutationsInterval,element);
                return result;
            }
        };
        return condition;
    }


    public static ExpectedCondition<Long> downloadScriptsStops() {
        ExpectedCondition condition = new ExpectedCondition<Long> () {
            @Nullable
            @Override
            public Long apply(@Nullable WebDriver webDriver) {
                JavascriptExecutor js = getJSExecutor(webDriver);
                Long result = (Long) js.executeAsyncScript(SCRIPTS_LOADING_STOPED,getMillsecondsFromSystemProperties(PATE_MUTATIONS_INTERVAL_SYS_PROP));
                return result;
            }
        };
        return condition;
    }

    public static JavascriptExecutor getJSExecutor(WebDriver drvr) {
        long executorTimeOutMills = getMillsecondsFromSystemProperties(JS_EXECUTOR_TIME_OUT_SYS_PROP);
        drvr.manage().timeouts().setScriptTimeout(executorTimeOutMills,TimeUnit.MILLISECONDS);
        JavascriptExecutor js = (JavascriptExecutor)drvr;
        return js;
    }

    private static long getMillsecondsFromSystemProperties(String propertyName) {
        String timestr = System.getProperty(propertyName,"2000");
        return Long.parseLong(timestr);
    }

    private static String getPageContentMD5 (JavascriptExecutor js) {
        String content = (String)js.executeAsyncScript(GET_DOCUMENT_CONTENT_ASYNC_SCRIPT);
        String md5 = StringUtils.getMD5(content);
        return md5;
    }

    private static ExpectedCondition<Boolean> createAsyncJSCondition(final String script) {
        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver drvr) {
                JavascriptExecutor jse = getJSExecutor(drvr);
                Boolean result = (Boolean)jse.executeAsyncScript(script);
                System.out.println(result);
                return result;
            }
        };
        return condition;
    }

    private static ExpectedCondition<Boolean> createSyncJSCondition(final String script) {
        ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver drvr) {
                JavascriptExecutor jse = getJSExecutor(drvr);
                Boolean result = (Boolean)jse.executeScript(script);
                System.out.println(result.booleanValue());
                return result;
            }
        };
        return condition;
    }

    private static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to sleep.", e);
        }
    }
}


    /*
    public static ExpectedCondition<Boolean> createAjaxStoppedAsyncCondition(final WebDriver drvr, ) {
        System.out.print("AJAX stopped: ");
        return createAsyncJSCondition(drvr, AJAX_STOPPED_ASYNC_SCRIPT, scriptTimeOut, timeUnits);
    }

    public ExpectedCondition<Boolean> createJQueryStoppedAsyncCondition(final WebDriver drvr) {
        System.out.print("JQuery Stopped: ");
        return createAsyncCondition(drvr, JQUERY_STOPPED_ASYNC_SCRIPT, 120, TimeUnit.SECONDS);
    }

    public ExpectedCondition<Boolean> createJQueryDefinedAsyncCondition(final WebDriver drvr) {
        System.out.print("JQuery Defined: ");
        return createAsyncCondition(drvr, JQUERY_DEFINED_ASYNC_SCRIPT, 120, TimeUnit.SECONDS);
    }

    public ExpectedCondition<Boolean> createDocumentReadySyncCondition(final WebDriver drvr) {
        System.out.print("Document ready: ");
        return createSyncCondition(drvr, DOCUMENT_READY_SYNC_SCRIPT);
    }

    public ExpectedCondition<Boolean> createJQueryStoppedSyncCondition(final WebDriver drvr) {
        System.out.print("JQuery Stopped: ");
        return createSyncCondition(drvr, JQUERY_STOPPED_SYNC_SCRIPT);
    }

    public ExpectedCondition<Boolean> createJQueryDefinedSyncCondition(final WebDriver drvr) {
        System.out.print("JQuery Defined: ");
        return createSyncCondition(drvr, JQUERY_DEFINED_SYNC_SCRIPT);
    }
   */


