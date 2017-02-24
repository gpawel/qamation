package org.qamation.java.sampler.browser;

import org.qamation.java.sampler.exceptions.StringComparisonException;
import org.qamation.utils.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;


public class PageProcessor extends PageNavigatAndCheck {
	
	private static final String EXTRACT_FROM="ENTER ELEMENT ID TO START VALUE EXTACTION FROM, leave empty for no extraction";
	private static final String EXTRACT_HOW_MACH="ENTER NUMBER OF SYMBOLS TO EXTRACT, leave empty to extract till end of field";
	private static final String EXTRACT_TO="ENTER VARIABLE NAME TO STORE EXTACTED DATA";
	private static final String COMMENT =  "ENTER A COMMENT TO BE INCLUDED INTO A LABEL. Leave empty ";
	private String comment;
	private String extractFrom;
	private String extractLength;
	private String extractTo;
	
	private boolean shouldExtract;
	
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(COMMENT,"${COMMENT}");
        defaultParameters.addArgument(EXTRACT_FROM,"${EXTRACT_FROM}");
        defaultParameters.addArgument(EXTRACT_HOW_MACH,"${EXTRACT_HOW_MUCH}");
        defaultParameters.addArgument(EXTRACT_TO,"${EXTRACT_TO}");
        return defaultParameters;
    }
	
	@Override
	protected void readSamplerParameters() {
		comment = getSamplerParameterValue(COMMENT);
		super.readSamplerParameters();
		extractFrom = getSamplerParameterValue(EXTRACT_FROM);
		extractLength = getSamplerParameterValue(EXTRACT_HOW_MACH);
		extractTo = getSamplerParameterValue(EXTRACT_TO);
		initProcessorVariablels();
	}
	
	protected void initProcessorVariablels() {
		shouldExtract = true;
		if (extractFrom.isEmpty()) shouldExtract = false;
	}


	protected void extractValue() {
		if (shouldExtract) {
			String value = extractFromPage(extractFrom);
			setStringIntoVariables(extractTo, value);
		}
		
	}

	private String extractFromPage(String extractFrom) {
			int length = parseExtractLength();
			String value = page.readTextFrom(extractFrom, length);
			return value;
			
	}

	private int parseExtractLength() {		
		if (extractLength.isEmpty()) {
			return 0;
		}
		else {
			return Integer.parseInt(extractLength);				 
		}		
	}
	
	@Override
	protected SampleResult assembleTestFailure(Exception ex) {
		SampleResult result = new SampleResult();
		result.setSuccessful(false);
		if (ex != null) addExceptionInformation(result,ex);
		if (comment != null) addCommentToLable(result);
		result.setStopThread(shouldStop);
		return result;
	}
	
	private void addExceptionInformation(SampleResult result, Exception ex) {
		if (ex instanceof StringComparisonException) {
			String label = getAssertionErrorLable();
			String message = getAssertionErrorMessage();
			result.setSampleLabel(label);
			result.setResponseMessage(message);
			return;
		}
		result.setResponseMessage("FAILED");
		result.setResponseData(StringUtils.getStackTrace(ex).getBytes());
		return;
	}

	private void addCommentToLable(SampleResult result) {		
		if (comment.isEmpty() ) return;
		String label = result.getSampleLabel();
		if (label == null || label.isEmpty()) 
			label = comment;
		else 
			label = comment + " - " + label;
		result.setSampleLabel(label);
		return;
	}

	@Override
	protected SampleResult assembleTestResult() {
		SampleResult result = new SampleResult();
		result.setSuccessful(true);
		String label = getLabel();
		result.setSampleLabel(label);
		addCommentToLable(result);
		result.setResponseMessageOK();
		return result;
	}
	
}
