package org.qamation.excel.utils;

import java.io.File;
import java.net.URL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class ExcelUtilsTests {
	ClassLoader classLoader;
    URL bookXURL;
    URL bookSURL;
    @Before
	public void setUp() {
        classLoader = Thread.currentThread().getContextClassLoader();
        bookXURL = classLoader.getResource("Book1.xlsx");
        bookSURL = classLoader.getResource("Book1.xls");
	}
	
	@After
	public void tearDown() {}
	
	@Test
	public void readExcelFile() {

		try {
			Workbook wbx = WorkbookFactory.create(new File(bookXURL.getFile()));
			Workbook wbs = WorkbookFactory.create(new File(bookSURL.getFile()));
			Assert.assertTrue(wbx.getNumberOfSheets()==wbs.getNumberOfSheets());
			Sheet sheetX = wbx.getSheetAt(0);
			Sheet sheetS = wbs.getSheetAt(0);
			Assert.assertEquals(sheetX.getPhysicalNumberOfRows(), sheetS.getPhysicalNumberOfRows());
			Assert.assertEquals(sheetX.getFirstRowNum(), sheetS.getFirstRowNum());
			Assert.assertEquals(sheetX.getLastRowNum(), sheetS.getLastRowNum());
			for (int i=sheetX.getFirstRowNum(); i < sheetX.getLastRowNum(); i++) {
				Row rx = sheetX.getRow(i);
				Row rs = sheetS.getRow(i);
				if (rx != null && rs != null) {
					Assert.assertEquals(rx.getPhysicalNumberOfCells(), rs.getPhysicalNumberOfCells());
					Assert.assertEquals(rx.getFirstCellNum(), rs.getFirstCellNum());
					Assert.assertEquals(rx.getLastCellNum(), rs.getLastCellNum());
					for (short j = rx.getFirstCellNum(); j < rx.getLastCellNum(); j++) {
						Cell cx = rx.getCell(j, MissingCellPolicy.RETURN_NULL_AND_BLANK);
						Cell cs = rs.getCell(j, MissingCellPolicy.RETURN_NULL_AND_BLANK);
						CellAddress cax = cx.getAddress();
						CellAddress cas = cs.getAddress();
						System.out.println("[" + (i + 1) + "][" + (j + 1) + "] = " + cx.getStringCellValue() + " (" + cs.getStringCellValue() + ")");
						Assert.assertTrue(cax.compareTo(cas) == 0);
					}
				}
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Test
	public void reatAtij() {
		ExcelUtils utils = ExcelUtils.getExcelWithHeaderLine(bookXURL.getFile(),0);
		String v = utils.readValueAt(5, 2);
		Assert.assertTrue("c6".equals(v));
		v=utils.readValueAt(8, 4);
		Assert.assertTrue(v.isEmpty());
		v=utils.readValueAt(8, 3);
		Assert.assertTrue(v.equals("cc"));
		v=utils.readValueAt(7, 5);
		Assert.assertTrue(v.isEmpty());
		v=utils.readValueAt(7,4);
		Assert.assertTrue(v.equals("ss"));
		v = utils.readValueAt(7,10);
		Assert.assertTrue(v.isEmpty());
		v = utils.readValueAt(12, 5);
		Assert.assertTrue(v.isEmpty());
	}	
	
	@Test
	public void readLine() {
		ExcelUtils utils = ExcelUtils.getExcelWithHeaderLine(bookXURL.getFile(),0);
		String[] expected = new String[] {
			"a1"
			,"b1"
			,""
		};
		String [] actual = utils.readValuesFromLine(3);
		Assert.assertArrayEquals(expected, actual);
		expected = new String[] {
			"sdfg"
			,""
			,"ghfj"
			,""
			,""
			,"sds"
		};
		actual = utils.readValuesFromLine(14);
		Assert.assertArrayEquals(expected, actual);
		expected = new String[] {
				""
				,""
				,""
				,""			
		};
		actual = utils.readValuesFromLine(13);
		Assert.assertArrayEquals(expected, actual);
	}


	
	
	
}
