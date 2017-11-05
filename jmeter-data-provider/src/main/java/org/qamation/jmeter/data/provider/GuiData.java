package org.qamation.jmeter.data.provider;

import org.qamation.data.provider.DataProvider;

public interface GuiData {

    <T extends DataProvider> T callDataProviderFactory();

    String getFilename();

    String getDataProviderImplClassName();

    boolean isResetAtEOF();

    boolean isResetAtTestStart();

    String getShareMode();

    int getTabNumber();

    String getFieldNames();

    boolean isIsfirstLineIsHeaer();
}
