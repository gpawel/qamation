package org.qamation.java.sampler.abstracts;

import org.apache.jmeter.config.Arguments;
import org.qamation.web.page.Page;
import org.qamation.web.page.WebPageFactory;



public abstract class AbstractExtentionPage extends AbstractExtentionBrowser  {
	protected static final String PAGE_IMPLEMENTATION_CLASS_NAME_PARAMETER = "ENTER PAGE IMPLEMENTATION CLASS";

	protected String pageImplementationClass;
	protected Page page;

	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(PAGE_IMPLEMENTATION_CLASS_NAME_PARAMETER,"${PAGE_IMPLEMENTATION_CLASS}");
        return defaultParameters;
    }
	

	protected void readSamplerParameters() {
		super.readSamplerParameters();
		pageImplementationClass = getSamplerParameterValue(PAGE_IMPLEMENTATION_CLASS_NAME_PARAMETER);
	}
	
	protected Page getPageInstance() {
		return WebPageFactory.createPageInstance(pageImplementationClass, driver);
	}





}
