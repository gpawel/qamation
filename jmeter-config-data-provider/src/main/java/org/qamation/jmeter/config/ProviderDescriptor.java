package org.qamation.jmeter.config;

import org.qamation.data.provider.DataProvider;

public class ProviderDescriptor <T extends DataProvider> {

    private T provider;

    private boolean resetAtIterationStart;

    public ProviderDescriptor  (T provider) {
        this.provider = provider;
    }

}
