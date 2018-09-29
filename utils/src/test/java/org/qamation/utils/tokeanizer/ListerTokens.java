package org.qamation.utils.tokeanizer;

import org.qamation.utils.tokenizer.TokenListener;

public class ListerTokens implements TokenListener {
    private double dval = -3;
    private String sval = "";
    private boolean eol = false;
    private boolean eof = false;

    @Override
    public void gotStringValue(String val) {
        sval=sval.concat(val);
    }

    @Override
    public void gotNumberValue(double d) {
        dval = d;
    }

    @Override
    public void eolReached() {
        eol = true;
    }

    @Override
    public void eofReached() {
        eof = true;
    }

    public double getDval() {
        return dval;
    }

    public String getSval() {

        return sval;
    }

    public boolean isEol() {
        return eol;
    }

    public boolean isEof() {
        return eof;
    }
}
