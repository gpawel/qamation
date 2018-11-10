package org.qamation.webdriver.utils.xpath;



import org.qamation.utils.RegExpUtils;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class KeyWords {

    public static final String ANY ="any";
    public static final String ELEMENT="element";
    public static final String ATTRIBUTE="attribute";
    public static final String WITH = "with";
    public static final String VALUE = "value";
    public static final String AND = "and";
    public static final String CHILD = "child";
    public static final String CONTAINS = "contains";
    public static final String PARENT = "parent";
    public static final String DESCENDANT = "descendant";
    public static final String AT="at";
    public static final String POSITION = "position";
    public static final String WHICH = "which";
    public static final String EOF="eof";
    public static final String EOL="eol";


    public static final String WITH_VALUE_CONTAINS = "(?i)with value contains";
    private static final String NODE_VALUE_REGEXP = "(?i)with value\\s{1,}'(.*)'.*";
    private static final String NODE_NAME_REGEXP = "(?ism)((element)|(child)|(descendant)|(parent)) (.+?) ((with value)|(contains))";
    private static final String CONTAINS_REGEXP = "(?i)contains\\s{1,}'(.*)'.*";
    private static final String TEXT_EQUAL_VALUE_TEMPLE = "\\[text()='\\$\\{value!!\\}'\\]";



    private static KeyWords xPathKeyWords = null;

    private Map<String,Function<Tokens,String>> xpathTags = null;
    private boolean isBracketOpened;
    private CurrentNode currentNode;
    private CurrentPlace currentPlace;


    public static Map<String, Function<Tokens,String>> getKeyWordsTags() {
        if (xPathKeyWords == null) {
            xPathKeyWords = new KeyWords();
        }
        return xPathKeyWords.getXpathTags();
    }


    private KeyWords() {
        if (xpathTags == null) {
            xpathTags = mapTags();
        }
        isBracketOpened = false;
    }

    public Map<String, Function<Tokens, String>> getXpathTags() {
        return xpathTags;
    }

    private Map<String,Function<Tokens,String>> mapTags() {
        Map<String,Function<Tokens,String>> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put(WITH,getWithFunction());
        map.put(VALUE, getValueFunction());
        map.put(ELEMENT,getElementFunction());
        map.put(CHILD,getElementFunction());
        map.put(AT,getAtFunction());
        map.put(AND, getAndFunction());
        map.put(CONTAINS, getContainsFunction());
        map.put(ATTRIBUTE, getAttriuteFunction());
        map.put(ANY,getAnyFunction());
        map.put(POSITION,getPositionFunction());
        map.put(DESCENDANT, getDescendantFunction());
        map.put(EOL,getEOLFunction());
        map.put(EOF,getEOFFunction());
        map.put(WHICH,getWichFunction());
        return map;

    }

    private Function<Tokens,String> getWichFunction() {
        Function<Tokens,String> which = (list)->
        {
            return "";
        };
        return which;
    }

    private Function<Tokens,String> getDescendantFunction() {
        Function<Tokens,String> descendant = (list)->
        {
            currentNode = CurrentNode.Element;
            if (list.hasNext())  {
                String elName = list.getNextToken();
                return "//"+elName;
            }
            else throw new RuntimeException("'element' keyword should be followed by its name");
        };
        return descendant;
    }

    private Function<Tokens,String> getPositionFunction() {
        Function<Tokens,String> position = (list)->
        {
            if (list.hasNext()) {
                String sv = list.getNextToken();
                double value = Double.parseDouble(sv);
                //DecimalFormat formater = new DecimalFormat("##");
                //sv = "["+formater.format(value)+"]";
                sv = String.format("[%.0f]",value);

                return sv;
            }
            else throw new RuntimeException("Value is expected after 'position' keyword, like '...position [1]'");
        };
        return position;
    }


    private Function<Tokens,String> getAttriuteFunction() {
        Function<Tokens,String> attribute = (list)->
        {
            currentNode = CurrentNode.Attribute;
            if (list.hasNext())  {
                String attrName = list.getNextToken();
                return "@"+attrName;
            }
            else throw new RuntimeException("nothing found after 'attribute' keyword");
        };
        return attribute;
    }

    private Function<Tokens,String> getAnyFunction() {
        Function<Tokens,String> any= (list)->
        {
            if (list.hasNext())  {
                String el = list.getNextToken();
                if (el.equalsIgnoreCase("element")) {
                    setNode(CurrentNode.Element);
                    setPlace(CurrentPlace.InPath);
                    return "/*";
                }
                throw new RuntimeException("'any' keyword should be followed by 'element'");
            }
            else throw new RuntimeException("nothing found after 'any' keyword");
        };
        return any;
    }

    private Function<Tokens,String> getElementFunction() {
        Function<Tokens,String> element = (list)->
        {
            currentNode = CurrentNode.Element;
            if (list.hasNext())  {
                String elName = list.getNextToken();
                return "/"+elName;
            }
            else throw new RuntimeException("'element' keyword should be followed by its name");
        };
        return element;

    }

    private Function<Tokens,String> getWithFunction() {
        Function<Tokens,String> with =  (list)->
        {
            if (list.hasNext()) {
                String nt = list.getNextToken();
                list.moveBack();
                if (isNode(nt)) {
                    return "";
                }
                else {
                    if (isBracketOpened) return "";
                    return openBracket();
                }
            }
            else throw new RuntimeException("xpath description cannot end at 'with' keyword");
        };
        return with;

    }

    private Function<Tokens, String> getAndFunction() {
        Function<Tokens, String> and = (list) ->
        {
            if (isBracketOpened) {
                String nt = list.getNextToken();
                if (isNode(nt))  {
                    list.moveBack();
                    return closeBracket();
                }
                return " and ";
            }
            return "";

        };
        return and;

    }

    private Function<Tokens,String> getValueFunction() {
        Function<Tokens,String> with_value = (list)->
        {
            if (list.hasNext())  {
                String value = list.getNextToken();

                    if (currentNode == CurrentNode.Element) {
                        return "text()='" + value + "'";
                    }
                    else return "='"+value+"'";
            }
            throw new RuntimeException("'value' keyword should be followed by actual value");
        };
        return with_value;
    }

    private Function<Tokens,String> getContainsFunction() {
        Function<Tokens,String> element_contains= (list)->
        {
            StringBuilder sb = new StringBuilder();
            if (isBracketOpened) {}
            else sb.append(openBracket());
            if (list.hasNext())  {
                String value = list.getNextToken();
                if (currentNode == CurrentNode.Attribute) {
                    String attName = list.getPreviousToken();
                    list.moveForward();
                    sb.append("contains(@"+attName+","+value+")");
                }
                else {
                    sb.append("contains(text(),'" + value + "')");
                }
                return sb.toString();
            }
            throw new RuntimeException("'element contains' keyword should be followed by value ");
        };
        return element_contains;

    }

    private Function<Tokens,String> getAtFunction() {
        Function<Tokens,String> at = (list)->
        {
            return "";
        };
        return at;
    }

    private Function<Tokens,String> getEOFFunction() {
        return getEOLFunction();
    }

    private Function<Tokens,String> getEOLFunction() {
        Function<Tokens,String> eol = (list) -> {
            if (isBracketOpened) {
                return closeBracket();
            }
            return "";
        };
        return eol;
    }

    private String substituteValues(String s, String[] val) {
        for (int i=0; i < val.length; i++) {
            s = s.replace("${value"+i+"}",val[i]);
        }
        return s;
    }



    private String eraceValues(String s, String[] val) {
        for (String x: val) {
            s = s.replace("'"+x+"'","");
        }
        return s;
    }

    private String [] getElementsValues(String desc) {

        return new RegExpUtils(desc, NODE_VALUE_REGEXP).getAllFindings();

    }

    private void setPlace(CurrentPlace place) {
        currentPlace = place;
    }

    private void setNode(CurrentNode node) {
        currentNode = node;
    }

    private String openBracket() {
        currentPlace = CurrentPlace.InCondition;
        isBracketOpened =true;
        return "[";
    }

    private String closeBracket() {
        currentPlace = CurrentPlace.InPath;
        isBracketOpened =false;
        return "]";
    }



    private boolean isNode(String next) {
        if (next.equalsIgnoreCase(CHILD)) {
            return true;
        }
        else if (next.equalsIgnoreCase(DESCENDANT)) {
            return true;
        }
        else if (next.equalsIgnoreCase(PARENT)) {
            return true;
        }
        else if (next.equalsIgnoreCase(ELEMENT)) {
            return true;
        }
        else return false;
    }

    private boolean isAttribute(String next) {
        if (next.equalsIgnoreCase("attribute")) return true;
        else return false;
    }

    private boolean isValue(String next) {
        if (next.equalsIgnoreCase("value"))  return true;
        else return true;
    }


}
