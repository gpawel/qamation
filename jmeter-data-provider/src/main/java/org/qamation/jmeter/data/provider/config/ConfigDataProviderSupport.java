package org.qamation.jmeter.data.provider.config;

import org.apache.jmeter.threads.JMeterContext;
import org.qamation.jmeter.data.provider.DataProviderSupport;

public class ConfigDataProviderSupport extends DataProviderSupport {

    public static final String SHARE_MODE_ALL = "shareMode.all";
    public static final String SHARE_MODE_GROUP = "shareMode.group";
    public static final String SHARE_MODE_THREAD = "shareMode.thread";


    public ConfigDataProviderSupport(String fileName, String dataProviderImplClass, int tabNumber, String[] header) {
        super(fileName, dataProviderImplClass, tabNumber, header);
    }

    public String getKey(JMeterContext context) {
        String suffix = getSuffix(context);
        String key = getFilename() + suffix;
        return key;
    }

    private String getSuffix(JMeterContext context) {
        String shareMode = getShareMode();
        int modeInt = SimpleDataBeanInfo.getShareModeAsInt(shareMode);
        String suffix;
        switch (modeInt) {
            case SimpleDataBeanInfo.SHARE_ALL: {
                suffix = "";
                break;
            }
            case SimpleDataBeanInfo.SHARE_GROUP: {
                suffix = context.getThreadGroup().getName();
                break;
            }
            case SimpleDataBeanInfo.SHARE_THREAD: {
                suffix = context.getThread().getThreadName();
                break;
            }
            default: {
                suffix = "";
                break;
            }
        }
        return suffix;
    }

}
