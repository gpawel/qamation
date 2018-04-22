package org.qamation.java.sampler.browser;

import org.qamation.navigator.NavigationString;
import org.qamation.navigator.WebPageNavigator;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.qamation.java.sampler.abstracts.AbstractExtentionPage;



public class PageNavigator extends AbstractExtentionPage {
	public static final String NAVIGATION_SEQUENCE_PARAMETER = "ENTER NAVAGATION SCTRING";
	public static final String NAVIGATION_SEQUENCE_DELIMETER_PARAMETER = "ENTER A SYMBOL THAT SEPARATES NAVIGATION STEPS";

	protected String navigationSequence="";
	protected String navigationSequenceDelimeter;


	protected boolean shouldNavigate = true;

	public Arguments getDefaultParameters() {
		Arguments defaultParameters = super.getDefaultParameters();
		defaultParameters.addArgument(NAVIGATION_SEQUENCE_PARAMETER, "");
		defaultParameters.addArgument(NAVIGATION_SEQUENCE_DELIMETER_PARAMETER, "\\.");
		return defaultParameters;
	}

	@Override
	protected void readSamplerParameters() {
		super.readSamplerParameters();
		navigationSequence = getSamplerParameterValue(NAVIGATION_SEQUENCE_PARAMETER);
		navigationSequenceDelimeter = getSamplerParameterValue(NAVIGATION_SEQUENCE_DELIMETER_PARAMETER);
		setUp();
	}

	private void setUp() {
		shouldNavigate = true;
		if (navigationSequence.isEmpty()) {
			shouldNavigate = false;
		}
	}

	@Override
	protected void toDo() {
		page = getPageInstance();
		if (shouldNavigate) {
			navigate(navigationSequence, navigationSequenceDelimeter);
		}
	}

	protected long navigate(String navigationSequence, String navigationSequenceDelimeter) {
		//System.out.println(String.valueOf(this.hashCode())+"-"+String.valueOf(driver.hashCode())+": navigation -> "+navigationSequence);
		WebPageNavigator navigator = getWebPageNavigator();
		String[] tokens = getNavigationTokens(navigationSequence, navigationSequenceDelimeter);
		long start_ts = System.currentTimeMillis();
		navigator.processNavigationSequience(tokens, page);
		long end_ts = System.currentTimeMillis();
		return end_ts - start_ts;
	}

	@Override
	protected SampleResult assembleTestResult() {
		result = setSuccess("OK", (navigationSequence + " IS PROCESSED").getBytes());
		return result;
	}

	@Override
	protected SampleResult assembleTestFailure(Exception e) {
		String message = navigationSequence + " CANNOT BE PROCESSED";
		result = setFailure(message, e);
		return result;
	}

	private WebPageNavigator getWebPageNavigator() {
		return new WebPageNavigator(driver);
	}

	private String[] getNavigationTokens(String navigationSequence, String navigationSequenceDelimeter) {
		NavigationString ns = new NavigationString(navigationSequence, navigationSequenceDelimeter);
		return ns.getNavigationSequence();
	}

}
