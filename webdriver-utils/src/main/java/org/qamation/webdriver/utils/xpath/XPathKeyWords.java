package org.qamation.webdriver.utils.xpath;



import org.qamation.utils.RegExpUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class XPathKeyWords {
    public static final String ANY ="any";
    public static final String ELEMENT="element";
    public static final String ATTRIBUTE="attribute";
    public static final String WITH = "with";
    public static final String VALUE = "value";
    public static final String AND = "and";
    public static final String CHILD = "child";
    public static final String CONTAINS = "contains";

    public static final String WITH_VALUE_CONTAINS = "(?i)with value contains";
    private static final String NODE_VALUE_REGEXP = "(?i)with value\\s{1,}'(.*)'.*";
    private static final String NODE_NAME_REGEXP = "(?ism)((element)|(child)|(descendant)|(parent)) (.+?) ((with value)|(contains))";
    private static final String CONTAINS_REGEXP = "(?i)contains\\s{1,}'(.*)'.*";
    private static final String TEXT_EQUAL_VALUE_TEMPLE = "\\[text()='\\$\\{value!!\\}'\\]";


    private static Map<String,Function<Iterator<String>,String>> xpathTags = null;


    public static Map<String, Function<Iterator<String>,String>> getXpathTags() {
        if (xpathTags == null) {
            xpathTags = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            mapTagsDescriptionToXPathElements();
        }
        return xpathTags;
    }

    private static void mapTagsDescriptionToXPathElements() {
        addANY();
        addWITH();
        addVALUE();
        addELEMENT();
        addAND();
        addCHILD();
        addELEMENT_CONTAINS();

        /*
        xpathTags.put(ANY,"//*");
        xpathTags.put(ELEMENT,"//${elementName}");
        xpathTags.put(ATTRIBUTE,"@${attributeName}");
        xpathTags.put(WITH_VALUE_CONTAINS,"[contains(text(),'${value})]");
        xpathTags.put(WITH,"[text()='${value}']");
        */
    }

    private static void addANY() {
        Function<Iterator<String>,String> any= (list)->
        {
            if (list.hasNext())  {
                String el = list.next();
                if (el.equalsIgnoreCase("element")) return "/*";
            }
            throw new RuntimeException("'element' is expected after 'any' keyword.");
        };
        xpathTags.put(ANY,any);
    }

    private static void addELEMENT() {
        Function<Iterator<String>,String> element = (list)->
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

    private static void addCHILD() {
        Function<Iterator<String>,String> child = (list)->
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

    private static void addWITH() {
        Function<Iterator<String>,String> with =  (list)->
        {
            // this is 'connector' word
            // without proper AST will skip for now.
            return "";
        };
        xpathTags.put(WITH,with);
    }

    private static void addAND() {
        Function<Iterator<String>,String> and= (list)->
        {
            // this is 'connector' word
            // without proper AST will skip for now.
            return "";
        };
        xpathTags.put(AND,and);
    }

    private static void addVALUE() {
        Function<Iterator<String>,String> with_value= (list)->
        {
            if (list.hasNext())  {
                String value = list.next();
                if (value == null) throw new RuntimeException("Found something like<with null>; Expected: ...with value 'value'");
                return "[text()='"+value+"']";
            }
            throw new RuntimeException("'value' keyword should be followed by actual ");
        };
        xpathTags.put(VALUE,with_value);
    }

    private static void addELEMENT_CONTAINS() {
        Function<Iterator<String>,String> contains= (list)->
        {
            if (list.hasNext())  {
                String value = list.next();
                if (value == null) throw new RuntimeException("Found something like<with null>; Expected: ...with value 'value'");
                return "[text()='"+value+"']";
            }
            throw new RuntimeException("'value' keyword should be followed by actual ");
        };
        xpathTags.put(CONTAINS,contains);
    }

    private static String substituteValues(String s, String[] val) {
        for (int i=0; i < val.length; i++) {
            s = s.replace("${value"+i+"}",val[i]);
        }
        return s;
    }



    private static String eraceValues(String s, String[] val) {
        for (String x: val) {
            s = s.replace("'"+x+"'","");
        }
        return s;
    }

    private static String [] getElementsValues(String desc) {

        return new RegExpUtils(desc, NODE_VALUE_REGEXP).getAllFindings();

    }


}
