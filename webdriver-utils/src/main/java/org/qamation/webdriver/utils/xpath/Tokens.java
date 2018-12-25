package org.qamation.webdriver.utils.xpath;

import java.util.ArrayList;

public class  Tokens  {

    private ArrayList<String> tokens;
    private int currentIndex;

    public Tokens () {
        tokens = new ArrayList<>();
        currentIndex=0;
    }

    public void add(String text) {
        tokens.add(text);
    }

    public void moveForward() {
        if (hasNext()) currentIndex++;
        else throw new IndexOutOfBoundsException("Last token is in use");
    }

    public void moveBack() {
        currentIndex--;
    }

    public boolean hasNext() {
        if (currentIndex < tokens.size()) return true;
        return false;
    }

    public String getNextToken() {
        moveForward();
        return tokens.get(currentIndex);
    }

    public String getPreviousToken() {
        if (currentIndex>0) {
            moveBack();
            return tokens.get(currentIndex);
        }
        else throw new IndexOutOfBoundsException("Cannot get previous token: currently at the firs token");
    }

    public String getCurrentToken() {
        return tokens.get(currentIndex);
    }

    public void resetCurrentPosition() {
        currentIndex=0;
    }

    public void addEOL() {
        tokens.add(KeyWords.EOL);
    }

    public void addEOF() {
        tokens.add(KeyWords.EOF);
    }

    public int size() {return tokens.size();}

    public ArrayList<String> getTokens() {
        ArrayList<String> arr = new ArrayList<String>();
        for (String s:tokens) {
            arr.add(s);
        }
        return arr;
    }

}
