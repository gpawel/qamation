import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.qamation.mouse.MouseActionSupplier;
import org.qamation.web.page.Page;
import org.qamation.web.page.WebPageFactory;
import org.qamation.webdriver.utils.LocatorFactory;
import org.qamation.webdriver.utils.WebDriverFactory;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MouseNavigationTests {

    WebDriver driver;
    String chromeDriverPath = "../../Selenium/Chrome/chromedriver";
    Page page;
    @Before
    public void setUp() {
        driver = WebDriverFactory.createChromeWebDriver(chromeDriverPath);
        page = WebPageFactory.createGeneralPageInstance(driver);
        page.openPage("http://yahoo.com");
        page.isReady();
    }

    @After
    public void tearDown() {
        //driver.close();
    }

    @Test
    public void testClick() {
        MouseActionSupplier mouseActions = new MouseActionSupplier(driver);
        Action action = mouseActions.getAction("!");
        action.perform();
        action = mouseActions.getAction("!!");
        action.perform();
    }
    @Test
    public void testClickByDriver() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(5,5).build().perform();
        actions.click().build().perform();
    }
    //'Game of Thrones' star is World's Strongest Man
    @Test
    public void testClickByElement() {
        String xpath = "xpath=//h2[contains(text(),'The most horrendous')]";
        WebElement el = driver.findElement(LocatorFactory.getLocator(xpath));
        el.click();
    }

}
