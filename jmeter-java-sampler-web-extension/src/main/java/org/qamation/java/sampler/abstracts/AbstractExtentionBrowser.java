package org.qamation.java.sampler.abstracts;

import java.net.URL;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;




public abstract class AbstractExtentionBrowser extends AbstractExtention {
	protected static final String BROWSER_VARIABLE = "ENTER VARIABLE NAME WHERE BROWSER IS STORED";
	protected static final String HUB_URL = "HUB_URL";

	protected String browserVariableName;
	protected WebDriver driver;

	
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();        
        defaultParameters.addArgument(BROWSER_VARIABLE,"${BROWSER_NAME}");
        return defaultParameters;
    }
	

	protected void readSamplerParameters() {
		super.readSamplerParameters();
		browserVariableName = getSamplerParameterValue(BROWSER_VARIABLE);
	}
	
	@Override 
	public SampleResult implementTest() {
		try {	
			readSamplerParameters();
			getDriverFromContext(); 
			toDo();
			SampleResult result = assembleTestResult();
			return result;
			
		} catch (Exception e) {
			SampleResult result = assembleTestFailure(e);
			return result;
		}
	}
	
	protected Object getObjectFromVariables(String key) {
		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		return vars.getObject(key);
	}
	
	protected URL getUrl(String urlDescription) {
		try {
			URL url = new URL(urlDescription);
			return url;
		}
		catch (Exception e) {
			throw new RuntimeException (e);
		}
	}
	
	private String getHub_Url() {
		String hub_url = ctx.getParameter("HUB_URL");
		if (hub_url == null || hub_url=="") throw new RuntimeException("HUB_URL must be provided via ${HUB_URL} variable");
		return hub_url;
	}
	
	protected URL getHubUrl() {
			String hub_url = getHub_Url();
			return getUrl(hub_url);
	}
	
	protected URL getUrlFromSamplerParameter(String samplerParameter) {
		try {
			String urlDescription = getSamplerParameterValue(samplerParameter);
			return getUrl(urlDescription);
		}
		catch (Exception e) {
			throw new RuntimeException (e);
		}
	}
	
	protected WebDriver getWebDriverByName(String browserVariableName) {
		return (WebDriver)getObjectFromVariables(browserVariableName);
	}
	
	protected WebDriver getWebDriverByParameterName(String samplerParameter) {
		String browserName = getSamplerParameterValue(samplerParameter);
		return getWebDriverByName(browserName);
	}
	

	
	protected void replaceParametesValue(Arguments defaultArguments, String key, String newValue) {
		defaultArguments.removeArgument(key);
		defaultArguments.addArgument(key, newValue);
	}
	
	protected void getDriverFromContext() {
		driver = getWebDriverByName(browserVariableName);
	}

	protected String getDuration(long duration) {
		String term;
		if (duration == 0) term="";
		else term = "load time: "+String.valueOf(duration)+" millis.";
		return term;
	}

}
