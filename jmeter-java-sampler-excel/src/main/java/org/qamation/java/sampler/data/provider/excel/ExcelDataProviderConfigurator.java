package org.qamation.java.sampler.data.provider.excel;


import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.qamation.excel.utils.ExcelUtils;
import org.qamation.java.sampler.abstracts.AbstractExcelDataProvider;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.qamation.utils.FileUtils;
import org.sikuli.guide.Run;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class ExcelDataProviderConfigurator  extends AbstractExcelDataProvider implements TestStateListener  {
    private static final Logger log = LoggingManager.getLoggerForClass();
    private static final String EXCEL_FILE = "ENTER EXCEL FILE NAME";
    private static final String ACTIVE_WORKSHEET = "ENTER ACTIVE WORK SHEET. EMPTY IF FIRST SHEET";
    private static final String VARIABLES = "HEADER LINE";

    protected String exceFileName;
    protected int workSheetIndex;

    public Arguments getDefaultParameters() {
        Arguments defaultParameters = super.getDefaultParameters();
        defaultParameters.addArgument(EXCEL_FILE,"<excel file name here>");
        defaultParameters.addArgument(ACTIVE_WORKSHEET,"");
        defaultParameters.addArgument(VARIABLES,"FIRST LINE IN THE FILE SHOULD BE A HEADER");
        return defaultParameters;
    }
    protected void readSamplerParameters() {
        super.readSamplerParameters();
        exceFileName = readExcelFileName();
        workSheetIndex = readActiveWorkSheet();

    }
    protected void toDo() {
        try {
            exceFileName = createWorkingFile(exceFileName);
            excelUtils = ExcelUtils.getExcelWithHeaderLine(exceFileName, workSheetIndex);
            setObjectIntoVariables(dataProviderName, excelUtils.iterator());
            setStringIntoVariables(hasNextVarName,String.valueOf(excelUtils.iterator().hasNext()));
            setObjectIntoVariables("EXCELUTILS",excelUtils);
        }
        catch (Exception e) {
            assembleTestFailure(e);
        }
    }



    protected SampleResult assembleTestResult() {
        return setSuccess(null
                ,"Data provider based on file "+exceFileName+" is created.\n It can be accessed through ${"+dataProviderName+"} variable"
                ,null);
    }
    protected SampleResult assembleTestFailure(Exception e) {
        SampleResult result = setFailure(null,"Unable to create a data provider from",e.toString());
        return result;
    }
    private int readActiveWorkSheet() {
        String sheetNum = getSamplerParameterValue(ACTIVE_WORKSHEET);
        if (sheetNum.isEmpty()) return 0;
        else return Integer.parseInt(sheetNum);
    }
    private String readExcelFileName() {
        String fileName;
        fileName = getSamplerParameterValue(EXCEL_FILE);
        if (fileName.isEmpty()) throw new RuntimeException("Excel Data file name is not provided.");
        return fileName;
    }

    private String createWorkingFile(String exceFileName) {
        String suffix = FileUtils.getFileNameExtention(exceFileName);
        String prefix = generateFileNamePrefix();
        String tempFileName = prefix+suffix;
        return tempFileName;
    }

    private String generateFileNamePrefix() {
        SecureRandom sr = new SecureRandom();
        long l = sr.nextLong();
        return String.valueOf(l);
    }

    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String s) {
        testStarted();
    }

    @Override
    public void testEnded() {
        new File(exceFileName).delete();
    }

    @Override
    public void testEnded(String s) {
        testEnded();
    }
}
