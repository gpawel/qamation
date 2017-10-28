package org.qamation.jmeter.config.data.provider.excel;

import org.qamation.jmeter.config.data.provider.AbstractDataBeanInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;

public class ExcelDataBeanInfo extends AbstractDataBeanInfo {
    private static final Logger log = LoggerFactory.getLogger(ExcelDataBeanInfo.class);

    protected static final String TAB_NUMBER = "tabNumber";
    protected static final String HAS_MORE_VAR_NAME = "hasNextVariableName";

    public ExcelDataBeanInfo () {super(ExcelData.class);}


    @Override
    protected void setProperties() {
        createPropertyGroup("exceldata_properties", new String[] {FILENAME,CLASSNAME, RESET_AT_EOF,SHAREMODE,TAB_NUMBER,HAS_MORE_VAR_NAME});
        PropertyDescriptor p = manageFileNamePropertyDescriptor();
        p = manageClassNamePropertyDescriptor();
        p = manageResetAtEOFPropertyDescriptor();
        p = manageShareModePropertyDescriptor();
        p = manageTabNumberPropertyDescriptor();
        p = manageHasMoreVarName();
    }

    private PropertyDescriptor manageHasMoreVarName() {
        PropertyDescriptor p = property(HAS_MORE_VAR_NAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,"HAS_MORE");
        return p;

    }

    protected PropertyDescriptor manageTabNumberPropertyDescriptor() {
        PropertyDescriptor p = property(TAB_NUMBER);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,0);
        return p;
    }



}
