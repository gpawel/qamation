package org.qamation.webdriver.utils.xpath;

import org.openqa.selenium.By;
import org.qamation.utils.tokenizer.TokenListener;
import org.qamation.webdriver.utils.LocatorFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;


public class Translator {
    private static final String WHITE_SPECE_NOT_IN_QUOTE = "\\s+(?=([^']*'[^']*')*[^']*$)";

    public static String getXpath(String xpathDescription) {
            return translateDescriptionToXpath(xpathDescription);

    }

    public static String translateDescriptionToXpath(String xpathDescription) {
        Scanner scaner = new Scanner();
        Tokens tokens = scaner.getTokens(xpathDescription);
        if (checkShortFormOfElementID(tokens)) return "xpath=//*[@id='"+tokens.getCurrentToken()+"']";
        Map<String,Function<Tokens,String>> keys = KeyWords.getKeyWordsTags();
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        while (tokens.hasNext()) {
            String token = tokens.getCurrentToken();
            Function<Tokens,String> f = keys.get(token);
            if (f==null) throw new RuntimeException("Unknown token: <"+token+">");
            String xpathPortion = f.apply(tokens);
            sb.append(xpathPortion);
            tokens.moveForward();
        }
        return "xpath="+sb.toString();
    }

    private static boolean checkShortFormOfElementID(Tokens tokens) {
        ArrayList<String> arr = tokens.getTokens();
        String s = arr.get(arr.size()-1);
        if (tokens.size()==2 && (s==KeyWords.EOF || s==KeyWords.EOL)) return true;
        else return false;
    }


}
