package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.gui.TypeEditor;
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

        PropertyDescriptor p = property(FILENAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, "<ENTER FILE NAME HERE>");

        p = property(CLASSNAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,"ENTER CLASS NAME IMPLEMENTING DataProvider HERE");

        p=property(SOURCENAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,"SIMPLEDATA");

        p = property(RESET);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,Boolean.FALSE);

        p = property(SHAREMODE);
        p.setValue(RESOURCE_BUNDLE, getBeanDescriptor().getValue(RESOURCE_BUNDLE));
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, SHARE_TAGS[SHARE_ALL]);
        p.setValue(NOT_OTHER, Boolean.FALSE);
        p.setValue(NOT_EXPRESSION, Boolean.FALSE);
        p.setValue(TAGS,SHARE_TAGS);
    }

}
