package org.qamation.web.page;

import org.qamation.webdriver.utils.WebDriverUtils;

public class AbstractPage<A extends WebDriverUtils> implements Page {
	protected  A utils;

	public boolean isReady() {
		boolean result = utils.isPageReady();
		return result;
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

}
