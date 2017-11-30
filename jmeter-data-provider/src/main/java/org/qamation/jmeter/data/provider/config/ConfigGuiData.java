package org.qamation.jmeter.data.provider.config;

import org.qamation.jmeter.data.provider.GuiData;

public interface ConfigGuiData extends GuiData {
    String getShareMode();
    boolean isResetAtEOF();
}
