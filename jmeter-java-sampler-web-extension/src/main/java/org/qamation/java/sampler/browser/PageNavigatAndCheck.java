package org.qamation.java.sampler.browser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.qamation.java.sampler.exceptions.StringComparisonException;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;


public class PageNavigatAndCheck extends PageNavigator {

	private static final String FRONTIER_ELEMENT_ID = "ENTER FRONTIER ELEMENT ID. Leave empty to skip verification.";
	private static final String EXPECTED_RESULT = "ENTER EXPECTED RESULT. Leave empty to skip verification.";
	private static final String BACK_TO_URL = "ENTER URL TO GO BACK. Leave empty to stay at the current page";

	protected String elementLocator;
	protected String expectedResult;
	protected String backToURL;

	protected String readText;
	protected boolean shouldVerifyText;
	protected boolean shouldGoBack;

	protected String comment="";



	public Arguments getDefaultParameters() {
		Arguments defaultParameters = super.getDefaultParameters();
		defaultParameters.addArgument(FRONTIER_ELEMENT_ID, "");
		defaultParameters.addArgument(EXPECTED_RESULT, "");
		defaultParameters.addArgument(BACK_TO_URL, "");
		return defaultParameters;
	}

	@Override
	protected void readSamplerParameters() {
		super.readSamplerParameters();
		elementLocator = getSamplerParameterValue(FRONTIER_ELEMENT_ID);
		expectedResult = getSamplerParameterValue(EXPECTED_RESULT);
		expectedResult = URLDecodeString(expectedResult);
		backToURL = getSamplerParameterValue(BACK_TO_URL);
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
		if (shouldVerifyText) {
			readText = readText();
			failed = verifyExpectedResult(readText, expectedResult);
		}

		if (shouldGoBack) {
			goToBackURL(backToURL);
		}

		if (failed) throw new StringComparisonException("Read text does not match to expected value");
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

	private String URLDecodeString(String str) {
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
