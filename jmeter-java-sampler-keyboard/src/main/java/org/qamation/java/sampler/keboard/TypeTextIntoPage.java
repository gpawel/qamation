package org.qamation.java.sampler.keboard;

import org.qamation.java.sampler.abstracts.AbstractExtentionKeyBoard;
import org.apache.jmeter.config.Arguments;

public class TypeTextIntoPage extends AbstractExtentionKeyBoard {
	
	protected static final String STRING_TO_TYPE = "ENTER WHAT TO TYPE ON THE SCREEN";
	
	
	@Override
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(STRING_TO_TYPE,"");
        return defaultParameters;
    }
	
	protected void type() {
		if (isLocationEmpty()) {
			keyboard.sendKeys(stringToEnter);
		}
		else {
			keyboard.sendKeysAt(stringToEnter, locationDescription);
		}
	}
	
	protected void readSamplerParameters() {
		super.readSamplerParameters();
		stringToEnter = getSamplerParameterValue(STRING_TO_TYPE);
	}
	
	private boolean isLocationEmpty() {
		if (locationDescription.isEmpty() || locationDescription.equalsIgnoreCase(LOCATION_PARAMETER_DEFAULT_VALUE))	return true;
		return false;
	}

	
}
