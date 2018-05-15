import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class MouseNavigationTests {

    WebDriver driver;
    String chromeDriverPath = "../../Selenium/Chrome/chromedriver";
    Page page;
    String xpath = "xpath=//*[@id='mega-bottombar']/ul/li[2]/a";
    By by;//= LocatorFactory.getLocator(xpath);
    @Before
    public void setUp() {
        driver = WebDriverFactory.createChromeWebDriver(chromeDriverPath);
        page = WebPageFactory.createGeneralPageInstance(driver);
        page.openPage("http://yahoo.com");
        page.isReady();
        by = LocatorFactory.getLocator(xpath);
    }

    @After
    public void tearDown() {
        //driver.close();
    }

    @Test
    public void testClick() {

        MouseActionSupplier mouseActions = new MouseActionSupplier(driver,768,55);
        Action a = mouseActions.getAction("~");
        a.perform();
        a = mouseActions.getAction("!");
        a.perform();
        page.isReady();
        Assert.assertTrue(driver.getPageSource().contains("Sign in"));

        /*
        By b = LocatorFactory.getLocator("uh-signin");
        WebElement el = driver.findElement(b);
        System.out.println(el.getLocation().x);
        System.out.println(el.getLocation().y);
        */

        /*
        mouseActions = new MouseActionSupplier(driver,el);
        action = mouseActions.getAction("!!");
        action.perform();
        */
        /*
        WebElement el = driver.findElement(by);
        Map<String,Supplier<Action>> map = new HashMap<>();
        map.put("!",()->new Actions(driver).click().build());
        Action act = map.get("!").get();
        act.perform();
        */
    }

    @Test
    public void testMovingActions() {
        String moviesLocation = "xpath=(//a[contains(text(),'Movies')])[2]";
        String newsLocation = "xpath=(//a[contains(text(),'News')])[2]";
        WebElement moviesElement = findElement(moviesLocation);
        Point movies = moviesElement.getLocation();
        Point news = getWebElementLocation(newsLocation);
        int xoffset = movies.getX() - news.getX();
        MouseActionSupplier mouseActions = new MouseActionSupplier(driver,moviesElement,-xoffset,0);
        Action a = mouseActions.getAction("~!");
        a.perform();
        a = mouseActions.getAction("^");
        a.perform();
        a = mouseActions.getAction("~^");
        a.perform();
        mouseActions = new MouseActionSupplier(driver);
        a = mouseActions.getAction("!");
        a.perform();
        page.isReady();


    }

    private Point getWebElementLocation(String location) {
        WebElement el = findElement(location);
        return el.getLocation();
    }

    private WebElement findElement(String location) {
        By by = LocatorFactory.getLocator(location);
        return driver.findElement(by);
    }

}
