package org.qamation.webdriver.utils.xpath;



import org.qamation.utils.RegExpUtils;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class XPathKeyWords {
    public static final String ANY ="(?i)any";
    public static final String ELEMENT="(?i)element";
    public static final String ATTRIBUTE="(?i)attribute";
    public static final String WITH_VALUE = "(?i)with value";
    public static final String WITH_VALUE_CONTAINS = "(?i)with value contains";
    private static final String NODE_VALUE_REGEXP = "(?i)with value\\s{1,}'(.*)'.*";
    private static final String NODE_NAME_REGEXP = "(?ism)((element)|(child)|(descendant)|(parent)) (.+?) ((with value)|(contains))";
    private static final String CONTAINS_REGEXP = "(?i)contains\\s{1,}'(.*)'.*";
    private static final String TEXT_EQUAL_VALUE_TEMPLE = "\\[text()='\\$\\{value!!\\}'\\]";


    private static Map<String,Supplier<Function>> xpathTags = null;


    public static Map<String, Supplier<Function>> getXpathTags() {
        if (xpathTags == null) {
            xpathTags = new TreeMap<String, Supplier<Function>>(String.CASE_INSENSITIVE_ORDER);
            mapTagsDescriptionToXPathElements();
        }
        return xpathTags;
    }

    private static void mapTagsDescriptionToXPathElements() {
        addANY();
        //addWITH_VALUE_EQUAL();
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
        Function<String,String> any= (desc)->
        {
            return "//*";
        };
        xpathTags.put(ANY,()->any);

    }

    private static void addELEMENT() {
        BiFunction<StringBuilder,String,StringBuilder> anyFunc = (sb,desc)-> {
            String s = sb.toString();
            // GET ELEMENT NAME HERE. THEN REPLACE:
            //s = s.replaceAll(ELEMENT,)
            return new StringBuilder(s);
        };
    }
/*
    private static void addWITH_VALUE_EQUAL() {
        Function<String,String> anyFunc = (desc)->
        {

            String[] val  = getElementsValues(desc);
            String s = sb.toString();
            s = eraceValues(s,val);
            s = insertXpathTemplate(s,WITH_VALUE,TEXT_EQUAL_VALUE_TEMPLE,val.length);
            //s = s.replaceAll(WITH_VALUE,"\\[text()='\\$\\{value\\}'\\]");
            s = substituteValues(s,val);
            return new StringBuilder(s);
        };
        xpathTags.put(WITH_VALUE,()->anyFunc);
    }
    */

    private static String insertXpathTemplate(String result, String target, String textEqualValueTemple, int len) {
        for (int i = 1; i < len; i++) {
            String templ = textEqualValueTemple.replace("!!",String.valueOf(i));
            result = result.replaceFirst(target,templ);
        }
        return result;
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
