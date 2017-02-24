package org.qamation.webdriver.utils;

import java.lang.reflect.Method;

import org.openqa.selenium.By;

public class LocatorFactory {

	public final String [] METHODS = new String[] {
		"id"
		,"xpath"
		,"cssSelector"
		,"className"		
		,"linkText"
		,"name"
		,"partialLinkText"
		,"tagName"
	}; 
	
	private  By executeMethod(Method method, String... param ) {
		try {
			return (By) method.invoke(null,param);
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to execute method "+method,e);
		}
	}
	
	private  Method findMethod(String methodName) {
		try {
			Class<By> cls = By.class;
			Method method = cls.getMethod(methodName, String.class);
			return method;
		} catch (Exception e) {
			throw new RuntimeException("Unable to create method By." + methodName,e);
		}
	}

	private By generateLocator(String location) {
		String[] elements = splitElementLocation(location);
		String methodName = elements[0];
		String description = elements[1];
		Method method = findMethod(methodName);
		By locator = executeMethod(method,description);
		return locator;
	}
	

	public By getLocator(String location) {
		if (location.contains("=")) {
			return generateLocator(location);
		} else
			return By.id(location);
	}
	
	public String[] splitElementLocation(String elementLocation) {
		if (elementLocation.contains("=")) {
			int elementLocationLength = elementLocation.length();
			for (String method: METHODS) {
				if (elementLocation.startsWith(method)) {
					int methodLength = method.length()+1;
					String value = elementLocation.substring(methodLength, elementLocationLength);
					return new String[] {method,value};					
				}
			}
			throw new RuntimeException(
					"location " + elementLocation + " is expected to have format <method>=<locaton description>");
		}
		else {
			String seleniumByMethod = "id";			
			return new String[] {seleniumByMethod,elementLocation};
		}
	}
	
}


