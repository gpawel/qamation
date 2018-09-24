package org.qamation.webdriver.utils;



import java.util.Map;
import java.util.TreeMap;

public class XPathTags {
    public static final String ANY ="any";
    public static final String ELEMENT="element";
    public static final String ATTRIBUTE="attribute";
    public static final String WITH_VALUE_EQUAL="with value equal";
    public static final String WITH_VALUE_CONTAINS="with value contains";

    private Map<String,String> xpathTags = null;

    public XPathTags(String xpathDescription) {
        xpathTags = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        mapTagsDescriptionToXPathElements();
    }

    private void mapTagsDescriptionToXPathElements() {
        xpathTags.put(ANY,"//*");
        xpathTags.put(ELEMENT,"//${elementName}");
        xpathTags.put(ATTRIBUTE,"@${attributeName}");
        xpathTags.put(WITH_VALUE_CONTAINS,"[contains(text(),'${value})]");
        xpathTags.put(WITH_VALUE_EQUAL,"[text()='${value}']");
    }


}
