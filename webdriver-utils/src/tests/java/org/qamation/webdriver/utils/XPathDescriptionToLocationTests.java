package org.qamation.webdriver.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.qamation.webdriver.utils.xpath.Translator;

public class XPathDescriptionToLocationTests {
    @Before
    public void setUp() {}

    @Test
    public void convertDescriptionToXpath_1() {
        String description =
            "aNy element with value 'Sign In'";
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
                "element 'li' with value ', an International Baccalaureate school in Doha, Qatar' and child 'a' with value 'Qatar Academy'";
        String expected =
                "//li[text()=', an International Baccalaureate school in Doha, Qatar']/a[text()='Qatar Academy']";
        convertAndAssert(description,expected);
    }

    @Test
    public void testElementContains() {
        //https://connect.ups.com/Tracking/GeekSquad/Default.aspx
        String description =
                "element span contains 'TRACK'";
        String expected = "//span[contains(text(),'TRACK')]";
        convertAndAssert(description,expected);
    }

    @Test
    public void testAttributeContains() {
        //https://connect.ups.com/Tracking/GeekSquad/Default.aspx
        String description =
                "element span with attribute id which contains 'TRACK'";
        String expected = "//span[contains(@id,'TRACK')]";
        convertAndAssert(description,expected);
    }

    @Test
    public void testChildAndIndex() {
        //*[@id='nav-link-yourAccount']/span[1]
        String description =
                "any element with attribute 'id' with value 'nav-link-yourAccount' and child 'span' at position 1";
        String expected = "//*[@id='nav-link-yourAccount']/span[1]";
        convertAndAssert(description,expected);
    }

    @Test
    public void testTwoChilds() {
        //xpath=//*[@id='atfResults']/li[1]/h2
        String description =
                "any element with attribute id with value 'atfResults' and child 'li' at position 1 and child h2";
        String expected = "//*[@id='atfResults']/li[1]/h2";
        convertAndAssert(description,expected);

    }
    @Test
    public void testTwoDescendant() {
        //xpath=//*[@id='atfResults']//li[1]//h2
        String description =
                "any element with attribute id with value 'atfResults' and descendant 'li' at position 1 and descendant h2";
        String expected = "//*[@id='atfResults']//li[1]//h2";
        convertAndAssert(description,expected);

    }




    private void convertAndAssert(String description, String expected) {
        Translator translator = new Translator();
        String xpathStr = translator.translateDescriptionToXpath(description);
        Assert.assertEquals(expected,xpathStr);
    }


    @After
    public void tearDown() {}

    /*
    NEED TO CREATE TESTS FOR EACH OF THE XPATH BELOW.

    <@!{xpath=//*[contains(text(),'Hello. Sign in')]}>
xpath=//h1[contains(text(),'Sign in')]

xpath=//*[@id='nav-link-yourAccount']/span[1]
<@!{xpath=//*[@id='twotabsearchtextbox']}>|hair {SPACE} brash
didYouMean


<@!{xpath=//*[@id='atfResults']//li[1]//h2}>
productTitle
<@!{add-to-cart-button}>
xpath=//*[@id='huc-v2-order-row-confirm-text']/h1

xpath=//*[@id='huc-v2-order-row-confirm-text']/h1
<@~{nav-link-yourAccount}>

<@!{nav-item-signout-sa}>
xpath=//h1[contains(text(),'Sign in')]
<@!{xpath=//*[contains(text(),'Hello. Sign in')]}>
xpath=//h1[contains(text(),'Sign in')]
gpawel17@mail.com|1qazxsw2!
xpath=//*[@id='nav-link-yourAccount']/span[1]
<@!{nav-cart}>
sc-active-cart

xpath=//*[@id='activeCartViewForm']//li[1]
<@!{xpath=//input[@value='Delete']}>
xpath=//*[@id='sc-active-cart']//h1
<@~{nav-link-yourAccount}>

<@!{nav-item-signout-sa}>
xpath=//h1[contains(text(),'Sign in')]

     */



}
