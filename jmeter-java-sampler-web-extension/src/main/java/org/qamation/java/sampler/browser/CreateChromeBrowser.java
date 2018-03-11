package org.qamation.java.sampler.browser;

import java.net.URL;

import org.qamation.webdriver.utils.WebDriverFactory;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.openqa.selenium.WebDriver;
import org.qamation.java.sampler.abstracts.AbstractExtentionBrowser;


public class CreateChromeBrowser extends AbstractExtentionBrowser {

	
	@Override
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(HUB_URL, "${HUB_URL}");
        return defaultParameters;
    }

	/*
	protected void readSamplerParameters() {
		super.readSamplerParameters();
	}
	*/
	
	private WebDriver createWebDriver() {
		URL hub = getHubUrl();
		WebDriver driver = WebDriverFactory.createRemoteChromeDriver(hub);
		return driver;
	}

	@Override
	protected void getDriverFromContext() {
		driver = createWebDriver();
	}
	
	@Override
	protected void toDo() {
		setObjectIntoVariables(browserVariableName,driver);
	}

	@Override
	protected SampleResult assembleTestResult() {
		String message = "Chrome browser is placed into variable named: "+browserVariableName;
		result = setSuccess(
				message,
				message.getBytes()
				);
		return result;
	}

	@Override
	protected SampleResult assembleTestFailure(Exception e) {
		result = setFailure("Chrome WebDriver cannot be created", e);
		result.setStopThread(shouldStop);
		return result;
	}
}
