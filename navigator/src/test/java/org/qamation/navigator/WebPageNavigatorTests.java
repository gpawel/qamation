package org.qamation.navigator;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.qamation.web.page.Page;
import org.qamation.web.page.WebPageFactory;
import org.qamation.webdriver.utils.LocatorFactory;
import org.qamation.webdriver.utils.WebDriverFactory;

public class WebPageNavigatorTests {
    WebPageNavigator navigator;
    WebDriver driver;
    Page page;

    @Before
    public void setUp() {
        driver = WebDriverFactory.createChromeWebDriver();
        navigator = new WebPageNavigator(driver);
        page = WebPageFactory.createGeneralPageInstance(driver);
        page.openPage("http://yahoo.com");
        page.isReady();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void openPage() {
        navigator.processNavigationSequience(new String[]{"moon","{ENTER}"},page);
        assertTrue(driver.getPageSource().contains("<b>Moon </b>- Image Results"));
    }

    //@Test
    public void processNavigationString() {
        String navigation = "moon <@!{}>";
        By by = LocatorFactory.getLocator("uh-search-button");
        WebElement el= driver.findElement(by);

        //NavigationString navigationString = new NavigationString()
    }



}
