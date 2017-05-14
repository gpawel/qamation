package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel on 2017-04-22.
 */
public class SimpleDataBeanInfo extends BeanInfoSupport {

    private static final Logger log = LoggingManager.getLoggerForClass();

    private static final String FILENAME = "filename";
    private static final String CLASSNAME = "dataProviderImplClassName";
    private static final String RESET = "resetAtEOF";
    private static final String SOURCENAME= "dataSourceName";


    public SimpleDataBeanInfo() {
        super(SimpleData.class);

        log.info("Bean Info super is done.");
        log.info("creating group");

        createPropertyGroup("dataprovider_properties", new String[] {FILENAME,CLASSNAME,RESET,SOURCENAME});

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

    }

}
