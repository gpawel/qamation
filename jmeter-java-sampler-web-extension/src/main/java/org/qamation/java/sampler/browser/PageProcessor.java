package org.qamation.java.sampler.browser;

import com.google.common.base.Function;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.qamation.java.sampler.exceptions.StringComparisonException;
import org.qamation.utils.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;


public class PageProcessor extends PageNavigatAndCheck {

    private static final String EXTRACT_TO = "ENTER VARIABLE NAME TO STORE EXTACTED DATA";
    private static final String COMMENT = "ENTER A COMMENT TO BE INCLUDED INTO A LABEL. Leave empty ";
    private String comment;

    private String extractTo;
    private boolean shouldExtract;

    public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(COMMENT, "${COMMENT}");
        defaultParameters.addArgument(EXTRACT_TO, "${EXTRACT_TO}");
        return defaultParameters;
    }

    @Override
    protected void readSamplerParameters() {
        comment = getSamplerParameterValue(COMMENT);
        super.readSamplerParameters();
        extractTo = getSamplerParameterValue(EXTRACT_TO);
        initProcessorVariablels();
    }

    protected void initProcessorVariablels() {
        shouldExtract = true;
        if (elementLocator.isEmpty() || extractTo.isEmpty()) shouldExtract = false;
    }

    @Override
    protected void toDo() {
        initPageInstance();
        navigatePage();
        boolean failed = verifyText();
        extractValue();
        goBack();
        if (failed) throw new StringComparisonException("Read text does not match to expected value");
    }

    @Override
    protected boolean verifyText() {
        boolean passed = false;
        boolean failed = true;
        if (shouldVerifyText) {
            ExpectedCondition<String> condition = getCondition(elementLocator, expectedResult);
            try {
                readText = page.isReady(condition);
                return passed;
            }
            catch (TimeoutException ex) {
                readText = readText();
                return failed;

            }
        }
        return passed;
    }

    protected void extractValue() {
        if (shouldExtract) {
            String value = extractFromPage(elementLocator);
            setStringIntoVariables(extractTo, value);
        }
    }

    protected String extractFromPage(String extractFrom) {
        String value = page.readTextFrom(extractFrom);
        return value;
    }

    @Override
    protected SampleResult assembleTestFailure(Exception ex) {
        SampleResult result = new SampleResult();
        result.setSuccessful(false);
        if (ex != null) addExceptionInformation(result, ex);
        if (comment != null) addCommentToLable(result);
        result.setStopThread(shouldStop);
        return result;
    }

    private void addExceptionInformation(SampleResult result, Exception ex) {
        if (ex instanceof StringComparisonException) {
            String label = getAssertionErrorLable();
            String message = getAssertionErrorMessage();
            result.setSampleLabel(label);
            result.setResponseMessage(message);
            return;
        }
        result.setResponseMessage("FAILED");
        result.setResponseData(StringUtils.getStackTrace(ex).getBytes());
        return;
    }

    private void addCommentToLable(SampleResult result) {
        if (comment.isEmpty()) return;
        String label = result.getSampleLabel();
        if (label == null || label.isEmpty())
            label = comment;
        else
            label = comment + " - " + label;
        result.setSampleLabel(label);
        return;
    }

    @Override
    protected SampleResult assembleTestResult() {
        SampleResult result = new SampleResult();
        result.setSuccessful(true);
        String label = getLabel();
        result.setSampleLabel(label);
        addCommentToLable(result);
        result.setResponseMessageOK();
        return result;
    }

    protected ExpectedCondition<String> getExpectedTextIsReadyCondition(final String location, final String expectedString) {
        ExpectedCondition<String> condition = new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver webDriver) {
                String read = page.readTextFrom(location,expectedString.length());
                if (read.equals(expectedString)) return read;
                else return null;
            }
        };
        return condition;
    }

    //processor
    private ExpectedCondition<String> getCondition(final String location, final String expectedString) {
        return getExpectedTextIsReadyCondition(location, expectedString);
    }

}
