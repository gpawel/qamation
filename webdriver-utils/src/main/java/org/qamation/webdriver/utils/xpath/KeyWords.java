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


    public static final String WITH_VALUE_CONTAINS = "(?i)with value contains";
    private static final String NODE_VALUE_REGEXP = "(?i)with value\\s{1,}'(.*)'.*";
    private static final String NODE_NAME_REGEXP = "(?ism)((element)|(child)|(descendant)|(parent)) (.+?) ((with value)|(contains))";
    private static final String CONTAINS_REGEXP = "(?i)contains\\s{1,}'(.*)'.*";
    private static final String TEXT_EQUAL_VALUE_TEMPLE = "\\[text()='\\$\\{value!!\\}'\\]";



    private static KeyWords xPathKeyWords = null;

    private Map<String,Function<Tokens,String>> xpathTags = null;
    private CurrentNode currentNode;
    private CurrentPlace currentPlace;

    private KeyWords() {
        if (xpathTags == null) {
            xpathTags = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            mapTagsDescriptionToXPathElements();
        }
    }

    public static Map<String, Function<Tokens,String>> getTranslationRules() {
        if (xPathKeyWords == null) {
            xPathKeyWords = new KeyWords();
        }
        return xPathKeyWords.xpathTags;
    }


    private void mapTagsDescriptionToXPathElements() {
        addANY();
        addWITH();
        addVALUE();
        addELEMENT();
        addAND();
        addCHILD();
        addELEMENT_CONTAINS();
        addATTRIBUTE();
        //currentPlace.InCondition;

        /*
        xpathTags.put(ANY,"//*");
        xpathTags.put(ELEMENT,"//${elementName}");
        xpathTags.put(ATTRIBUTE,"@${attributeName}");
        xpathTags.put(WITH_VALUE_CONTAINS,"[contains(text(),'${value})]");
        xpathTags.put(WITH,"[text()='${value}']");
        */
    }

    private void addATTRIBUTE() {
        Function<Tokens,String> attribute = (list)->
        {
            setNode(CurrentNode.Attribute);
            if (list.hasNext())  {
                String attrName = list.next();
                if (attrName == null) throw new RuntimeException("attribute name is expected after 'attribute' keyword.");
                //String attr

            }

        };
        xpathTags.put(ATTRIBUTE,attribute);
    }

    private void addANY() {
        Function<Tokens,String> any= (list)->
        {
            if (list.hasNext())  {
                String el = list.next();
                if (el.equalsIgnoreCase("element")) {
                    setNode(CurrentNode.Element);
                    setPlace(CurrentPlace.InPath);
                    return "/*";
                }
                throw new RuntimeException("'element' is expected after 'any' keyword.");
            }
        };
        xpathTags.put(ANY,any);
    }

    private void addELEMENT() {
        Function<Tokens,String> element = (list)->
        {
            if (list.hasNext())  {
                String elName = list.next();
                if (elName == null) throw new RuntimeException("Element's name is expected after 'element' keyword");

                return "/"+elName;
            }
            throw new RuntimeException("'element' keyword should be followed by its name");
        };
        xpathTags.put(ELEMENT,element);
    }

    private void addCHILD() {
        Function<Tokens,String> child = (list)->
        {
            if (list.hasNext())  {
                String childName = list.next();
                if (childName == null) throw new RuntimeException("Child's name is expected after 'child' keyword");
                return "/"+childName;
            }
            throw new RuntimeException("'child' keyword should be followed by its name");
        };
        xpathTags.put(CHILD,child);
    }

    private void addWITH() {
        Function<Tokens,String> with =  (list)->
        {
            if (list.hasNext()) {
                String value = list.next();
                if (value.equalsIgnoreCase("value")) {
                    if (value == null) throw new RuntimeException("Found something like<with null>; Expected: ...with value 'value'");
                    return "text()="+value+"";
                }
                if (value.equalsIgnoreCase("attribute"))
            }
                    // this is 'connector' word
            // without proper AST will skip for now.
            return "[";
        };
        xpathTags.put(WITH,with);
    }

    private void addAND() {
        Function<Tokens,String> and= (list)->
        {
            String result="";


                if (currentPlace == CurrentPlace.InPath)
                    return "/";
                else {

                }

            }
            // this is 'connector' word
            // without proper AST will skip for now.
            return result;
        };
        xpathTags.put(AND,and);
    }

    private void addVALUE() {
        Function<Tokens,String> with_value= (list)->
        {
            if (list.hasNext())  {
                String value = list.next();
                if (value == null) throw new RuntimeException("Found something like<with null>; Expected: ...with value 'value'");
                    if (currentNode = CurrentNode.Element) {
                        return "text()=" + value + "";
                    }
                    else return "="+value;
            }
            throw new RuntimeException("'value' keyword should be followed by actual value");
        };
        xpathTags.put(VALUE,with_value);
    }

    private void addELEMENT_CONTAINS() {
        Function<Tokens,String> contains= (list)->
        {
            if (list.hasNext())  {
                String value = list.next();
                if (value == null) throw new RuntimeException("Found <contains null>; Expected: contains 'value'");
                return "[contains(text(),'"+value+"')]";
            }
            throw new RuntimeException("'element contains' keyword should be followed by value ");
        };
        xpathTags.put(CONTAINS,contains);
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

    /*
    private String processNodes(String next, Iterator<String> list) {
        Function<Iterator<String>,String> func = xpathTags.get(next);
        if (func == null) throw new RuntimeException(next+" not found in a list of key words.");
        else return func.apply(list);
    }
*/
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