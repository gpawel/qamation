package org.qamation.jmeter.data.provider.config;

import org.apache.jmeter.threads.JMeterContext;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.data.provider.DataProviderFactory;
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
        int modeInt = ExcelDataBeanInfo.getShareModeAsInt(shareMode);
        String suffix;
        switch (modeInt) {
            case ExcelDataBeanInfo.SHARE_ALL: {
                suffix = "_all";
                break;
            }
            case ExcelDataBeanInfo.SHARE_GROUP: {
                suffix = context.getThreadGroup().getName();
                break;
            }
            case ExcelDataBeanInfo.SHARE_THREAD: {
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
