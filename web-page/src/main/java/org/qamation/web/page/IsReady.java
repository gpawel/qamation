package org.qamation.web.page;

import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;

public interface IsReady {
	public boolean isReady();
	public <T> T isReady(Function<WebDriver,T> condition);
}
