package org.qamation.webdriver.utils;

import org.openqa.selenium.By;
import org.qamation.utils.tokenizer.TokenListener;
import java.util.ArrayList;
import java.util.List;


public class XPathToLocator implements TokenListener {
    private List<String> vacabulary = new ArrayList<String>();

    public void addVacabulary(List<String> words) {
        vacabulary.addAll(words);
    }

    public By getLocator(String xpathDescription) {
        if (xpathDescription.length()==1) return LocatorFactory.getLocator(xpathDescription);
        String xpath = convertDescriptInToXpath(xpathDescription);
        return LocatorFactory.getLocator(xpath);
    }





    private String convertDescriptInToXpath(String xpathDescription) {
       return "";
    }


    @Override
    public void gotStringValue(String val) {

    }

    @Override
    public void gotNumberValue(double d) {

    }

    @Override
    public void eolReached() {

    }

    @Override
    public void eofReached() {

    }
}
