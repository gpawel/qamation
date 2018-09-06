package org.qamation.java.sampler.browser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.qamation.java.sampler.exceptions.StringComparisonException;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;


public class PageNavigatAndCheck extends PageNavigator {

	private static final String ELEMENT_LOCATOR = "ENTER ELEMENT ID.  Provide empty value to skip verification";
	private static final String EXPECTED_RESULT = "ENTER EXPECTED RESULT. Provide empty value to skip verification.";
	private static final String NAVIGATE_TO_URL = "ENTER URL TO GO BACK. Provide empty value to skip.";

	protected String elementLocator;
	protected String expectedResult;
	protected String backToURL;

	protected String readText;
	protected boolean shouldVerifyText;
	protected boolean shouldGoBack;

	protected String comment="";



	public Arguments getDefaultParameters() {
		Arguments defaultParameters = super.getDefaultParameters();
		defaultParameters.addArgument(ELEMENT_LOCATOR, "${ELEMENT_ID}");
		defaultParameters.addArgument(EXPECTED_RESULT, "${EXPECTED}");
		defaultParameters.addArgument(NAVIGATE_TO_URL, "${BACK_URL}");
		return defaultParameters;
	}

	@Override
	protected void readSamplerParameters() {
		super.readSamplerParameters();
		elementLocator = getSamplerParameterValue(ELEMENT_LOCATOR);
		expectedResult = getSamplerParameterValue(EXPECTED_RESULT);
		expectedResult = URLDecodeString(expectedResult);
		backToURL = getSamplerParameterValue(NAVIGATE_TO_URL);
		setUpNavigationAndCheck();
	}

	protected void setUpNavigationAndCheck() {
		readText = "";
		shouldVerifyText = true;
		shouldGoBack = true;
		duration = 0;
		if (elementLocator.isEmpty()) {
			shouldVerifyText = false;
		}

		if (expectedResult.isEmpty()) {
			shouldVerifyText = false;
		}

		if (backToURL.isEmpty()) {
			shouldGoBack = false;
		}
	}

	@Override
	protected void toDo() {
		super.toDo();
		boolean failed = false;
		failed = verifyText();
		goBack();
		if (failed) throw new StringComparisonException("Read text does not match to expected value");
	}

	protected void goBack() {
		if (shouldGoBack) {
			goToBackURL(backToURL);
		}
	}

	protected boolean verifyText() {
		if (shouldVerifyText) {
			readText = readText();
			return verifyExpectedResult(readText, expectedResult);
		}
		return false;
	}

	protected boolean verifyExpectedResult(String actual, String expected) {
		return !actual.equals(expected);
	}

	protected void goToBackURL(String url) {
	    page.goBack(url);
	}

	protected String readText() {
		return page.readTextFrom(elementLocator, expectedResult.length());
	}

	protected String URLDecodeString(String str) {
		try {
			String decodedStr = URLDecoder.decode(str, "UTF-8");
			return decodedStr;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unable to decode EXPECTED result: " + str, e);
		}
	}

	protected String getLabel() {
		String term = getDuration(duration);
		return "<"+navigationSequence + "> PAGE HAS \"" + readText + "\" AT: <" + elementLocator + "> "+term;
	}

	@Override
	protected SampleResult assembleTestResult() {
		String label = getLabel();
		String message = "OK";
		result = setSuccess(label,message, label);
		result.setSampleLabel(label);
		return result;
	}

	@Override
	protected SampleResult assembleTestFailure(Exception ex) {
		String label;
		String message;
		if (ex instanceof StringComparisonException) {
			label = getAssertionErrorLable();
			message = getAssertionErrorMessage();
		} else {
			label = "<" + navigationSequence + ">";
			message = "Opening <" + navigationSequence + "> OR " + "<" + backToURL + "> FAILED";
		}
		// label = label+"\n\n"+StringUtils.getStackTrace(ex);
		result = setFailure(label,message, ex.toString());
		result.setSampleLabel(label);
		result.setStopThread(shouldStop);
		return result;
	}

	protected String getAssertionErrorMessage() {
		return "ACTUAL AND EXPECTED RESULTS ARE DIFFERENT.";
	}

	protected String getAssertionErrorLable() {
		return getLabel() + " BUT EXPECTED: " + expectedResult;
	}

}
