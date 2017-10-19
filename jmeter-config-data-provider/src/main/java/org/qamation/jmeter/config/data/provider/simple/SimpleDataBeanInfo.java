package org.qamation.jmeter.config.data.provider.simple;

import org.qamation.jmeter.config.data.provider.AbstractDataBeanInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel on 2017-04-22.
 */
public class SimpleDataBeanInfo extends AbstractDataBeanInfo {

    private static final Logger log = LoggerFactory.getLogger(SimpleDataBeanInfo.class);


    public SimpleDataBeanInfo() {
        super(SimpleData.class);
    }



    protected void setProperties() {
        createPropertyGroup("General", new String[] {FILENAME,CLASSNAME,RESET,SOURCENAME,SHAREMODE});
        PropertyDescriptor p = manageFileNamePropertyDescriptor();
        p = manageClassNamePropertyDescriptor();
        p = manageSourcePropertyDescriptor();
        p = manageResetPropertyDescriptor();
        p = manageShareModePropertyDescriptor();
    }

    private PropertyDescriptor manageSourcePropertyDescriptor() {
        PropertyDescriptor p=property(SOURCENAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,"SIMPLEDATA");
        return p;
    }


}
