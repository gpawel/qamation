package org.qamation.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class QuoteCharTest {
	@Before
	public void setUp() {}
	
	@After
	public void tearDown() {}
	
	@Test
	public void testQuoteInAString() {
		char quote = '"';
		String testString = "this is a string with \" inside";
		char[] quoteSeq = {'"'};
		String quoteStr = new String(quoteSeq);
		Assert.assertTrue(testString.contains(quoteStr));
		
	}

}
