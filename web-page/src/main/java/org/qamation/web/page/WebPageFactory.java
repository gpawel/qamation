package org.qamation.web.page;

import org.qamation.webdriver.utils.BasedWebDriverInstanceFactory;
import org.openqa.selenium.WebDriver;


public class WebPageFactory extends BasedWebDriverInstanceFactory {
	public static  <A extends Page> A createPageInstance  (String pageImplementationClass, WebDriver driver) {
		A page = (A)createInstance(pageImplementationClass, driver);
		return page;
	}

	public static Page createGeneralPageInstance(WebDriver driver) {
		return (Page)createInstance("org.qamation.web.page.GeneralPage", driver);
	}
}
