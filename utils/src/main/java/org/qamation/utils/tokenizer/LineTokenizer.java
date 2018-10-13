package org.qamation.utils.tokenizer;

import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class LineTokenizer {
    private List<TokenListener> listeners;


    public LineTokenizer() {
        listeners = new ArrayList<>();
    }

    public void addTokenListener(TokenListener listener) {
        listeners.add(listener);
    }

    public void tokenizeString(String aLine) {
        StreamTokenizer tokenizer = getStreamTokenizer(aLine);
        processLine(tokenizer,aLine);
    }


    private void processLine(StreamTokenizer tokenizer, String aLine) {
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

    private StreamTokenizer getStreamTokenizer(String aLine) {
        StringReader reader = new StringReader(aLine);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        configure(tokenizer);
        return tokenizer;

    }

    private void configure(StreamTokenizer tokenizer) {
        tokenizer.commentChar('#');
        tokenizer.quoteChar((char)39);
        tokenizer.slashSlashComments(true);
        tokenizer.slashStarComments(true);
        tokenizer.eolIsSignificant(true);
        tokenizer.wordChars((char)48,(char)57);


    }
}
