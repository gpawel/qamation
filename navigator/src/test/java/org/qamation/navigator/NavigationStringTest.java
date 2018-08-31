package org.qamation.navigator;

import org.qamation.utils.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NavigationStringTest {
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {	
	}
	
	private void printTokens(String[] tokens) {
		for (int i=0; i < tokens.length; i++) {
			System.out.println(" > "+tokens[i]);
		}
	}
	
	private String[] parseLine(String line,String delim) {
		NavigationString ns = new NavigationString(line, delim);
		System.out.println("RegExp: "+ns.getRegExp());
		String[] tokens = ns.getNavigationSequence();
		printTokens(tokens);
		return tokens;
	}
	
	@Test
	public void parseNavigationTest(){
		String navigation = "01,01,{F2},+{F3}";
		String[] tokens = parseLine(navigation,",");
		Assert.assertEquals(tokens.length,4);
		
	}
	
	@Test
	public void parsNavigationWithDelimTest() {
		String line = "01\t01\t\"1717,18\"\t{F6}";
		String[] tokens = parseLine(line,"\t");
		Assert.assertEquals("01",tokens[0]);
		Assert.assertEquals("1717,18",tokens[2]);
		Assert.assertEquals("{F6}",tokens[3]);
	}

	@Test
	public void parsNavigationWithDelimTestsSingleQuote() {
		String line = "01,01,'1717,18',{F6}";
		String[] tokens = parseLine(line,",");
		Assert.assertEquals("01",tokens[0]);
		Assert.assertEquals("'1717,18'",tokens[2]);
		Assert.assertEquals("{F6}",tokens[3]);
	}
	
	@Test
	public void parseNavigationWithDoubleQuoteInside() {
		String line = "\"this is a 4\" stick\",01,01,Shift+F4";
		String[] tokens=parseLine(line,",");
		Assert.assertEquals("this is a 4\" stick",tokens[0]);
		line = "01\t02\t\"this is 5\" stick\"\t{F15}";
		tokens=parseLine(line,"\t");
		Assert.assertEquals("this is 5\" stick",tokens[2]);
	}
	
	@Test
	public void parseNavigationWithSingleQuotesInside() {
		String line = "this is a 4' stick,01,01,Shift+F4";
		String[] tokens = parseLine(line,",");
		Assert.assertEquals("this is a 4' stick",tokens[0]);
	}
	
	@Test
	public void extractFromCurlyBrackets() {
		String s = "{asfas}";		
		String result = StringUtils.extractContentFromCurlyBruckets(s);
		Assert.assertEquals("asfas",result);
		s="blablabla";
		result = StringUtils.extractContentFromCurlyBruckets(s);
		Assert.assertEquals("blablabla",result);
		Assert.assertEquals("blablabla",s);
	}
	
	@Test
	public void parseEmptyNavigationLine() {
		String line = "";
		NavigationString ns = new NavigationString(line," ");
		String[] tokens = ns.getNavigationSequence();
		Assert.assertEquals(0, tokens.length);
	}


	
	@Test
	public void parseOnlyOneTokenInNavigationLine() {
		String line = "01";
		NavigationString ns = new NavigationString(line,"\\.");
		String[] tokens = ns.getNavigationSequence();
		Assert.assertEquals(1, tokens.length);
	}
	@Test
	public void parseTwoTokensNavigationLine() {
		String line = "01.01";
		NavigationString ns = new NavigationString(line,"\\.");
		String[] tokens = ns.getNavigationSequence();
		Assert.assertEquals(2, tokens.length);
	}
	@Test
	public void parseThreeTokensNavigationLine() {
		String line = "01.01.03";
		NavigationString ns = new NavigationString(line,"\\.");
		String[] tokens = ns.getNavigationSequence();
		Assert.assertEquals(3, tokens.length);
	}

	@Test
	public void parseNavigationWithMouse() {
	    String line = "01.02.<@!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
	    NavigationString ns = new NavigationString(line,"\\.");
	    String[] tokens = ns.getNavigationSequence();
	    Assert.assertEquals(4,tokens.length);
	    Assert.assertTrue(tokens[2].equalsIgnoreCase("<@!{xpath=\"/*[@id='login']\"}>"));
	    Assert.assertTrue(tokens[0].equalsIgnoreCase("01"));
	    Assert.assertEquals("02",tokens[1]);
	    Assert.assertEquals("bla{SPACE}bla",tokens[3]);
	}

	@Test
	public void parseNavigationWithOnlyOneMouseNavigation() {
		String line = "<@!{xpath=\"/*[@id='login']\"}>";
		NavigationString ns = new NavigationString(line,"\\.");
		String[] tokens = ns.getNavigationSequence();
		Assert.assertEquals(1,tokens.length);
		Assert.assertTrue(tokens[0].equalsIgnoreCase("<@!{xpath=\"/*[@id='login']\"}>"));
	}

	@Test
	public void parseNavigationOnlyMouseNavigation() {
		String line = "<@!{xpath=\"/*[@id='login']\"}>|<@!{xpath=\"/*[@id='password']\"}>";
		String tokens[] = parseLine(line,"\\|");
		Assert.assertEquals(2,tokens.length);
		Assert.assertEquals("<@!{xpath=\"/*[@id='login']\"}>",tokens[0]);
		Assert.assertEquals("<@!{xpath=\"/*[@id='password']\"}>",tokens[1]);
	}

	@Test
	public void parseNavigationOnlyMouseNavigation2() {
		String line = "<@!{xpath=\"/*[@id='login']\"}>.<@!{xpath=\"/*[@id='password']\"}>";
		String tokens[] = parseLine(line,"\\.");
		Assert.assertEquals(2,tokens.length);
		Assert.assertEquals("<@!{xpath=\"/*[@id='login']\"}>",tokens[0]);
		Assert.assertEquals("<@!{xpath=\"/*[@id='password']\"}>",tokens[1]);
	}

	@Test
	public void parseNavigationOnlyMouseNavigation3() {
		String line = "<@!{xpath=\"/*[@id='login']\"}> <@!{xpath=\"/*[@id='password']\"}>";
		String tokens[] = parseLine(line,"\\.");
		Assert.assertEquals(1,tokens.length);
		Assert.assertEquals("<@!{xpath=\"/*[@id='login']\"}> <@!{xpath=\"/*[@id='password']\"}>",tokens[0]);

	}


	@Test
	public void splitNavigationWithMouseAction1() {
		String line = "<@!{xpath=/*[contains(text(),'Hi. Sign in')]}>.Jhon {TAB} Smith.{ENTER}";
		String[] tokens = parseLine(line,"\\.");
		Assert.assertEquals("<@!{xpath=/*[contains(text(),'Hi. Sign in')]}>",tokens[0]);
	}

	@Test
	public void splitNavigationWithMouseAction4() {
		String line = "<@!{xpath=/*[contains(text(),'Hi. Sign in')]}>";
		String[] tokens = parseLine(line,"\\.");
		Assert.assertEquals("<@!{xpath=/*[contains(text(),'Hi. Sign in')]}>",tokens[0]);
	}
}
