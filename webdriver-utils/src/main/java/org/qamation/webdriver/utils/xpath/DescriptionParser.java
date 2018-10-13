package org.qamation.webdriver.utils.xpath;

import org.qamation.utils.tokenizer.LineTokenizer;
import org.qamation.utils.tokenizer.TokenListener;

import java.io.StreamTokenizer;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class DescriptionParser implements TokenListener {

    private Map<String,Supplier<Function>> keywords;
    private LineTokenizer tokenizer;
    private LinkedList<String> convertedDescription;

    public DescriptionParser (Map<String, Supplier<Function>> keywords) {
        this.tokenizer = new LineTokenizer();
        this.tokenizer.addTokenListener(this);
        this.keywords = keywords;
        convertedDescription = new LinkedList<>();
    }
    public String processDescription(String xpathDescription) {
        tokenizer.tokenizeString(xpathDescription);
        return buildString(convertedDescription);
    }

    private String buildString(LinkedList<String> convertedDescription) {
        StringBuilder sb = new StringBuilder();
        for (String s : convertedDescription) {
            sb = sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public void gotStringValue(String val) {
        Supplier s = keywords.get(val);
        s.get.

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
