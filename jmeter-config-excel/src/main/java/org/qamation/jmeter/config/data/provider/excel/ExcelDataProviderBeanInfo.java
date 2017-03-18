package org.qamation.jmeter.config.data.provider.excel;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.TestBean;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel.Gouchtchine on 03/10/2017.
 */
public class ExcelDataProviderBeanInfo extends BeanInfoSupport {
    private static final String GROUP_FILE_DATA ="excel_file_data";
    private static final String EXCELFILENAME = "excelFileName";

    public ExcelDataProviderBeanInfo(Class<? extends TestBean> beanClass) {
        super(beanClass);
        createPropertyGroup("excel_file_data",
                new String[] {EXCELFILENAME});
        PropertyDescriptor p = property(EXCELFILENAME);
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT,"<ENTER FILE NAME HERE>");
        p.setValue(NOT_EXPRESSION, Boolean.TRUE);
    }
}
