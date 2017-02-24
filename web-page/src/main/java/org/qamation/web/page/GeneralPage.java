package org.qamation.web.page;

import org.openqa.selenium.WebDriver;
import org.qamation.webdriver.utils.WebDriverUtils;

public class GeneralPage extends AbstractPage<WebDriverUtils>  {
	public GeneralPage (WebDriver driver) {
		utils = new WebDriverUtils(driver);
	}
}

