package org.qamation.webdriver.utils.xpath;

import org.openqa.selenium.By;
import org.qamation.webdriver.utils.LocatorFactory;

import java.util.Map;
import java.util.function.Function;


public class Translator {
    private static final String WHITE_SPECE_NOT_IN_QUOTE = "\\s+(?=([^']*'[^']*')*[^']*$)";

    public By getLocator(String xpathDescription) {
        if (xpathDescription.startsWith("xpath="))return LocatorFactory.getLocator(xpathDescription);
        else {
            String xpath = translateDescriptionToXpath(xpathDescription);
            return LocatorFactory.getLocator(xpath);
        }
    }

    public String translateDescriptionToXpath(String xpathDescription) {
        Scanner scaner = new Scanner();
        Tokens tokens = scaner.getTokens(xpathDescription);
        Map<String,Function<Tokens,String>> keys = KeyWords.getTranslationRules();
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        for (tokens.hasNext()) {

        }
        return xpath;
        /*
        StringBuilder sb = new StringBuilder(xpathDescription);
        Map<String,Supplier<BiFunction>> tags = KeyWords.getKeyworkToFunctionMap();
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
