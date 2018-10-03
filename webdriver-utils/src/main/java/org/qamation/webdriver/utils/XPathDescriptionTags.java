package org.qamation.webdriver.utils;



import org.qamation.utils.RegExpUtils;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class XPathDescriptionTags {
    public static final String ANY ="any element";
    public static final String ELEMENT="element";
    public static final String ATTRIBUTE="attribute";
    public static final String WITH_VALUE_EQUAL="with value";
    public static final String WITH_VALUE_CONTAINS="with value contains";
    private static final String VALUE_REGEXP = "with value\\s{1,}'(.*)'.*";
    private static final String CONTAINS_REGEXP = "contains\\s{1,}'(.*)'.*";


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
        xpathTags.put(WITH_VALUE_EQUAL,"[text()='${value}']");
        */
    }

    private static void addWITH_VALUE_EQUAL() {
        BiFunction<StringBuilder,String,StringBuilder> anyFunc = (sb,desc)->
        {
            String s = sb.toString();
            String val = new RegExpUtils(desc,VALUE_REGEXP).getFindingInFirstGroup(1);
            s = s.replace("'"+val+"'","");
            s = s.replace(WITH_VALUE_EQUAL,"[text()='${value}']");

            s = s.replace("${value}",val);
            return new StringBuilder(s);
        };
        xpathTags.put(WITH_VALUE_EQUAL,()->anyFunc);
    }

    private static void addANY() {
        BiFunction<StringBuilder,String,StringBuilder> anyFunc = (sb,desc)->
        {
            String s = sb.toString();
            s=s.replace(ANY,"//*");
            return new StringBuilder(s);
        };
        xpathTags.put(ANY,()->anyFunc);

    }


}
