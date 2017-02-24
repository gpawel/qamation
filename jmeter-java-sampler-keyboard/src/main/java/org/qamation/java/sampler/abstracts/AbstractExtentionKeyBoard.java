package org.qamation.java.sampler.abstracts;

import org.qamation.keyboard.Keyboard;
import org.qamation.keyboard.KeyboardFactory;
import org.qamation.utils.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;



public abstract class AbstractExtentionKeyBoard extends AbstractExtentionBrowser {
	protected static final String LOCATION_PARAMETER =  "ENTER LOCATION ON PAGE TO TYPE AT";
	protected static final String LOCATION_PARAMETER_DEFAULT_VALUE = "can be empty or xpath=<xpath here> OR id=<element id> is the same as just <element id> OR css=<css locator here> OR class=<class name here>";
	protected static final String KEYBOARD_IMPLEMENTATIN_CLASS_NAME_PARAMETER = "ENTER KEYBOARD IMPLEMENTING CLASS NAME";
	
	protected String keyboardImplementationClass;
	protected String locationDescription;
	protected String stringToEnter;
	protected Keyboard keyboard;
	
	@Override
	public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(LOCATION_PARAMETER, LOCATION_PARAMETER_DEFAULT_VALUE);
        defaultParameters.addArgument(KEYBOARD_IMPLEMENTATIN_CLASS_NAME_PARAMETER,"KeyboardEmulator");
        return defaultParameters;
    }


	protected void readSamplerParameters() {
		super.readSamplerParameters();
		locationDescription = getSamplerParameterValue(LOCATION_PARAMETER);
		keyboardImplementationClass = getSamplerParameterValue(KEYBOARD_IMPLEMENTATIN_CLASS_NAME_PARAMETER);
	}

	protected void getDriverFromContext() {
		driver = getWebDriverByName(browserVariableName);
	}
	
	
	
	private void createKeyboard() {
		keyboard = KeyboardFactory.createKeyboard(keyboardImplementationClass, driver);
	}
	
	protected void toDo() {
		createKeyboard();
		type();
	}
	
	protected SampleResult assembleTestResult() {
		byte[] data = ("Text typed: "+stringToEnter).getBytes();
		SampleResult result = setSuccess("Text typed successfully", data);
		return result;
	}
	
	protected SampleResult assembleTestFailure(Exception e) {
		String stackTrace = StringUtils.getStackTrace(e);
		e.printStackTrace();
		SampleResult result = setFailure("Unable to Enter text into page\n\n"+stackTrace, e);
		result.setStopThread(shouldStop);
		return result;
	}

	
	abstract protected void type();
	

}
