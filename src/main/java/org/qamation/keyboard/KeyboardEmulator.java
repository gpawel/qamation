package org.qamation.keyboard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.qamation.webdriver.utils.LocatorFactory;
import org.qamation.utils.StringUtils;
import org.qamation.webdriver.utils.WebDriverUtils;
import org.openqa.selenium.remote.RemoteWebDriver;

public class KeyboardEmulator implements Keyboard {

	private static final long KEY_PRESS_PAUSE_MIL_SEC = 20;
	private WebDriver driver;
	private org.openqa.selenium.interactions.Keyboard keyboard;
	private List<Field> keyboardKeys;
	
	public KeyboardEmulator(WebDriver driver) {
		this.driver = driver;
		this.keyboard = getKeyboardFromWebDriver();
		populateKeyboardKeys();
	}

	private void populateKeyboardKeys() {
		keyboardKeys = new ArrayList<Field>();
		Field[] declairedFields = Keys.class.getDeclaredFields();
		for (Field field : declairedFields) {
			if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				keyboardKeys.add(field);
			}
		}
	}

	public void pressKey(CharSequence key) {
		keyboard.pressKey(key);
	}

	public void releaseKey(CharSequence key) {
		keyboard.releaseKey(key);
	}

	public void sendKeys(CharSequence... keys) {
		String[] sequence = StringUtils.convertCharSequenceToArray(keys);
		for (int i=0; i < sequence.length; i++) {
			keyboard.sendKeys(sequence[i]);
			pause(KEY_PRESS_PAUSE_MIL_SEC);
		}
	}
	
	public void sendKeysAt(String location, CharSequence... keys) {
		By locator = createLocator(location);
		WebElement el = findElement(locator);		
		typeKeysAt(el,keys);
	}

	private void typeKeysAt(WebElement el, CharSequence[] keys) {
		String[] sequence = StringUtils.convertCharSequenceToArray(keys);
		for (int i=0; i < sequence.length; i++) {
			el.sendKeys(sequence[i]);
			pause(KEY_PRESS_PAUSE_MIL_SEC);
		}
	}

	private By createLocator(String location) {
		LocatorFactory lu = new LocatorFactory();
		By locator = lu.getLocator(location);
		return locator;
	}
	
	private WebElement findElement(By locator) {
		WebDriverUtils utils = new WebDriverUtils(driver);
		WebElement element = utils.getWebElementIfPresents(locator);
		return element;
	}
	
	private void pause(long pauseLength) {
		try {
			Thread.sleep(pauseLength);
		} catch (Exception e) {
			throw new RuntimeException("Unable to pause because:\n", e);
		}
	}

	public void sendSpecialKeys(String keys) {
		String[] sequence = keys.split(" ");
		for (int i = 0; i < sequence.length; i++) {
			String s = sequence[i];
			//if (processKeyCombination(s)) continue;
			if (processKeyChord(s)) continue;
			Keys key = convertToKeys(s);
			Actions action = new Actions(driver);
			action.sendKeys(key);			
			action.build().perform();
			//pause(KEY_PRESS_PAUSE_MIL_SEC);
		}
	}

	/*
	private boolean processKeyCombination(String s) {
		if (s.contains("+")) {
			String[] substrings = s.split("\\+");
			if (substrings.length < 2) return false; // like SHIFT+
			Actions action = new Actions(driver);
			action = pressModifiers(substrings, action);
			action = sendSpecialKey(substrings,action);
			action.build().perform();
			action = releaseModifiers(substrings,action);
			action.build().perform();
			return true;
		}
		return false;
	}
*/
	private boolean processKeyChord(String s) {
		if (s.contains("+")) {
			String[] substrings = s.split("\\+");
			if (substrings.length < 2) return false; // like SHIFT+
			//String keysToSend=Keys.chord(convertToKeys(substrings[0]),convertToCharSeq(substrings[1]));
			driver.switchTo().activeElement();
			Actions action = new Actions(driver);
			Keys modifier = convertToKeys(substrings[0]);
			Keys key = convertToKeys(substrings[1]);
			//action.sendKeys(Keys.chord(convertToKeys(substrings[0]),convertToCharSeq(substrings[1])));
			//action.build();
			keyboard.pressKey(modifier);
			keyboard.sendKeys(key);
			keyboard.releaseKey(modifier);
			//action.keyDown(modifier).sendKeys(key).keyUp(modifier).perform();
			return true;
		}
		return false;
	}

	private Actions sendSpecialKey(String[] substrings, Actions action) {
		int lastElement = substrings.length-1;
		CharSequence chars = convertToCharSeq(substrings[lastElement]);
		action.sendKeys(chars);
		return action;
	}

	private Actions releaseModifiers(String[] substrings, Actions action) {
		for (int i=substrings.length-2; i>=0; i--) {
			Keys chars = convertToKeys(substrings[i]);
			action.keyUp(chars);			
		}
		return action;
	}

	private Actions pressModifiers(String[] substrings, Actions action) {
		for (int i=0; i < substrings.length-1; i++) {
			Keys chars = convertToKeys(substrings[i]);			
			action.keyDown(chars);
			//keyboard.pressKey(chars);
		}
		return action;
	}

	private CharSequence convertToCharSeq(String string) {
		try {
			for (Field field : keyboardKeys) {
				if (field.getName().equalsIgnoreCase(string)) {
					return (CharSequence) field.get(null);
				}
			}
			return Keys.NULL;
		} catch (Exception e) {
			throw new RuntimeException("Unable convert input string into a Key.", e);
		}
	}

	private Keys convertToKeys(String string) {
		try {
			for (Field field : keyboardKeys) {
				if (field.getName().equalsIgnoreCase(string)) {
					return (Keys) field.get(null);
				}
			}
			return Keys.NULL;
		} catch (Exception e) {
			throw new RuntimeException("Unable convert input string into a Key.", e);
		}
	}

	public org.openqa.selenium.interactions.Keyboard getKeyboardFromWebDriver() {
		if (driver instanceof ChromeDriver) {
			return ((ChromeDriver)driver).getKeyboard();
		}
		else if (driver instanceof InternetExplorerDriver) {
			return ((InternetExplorerDriver)driver).getKeyboard();
		}
		else if (driver instanceof FirefoxDriver) {
			return ((FirefoxDriver)driver).getKeyboard();
		}
		else if (driver instanceof RemoteWebDriver) {
			return ((RemoteWebDriver)driver).getKeyboard();
		}
		else {
			return new org.openqa.selenium.interactions.Keyboard() {
				@Override
				public void sendKeys(CharSequence... charSequences) {
					Actions actions = new Actions(driver);
					actions.sendKeys(charSequences).perform();
				}

				@Override
				public void pressKey(CharSequence charSequence) {
					Keys k = convertToKeys(charSequence.toString());
					Actions actions = new Actions(driver);
					actions.keyDown(k).perform();
				}

				@Override
				public void releaseKey(CharSequence charSequence) {
					Keys k = convertToKeys(charSequence.toString());
					Actions actions = new Actions(driver);
					actions.keyUp(k).perform();
				}
			};
		}
	}
}
