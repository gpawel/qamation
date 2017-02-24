package org.qamation.java.sampler.read;


import org.qamation.java.sampler.abstracts.AbstractExtentionReadFromPage;
import org.qamation.utils.StringUtils;
import org.qamation.web.page.Page;
import org.qamation.webdriver.utils.BasedWebDriverInstanceFactory;
import org.apache.jmeter.config.Arguments;



public class ReadTextFromPage  extends AbstractExtentionReadFromPage {
	
	protected static final String LENGTH_PARAMETER = "ENTER NUMBER OF SYMBOLS TO READ"; 
	
	private Page page;
	protected int length;
	
	@Override
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(LENGTH_PARAMETER,"");
        return defaultParameters;
    }
	
	@Override
	protected void readSamplerParameters() {
		super.readSamplerParameters();
		length = getLengthToRead();
	}
	
	private int getLengthToRead() {
		String readLength = getSamplerParameterValue(LENGTH_PARAMETER);
		if (readLength.isEmpty()) return 0;
		return StringUtils.convertStringToInt(readLength);
	}
	
	@Override
	protected Page createPage() {
		Page p = (Page) BasedWebDriverInstanceFactory.createInstance(pageImplementationClass, driver);
		return p;
		
	}

	@Override
	protected String readText() {
		if (length > 0) return page.readTextFrom(readFromlocationDescription, length);
		return page.readTextFrom(readFromlocationDescription);  
	}
	

	
	
	
	
}
