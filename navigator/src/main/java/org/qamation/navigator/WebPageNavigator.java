package org.qamation.navigator;

import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.interactions.Action;
import org.qamation.keyboard.KeyboardEmulator;
import org.qamation.mouse.MouseActionPatterns;
import org.qamation.mouse.MouseEmulator;
import org.qamation.web.page.IsReady;
import org.qamation.utils.StringUtils;

import org.openqa.selenium.WebDriver;

public class WebPageNavigator {
	private final String SUBTOKENS_DELIMETER = " ";	
	private final String SPECIAL_KEY_ENDS_WITH = "}";
	private static final HashMap<String,String> SPECIAL_KEY_STARTS_MAP;
	static {
		SPECIAL_KEY_STARTS_MAP = new HashMap<String,String>();
		SPECIAL_KEY_STARTS_MAP.put("{","{");
		SPECIAL_KEY_STARTS_MAP.put("+{","{SHIFT+");
		SPECIAL_KEY_STARTS_MAP.put("^{","{CONTROL+");
		SPECIAL_KEY_STARTS_MAP.put("%{","{ALT+");
	}


	private KeyboardEmulator keyboard;
	private WebDriver driver;

	public WebPageNavigator(WebDriver driver) {
		this.driver = driver;
		this.keyboard = new KeyboardEmulator(this.driver);
	}

	public void processNavigationSequience(String[] tokens, IsReady page) {
		for (String t : tokens) {
			String[] subTokens= processToken(t, page);
			String lastSubToken = subTokens[subTokens.length-1];
			pressEnterIfLastSubTokenIsNotSpecialKey(lastSubToken,page);
		}
	}


	private void pressEnterIfLastSubTokenIsNotSpecialKey(String lastSubToken, IsReady page) {
		if (isSpecialKey(lastSubToken)) return;
		if (isMouseAction(lastSubToken)) return;
		else {
			keyboard.sendSpecialKeys("ENTER");
			page.isReady();
		}
	}

	private boolean isMouseAction(String t) {
		return MouseActionPatterns.isMouseAction(t);
	}

	private boolean isSpecialKey(String token) {
		Set<String> specialKeysSet = SPECIAL_KEY_STARTS_MAP.keySet();
		for (String s : specialKeysSet ) {
			if (token.startsWith(s) && token.endsWith(SPECIAL_KEY_ENDS_WITH)) return true;
		}
		return false;
	}
	
	private String[] processToken(String token, IsReady page) {
		String[] subTokens;
		boolean doNotSplit = isMouseAction(token);
		if (doNotSplit) {
			subTokens = new String[] {token};
		}
		else {
			subTokens = splitTokenBy(SUBTOKENS_DELIMETER, token);
		}
		processSubTokens(subTokens, page);
		return subTokens;
	}

	private String[] splitTokenBy(String delimeter, String token) {
		return token.split(delimeter);
	}
	
	private void processSubTokens(String[] subTokens, IsReady page) {
		for (String st : subTokens) {
			if (isMouseAction(st)) {
				processMouseAction(st,page);
				continue;
			}
			else if (isSpecialKey(st)) {
				processSubTokenAsSpecialKey(st, page);
				continue;
			}
			keyboard.sendKeys(StringUtils.convertCharSequenceToArray(st));
		}
	}

	private void processMouseAction(String st, IsReady page) {
		MouseEmulator mouseEmulator = new MouseEmulator(driver);
		Action action = mouseEmulator.getAction(st);
		action.perform();
		page.isReady();
	}

	private void processSubTokenAsSpecialKey(String specialKey, IsReady page) {
		String convertedSpecialKey = rewriteSpecialKey(specialKey);
		String s = StringUtils.extractContentFromCurlyBruckets(convertedSpecialKey);
		keyboard.sendSpecialKeys(s);
		if (specialKey.equalsIgnoreCase("{TAB}")
				|| specialKey.equalsIgnoreCase("{DELETE}")
				) return;
		page.isReady();
	}

	private String rewriteSpecialKey(String specialKey) {
		Set<String> specialKeysSet = SPECIAL_KEY_STARTS_MAP.keySet();
		for (String s : specialKeysSet) {
			if (specialKey.startsWith(s)) return specialKey.replace(s, SPECIAL_KEY_STARTS_MAP.get(s));
		}
		throw new RuntimeException(specialKey+" is not supported");
	}
	
}