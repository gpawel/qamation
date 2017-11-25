package org.qamation.jmeter.data.provider.config;

import org.apache.jmeter.threads.JMeterContext;
import org.qamation.jmeter.data.provider.DataProviderSupport;
import org.slf4j.LoggerFactory;

public class DataProviderConfigSupport extends DataProviderSupport {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderConfigSupport.class);
    public static final String SHARE_MODE_ALL = "shareMode.all";
    public static final String SHARE_MODE_GROUP = "shareMode.group";
    public static final String SHARE_MODE_THREAD = "shareMode.thread";

    private ConfigGuiData configGuiData;

    public DataProviderConfigSupport(ConfigGuiData configGuiData, JMeterContext context) {
        super(configGuiData, context);
        this.configGuiData = configGuiData;
    }

    public String getDataProviderName() {
        String suffix = getSuffix(context);
        String key = configGuiData.getFilename() + suffix;
        log.info("Created Key: "+key);
        return key;
    }



    public void iterationStart() {
        String providerName = getDataProviderName();
        putDataIntoJMeterContext(providerName);
    }


    private String getSuffix(JMeterContext context) {
        String shareMode = configGuiData.getShareMode();
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
