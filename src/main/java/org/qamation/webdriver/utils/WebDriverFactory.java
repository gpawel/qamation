package org.qamation.webdriver.utils;

import java.net.URL;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.qamation.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {

    public static Logger log = LoggerFactory.getLogger(WebDriverFactory.class);

	public static WebDriver createRemoteIEDriver(URL hub) {
		DesiredCapabilities dc =  setIECapabilities();
		RemoteWebDriver driver = createRemoteWebdriver(hub, dc);
		return driver;
	}

	public static WebDriver createRemoteChromeDriver(URL hub) {
		return ChromeDriverFactory.createRemoteChromeDriver(hub);
	}
	public static WebDriver craeatRemoteChromeDriver(URL hub, ChromeOptions chromeOptions) {
		return ChromeDriverFactory.createRemoteChromeDriver(hub,chromeOptions);
	}

	public static WebDriver createChromeWebDriver(String path) {
		WebDriver driver = ChromeDriverFactory.createChromeDriver(path);
		return driver;
	}

	public static WebDriver createChromeWebDriver(String path, ChromeOptions chromeOptions) {
		WebDriver driver = ChromeDriverFactory.createChromeDriver(path, chromeOptions);
		return driver;
	}

	public static WebDriver createChromeWebDriver() {
		return ChromeDriverFactory.createChromeDriver();
	}

	public static MutableCapabilities getDefaultMutualCapabilities() {
		MutableCapabilities mc = new MutableCapabilities();
		mc.setCapability(org.openqa.selenium.remote.CapabilityType.ACCEPT_SSL_CERTS, true);
		mc.setCapability(org.openqa.selenium.remote.CapabilityType.SUPPORTS_JAVASCRIPT,true);
		mc.setCapability(org.openqa.selenium.remote.CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
		return mc;
	}

	private static void addDefaultCapabilitiesTo(DesiredCapabilities dc) {
		dc.merge(getDefaultMutualCapabilities());
	}

	private static String getOSName() {
		return System.getProperty("os.name");
	}

	public static WebDriver createIEWebDriver(String path) {
		System.setProperty("webdriver.ie.driver",path);
		DesiredCapabilities dc = setIECapabilities();
		addDefaultCapabilitiesTo(dc);
		WebDriver driver = new InternetExplorerDriver(dc);
		return driver;
	}

	public static WebDriver createFFWebDriver(String path) {
		System.setProperty("webdriver.firefox.bin",path);
		DesiredCapabilities dc = setFFCapabilities();
		addDefaultCapabilitiesTo(dc);
		WebDriver driver = new FirefoxDriver(dc);
		return driver;
	}

    public static WebDriver createRemoteFFWebDriver(URL hub) {
		DesiredCapabilities dc = setFFCapabilities();
		RemoteWebDriver driver = createRemoteWebdriver(hub,dc);
		return driver;
    }

	public static RemoteWebDriver createRemoteWebdriver(URL hub, DesiredCapabilities capabilities) {
		addDefaultCapabilitiesTo(capabilities);
		RemoteWebDriver driver = 	new RemoteWebDriver(hub, capabilities);
		return driver;
	}

	private static DesiredCapabilities setFFCapabilities() {
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		return dc;
	}

	private static DesiredCapabilities setIECapabilities() {
		DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
		dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
		dc.setCapability(
				org.openqa.selenium.ie.InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				true);

		dc.setCapability(
				org.openqa.selenium.ie.InternetExplorerDriver.IGNORE_ZOOM_SETTING,
				true);
		dc.setCapability(org.openqa.selenium.ie.InternetExplorerDriver.NATIVE_EVENTS, true);
		dc.setCapability(org.openqa.selenium.ie.InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,
				true);
		dc.setCapability(org.openqa.selenium.ie.InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,
				true);

		//dc.setCapability(org.openqa.selenium.ie.InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);

		return dc;
	}












}
