package org.qamation.jmeter.config.data.provider.excel;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.TestBean;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel.Gouchtchine on 03/10/2017.
 */
public class ExcelDataProviderBeanInfo extends BeanInfoSupport {
    private static final String FILENAME = "fileName";

    protected ExcelDataProviderBeanInfo() {
        super(ExcelDataProvider.class);

        createPropertyGroup("file_properties", new String[] {FILENAME});

        PropertyDescriptor p = property(FILENAME);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "<ENTER FILE NAME HERE>");
        p.setValue(NOT_EXPRESSION,Boolean.TRUE);

    }


}
