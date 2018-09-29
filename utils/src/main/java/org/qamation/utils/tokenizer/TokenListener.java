package org.qamation.utils.tokenizer;

import java.io.StreamTokenizer;

public interface TokenListener {
    void gotStringValue(String val);
    void gotNumberValue(double d);
    void eolReached();
    void eofReached();
}
