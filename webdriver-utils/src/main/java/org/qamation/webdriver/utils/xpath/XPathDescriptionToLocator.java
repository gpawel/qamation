package org.qamation.webdriver.utils.xpath;

import org.openqa.selenium.By;
import org.qamation.webdriver.utils.LocatorFactory;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;


public class XPathDescriptionToLocator {
    private static final String WHITE_SPECE_NOT_IN_QUOTE = "\\s+(?=([^']*'[^']*')*[^']*$)";

    public By getLocator(String xpathDescription) {
        if (xpathDescription.startsWith("xpath="))return LocatorFactory.getLocator(xpathDescription);
        else {
            String xpath = getLocatorAsString(xpathDescription);
            return LocatorFactory.getLocator(xpath);
        }
    }

    public String getLocatorAsString(String xpathDescription) {
        DescriptionParser parser = new DescriptionParser(XPathKeyWords.getXpathTags());
        String xpath = parser.processDescription(xpathDescription);
        return xpath;
        /*
        StringBuilder sb = new StringBuilder(xpathDescription);
        Map<String,Supplier<BiFunction>> tags = XPathKeyWords.getXpathTags();
        for (String key: tags.keySet()) {
            BiFunction<StringBuilder,String,StringBuilder> f = tags.get(key).get();
            sb = f.apply(sb,xpathDescription);
        }
        String res = sb.toString();
        res = res.replaceAll(WHITE_SPECE_NOT_IN_QUOTE,"");
        return res;
        */
    }


}
