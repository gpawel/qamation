package org.qamation.java.sampler.tests;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJMeterFunctionExecution {
	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void generateInvoiceNumber() {
		String currentTime = String.valueOf(System.currentTimeMillis());
		String random = (String) currentTime.subSequence(6, 13);
		if (random.startsWith("0")) {
			System.out.println("starts with 0: " + random);

			Random rand = new Random();
			int max = 9;
			int min = 1;
			// nextInt excludes the top value so we have to add 1 to include the
			// top value
			int randomNum = rand.nextInt((max - min) + 1) + min;
			String newChars = String.valueOf(randomNum);
			random = random.replace("0", newChars);
			System.out.println("replaced: " + random);
		}
		System.out.println("INVOICE_NUMBER: " + random);
	}

	@Test
	public void generateAnotherInvoiceNumber() {
		Random rand = new Random();
		int min = 2000000;
		int max = 2001000;
		int randomNum = rand.nextInt((max - min) + 1) + min;
		String random = String.valueOf(randomNum);
		System.out.println("INVOICE_NUMBER: " + random);
	}

}
