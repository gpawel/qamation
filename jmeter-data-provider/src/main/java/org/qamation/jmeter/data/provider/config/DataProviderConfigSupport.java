package org.qamation.jmeter.data.provider.config;

import org.apache.jmeter.threads.JMeterContext;
import org.qamation.jmeter.data.provider.DataProviderSupport;
import org.qamation.jmeter.data.provider.GuiData;

public class DataProviderConfigSupport extends DataProviderSupport {

    public static final String SHARE_MODE_ALL = "shareMode.all";
    public static final String SHARE_MODE_GROUP = "shareMode.group";
    public static final String SHARE_MODE_THREAD = "shareMode.thread";

    public DataProviderConfigSupport(GuiData guiData, JMeterContext context) {
        super(guiData, context);
    }

    public String getKey() {
        String suffix = getSuffix(context);
        String key = guiData.getFilename() + suffix;
        return key;
    }



    public void iterationStart() {
        String key = getKey();
        putDataIntoJMeterContext(key);

    }


    private String getSuffix(JMeterContext context) {
        String shareMode = guiData.getShareMode();
        int modeInt = ExcelDataConfigBeanInfo.getShareModeAsInt(shareMode);
        String suffix;
        switch (modeInt) {
            case ExcelDataConfigBeanInfo.SHARE_ALL: {
                suffix = "_all";
                break;
            }
            case ExcelDataConfigBeanInfo.SHARE_GROUP: {
                suffix = context.getThreadGroup().getName();
                break;
            }
            case ExcelDataConfigBeanInfo.SHARE_THREAD: {
                suffix = context.getThread().getThreadName();
                break;
            }
            default: {
                suffix = "_all";
                break;
            }
        }
        return suffix;
    }


}
