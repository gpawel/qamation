package org.qamation.excel.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.qamation.utils.FileUtils;
import org.qamation.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class ExcelReader {
    private Workbook workBook;
    private int activeSheetIndex;
    private String fileName;
    private Sheet sheet;
    private FormulaEvaluator evaluator;
    private String[] fieldNames;
    private int rowSize;
    private File theFile;

    public ExcelReader(String fileName, int index) {
        init(fileName, index);
        this.fieldNames = readFirstLine();
    }

    public ExcelReader(String fileName, int sheetIndex, String[] headers) {
        init(fileName, sheetIndex);
        this.fieldNames = headers;
    }



    public ExcelReader(String fileName) {
        this(fileName, 0);
    }

    public Iterator iterator() {
        return new Iterator<String[]>() {
            private int cursor = 1;
            int availableLines = getNmberOfLinesInActiveWorkSheet();

            @Override
            public boolean hasNext() {
                if (cursor < availableLines) return true;
                return false;
            }

            @Override
            public String[] next() {
                return readValuesFromLine(cursor++);
            }
        };
    }

    public String[][] getData() {
        int lines = getNmberOfLinesInActiveWorkSheet();
        String[][] data = new String[lines][];
        for (int i = 0; i < lines; i++) {
            data[i] = readValuesFromLine(i);
        }
        return data;
    }

    public int getNmberOfLinesInActiveWorkSheet() {
        return sheet.getLastRowNum() + 1;
    }

    public String[] readValuesFromLine(int index) {
        Row row = getRow(index);
        if (row == null) {
            return createEmptyRow();
        }
        String[] values = convertRowToStringArray(row);
        return values;
    }

    public String getFileName() {
        return fileName;
    }

    public int getActiveSheetIndex() {
        return activeSheetIndex;
    }

    public void closeWorkBook() throws IOException {
        closeBook();
        deleteFile();
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    private void closeBook() {
        try {
            if (workBook != null) {
                workBook.close();
                workBook = null;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Unable to close workbook for file: " + theFile.getPath());
        }
    }

    private void deleteFile() {
        if (theFile.exists()) theFile.delete();
    }

    private String[] readFirstLine() {
        int headerLength = getHeaderLineSize();
        String[] headers = new String[headerLength];
        for (int i = 0; i < headerLength; i++) {
            Cell c = sheet.getRow(0).getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            headers[i] = getStringValueFromCell(c);
        }
        return headers;
    }

    private int getHeaderLineSize() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
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

    private String[] convertRowToStringArray(Row row) {
        String[] vals = new String[this.rowSize];
        for (int i = 0; i < this.rowSize; i++) {
            Cell c = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            vals[i] = getStringValueFromCell(c);//c.getStringCellValue();
        }
        return vals;
    }

    private String getStringValueFromCell(Cell c) {

        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_STRING:
                return c.getStringCellValue();
            case Cell.CELL_TYPE_BOOLEAN: {
                if (c.getBooleanCellValue()) return "true";
                return "false";
            }
            case Cell.CELL_TYPE_ERROR:
                return "ERROR: " + String.valueOf(c.getErrorCellValue());
            case Cell.CELL_TYPE_FORMULA: {
                return evaluateCell(c);
            }
            case Cell.CELL_TYPE_NUMERIC: {
                if (DateUtil.isCellDateFormatted(c)) {
                    return String.valueOf(c.getDateCellValue());
                } else
                    return String.valueOf(c.getNumericCellValue());
            }
            default:
                return String.valueOf(c);
        }
    }

    private String evaluateCell(Cell c) {
        Cell newCell = evaluator.evaluateInCell(c);
        if (newCell != null)
            return getStringValueFromCell(newCell);
        return "";
    }

    private void init(String fileName, int index) {
        try {
            this.fileName = getTempFileName(fileName);
            this.theFile = createFile(this.fileName);
            this.workBook = createWorkBook(theFile);
            if (checkIndex(index, workBook.getNumberOfSheets()))
                throw new RuntimeException("Sheet index cannot be less than 0 or hight than available sheets.");
            this.activeSheetIndex = index;
            this.sheet = workBook.getSheetAt(activeSheetIndex);
            this.rowSize = getHeaderLineSize();
            this.evaluator = workBook.getCreationHelper().createFormulaEvaluator();
            addShutDownHook();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create a workbook from " + fileName + "\n" + StringUtils.getStackTrace(ex));
        }
    }

    private String getTempFileName(String originalName) {
        return FileUtils.createTempFile(originalName);
    }

    private File createFile(String name) {
        return new File(name);
    }

    private Workbook createWorkBook(File file) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(file);
    }

    private boolean checkIndex(int givenSheetIndex, int workbookSize) {
        return (givenSheetIndex < 0 || givenSheetIndex > workbookSize);
    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                closeBook();
                deleteFile();
            }
        }, "Shutdown-thread"));
    }


}
