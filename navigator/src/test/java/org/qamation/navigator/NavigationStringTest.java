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
	
	private String[] parseLine(String line) {
		NavigationString ns = new NavigationString(line, ",");
		System.out.println("RegExp: "+ns.getRegExp());
		String[] tokens = ns.getNavigationSequence();
		printTokens(tokens);
		return tokens;
	}
	
	@Test
	public void parseNavigationTest(){
		String navigation = "01,01,{F2},+{F3}";
		String[] tokens = parseLine(navigation);
		Assert.assertEquals(tokens.length,4);
		
	}
	
	@Test
	public void parsNavigationWithDelimTest() {
		String line = "01,01,\"1717,18\",{F6}";
		String[] tokens = parseLine(line);
		Assert.assertEquals(tokens[0],"01");
		Assert.assertEquals(tokens[3],"{F6}");
	}
	
	@Test
	public void parseNavigationWithDoubleQuoteInside() {
		String line = "\"this is a 4\" stick\",01,01,Shift+F4";
		String[] tokens=parseLine(line);
		Assert.assertEquals(tokens[0],"this is a 4\" stick");
		line = "01,02,\"this is 5'' stick\",{F15}";
		tokens=parseLine(line);
		Assert.assertEquals(tokens[2],"this is 5'' stick");
	}
	
	@Test
	public void parseNavigationWithSingleQuotesInside() {
		String line = "this is a 4' stick,01,01,Shift+F4";
		String[] tokens = parseLine(line);
		Assert.assertEquals(tokens[0],"this is a 4' stick");
	}
	
	@Test
	public void extractFromCurlyBrackets() {
		String s = "{asfas}";		
		String result = StringUtils.extractContentFromCurlyBruckets(s);
		Assert.assertEquals(result,"asfas");
		s="blablabla";
		result = StringUtils.extractContentFromCurlyBruckets(s);
		Assert.assertEquals(result,"blablabla");
		Assert.assertEquals(s,"blablabla");
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
		Assert.assertEquals(0, tokens.length);
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
}
