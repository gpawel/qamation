package org.qamation.excel.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.qamation.utils.StringUtils;

@Deprecated
public class ExcelUtils {
    private Workbook workBook;
    private int activeSheetIndex;
    private String fileName;
    private Sheet sheet;
    private FormulaEvaluator evaluator;

    private String[] fieldNames;
    private int rowSize;

    public static ExcelUtils getExcelWithHeaderLine(String fileName, int activeSheetIndex) {
        return new ExcelUtils(fileName,activeSheetIndex);
    }
    private ExcelUtils(String fileName, int index) {
        try {
            this.fileName = fileName;
            workBook = WorkbookFactory.create(new File(fileName));
            if (index < 0 || index > workBook.getNumberOfSheets())
                throw new RuntimeException("Sheet index cannot be less than 0 or hight than available sheets.");
            this.activeSheetIndex = index;
            this.sheet = workBook.getSheetAt(activeSheetIndex);
            fieldNames = readFirstLine();
            rowSize = getHeaderLineSize();
            evaluator = workBook.getCreationHelper().createFormulaEvaluator();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unable to create a workbook from " + fileName,ex);
        }
    }
    public static Workbook createNewXLSXWorkBook() {
        return new XSSFWorkbook();
    }
    public static Workbook createNewXLSWorkBook() {
        return new HSSFWorkbook();
    }
    public static Workbook createWorkBookFor(String fileName) {
        if (fileName.endsWith("xlsx")) {
            return createNewXLSXWorkBook();
        }
        return createNewXLSWorkBook();
    }
    public static void createNewExcelFile(String fileName) {
        try {
            checkIfFileNameIsAvailable(fileName);
            Workbook wb = createWorkBookFor(fileName);
            wb.createSheet("Sheet1");
            wb.createSheet("Sheet2");
            wb.createSheet("Sheet3");
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create org.qamation.jmeter.data.provider.controller.excel file for " + fileName,ex);
        }
    }
    public Iterator iterator() {
        return new Iterator<String[]>() {
            private int cursor = 1;
            int availableLines = getNmberOfLinesInActiveWorkSheet();

            @Override
            public boolean hasNext() {
                if (cursor <= availableLines) return true;
                return false;
            }
            @Override
            public String[] next() {
                cursor ++;
                return readValuesFromLine(cursor-1);
            }
        };
    }
    public int getActiveSheetIndex() {
        return activeSheetIndex;
    }

    public String[] readValuesFromLine(int index) {
        Row row = getRow(index);
        if (row == null) {
            return createEmptyRow();
        }
        String[] values = convertRowToStringArray(row);
        return values;
    }
    public int getNumberOfAvailableWorkSheets() {
        return workBook.getNumberOfSheets();
    }
    public int getNmberOfLinesInActiveWorkSheet() {
        return sheet.getLastRowNum();
    }
    public String readValueAt(int rowIndex, int colIndex) {
        Row row = getRow(rowIndex);
        if (row == null) return "";
        Cell c = row.getCell(colIndex, MissingCellPolicy.RETURN_NULL_AND_BLANK);
        if (c == null) return "";
        return c.getStringCellValue();
    }
    public String getFileName() {return fileName;}
    public String[] getFieldNames() {return fieldNames;}
    public void closeWorkbook() {
        try {
            workBook.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close workbook",e);
        }
    }

    private static void checkIfFileNameIsAvailable(String fileName) {
        File f = new File(fileName);
        if (f.isDirectory() || f.exists()) throw new RuntimeException(fileName + " is already exists");
    }
    private String readFromCell(Cell c) {
        if (c == null) return "";
        else return c.getStringCellValue();
    }
    private Row getRow(int rowNumber) {
        if (rowNumber < 0 || rowNumber > sheet.getLastRowNum())
            throw new RuntimeException("Row index cannot be less than 0 or greater than number of rows in the current sheet.");
        Row row = sheet.getRow(rowNumber);
        return row;
    }
    private String[] createEmptyRow() {
        String[] values = new String[rowSize];
        for (int i = 0; i < rowSize; i++) {
            values[i] = "";
        }
        return values;
    }
    private String[] readFirstLine() {
        int headerLength = getHeaderLineSize();
        String[] headers = new String[headerLength];
        for (int i=0; i<headerLength; i++) {
            Cell c = sheet.getRow(0).getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
            headers[i] = c.getStringCellValue();
        }
        return headers;
    }
    private int getHeaderLineSize() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    private String[] convertRowToStringArray(Row row) {
        String[] vals = new String[this.rowSize];
        for (int i=0; i<this.rowSize; i++) {
            Cell c = row.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
            vals[i] = getStringValueFromCell(c);//c.getStringCellValue();
        }
        return vals;
    }

    private String getStringValueFromCell(Cell c) {

        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK: return "";
            case Cell.CELL_TYPE_STRING: return c.getStringCellValue();
            case Cell.CELL_TYPE_BOOLEAN : {
                if (c.getBooleanCellValue()) return "true";
                return "false";
            }
            case Cell.CELL_TYPE_ERROR: return "ERROR: "+String.valueOf(c.getErrorCellValue());
            case Cell.CELL_TYPE_FORMULA: {
                return evaluateCell(c);
            }
            case Cell.CELL_TYPE_NUMERIC: {
                if (DateUtil.isCellDateFormatted(c)) {
                    return String.valueOf(c.getDateCellValue());
                }
                else
                    return String.valueOf(c.getNumericCellValue());
            }
            default: return String.valueOf(c);
        }
    }
    private String evaluateCell(Cell c) {
        Cell newCell = evaluator.evaluateInCell(c);
        if (newCell != null)
            return getStringValueFromCell(newCell);
        return "";
    }
}
