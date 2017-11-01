package org.qamation.jmeter.data.provider.controller.excel;

import org.apache.jmeter.testbeans.TestBean;
import org.qamation.jmeter.data.provider.config.AbstractDataConfigBeanInfo;

import java.beans.PropertyDescriptor;

public class ExcelDataControllerBeanInfo extends AbstractDataConfigBeanInfo {

    private final static String FILENAME="fileName";
    private final static String CLASSNAME="dataProviderImplClassName";
    private final static String TAB_NUMBER ="tabNumber";

    public ExcelDataControllerBeanInfo(Class<? extends TestBean> beanClass) {
        super(ExcelDataController.class);
    }

    @Override
    protected void setProperties() {
        createPropertyGroup("excel_data_controller", new String[] {FILENAME,CLASSNAME, TAB_NUMBER});
        PropertyDescriptor p = manageFileNamePropertyDescriptor();
        p = manageClassNamePropertyDescriptor();
        p = manageTabNumberPropertyDescriptor();

    }

    private PropertyDescriptor manageTabNumberPropertyDescriptor() {
        PropertyDescriptor p = property(TAB_NUMBER);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,0);
        return p;
    }

}
