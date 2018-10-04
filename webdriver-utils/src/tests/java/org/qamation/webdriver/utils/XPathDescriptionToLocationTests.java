package org.qamation.webdriver.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XPathDescriptionToLocationTests {
    @Before
    public void setUp() {}

    @Test
    public void convertDescriptionToXpath_1() {
        String description =
            "any element with value 'Sign In'";
        String expected =
                "//*[text()='Sign In']";
        XPathDescriptionToLocator converter = new XPathDescriptionToLocator();
        String xpathStr = converter.getLocatorAsString(description);
        Assert.assertEquals(expected,xpathStr);
    }

    @Test
    public void convertDescriptionToXpathIgnoringCase() {
        String description =
                "ANY eleMEnt wiTh vaLue 'Sign In'";
        String expected =
                "//*[text()='Sign In']";
        XPathDescriptionToLocator converter = new XPathDescriptionToLocator();
        String xpathStr = converter.getLocatorAsString(description);
        Assert.assertEquals(expected,xpathStr);
    }

    @After
    public void tearDown() {}



}
