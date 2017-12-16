package org.qamation.jmeter.data.provider;

public interface GuiData {

    String getFilename();

    String getDataProviderImplClassName();

    String getTabNumber();

    String getFieldNames();

    boolean isFirstLineHeader();

    String getClassNameForStorage();
}
