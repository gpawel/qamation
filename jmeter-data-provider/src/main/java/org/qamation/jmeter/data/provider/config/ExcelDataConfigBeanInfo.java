package org.qamation.jmeter.data.provider.config;

import org.qamation.jmeter.data.provider.DataProviderBeanInfoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;

/**
 * Created by Pavel on 2017-04-22.
 */
public class ExcelDataConfigBeanInfo extends DataProviderBeanInfoSupport {

    private static final Logger log = LoggerFactory.getLogger(ExcelDataConfigBeanInfo.class);

    // Access needed from CSVDataSet
    protected static final String[] SHARE_TAGS = new String[3];
    protected static final int SHARE_ALL = 0;
    protected static final int SHARE_GROUP = 1;
    protected static final int SHARE_THREAD = 2;

    // Store the resource keys
    static {
        SHARE_TAGS[SHARE_ALL] = "shareMode.all"; //$NON-NLS-1$
        SHARE_TAGS[SHARE_GROUP] = "shareMode.group"; //$NON-NLS-1$
        SHARE_TAGS[SHARE_THREAD] = "shareMode.thread"; //$NON-NLS-1$
    }

    public final String SHAREMODE = "shareMode";

    public static int getShareModeAsInt(String mode) {
        if (mode == null || mode.length() == 0) {
            return SHARE_ALL; // default (e.g. if test plan does not have definition)
        }
        for (int i = 0; i < SHARE_TAGS.length; i++) {
            if (SHARE_TAGS[i].equals(mode)) {
                return i;
            }
        }
        return -1;
    }

    public ExcelDataConfigBeanInfo() {
        super(ExcelDataConfig.class);
    }

    protected void setProperties() {
        createPropertyGroup("General", new String[]{
                FILENAME,
                CLASSNAME,
                RESET_AT_EOF,
                SHAREMODE,
                TAB_NUMBER,
                FIELDS,
                IS_FIRST_LINE_HEADER
        });

        PropertyDescriptor p = manageFileNameDescriptor();
        p = manageClassNameDescriptor();
        p = manageResetAtEOFDescriptor();
        p = manageShareModePropertyDescriptor();
        p = manageTabNamberDescriptor();
        p = manageFieldsDescriptor();
        p = manageIsFirstLineHeaderDescriptor();
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


}
