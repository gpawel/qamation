package org.qamation.webdriver.utils.xpath;



import org.qamation.utils.RegExpUtils;

import java.util.HashMap;
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


    public static final String WITH_VALUE_CONTAINS = "(?i)with value contains";
    private static final String NODE_VALUE_REGEXP = "(?i)with value\\s{1,}'(.*)'.*";
    private static final String NODE_NAME_REGEXP = "(?ism)((element)|(child)|(descendant)|(parent)) (.+?) ((with value)|(contains))";
    private static final String CONTAINS_REGEXP = "(?i)contains\\s{1,}'(.*)'.*";
    private static final String TEXT_EQUAL_VALUE_TEMPLE = "\\[text()='\\$\\{value!!\\}'\\]";



    private static KeyWords xPathKeyWords = null;

    private Map<String,Function<Tokens,String>> xpathTags = null;
    private CurrentNode currentNode;
    private CurrentPlace currentPlace;


    public static Map<String, Function<Tokens,String>> getKeyWordsTags() {
        if (xPathKeyWords == null) {
            xPathKeyWords = new KeyWords();
        }
        return xPathKeyWords.xpathTags;
    }


    private KeyWords() {
        if (xpathTags == null) {
            xpathTags = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            xpathTags = mapTags();
        }
    }

    private Map<String,Function<Tokens,String>> mapTags() {
        Map<String,Function<Tokens,String>> map = new HashMap<>();
        map.put(WITH,getWithFunction());
        map.put(VALUE, getValueFunction());
        map.put(ELEMENT,getElementFunction());
        map.put(AND, getAndFunction());
        map.put(CHILD, getChildFunction());
        map.put(CONTAINS,getElementContainsFunction());
        map.put(ATTRIBUTE, getAttriuteFunction());
        map.put(ANY,getAnyFunction());
        return map;

    }

    private Function<Tokens,String> getAttriuteFunction() {
        Function<Tokens,String> attribute = (list)->
        {
            setNode(CurrentNode.Attribute);
            if (list.hasNext())  {
                String attrName = list.getNextToken();
                if (attrName == null) throw new RuntimeException("attribute name is expected after 'attribute' keyword.");
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
            if (list.hasNext())  {
                String elName = list.getNextToken();
                if (elName == null) throw new RuntimeException("Element's name is expected after 'element' keyword");

                return "/"+elName;
            }
            else throw new RuntimeException("'element' keyword should be followed by its name");
        };
        return element;

    }

    private Function<Tokens,String> getChildFunction() {
        Function<Tokens,String> child = (list)->
        {
            if (list.hasNext())  {
                String childName = list.getNextToken();
                if (childName == null) throw new RuntimeException("Child's name is expected after 'child' keyword");
                return "/"+childName;
            }
            else throw new RuntimeException("'child' keyword should be followed by its name");
        };
        return child;
    }

    private Function<Tokens,String> getWithFunction() {
        Function<Tokens,String> with =  (list)->
        {
            if (list.hasNext()) {
                setPlace(CurrentPlace.InCondition);
                return openBracket();
            }
            else throw new RuntimeException("xpath description cannot end at 'with' keyword");

        };
        return with;

    }

    private Function<Tokens,String> getAndFunction() {
        Function<Tokens,String> and= (list)->
        {
            String result="";


                if (currentPlace == CurrentPlace.InPath)
                    return "/";
                else if {

                }

            // this is 'connector' word
            // without proper AST will skip for now.

        };
        return and;

    }

    private Function<Tokens,String> getValueFunction() {
        Function<Tokens,String> with_value = (list)->
        {
            if (list.hasNext())  {
                String value = list.getNextToken();
                if (value == null) throw new RuntimeException("Found something like<with null>; Expected: ...with value 'value'");
                    if (currentNode == CurrentNode.Element) {
                        return "text()=" + value + "";
                    }
                    else return "="+value;
            }
            throw new RuntimeException("'value' keyword should be followed by actual value");
        };
        return with_value;
    }

    private Function<Tokens,String> getElementContainsFunction() {
        Function<Tokens,String> element_contains= (list)->
        {
            if (list.hasNext())  {
                String value = list.getNextToken();
                if (value == null) throw new RuntimeException("Found <contains null>; Expected: contains 'value'");
                return "[contains(text(),'"+value+"')]";
            }
            throw new RuntimeException("'element contains' keyword should be followed by value ");
        };
        return element_contains;

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
        return "[";
    }

    private String closeBracket() {
        currentPlace = CurrentPlace.InPath;
        return "]";
    }



    private boolean isNode(String next) {
        if (next.equalsIgnoreCase(CHILD)) {
            return true;
        }
        else if (next.equalsIgnoreCase(DESCENDANT)) {
            return true
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
