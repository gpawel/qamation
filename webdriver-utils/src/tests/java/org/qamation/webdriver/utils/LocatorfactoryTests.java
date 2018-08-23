package org.qamation.webdriver.utils;

import org.junit.Assert;
import org.junit.Test;

public class LocatorfactoryTests {
    @Test
    public void testXPathLocatorFactory() {
        String x = "xpath=\"//*[@id='search-form']/fieldset/button\"";
        String[] all = LocatorFactory.splitElementLocation(x);
        Assert.assertEquals("xpath",all[0]);
        Assert.assertEquals("//*[@id='search-form']/fieldset/button",all[1]);
    }
    @Test
    public void testXPathLocatorFactory1() {
        String x = "xpath=//*[@id='search-form']/fieldset/button";
        String[] all = LocatorFactory.splitElementLocation(x);
        Assert.assertEquals("xpath",all[0]);
        Assert.assertEquals("//*[@id='search-form']/fieldset/button",all[1]);
    }
}
