package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.gui.TypeEditor;
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
    private static final String SOURCENAME = "dataLabel";
    private static final String SHAREMODE = "shareMode";

    // Access needed from CSVDataSet
    static final String[] SHARE_TAGS = new String[3];
    static final int SHARE_ALL    = 0;
    static final int SHARE_GROUP  = 1;
    static final int SHARE_THREAD = 2;

    // Store the resource keys
    static {
        SHARE_TAGS[SHARE_ALL]    = "shareMode.all"; //$NON-NLS-1$
        SHARE_TAGS[SHARE_GROUP]  = "shareMode.group"; //$NON-NLS-1$
        SHARE_TAGS[SHARE_THREAD] = "shareMode.thread"; //$NON-NLS-1$
    }


    public SimpleDataBeanInfo() {
        super(SimpleData.class);

        log.info("Bean Info super is done.");
        log.info("creating group");

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
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, SHARE_TAGS[SHARE_ALL]);
        p.setValue(NOT_OTHER, Boolean.FALSE);
        p.setValue(NOT_EXPRESSION, Boolean.FALSE);
        p.setValue(TAGS,SHARE_TAGS);
    }

    public static int getShareModeAsInt(String mode) {
        if (mode == null || mode.length() == 0){
            return SHARE_ALL; // default (e.g. if test plan does not have definition)
        }
        for (int i = 0; i < SHARE_TAGS.length; i++) {
            if (SHARE_TAGS[i].equals(mode)) {
                return i;
            }
        }
        return -1;
    }

}
