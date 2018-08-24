import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;
import org.qamation.keyboard.KeyboardEmulator;
import org.qamation.mouse.MouseEmulator;
import org.qamation.web.page.Page;
import org.qamation.web.page.WebPageFactory;
import org.qamation.webdriver.utils.LocatorFactory;
import org.qamation.webdriver.utils.WebDriverFactory;

import java.util.List;

public class MouseEmulatorTests {
    WebDriver driver;
    String chromeDriverPath = "../../Selenium/Chrome/chromedriver";
    Page page;

    By by;//= LocatorFactory.getLocator(xpath);
    @Before
    public void setUp() {
        driver = WebDriverFactory.createChromeWebDriver(chromeDriverPath);
        page = WebPageFactory.createGeneralPageInstance(driver);
        page.openPage("http://wikipedia.com");
        page.isReady();
    }
    @Test
    public void mouseEmulatorTest1() {
        page.openPage("http://wikipedia.com");
        page.isReady();
        MouseEmulator em = new MouseEmulator(driver);
        KeyboardEmulator kem = new KeyboardEmulator(driver);
        kem.sendKeys("qa");
        Action act = em.getAction("<@!{xpath=//*[@id='search-form']/fieldset/button}>");
        act.perform();
        page.isReady();
        String header = page.readTextFrom("firstHeading");
        Assert.assertEquals("QA",header);
    }

    @Test
    public void seachButtonTest() {
        page.openPage("http://wikipedia.com");
        page.isReady();
        String xpath = "//*[@id='search-form']/fieldset/button";
        List<WebElement> list = driver.findElements(By.xpath(xpath));
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(1,list.size());
        list.get(0).click();
    }
}
