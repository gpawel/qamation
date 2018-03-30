package org.qamation.navigator;

import java.util.HashMap;
import java.util.Set;
import org.qamation.keyboard.KeyboardEmulator;
import org.qamation.web.page.Page;
import org.qamation.utils.StringUtils;
import org.qamation.web.page.WebPageFactory;
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
	
	private Page page;
	private KeyboardEmulator keyboard;
	private WebDriver driver;
	
	public WebPageNavigator(WebDriver driver, String pageImplClass) {
		this.driver = driver;
		this.page = WebPageFactory.createPageInstance(pageImplClass, this.driver);
		this.keyboard = new KeyboardEmulator(this.driver);
	}

	public WebPageNavigator(WebDriver driver, Page page) {
		this.driver = driver;
		this.page = page;
		this.keyboard = new KeyboardEmulator(this.driver);
	}
	
	public void processNavigationSequience(String[] tokens) {
		for (String t : tokens) {
			String[] subTokens= processToken(t);
			String lastSubToken = subTokens[subTokens.length-1];
			pressEnterIfLastSubTokenIsNotSpecialKey(lastSubToken);
		}
	}
	
	private void pressEnterIfLastSubTokenIsNotSpecialKey(String lastSubToken) {
		if (isSpecialKey(lastSubToken)) return;
		else {
			keyboard.sendSpecialKeys("ENTER");
			page.isReady();
		}
	}
	
	private boolean isSpecialKey(String token) {
		Set<String> specialKeysSet = SPECIAL_KEY_STARTS_MAP.keySet();
		for (String s : specialKeysSet ) {
			if (token.startsWith(s) && token.endsWith(SPECIAL_KEY_ENDS_WITH)) return true;
		}
		return false;
	}
	
	private String[] processToken(String token) {
		String[] subTokens = splitTokenBy(SUBTOKENS_DELIMETER, token);
		processSubTokens(subTokens);
		return subTokens;
	}

	private String[] splitTokenBy(String delimeter, String token) {
		String[] subTokens = token.split(delimeter);
		return subTokens;
	}
	
	private void processSubTokens(String[] subTokens) {
		for (String st : subTokens) {
			if (isSpecialKey(st)) {
				processSubTokenAsSpecialKey(st);
				continue;
			}
			keyboard.sendKeys(StringUtils.convertCharSequenceToArray(st));
		}
	}
	
	private void processSubTokenAsSpecialKey(String specialKey) {
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