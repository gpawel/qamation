package org.qamation.jmeter.config.data.provider;

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
        createPropertyGroup("dataprovider_properties", new String[] {FILENAME,CLASSNAME,RESET,SOURCENAME,SHAREMODE});
        PropertyDescriptor p = manageFileNamePropertyDescriptor();
        p = manageClassNamePropertyDescriptor();
        p = manageSourcePropertyDescriptor();
        p = manageResetPropertyDescriptor();
        p = manageShareModePropertyDescriptor();
    }
}
