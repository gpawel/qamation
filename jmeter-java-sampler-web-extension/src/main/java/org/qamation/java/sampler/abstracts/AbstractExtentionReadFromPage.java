package org.qamation.java.sampler.abstracts;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.qamation.web.page.Page;


public abstract class AbstractExtentionReadFromPage extends AbstractExtentionPage {
	protected static final String RESULT_VARIABLE_NAME_PARAMETER = "ENTER VARIABLE NAME TO STORE RESULT";
	protected static final String LOCATION_PARAMETER =  "ENTER LOCATION TO READ FROM";

	protected String readFromlocationDescription;
	protected String resultVariableName;
	protected String readText;

	protected void readSamplerParameters() {
		super.readSamplerParameters();
		readFromlocationDescription = getSamplerParameterValue(LOCATION_PARAMETER);
		resultVariableName = getSamplerParameterValue(RESULT_VARIABLE_NAME_PARAMETER);	
	}
	
	
	@Override
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(LOCATION_PARAMETER, "xpath=<xpath here> OR id=<element id> is the same as just <element id> OR css=<css locator here> OR class=<class name here>");
        defaultParameters.addArgument(RESULT_VARIABLE_NAME_PARAMETER,"READ_TEXT");
        return defaultParameters;
    }
	
	@Override
	protected SampleResult assembleTestResult() {
		byte[] data = readText.getBytes();
		SampleResult result = setSuccess("Text read successfully ",data);
		return result;
	}
	
	@Override
	protected SampleResult assembleTestFailure(Exception e) {
		SampleResult result = setFailure("Cannot reade text "+e.toString(), e);
		return result;
	}
	
	@Override
	protected void toDo() {
		page = createPage();
		readText = readText();
		setStringIntoVariables(resultVariableName, readText);
	}
	
	abstract protected Page createPage();
	abstract protected String readText();

}
