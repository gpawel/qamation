package org.qamation.webdriver.utils.xpath;

import org.qamation.utils.tokenizer.LineTokenizer;
import org.qamation.utils.tokenizer.TokenListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

public class DescriptionParser implements TokenListener {

    private Map<String,Function<Iterator<String>,String>> keywords;
    private LineTokenizer tokenizer;
    private LinkedList<String> convertedDescription;

    public DescriptionParser (Map<String,Function<Iterator<String>,String>> keywords) {
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
        sb.append("/");
        Iterator<String> iterator = convertedDescription.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Function<Iterator<String>,String> f = keywords.get(key);
            if (f == null) throw new RuntimeException("<"+key+"> is not valid keyword");
            sb.append(f.apply(iterator));
        }
        return sb.toString();
    }

    @Override
    public void gotStringValue(String val) {
        convertedDescription.add(val);
    };

    @Override
    public void gotNumberValue(double d) {
        convertedDescription.add(String.valueOf(d));
    }

    @Override
    public void eolReached() {

    }

    @Override
    public void eofReached() {

    }


}
