package org.qamation.jmeter.config.data.provider;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.event.LoopIterationListener;
import org.apache.jmeter.engine.util.NoConfigMerge;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jorphan.util.JMeterStopThreadException;
import org.qamation.data.provider.DataProvider;
import org.qamation.jmeter.config.Storage;
import org.qamation.jmeter.config.data.provider.simple.SimpleDataBeanInfo;
import org.slf4j.LoggerFactory;

public abstract class AbstractData extends ConfigTestElement
        implements
        TestBean,
        LoopIterationListener,
        NoConfigMerge {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AbstractData.class);

    public static final String SHARE_MODE_ALL = "shareMode.all";
    public static final String SHARE_MODE_GROUP = "shareMode.group";
    public static final String SHARE_MODE_THREAD = "shareMode.thread";

    protected String filename;
    protected String dataProviderImplClassName;

    protected boolean resetAtEOF;
    protected String shareMode;

    protected String getKey() {
        String suffix = getSuffix();
        String key = getFilename() + suffix;
        return key;
    }

    private String getSuffix() {
        String shareMode = getShareMode();
        int modeInt = SimpleDataBeanInfo.getShareModeAsInt(shareMode);
        String suffix;
        switch (modeInt) {
            case SimpleDataBeanInfo.SHARE_ALL: {
                suffix = "";
                break;
            }
            case SimpleDataBeanInfo.SHARE_GROUP: {
                final JMeterContext context = getThreadContext();
                suffix = context.getThreadGroup().getName();
                break;
            }
            case SimpleDataBeanInfo.SHARE_THREAD: {
                final JMeterContext context = getThreadContext();
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

    protected < P extends DataProvider > void isEndReached(P provider) {
        int size = provider.getSize();
        int currentIndex = provider.getCurrentLineIndex();
        if (currentIndex == size) {
            stopIfRequired();
        }
    }

    protected void stopIfRequired() {
        if (!isResetAtEOF()) {
            throw new JMeterStopThreadException("End of data.");
        }
    }

    protected <T extends DataProvider > T getDataProvider() {
        Storage<T> storage = Storage.getStorage();
        String key = getKey();
        T dataProvider;
        if (storage.hasKey(key)) {
            dataProvider = storage.get(key);
        } else {
            dataProvider = callDataProviderFactory();
            storage.put(key, dataProvider);
        }
        return dataProvider;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDataProviderImplClassName() {
        return dataProviderImplClassName;
    }

    public void setDataProviderImplClassName(String dataProviderImplClassName) {
        this.dataProviderImplClassName = dataProviderImplClassName;
    }

    public boolean isResetAtEOF() {
        return resetAtEOF;
    }

    public void setResetAtEOF(boolean resetAtEOF) {
        this.resetAtEOF = resetAtEOF;
    }

    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }

    public abstract <T extends DataProvider> T callDataProviderFactory();
}
