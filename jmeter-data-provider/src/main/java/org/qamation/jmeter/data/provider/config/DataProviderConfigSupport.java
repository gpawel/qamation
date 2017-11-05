package org.qamation.jmeter.data.provider.config;

import org.apache.jmeter.threads.JMeterContext;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
import org.qamation.jmeter.data.provider.DataProviderSupport;
import org.qamation.jmeter.data.provider.GuiData;

public class DataProviderConfigSupport extends DataProviderSupport {

    public static final String SHARE_MODE_ALL = "shareMode.all";
    public static final String SHARE_MODE_GROUP = "shareMode.group";
    public static final String SHARE_MODE_THREAD = "shareMode.thread";

    public DataProviderConfigSupport(GuiData guiData, JMeterContext context){
        super(guiData,context);
    }

     public String getKey() {
        String suffix = getSuffix(context);
        String key = guiData.getFilename() + suffix;
        return key;
    }

    @Override
    public <T extends DataProvider> T callDataProviderFactory() {
        guiData.
    }
        if (guiData.isIsfirstLineIsHeaer()) {
        return DataProviderFactory.createDataProviderInstance(guiData.getDataProviderImplClassName(), guiData.getFilename());
    }

    public void itterationStart() {
        String key = getKey();
        putDataIntoJMeterContext(key);

    }


    private String getSuffix(JMeterContext context) {
        String shareMode = guiData.getShareMode();
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
