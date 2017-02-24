package org.qamation.java.sampler.abstracts;

import org.qamation.excel.utils.ExcelUtils;
import org.apache.jmeter.config.Arguments;


public abstract class AbstractExcelDataProvider extends AbstractExtention {

    private final static String DATA_PROVIDER_NAME = "DATA PROVIDER NAME";
    private final static String HAS_NEXT = "ENTER VARIABLE NAME TO KEEP hasNext() VALUE";

    protected String dataProviderName;
    protected String hasNextVarName;

    protected ExcelUtils excelUtils;


    public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(DATA_PROVIDER_NAME,"DATA_PROVIDER_NAME");
        defaultParameters.addArgument(HAS_NEXT,"HAS_NEXT");
        return defaultParameters;
    }
    protected void readSamplerParameters() {
        dataProviderName = "";
        hasNextVarName = "";
        super.readSamplerParameters();
        dataProviderName = getSamplerParameterValue(DATA_PROVIDER_NAME);
        if (dataProviderName.isEmpty()) throw new RuntimeException("DATA PROVIDER NAME CANNOT BE EMPTY");
        hasNextVarName = getSamplerParameterValue(HAS_NEXT);
        if (hasNextVarName.isEmpty()) throw new RuntimeException("HAS_NEXT VARIABLE NAME CANNOT BE EMPTY");

    }
    protected String[] getFieldNames() {
        return excelUtils.getFieldNames();
    }
}
