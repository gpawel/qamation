package org.qamation.java.sampler.browser;

import org.apache.jmeter.samplers.SampleResult;
import org.openqa.selenium.WebDriver.Navigation;

import org.qamation.java.sampler.abstracts.AbstractExtentionPage;

public class BrowserRefresh extends AbstractExtentionPage {

	@Override
	protected void toDo() {
		getDriverFromContext();
		Navigation driverNavigator = driver.navigate();
		driverNavigator.refresh();
	}

	@Override
	protected SampleResult assembleTestResult() {
		String message = "Page refresh";
		SampleResult result = setSuccess(message, message.getBytes());
		return null;
	}

	@Override
	protected SampleResult assembleTestFailure(Exception e) {
		String message = "Unable to refresh the page";
		SampleResult result = setFailure(message,e);
		return result;
	}

}
