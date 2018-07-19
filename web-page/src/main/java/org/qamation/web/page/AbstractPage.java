package org.qamation.web.page;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.qamation.webdriver.utils.WebDriverUtils;

public class AbstractPage<A extends WebDriverUtils> implements Page {
	protected  A utils;
	protected long readyTime;

	public boolean isReady() {
		return utils.isPageReady();
	}

	public <T> T isReady(ExpectedCondition <T> condition) {
		return utils.isPageReady(condition);
	}

	public void openPage(String url) {
	    utils.openPage(url);
	}

	public void refresh() {
		utils.refresh();
	}

	@Override
	public void goBack(String url) {
		utils.goBack(url);
	}

	public String readTextFrom(String location) {
		return utils.readFromLocation(location);
	}
	
	public String readTextFrom(String location, int length) {
		return utils.readFromLocation(location,length);
	}

	public String getSource() {		
		return utils.getPageSource();
	}

	@Override
	public long getPageReadyTime() {
		return utils.getPageReadyTime();
	}

}
