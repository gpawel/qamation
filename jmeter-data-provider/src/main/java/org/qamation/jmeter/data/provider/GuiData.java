package org.qamation.jmeter.data.provider;

public interface GuiData {

    String getFilename();

    String getDataProviderImplClassName();

    boolean isResetAtEOF();

    String getTabNumber();

    String getFieldNames();

    boolean isFirstLineHeader();
}
