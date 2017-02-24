package org.qamation.java.sampler.browser;

import org.apache.jmeter.samplers.SampleResult;

import org.qamation.java.sampler.abstracts.AbstractExtentionBrowser;

public class CloseBrowser extends AbstractExtentionBrowser {

	@Override
	protected void toDo() {
		if (driver != null) {
			driver.close();
		}
		else throw new RuntimeException("Browser is NULL in variable "+browserVariableName);
		
	}

	@Override
	protected SampleResult assembleTestResult() {
		String message = "Closing browser from variable named '"+browserVariableName+"'";
		SampleResult result = setSuccess(message, message.getBytes());
		return result;
	}

	@Override
	protected SampleResult assembleTestFailure(Exception e) {
		SampleResult result = setFailure("Unable to close browser becaise:\n", e);
		return result;
	}

}
