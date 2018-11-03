package org.qamation.webdriver.utils.xpath;

import org.qamation.utils.tokenizer.LineTokenizer;
import org.qamation.utils.tokenizer.TokenListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;

public class Scanner implements TokenListener {

    private LineTokenizer tokenizer;
    private Tokens tokens;


    public Scanner() {
        this.tokenizer = new LineTokenizer();
        this.tokenizer.addTokenListener(this);
        this.tokens = new Tokens();

    }

    public Tokens getTokens(String xpathDescription) {
        tokenizer.tokenizeString(xpathDescription);
        return tokens;
    }

    @Override
    public void gotStringValue(String val) {
        tokens.add(val);
    };

    @Override
    public void gotNumberValue(double d) {
        tokens.add(String.valueOf(d));
    }

    @Override
    public void eolReached() {
        tokens.addEOL();

    }

    @Override
    public void eofReached() {
        tokens.addEOF();
    }


}
