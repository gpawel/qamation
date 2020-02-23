package org.qamation.webdriver.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverFactory {

    public static WebDriver createWebDriver(String path) {
        System.setProperty("webdriver.chrome.driver",path);
        return null;

    }




}
