package org.qamation.jmeter.data.provider.config;

import org.apache.jmeter.threads.JMeterContext;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.data.provider.DataProviderSupport;
import org.slf4j.LoggerFactory;

public class DataProviderConfigSupport extends DataProviderSupport {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProviderConfigSupport.class);
    public static final String SHARE_MODE_ALL = "shareMode.all";
    public static final String SHARE_MODE_GROUP = "shareMode.group";
    public static final String SHARE_MODE_THREAD = "shareMode.thread";

    private ConfigGuiData configGuiData;
    private String providerName;
    private JMeterContext context;





    public <T extends DataProvider> void iterationStart(ConfigGuiData configGuiData, JMeterContext context) {
        this.configGuiData = configGuiData;
        this.context = context;
        this.providerName = getDataProviderName(context);
        T dataProvider = DataProviderSupport.getDataProvider(providerName,configGuiData);
        putDataIntoJMeterContext(dataProvider, this.context);
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


    @Override
    protected <T extends DataProvider> void dataFinished(T dataProvider) {
        if (configGuiData.isResetAtEOF()) {
            dataProvider.reset();
            putDataIntoJMeterContext(dataProvider, context);
        }
        else throw new JMeterStopThreadException("End of data.");

    }

    private String getDataProviderName(JMeterContext context) {
        String suffix = getSuffix(context);
        String key = configGuiData.getFilename() + suffix;
        log.info("Created Key: "+key);
        return key;
    }
}
