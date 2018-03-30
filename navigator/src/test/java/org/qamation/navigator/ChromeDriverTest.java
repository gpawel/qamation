package org.qamation.navigator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class ChromeDriverTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public void createChromeDriverTest() {
        ChromeOptions options = new ChromeOptions();
        String path = "C:\\Selenium\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        //options.setBinary(new File("C:/TEST_ENV/Selenium/ChromeDriver/chromedriver.exe"));
        WebDriver driver = new ChromeDriver(options);
        driver.get("http://yahoo.com");
    }

    public static void main(String[] args) {
    }

    ChromeDriverTest() {
        WebDriver Driver = new ChromeDriver();
        createChromeDriverTest();
    }
}
