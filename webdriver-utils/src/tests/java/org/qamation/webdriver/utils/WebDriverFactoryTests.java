package org.qamation.webdriver.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.qamation.webdriver.utils.WebDriverFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactoryTests {
    WebDriver driver = null;
    @Before
    public void setUp(){}

    @After
    public void tearDown() {
        if (driver == null) return;
        driver.quit();
    }

    @Test
    public void createRemoteIEDriver() {
        //WebDriverFactory factory = new WebDriverFactory();
        try {
            driver = WebDriverFactory.createRemoteIEDriver(new URL("http://localhost:4445/wd/hub"));
            driver.get("http://bing.com");
            Thread.sleep(10000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
