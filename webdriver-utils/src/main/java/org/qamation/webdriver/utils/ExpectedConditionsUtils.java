package org.qamation.webdriver.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qamation.utils.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Function;


/**
 * Created by pavel.gouchtchine on 12/14/2016.
 */
public class ExpectedConditionsUtils {

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
                JavascriptExecutor js = WebDriverUtils.getJavaScriptExecutor(drvr);
                String md5_1 = getPageContentMD5(js);
                long sleepIntervalMils = TimeOutsConfig.getPageChangesIntervalMillis();//getMillsecondsFromSystemProperties(PATE_MUTATIONS_INTERVAL_SYS_PROP);
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
                JavascriptExecutor jse = WebDriverUtils.getJavaScriptExecutor(drvr);
                long mutationsTimeOut = TimeOutsConfig.getPageChangesTimeOutMillis(); //getMillsecondsFromSystemProperties(PAGE_MUTATIONS_TIME_OUT_SYS_PROP);
                long mutationsInterval = TimeOutsConfig.getPageChangesIntervalMillis();// getMillsecondsFromSystemProperties(PATE_MUTATIONS_INTERVAL_SYS_PROP);
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
                JavascriptExecutor js = WebDriverUtils.getJavaScriptExecutor(webDriver);
                Long result = (Long) js.executeAsyncScript(SCRIPTS_LOADING_STOPED,TimeOutsConfig.getLoadScriptTimeOutMillis(), TimeOutsConfig.getLoadScriptIntervalMillis());
                return result;
            }
        };
        return condition;
    }





    public static Function<WebDriver,Boolean> getDocumentReadyCondition() {
        Function<WebDriver,Boolean> condition = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver drvr) {
                JavascriptExecutor jse = WebDriverUtils.getJavaScriptExecutor(drvr);
                Boolean result = (Boolean)jse.executeAsyncScript(DOCUMENT_READY_ASYNC_SCRIPT);
                return result;
            }
        };
        return condition;
    }

    /*
    public static ExpectedCondition<Boolean> getSpinnerDissapearedCondition(final By spinnerLocation) {
        ExpectedCondition<Boolean> spinnerDisappers = new ExpectedCondition<Boolean>(){
            public Boolean apply(final WebDriver drvr) {
                List<WebElement> spinners = drvr.findElements(spinnerLocation);
                if (spinners.isEmpty()) {
                    return Boolean.valueOf(true);
                }
                return Boolean.valueOf(false);
            }
        };
        return spinnerDisappers;
    }
*/
    public static Function<WebDriver,Boolean> getSpinnerDissapearedCondition(final By spinnerLocation) {
        Function<WebDriver, Boolean> f = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(final WebDriver drvr) {
                List<WebElement> spinners = drvr.findElements(spinnerLocation);
                if (spinners.isEmpty()) {
                    return Boolean.valueOf(true);
                }
                return Boolean.valueOf(false);
            }
        };
        return f;
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
                JavascriptExecutor jse = WebDriverUtils.getJavaScriptExecutor(drvr);
                Boolean result = (Boolean)jse.executeAsyncScript(script);
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
                JavascriptExecutor jse = WebDriverUtils.getJavaScriptExecutor(drvr);
                Boolean result = (Boolean)jse.executeScript(script);
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


