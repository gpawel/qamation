package org.qamation.webdriver.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.qamation.webdriver.utils.WebDriverFactory;


public class ChromeDriverTest {
    WebDriver driver = null;
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        if (driver == null) return;
        driver.quit();
    }

    //@Test
    public void createChromeDriverTest() {
        ChromeOptions options = new ChromeOptions();
        String path = "C:\\Selenium\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        //options.setBinary(new File("C:/TEST_ENV/Selenium/ChromeDriver/chromedriver.exe"));
        driver = new ChromeDriver(options);
        driver.get("http://yahoo.com");
    }

    @Test
    public void createChromeDriverUsingDefaultPath() {
        driver = WebDriverFactory.createChromeWebDriver();
        driver.get("http://yahoo.com");
    }


}
