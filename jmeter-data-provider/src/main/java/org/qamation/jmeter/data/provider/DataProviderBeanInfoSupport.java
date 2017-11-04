package org.qamation.jmeter.data.provider;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.qamation.jmeter.data.provider.utils.DataBeanInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel on 2017-04-22.
 */
public abstract class DataProviderBeanInfoSupport extends BeanInfoSupport {

    private static final Logger log = LoggerFactory.getLogger(DataProviderBeanInfoSupport.class);

    private DataBeanInfoUtils beanUtils = new DataBeanInfoUtils();

    public static final String SHAREMODE = "shareMode";



    public DataProviderBeanInfoSupport(Class className) {
        super(className);
        log.info("Bean Info super is done.");
        log.info("creating group");
        setProperties();
    }







    public PropertyDescriptor manageFileNamePropertyDescriptor() {
        return manageStringPropertyWithDefaultValue(DataBeanInfoUtils.FILENAME,"<ENTER FILE NAME HERE>");
    }

    public PropertyDescriptor manageClassNamePropertyDescriptor() {
        return manageStringPropertyWithDefaultValue(DataBeanInfoUtils.CLASSNAME,"ENTER CLASS NAME IMPLEMENTING DataProvider HERE");
    }



    public PropertyDescriptor manageResetAtEOFPropertyDescriptor() {
        return manageStringPropertyWithDefaultValue(DataBeanInfoUtils.RESET_AT_EOF)
        PropertyDescriptor p = property(DataBeanInfoUtils.RESET_AT_EOF);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,Boolean.FALSE);
        return p;
    }



    protected PropertyDescriptor manageResetAtTestStartDescriptor() {
        PropertyDescriptor p = property(DataBeanInfoUtils.RESET_AT_TEST_START);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,Boolean.FALSE);
        return p;
    }

    public PropertyDescriptor manageStringPropertyWithDefaultValue(String propertyName,String defaultValue) {
        PropertyDescriptor p = property(propertyName);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, defaultValue);
        return p;
    }

    abstract protected void setProperties();


}
