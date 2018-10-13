package org.qamation.webdriver.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.qamation.webdriver.utils.xpath.XPathDescriptionToLocator;

public class XPathDescriptionToLocationTests {
    @Before
    public void setUp() {}

    @Test
    public void convertDescriptionToXpath_1() {
        String description =
            "any element with value 'Sign In'";
        String expected =
                "//*[text()='Sign In']";
        convertAndAssert(description,expected);
    }

    @Test
    public void convertDescriptionToXpathIgnoringCase() {
        String description =
                "ANY eleMEnt wiTh vaLue 'Sign In'";
        String expected =
                "//*[text()='Sign In']";
        convertAndAssert(description,expected);
    }


    // //li[text()=', an International Baccalaureate school in Doha, Qatar']/a[text()='Qatar Academy']
    @Test
    public void descriptionWithTwoElementsAndValues() {
        // Wikipedia; search QA
        String description =
                "element li with value ', an International Baccalaureate school in Doha, Qatar' and child with value 'Qatar Academy'";
        String expected =
                "//li[text()=', an International Baccalaureate school in Doha, Qatar']/a[text()='Qatar Academy']";
        convertAndAssert(description,expected);
    }

    // //div[text()[normalize-space()='Wikipedia']]


    private void convertAndAssert(String description, String expected) {
        XPathDescriptionToLocator converter = new XPathDescriptionToLocator();
        String xpathStr = converter.getLocatorAsString(description);
        Assert.assertEquals(expected,xpathStr);
    }


    @After
    public void tearDown() {}



}
