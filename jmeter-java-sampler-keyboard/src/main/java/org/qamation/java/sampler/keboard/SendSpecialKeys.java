package org.qamation.java.sampler.keboard;

import org.qamation.java.sampler.abstracts.AbstractExtentionKeyBoard;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;


public class SendSpecialKeys extends AbstractExtentionKeyBoard {
	private final static String SPECIAL_KEYS_TO_ENTER_PARAMETER = "ENTER SPECIAL KEYS TO TIPE. FOR EXAMPLE F3 OR SHIFT+F3";
	
	private String keysToType="";
	
	@Override
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(SPECIAL_KEYS_TO_ENTER_PARAMETER,"see https://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/Keys.html");
        return defaultParameters;
    }
	
	protected void readSamplerParameters() {
		super.readSamplerParameters();
		keysToType=getSamplerParameterValue(SPECIAL_KEYS_TO_ENTER_PARAMETER);
	}

	protected void type() {		
			keyboard.sendSpecialKeys(keysToType);
		
	}
	
	@Override
	protected SampleResult assembleTestResult() {
		byte[] data = ("Enter is hitted").getBytes();
		SampleResult result = setSuccess("Enter is hitted successfully", data);
		return result;
	}

}
