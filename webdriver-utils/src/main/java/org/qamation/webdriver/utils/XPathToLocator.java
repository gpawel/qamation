package org.qamation.webdriver.utils;



import org.openqa.selenium.By;

public class XPathToLocator {
    public By getLocator(String xpathDescription) {
        if (xpathDescription.length()==1) return LocatorFactory.getLocator(xpathDescription);
        String xpath = convertDescriptInToXpath(xpathDescription);
        return LocatorFactory.getLocator(xpath);
    }

    private String convertDescriptInToXpath(String xpathDescription) {
        return null;
    }
}
