package org.qamation.jmeter.utils.storage.data.provider;

import org.qamation.data.provider.DataProvider;

public class ProviderDescriptor <T extends DataProvider> {

    private T provider;

    private boolean resetAtIterationStart;

    public ProviderDescriptor  (T provider) {
        this.provider = provider;
    }

}
