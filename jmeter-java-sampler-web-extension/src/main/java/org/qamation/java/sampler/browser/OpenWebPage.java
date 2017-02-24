package org.qamation.java.sampler.browser;

import org.qamation.utils.StringUtils;
import org.qamation.web.page.Page;
import org.qamation.web.page.WebPageFactory;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;


import org.qamation.java.sampler.abstracts.AbstractExtentionPage;



public class OpenWebPage extends AbstractExtentionPage {

	protected static final String URL_TO_OPEN = "ENTER WEB PAGE TO BE OPENED";
	long start_ts;
	long end_ts;
	String url;
	
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(URL_TO_OPEN,"http://cbc.ca");
        return defaultParameters;
    }

	protected void readSamplerParameters() {
		super.readSamplerParameters();
		url = getSamplerParameterValue(URL_TO_OPEN);
	}

	@Override
	protected void toDo() {
		Page page = WebPageFactory.createPageInstance(pageImplementationClass, driver);
		start_ts = System.currentTimeMillis();end_ts=0;
		page.openPage(url);
		end_ts = System.currentTimeMillis();
	}

	@Override
	protected SampleResult assembleTestResult() {
		String term = getDuration(end_ts - start_ts);
		String message = "PAGE at "+url+" IS OPENED. "+term;
		SampleResult result = setSuccess(message,message,message);
		return result;
	}

	@Override
	protected SampleResult assembleTestFailure(Exception e) {
		String stackTrace = StringUtils.getStackTrace(e);
		SampleResult result = setFailure("Cannot open provided URL\n\n"+stackTrace,e);
		return result;
	}

}
