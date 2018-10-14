package org.qamation.webdriver.utils.xpath;



import org.qamation.utils.RegExpUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class XPathKeyWords {
    public static final String ANY ="any";
    public static final String ELEMENT="(?i)element";
    public static final String ATTRIBUTE="(?i)attribute";
    public static final String WITH_VALUE = "with";
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
        addWITH_VALUE_EQUAL();
        //addELEMENT();

        /*
        xpathTags.put(ANY,"//*");
        xpathTags.put(ELEMENT,"//${elementName}");
        xpathTags.put(ATTRIBUTE,"@${attributeName}");
        xpathTags.put(WITH_VALUE_CONTAINS,"[contains(text(),'${value})]");
        xpathTags.put(WITH_VALUE,"[text()='${value}']");
        */
    }

    private static void addANY() {
        Function<Iterator<String>,String> any= (list)->
        {
            if (list.hasNext())  {
                String el = list.next();
                if (el.equalsIgnoreCase("element")) return "//*";
            }
            throw new RuntimeException("'element' is expected after 'any' keyword.");
        };
        xpathTags.put(ANY,any);
    }

    private static void addELEMENT() {
        BiFunction<StringBuilder,String,StringBuilder> anyFunc = (sb,desc)-> {
            String s = sb.toString();
            // GET ELEMENT NAME HERE. THEN REPLACE:
            //s = s.replaceAll(ELEMENT,)
            return new StringBuilder(s);
        };
    }

    private static void addWITH_VALUE_EQUAL() {
        Function<Iterator<String>,String> with_value= (list)->
        {
            if (list.hasNext())  {
                String el = list.next();
                if (el == null) throw new RuntimeException("null is found after 'with' keyword");
                if (el.equalsIgnoreCase("value")) {
                    String value = list.next();
                    if (value == null) throw new RuntimeException("Found <with null>; Expected: ...with value 'value'");
                    return "[text()='"+value+"']";
                }
                else throw new RuntimeException("'value' word is expected after 'with' keyword");
            }
            throw new RuntimeException("'with' should be followed by 'value' word.");
        };
        xpathTags.put(WITH_VALUE,with_value);
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
