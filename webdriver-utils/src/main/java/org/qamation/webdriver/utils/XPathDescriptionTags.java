package org.qamation.webdriver.utils;



import org.qamation.utils.RegExpUtils;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class XPathDescriptionTags {
    public static final String ANY ="(?i)any element";
    public static final String ELEMENT="(?i)element";
    public static final String ATTRIBUTE="(?i)attribute";
    public static final String WITH_VALUE = "(?i)with value";
    public static final String WITH_VALUE_CONTAINS = "(?i)with value contains";
    private static final String VALUE_REGEXP = "(?i)with value\\s{1,}'(.*)'.*";
    private static final String CONTAINS_REGEXP = "(?i)contains\\s{1,}'(.*)'.*";


    private static Map<String,Supplier<BiFunction>> xpathTags = null;


    public static Map<String, Supplier<BiFunction>> getXpathTags() {
        if (xpathTags == null) {
            xpathTags = new TreeMap<String, Supplier<BiFunction>>(String.CASE_INSENSITIVE_ORDER);
            mapTagsDescriptionToXPathElements();
        }
        return xpathTags;
    }

    private static void mapTagsDescriptionToXPathElements() {
        addANY();
        addWITH_VALUE_EQUAL();
        /*
        xpathTags.put(ANY,"//*");
        xpathTags.put(ELEMENT,"//${elementName}");
        xpathTags.put(ATTRIBUTE,"@${attributeName}");
        xpathTags.put(WITH_VALUE_CONTAINS,"[contains(text(),'${value})]");
        xpathTags.put(WITH_VALUE,"[text()='${value}']");
        */
    }

    private static void addWITH_VALUE_EQUAL() {
        BiFunction<StringBuilder,String,StringBuilder> anyFunc = (sb,desc)->
        {
            String val  = getElementsValues(desc);
            String s = sb.toString();
            s = removeExtraValues(s,val);
            s = s.replaceAll(WITH_VALUE,"\\[text()='\\$\\{value\\}'\\]");
            s = substituteValues(s,val);
            return new StringBuilder(s);
        };
        xpathTags.put(WITH_VALUE,()->anyFunc);
    }

    private static String substituteValues(String s, String val) {
        return s.replace("${value}",val);
    }

    private static void addANY() {
        BiFunction<StringBuilder,String,StringBuilder> anyFunc = (sb,desc)->
        {
            String s = sb.toString();
            s=s.replaceAll(ANY,"//*");
            return new StringBuilder(s);
        };
        xpathTags.put(ANY,()->anyFunc);

    }

    private static String removeExtraValues(String s, String val) {
        return s.replace("'"+val+"'","");
    }

    private static String getElementsValues(String desc) {
        return new RegExpUtils(desc,VALUE_REGEXP).getFindingInFirstGroup(1);

    }


}
