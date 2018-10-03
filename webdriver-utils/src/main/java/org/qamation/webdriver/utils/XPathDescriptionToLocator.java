package org.qamation.webdriver.utils;

import org.openqa.selenium.By;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;


public class XPathDescriptionToLocator {
    private static final String WHITE_SPECE_NOT_IN_QUOTE = "\\s+(?=([^']*'[^']*')*[^']*$)";

    public By getLocator(String xpathDescription) {
        String xpath = getLocatorAsString(xpathDescription);
        return LocatorFactory.getLocator(xpath);
    }

    public String getLocatorAsString(String xpathDescription) {
        StringBuilder sb = new StringBuilder(xpathDescription);
        Map<String,Supplier<BiFunction>> tags = XPathDescriptionTags.getXpathTags();
        for (String key: tags.keySet()) {
            BiFunction<StringBuilder,String,StringBuilder> f = tags.get(key).get();
            sb = f.apply(sb,xpathDescription);
        }
        String res = sb.toString();
        res = res.replaceAll(WHITE_SPECE_NOT_IN_QUOTE,"");
        return res;
    }


}
