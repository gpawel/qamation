package org.qamation.webdriver.utils.xpath;

import java.util.ArrayList;

public class Tokens extends ArrayList<String> {
    private ArrayList<String> tokens;
    private int currentIndex;
    public Tokens() {
        super();
        currentIndex=0;
    }

    public void moveForward() {
        currentIndex++;
    }

    public void moveBack() {
        currentIndex--;
    }

    public boolean hasNext() {
        if (currentIndex < tokens.size()) return true;
        return false;
    }

    public String getNextToken() {
        if (hasNext()) {
            currentIndex++;
            return tokens.get(currentIndex);
        }
        else throw new IndexOutOfBoundsException ("Already at the last token");
    }

    public String getCurrentToken() {
        return tokens.get(currentIndex);
    }

    public void resetCurrentPosition() {
        currentIndex=0;
    }

}
