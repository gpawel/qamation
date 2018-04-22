package org.qamation.navigator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.qamation.web.page.Page;
import org.qamation.web.page.WebPageFactory;
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
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void openPage() {
        page.openPage("http://yahoo.com");
        page.isReady();
        navigator.processNavigationSequience(new String[]{"moon","{ENTER}"},page);
    }


}
