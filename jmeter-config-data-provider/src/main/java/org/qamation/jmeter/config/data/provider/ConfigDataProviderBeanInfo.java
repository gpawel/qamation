package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.qamation.jmeter.config.data.provider.excel.ExcelDataProvider;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel on 2017-04-22.
 */
public class ConfigDataProviderBeanInfo extends BeanInfoSupport {

    private static final Logger log = LoggingManager.getLoggerForClass();

    private static final String FILENAME = "filename";


    public ConfigDataProviderBeanInfo() {
        super(ConfigDataProvider.class);

        log.info("Bean Info super is done.");
        log.info("creating group");
        createPropertyGroup("dataprovider_properties", new String[] {FILENAME});

        PropertyDescriptor p = property(FILENAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, "<ENTER FILE NAME HERE>");
        p.setValue(NOT_EXPRESSION,Boolean.TRUE);
        p.setValue(RESOURCE_BUNDLE, getBeanDescriptor().getValue(RESOURCE_BUNDLE));
    }

}
