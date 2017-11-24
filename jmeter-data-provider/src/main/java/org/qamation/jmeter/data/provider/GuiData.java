package org.qamation.jmeter.data.provider;

public interface GuiData {

    String getFilename();

    String getDataProviderImplClassName();

    boolean isResetAtEOF();

    String getShareMode();

    String getTabNumber();

    String getFieldNames();

    boolean isFirstLineHeader();
}
