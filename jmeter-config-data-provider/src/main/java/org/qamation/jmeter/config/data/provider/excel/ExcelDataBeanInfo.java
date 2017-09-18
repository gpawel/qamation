package org.qamation.jmeter.config.data.provider.excel;

import org.qamation.jmeter.config.data.provider.AbstractDataBeanInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;

public class ExcelDataBeanInfo extends AbstractDataBeanInfo {
    private static final Logger log = LoggerFactory.getLogger(ExcelDataBeanInfo.class);

    protected static final String TAB_NUMBER = "tabNumber";

    public ExcelDataBeanInfo () {super(ExcelDataBeanInfo.class);}


    @Override
    protected void setProperties() {
        createPropertyGroup("exceldata_properties", new String[] {FILENAME,CLASSNAME,RESET,SOURCENAME,SHAREMODE,TAB_NUMBER});
        PropertyDescriptor p = manageFileNamePropertyDescriptor();
        p = manageClassNamePropertyDescriptor();
        p = manageSourcePropertyDescriptor();
        p = manageResetPropertyDescriptor();
        p = manageShareModePropertyDescriptor();
        p = manageTabNumberPropertyDescriptor();
    }

    private PropertyDescriptor manageTabNumberPropertyDescriptor() {
        PropertyDescriptor p = property(TAB_NUMBER);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,0);
        return p;
    }

}
