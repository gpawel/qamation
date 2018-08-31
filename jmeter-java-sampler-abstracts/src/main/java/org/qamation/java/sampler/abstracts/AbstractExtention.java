package org.qamation.java.sampler.abstracts;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.qamation.utils.RegExpUtils;
import org.qamation.utils.StringUtils;
/*
public static final String EXTRACT_FUNCTION_PARAMETERS_REGEX = "(?mi)\\((.*)\\)";
    public static final String EXTRACT_FUNCTION_NAME = "(?mi)\\$\\{__(.*?)\\(";
    public static final String SPLIT_FUNCT_PARAMS_REGEX = "(?mi)(?<!\\\\),";
 */

public abstract class AbstractExtention extends AbstractJavaSamplerClient {
	protected static final String STOP_TEST_AFTER_FAIL = "ENTER true OR false TO INDICATE WHETHER TO STOP AFTER FAILURE";
	public static final String EXTRACT_VARIABLES_REGEX = "(?mi)(?=\\$\\{[^_])\\$\\{(.*?)\\}";


	protected JavaSamplerContext ctx;
	protected boolean shouldStop;
	protected SampleResult result;


	abstract protected void toDo();
	abstract protected SampleResult assembleTestResult();
	abstract protected SampleResult assembleTestFailure(Exception e);

	public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument(STOP_TEST_AFTER_FAIL,"${STOP_INDICATOR}");
        return defaultParameters;
    }
	protected void readSamplerParameters() {
		shouldStop = false;
		String stopping = getSamplerParameterValue(STOP_TEST_AFTER_FAIL);
		shouldStop = Boolean.valueOf(stopping);
	}
	public SampleResult runTest(JavaSamplerContext ctx) {
		this.ctx = ctx;
		result = new SampleResult();
		result.sampleStart();
		return implementTest();
	}

	public SampleResult implementTest() {
		try {	
			readSamplerParameters();
			toDo();
			result.sampleEnd();
			assembleTestResult();
			return result;
			
		} catch (Exception e) {
			result.sampleEnd();
			assembleTestFailure(e);
			return result;
		}
	}
	

	
	protected void setObjectIntoVariables(String key, Object obj) {
		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		vars.putObject(key, obj);
	}
	
	protected void setStringIntoVariables(String key, String value) {
		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		vars.put(key, value);
	}
	
	protected String getStringFromVariables(String key) {
		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		String value = vars.get(key);
		if (value == null) throw new RuntimeException("Variable name "+key+" is not found.");
		return value;
	}
	
	protected Object getObjectFromVariables(String key) {
		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		return vars.getObject(key);
	}
	
	protected String substituteVariables(String input) {
	    input = substitureVars(input);
		return input;
	}

    private String[] getFunctionsResults(String[] functions, String[] functionsParams) {
	    if (functions.length !=  functionsParams.length) throw new RuntimeException("Number of functions does not match to number of parameters.");
	    return null;
    }

	private String substitureVars(String input) {
		RegExpUtils ru = new RegExpUtils(input,EXTRACT_VARIABLES_REGEX);
		String[] rur = ru.getAllFindings();
		if (rur.length < 2) return input;
		String result = new String (input);
		for (int i = 0; i <= rur.length-2; i=i+2) {
			String variable = rur[i];
			String varName = rur[i+1];
			String varValue = getStringFromVariables(varName);
			result = result.replace(variable, varValue);
		}
		return result;
	}



	protected String getSamplerParameterValue(String samplerParameterName) {
		String parameterValue = ctx.getParameter(samplerParameterName);		
		if (parameterValue.isEmpty()) return "";
		String result = substituteVariables(parameterValue);
		return result;
	}
	
	
	protected void replaceParametesValue(Arguments defaultArguments, String key, String newValue) {
		defaultArguments.removeArgument(key);
		defaultArguments.addArgument(key, newValue);
	}
	
	@Deprecated
	protected SampleResult setFailure(String message, Exception e) {		
		setFailure(message);
		result.setResponseMessage(message + "\n\n" + e.toString());
		result.setResponseData((StringUtils.getStackTrace(e)).getBytes());
		result.setStopThread(shouldStop);
		return result;
	}
	
	@Deprecated
	protected SampleResult setFailure(String message) {
		result.setSuccessful(false);
		result.setResponseCode("FAILED");
		result.setResponseMessage(message);
		return result;
	}
	
	@Deprecated
	protected SampleResult setSuccess(String message, byte[] data) {
		result.setResponseCodeOK();
		result.setResponseMessage(message);
		result.setResponseData(data);
		result.setSuccessful(true);
		return result;
	}

	protected SampleResult setSuccess(String label, String responseMessage, String responseData) {
		getNewSampleResult(label, responseMessage, responseData);
		result.setSuccessful(true);
		result.setResponseCode("OK");
		return result;
	}
	
	protected SampleResult setFailure(String label, String responseMessage, String responseData) {
		getNewSampleResult(label, responseMessage, responseData);
		result.setSuccessful(false);
		result.setResponseCode("FAILED");
		result.setStopTest(shouldStop);
		return result;
	}
	
	protected SampleResult getNewSampleResult(String label, String responseMessage, String responseData) {
		if (label != null) result.setSampleLabel(label);
		if (responseMessage != null) result.setResponseMessage(responseMessage);
		if (responseData != null) result.setResponseData(responseData.getBytes());
		return result;
	}
	
}
