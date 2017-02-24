package org.qamation.utils;

import java.util.Formatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class FormatTest {
	
	@Before
	public void setUp() {}
	
	@After
	public void tearDown() {}
	
	@Test
	public void testProductNumberFormat() {		
		Assert.assertEquals("123-4567",formatProdNumber("1234567"));		
		Assert.assertEquals("4000", formatProdNumber("4000"));
		Assert.assertEquals("32", formatProdNumber("32"));
		Assert.assertEquals("12-3456", formatProdNumber("123456"));
		Assert.assertEquals("1-2345", formatProdNumber("12345"));
		//System.out.println("result: "+buff.toString());
	}
	
	private String formatProdNumber(String input) {
		StringBuffer buff = new StringBuffer(input);
		if (buff.length()<5) return input;
		int length = buff.length();
		buff.insert(length-4,"-");
		return buff.toString();
	}
	

}
