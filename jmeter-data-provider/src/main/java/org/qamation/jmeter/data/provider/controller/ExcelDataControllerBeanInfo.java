package org.qamation.jmeter.data.provider.controller;

import org.apache.jmeter.testbeans.TestBean;
import org.qamation.jmeter.data.provider.DataProviderBeanInfoSupport;

import java.beans.PropertyDescriptor;

public class ExcelDataControllerBeanInfo extends DataProviderBeanInfoSupport {


    public ExcelDataControllerBeanInfo() {
        super(ExcelDataController.class);
    }

    @Override
    protected void setProperties() {
        createPropertyGroup("excel_data_controller", new String[] {FILENAME,CLASSNAME, TAB_NUMBER, FIELDS, IS_FIRST_LINE_HEADER, RESET_AT_EOF});
        PropertyDescriptor p;
        p = manageFileNameDescriptor();
        p = manageClassNameDescriptor();
        p = manageResetAtEOFDescriptor();
        p = manageTabNamberDescriptor();
        p = manageFieldsDescriptor();
        p = manageIsFirstLineHeaderDescriptor();

    }
}
