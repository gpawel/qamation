package org.qamation.utils.tokenizer;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class StringLineTokenizer {
    private List<TokenListener> listeners;

    public StringLineTokenizer() {
        listeners = new ArrayList<>();
    }

    public void addTokenListener(TokenListener listener) {
        listeners.add(listener);
    }

    public void tokenizeString(String aLine) {
        StreamTokenizer tokenizer = getStreamTokenizer(aLine);
        try {
            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                notifyListeners(tokenizer);
            }
            notifyListeners(tokenizer);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Problem reading from tokenizer.",ex);
        }
    }

    private void notifyListeners(StreamTokenizer tokenizer) {
        for (TokenListener l : listeners) {
            if (tokenizer.ttype == StreamTokenizer.TT_EOL) {
                l.eolReached();
                continue;
            }
            else if (tokenizer.ttype == StreamTokenizer.TT_EOF) {
                l.eofReached();
                continue;
            }
            else if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                l.gotNumberValue(tokenizer.nval);
                continue;
            }
            else {
                l.gotStringValue(tokenizer.sval);
                continue;
            }
        }
    }

    public StreamTokenizer getStreamTokenizer(String xpathDescription) {
        StringReader reader = new StringReader(xpathDescription);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        configure(tokenizer);
        return tokenizer;

    }

    private void configure(StreamTokenizer tokenizer) {
        tokenizer.commentChar('#');
        tokenizer.quoteChar('"');
        tokenizer.slashSlashComments(true);
        tokenizer.slashStarComments(true);
        tokenizer.eolIsSignificant(true);


    }
}
