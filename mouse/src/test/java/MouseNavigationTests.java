import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.qamation.web.page.Page;
import org.qamation.web.page.WebPageFactory;
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
        driver.close();
    }

    @Test
    public void openMailDotComUrlTes() {
        System.out.println("User dir: "+System.getProperty("user.dir"));
    }
}
