package org.qamation.java.sampler.keboard;

import org.qamation.java.sampler.abstracts.AbstractExtentionKeyBoard;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.openqa.selenium.Keys;
import org.qamation.utils.StringUtils;
public class HitTab extends AbstractExtentionKeyBoard {

private final static String HOW_MANY = "ENTER NUMBER OF TIMES TO HIT TAB KEY"; 
	
	private int howMany;
	
	@Override
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(HOW_MANY,"1");
        return defaultParameters;
    }
	
	protected void readSamplerParameters() {
		super.readSamplerParameters();
		String hm = getSamplerParameterValue(HOW_MANY);
		howMany = StringUtils.convertStringToInt(hm);
	}

	protected void type() {
		for (int i=0; i<howMany; i++)
			keyboard.sendKeys(Keys.TAB);
	}
	
	@Override
	protected SampleResult assembleTestResult() {
		byte[] data = ("Tab is hitted").getBytes();
		SampleResult result = setSuccess("Tab is hitted successfully", data);
		return result;
	}

}
