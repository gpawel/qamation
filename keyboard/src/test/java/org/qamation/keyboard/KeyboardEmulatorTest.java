package org.qamation.keyboard;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.qamation.webdriver.utils.WebDriverFactory;

public class KeyboardEmulatorTest {

	private static String KEYBOARD_CLASS = "KeyboardEmulator";
	private WebDriver driver;
	

	
	@Test
	public void createKeyEmulator() {
		driver = WebDriverFactory.createChromeWebDriver("C:/QA-AUTOMATION-ENV/Selenium/ChromeDriver/chromedriver.exe");//createRemoteChromeDriver(hub);
		KeyboardEmulator keyboard = new KeyboardEmulator(driver);
	}
	

	
}
