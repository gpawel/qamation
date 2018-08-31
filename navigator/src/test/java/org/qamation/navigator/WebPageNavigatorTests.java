package org.qamation.navigator;

import org.junit.After;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.qamation.web.page.Page;
import org.qamation.web.page.WebPageFactory;
import org.qamation.webdriver.utils.LocatorFactory;
import org.qamation.webdriver.utils.WebDriverFactory;

import java.util.List;

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

    /*
    @After
    public void tearDown() {
        driver.quit();
    }
*/

    @Test
    public void openYahooPage() {
        page.openPage("http://yahoo.com");
        page.isReady();
        navigator.processNavigationSequience(new String[]{"moon","{ENTER}"},page);
        assertTrue(driver.getPageSource().contains("<b>Moon </b>- Image Results"));
    }

    @Test
    public void processNavigationStringWikipedia() {
        page.openPage("http://wikipedia.com");
        page.isReady();
        String[] navigation = new String[] {"qa <@!{xpath=//*[@id='search-form']/fieldset/button}>"};
        WebPageNavigator navigator = new WebPageNavigator(driver);
        navigator.processNavigationSequience(navigation,page);
        By by = LocatorFactory.getLocator("firstHeading");
        List<WebElement> els = driver.findElements(by);
        Assert.assertNotNull(els);
        Assert.assertTrue(els.size()==1);
        Assert.assertEquals("QA",els.get(0).getText());
        //NavigationString navigationString = new NavigationString()
    }

    @Test
    public void processNavigationStringAmazon() {
        page.openPage("http://amazon.ca");
        page.isReady();
        String[] navigation = new String[] {"<@!!{xpath=//*[contains(text(),'Hello. Sign in')]}>"};
        WebPageNavigator navigator = new WebPageNavigator(driver);
        navigator.processNavigationSequience(navigation,page);
    }




}
