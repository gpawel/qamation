package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel on 2017-04-22.
 */
public abstract class AbstractDataBeanInfo extends BeanInfoSupport {

    private static final Logger log = LoggerFactory.getLogger(AbstractDataBeanInfo.class);

    protected static final String FILENAME = "filename";
    protected static final String CLASSNAME = "dataProviderImplClassName";
    protected static final String RESET = "resetAtEOF";
    protected static final String SOURCENAME = "dataLabel";
    protected static final String SHAREMODE = "shareMode";

    // Access needed from CSVDataSet
    protected static final String[] SHARE_TAGS = new String[3];
    protected static final int SHARE_ALL    = 0;
    protected static final int SHARE_GROUP  = 1;
    protected static final int SHARE_THREAD = 2;

    // Store the resource keys
    static {
        SHARE_TAGS[SHARE_ALL]    = "shareMode.all"; //$NON-NLS-1$
        SHARE_TAGS[SHARE_GROUP]  = "shareMode.group"; //$NON-NLS-1$
        SHARE_TAGS[SHARE_THREAD] = "shareMode.thread"; //$NON-NLS-1$
    }


    public AbstractDataBeanInfo(Class className) {
        super(className);
        log.info("Bean Info super is done.");
        log.info("creating group");
        setProperties();
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

    protected PropertyDescriptor manageFileNamePropertyDescriptor() {
        PropertyDescriptor p = property(FILENAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, "<ENTER FILE NAME HERE>");
        return p;
    }

    protected PropertyDescriptor manageClassNamePropertyDescriptor() {
        PropertyDescriptor p = property(CLASSNAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,"ENTER CLASS NAME IMPLEMENTING DataProvider HERE");
        return p;
    }

    protected PropertyDescriptor manageSourcePropertyDescriptor() {
        PropertyDescriptor p=property(SOURCENAME);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,"SIMPLEDATA");
        return p;
    }

    protected PropertyDescriptor manageResetPropertyDescriptor() {
        PropertyDescriptor p = property(RESET);
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT,Boolean.FALSE);
        return p;
    }

    protected PropertyDescriptor manageShareModePropertyDescriptor() {
        PropertyDescriptor p = property(SHAREMODE);
        p.setValue(RESOURCE_BUNDLE, getBeanDescriptor().getValue(RESOURCE_BUNDLE));
        p.setValue(NOT_UNDEFINED,Boolean.TRUE);
        p.setValue(DEFAULT, SHARE_TAGS[SHARE_ALL]);
        p.setValue(NOT_OTHER, Boolean.FALSE);
        p.setValue(NOT_EXPRESSION, Boolean.FALSE);
        p.setValue(TAGS,SHARE_TAGS);
        return p;
    }

    abstract protected void setProperties();


}
