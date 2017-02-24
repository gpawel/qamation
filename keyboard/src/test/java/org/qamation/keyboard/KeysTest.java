package org.qamation.keyboard;

import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.qamation.webdriver.utils.WebDriverFactory;

public class KeysTest {

	private final String HOST = "HTTP://GOOGLE.COM";
	
	@Test
	public void typeKey() {
		WebDriver driver = WebDriverFactory.createChromeWebDriver("C:/QA-AUTOMATION-ENV/Selenium/ChromeDriver/chromedriver.exe");
		driver.get(HOST);
		KeyboardEmulator emulator = new KeyboardEmulator(driver);
		Keyboard keyboard = emulator.getKeyboardFromWebDriver();
		String k = "F1";
		keyboard.sendKeys(k);
		try {Thread.sleep(5000);} catch (Exception e) {}
		keyboard.sendKeys(Keys.F1);
		try {Thread.sleep(5000);} catch (Exception e) {}
	}
	

	
}
