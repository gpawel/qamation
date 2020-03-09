package org.qamation.webdriver.utils;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.qamation.navigator.WebPageNavigator;
import org.qamation.utils.ResourceUtils;
import org.qamation.web.page.GeneralPage;

import java.net.URL;

public class ChromeDriverFactory {

    public static ChromeDriver createChromeDriver(String pathToExec) {
        System.setProperty("webdriver.chrome.driver",pathToExec);
        ChromeOptions chromeOptions = getChromeOptions();
        return new ChromeDriver(chromeOptions);
    }

    public static ChromeDriver createChromeDriver(String pathToExec, ChromeOptions chromeOptions) {
        System.setProperty("webdriver.chrome.driver",pathToExec);
        return new ChromeDriver(chromeOptions);
    }

    public static ChromeDriver createChromeDriver() {
        String pathToExec = getChromeDriverPath();
        return createChromeDriver(pathToExec);
    }

    public static WebDriver createRemoteChromeDriver(URL hub, ChromeOptions chromeOptions) {
        RemoteWebDriver driver = WebDriverFactory.createRemoteWebdriver(hub, new DesiredCapabilities(chromeOptions));
        return driver;
    }

    public static WebDriver createRemoteChromeDriver(URL hub) {
        ChromeOptions chromeOptions = getChromeOptions();
        RemoteWebDriver driver = WebDriverFactory.createRemoteWebdriver(hub, new DesiredCapabilities(chromeOptions));
        return driver;
    }

    public static String getChromeDriverPath() {
        String defValue = System.getProperty("user.dir")+"/Selenium/ChromeDriver/chromedriver";
        return ResourceUtils.getSystemProperty("webdriver.chrome.driver",defValue);
    }

    public static ChromeOptions getChromeOptions() {
        MutableCapabilities mc = WebDriverFactory.getDefaultMutualCapabilities();
        return getChromeOptionsFrom(mc);
    }

    private static ChromeOptions getChromeOptionsFrom(MutableCapabilities mc) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(mc);
        chromeOptions = chromeOptions.addArguments("--disable-extensions");
        return chromeOptions;
    }

    private static DesiredCapabilities setChromeCapabilities() {
        DesiredCapabilities dc = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options = options.addArguments("--disable-extensions");
        dc.setCapability(ChromeOptions.CAPABILITY, options);
        return dc;
    }


}
