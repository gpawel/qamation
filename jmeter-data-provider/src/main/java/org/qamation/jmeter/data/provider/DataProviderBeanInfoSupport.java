package org.qamation.jmeter.data.provider;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel on 2017-04-22.
 */
public abstract class DataProviderBeanInfoSupport extends BeanInfoSupport {

    private static final Logger log = LoggerFactory.getLogger(DataProviderBeanInfoSupport.class);


    public static final String FILENAME = "filename";
    public static final String CLASSNAME = "dataProviderImplClassName";
    public static final String RESET_AT_EOF = "resetAtEOF";
    public static final String TAB_NUMBER = "tabNumber";
    public static final String FIELDS = "fieldNames";
    public static final String IS_FIRST_LINE_HEADER = "isFirstLineHeader";

    public DataProviderBeanInfoSupport(Class className) {
        super(className);
        log.info("Bean Info super is done for "+className.getCanonicalName());
        log.info("creating group");
        setProperties();
    }

    public PropertyDescriptor manageFileNameDescriptor() {
        return manageStringPropertyWithDefaultValue(FILENAME,"<ENTER FILE NAME HERE>");
    }

    public PropertyDescriptor manageClassNameDescriptor() {
        return manageStringPropertyWithDefaultValue(CLASSNAME,"org.qamation.data.provider.DataProviderExcelAdapter");
    }

    public PropertyDescriptor manageResetAtEOFDescriptor() {
        return manageBooleanPropertyWithDefaultValue(RESET_AT_EOF,Boolean.FALSE);
    }


    public PropertyDescriptor manageTabNamberDescriptor() {
        return manageStringPropertyWithDefaultValue(TAB_NUMBER,"0");
    }

    public PropertyDescriptor manageFieldsDescriptor() {
        return manageStringPropertyWithDefaultValue(FIELDS,"");
    }

    public PropertyDescriptor manageIsFirstLineHeaderDescriptor() {
        return manageBooleanPropertyWithDefaultValue(IS_FIRST_LINE_HEADER,true);
    }


    public PropertyDescriptor manageStringPropertyWithDefaultValue(String propertyName,String defaultValue) {
        PropertyDescriptor p = property(propertyName);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, defaultValue);
        return p;
    }

    public PropertyDescriptor manageStringPropertyNoDefaultValue(String propertyName) {
        PropertyDescriptor p = property(propertyName);
        p.setValue(NOT_UNDEFINED,Boolean.FALSE);
        return p;
    }

    public PropertyDescriptor manageBooleanPropertyWithDefaultValue(String propertyName,Boolean defaultValue) {
        PropertyDescriptor p = property(propertyName);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, defaultValue);
        return p;
    }

    abstract protected void setProperties();


}
