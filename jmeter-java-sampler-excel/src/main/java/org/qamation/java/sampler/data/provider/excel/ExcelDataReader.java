package org.qamation.java.sampler.data.provider.excel;

import net.didion.jwnl.data.Exc;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.qamation.excel.utils.ExcelUtils;
import org.qamation.java.sampler.abstracts.AbstractExcelDataProvider;
import org.qamation.utils.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;


import java.util.Iterator;

public class ExcelDataReader extends AbstractExcelDataProvider  {
    private static final Logger log = LoggingManager.getLoggerForClass();

    private final static String DATA_PROVIDER_NAME = "DATA PROVIDER NAME";
    private StringBuffer currentLine;
    private Iterator<String[]> it;
    private String[] fieldNames;
    protected ExcelUtils excelUtils;

    public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        return defaultParameters;
    }
    protected void readSamplerParameters() {
        super.readSamplerParameters();
        it = (Iterator<String[]>) getObjectFromVariables(dataProviderName);
        excelUtils = (ExcelUtils) getObjectFromVariables("EXCELUTILS");
        if (excelUtils == null){
            log.error("Data Provider is null");
            throw new NullPointerException("Data Provider is null");
        }
        fieldNames = excelUtils.getFieldNames();
    }

    @Override
    protected void toDo() {
        if (it.hasNext()) {
            String [] line = it.next();
            saveLineInContext(line);
        }
        setStringIntoVariables(hasNextVarName,String.valueOf(it.hasNext()));
    }
    private void saveLineInContext(String[] line) {
        currentLine = new StringBuffer();
        for (int i=0; i<fieldNames.length; i++) {
            String value = substituteVariables(line[i]);
            setStringIntoVariables(fieldNames[i],value);
            currentLine.append(fieldNames[i]+" = "+value+"\n");
        }
        currentLine.append("hasNext = "+String.valueOf(it.hasNext()));
    }
    @Override
    protected SampleResult assembleTestResult() {
        String message = "LINE READ SUCCESSFULL";
        SampleResult result = setSuccess(null,message,currentLine.toString());
        return result;
    }
    @Override
    protected SampleResult assembleTestFailure(Exception e) {
        SampleResult result = setFailure(null,"Unable to read from data source."+e.toString(), StringUtils.getStackTrace(e));
        if (e instanceof NullPointerException) {
            result.setStopThread(true);
            result.setStopTestNow(true);
            result.setResponseMessage("Data Provider is null");
        }
        return result;
    }


}
