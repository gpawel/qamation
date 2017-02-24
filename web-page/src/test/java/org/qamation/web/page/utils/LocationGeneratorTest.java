package org.qamation.web.page.utils;


import org.qamation.webdriver.utils.LocatorFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

public class LocationGeneratorTest {
	String location = "";
	
	@Before
	public void setUp() {}
	
	@After
	public void tearDown() {}
	
	private void verify (By expected, String location) {
		LocatorFactory lu = new LocatorFactory();
		By locator = lu.getLocator(location);
		System.out.print("Comparing "+expected.toString()+" and "+locator.toString());
		assertNotNull(locator);
		assertEquals(expected, locator);
		System.out.println("...OK");
	}
	
	@Test
	public void testById() {
		location = "id=R0C0";
		verify(By.id("R0C0"), location);
	}
	
	@Test
	public void testByComplexId() {
		location = "r1c1";
		verify(By.id(location),location);
	}
	
	@Test
	public void testByClassName() {
		location = "className=this_is_class_name";
		verify(By.className("this_is_class_name"),location);
	}
	
	@Test
	public void testByCSSSelector() {
		location = "cssSelector=this_is_css_selector";
		verify(By.cssSelector("this_is_css_selector"),location);
	}

}
