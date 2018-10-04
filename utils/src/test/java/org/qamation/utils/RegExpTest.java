package org.qamation.utils;

import org.junit.Assert;
import org.junit.Test;


public class RegExpTest {

    public String lineWithVarsAndScripts="asdf ${x} a\nd ${__BeanShell(1+1+${var})} s\r\ndfa ${z1} ${__BeanShell(1+${xyz}+1)} \na ${sdf} asa${__time()} sd ${__RandomString(5,abcdefg,myVar)} ";
	public static final String SPLIT_FUNCT_PARAMS_REGEX = "(?mi)(?<!\\\\),";
	private static final String WHITE_SPECE_NOT_IN_QUOTE = "\\s+(?=([^']*'[^']*')*[^']*$)";
	public static final String EXTRACT_VARIABLES_REGEX = "(?mi)(?=\\$\\{[^_])\\$\\{(.*?)\\}";

	@Test
	public void testRegExp() {
		String id_1 = "R33C201";
		String id_2 = "C33R111";
		String p_1="(?i)C([0-9]{1,})";
		String id_3="R12222C1123423"; 
		String p_2="(?i)R([0-9]{1,})C";
		String id_matching = "(?i)R[0-9]{1,}C[0-9]{1,}";
		RegExpUtils utils = new RegExpUtils(id_1,id_matching);
		utils.printAllFindings();
		utils.printFinds();
		Assert.assertTrue(utils.isInputMatches());
		utils = new RegExpUtils(id_2,id_matching);
		//Assert.assertTrue(utils.isInputMatches());
	}
	
	@Test
	public void testExtractVariableName() {
		String str = "${var1} asf ${var2}";
		String regExp1 = "\\$\\{(.??)\\}";
		String regExp2 = "\\$\\{(.*?)\\}";
		String regExp3 = "\\$\\{(.+?)\\}";
		
		RegExpUtils u1 = new RegExpUtils(str,regExp1);
		RegExpUtils u2 = new RegExpUtils(str,regExp2);
		RegExpUtils u3 = new RegExpUtils(str,regExp3);
		String[] result1 = u1.getAllFindings();
		String[] result2 = u2.getAllFindings();
		String[] result3 = u3.getAllFindings();
		u1.printAllFindings();
		u2.printAllFindings();
		u3.printAllFindings();
		Assert.assertEquals(result2[1], "var1");
		Assert.assertEquals(result2[3], "var2");
		Assert.assertEquals(result3[1], "var1");
		Assert.assertEquals(result3[3], "var2");
		
	}
	
	@Test
	public void testExtractVariables() {
		String str = "asdf ${var0} ${var1} asf ${var2} asd";
		String regExp1 = "\\$\\{(.??)\\}";
		String regExp2 = "\\$\\{(.*?)\\}";
		String regExp3 = "\\$\\{(.+?)\\}";
		
		RegExpUtils u1 = new RegExpUtils(str,regExp1);
		RegExpUtils u2 = new RegExpUtils(str,regExp2);
		RegExpUtils u3 = new RegExpUtils(str,regExp3);
		String[] result1 = u1.getAllFindings();
		String[] result2 = u2.getAllFindings();
		String[] result3 = u3.getAllFindings();
		u1.printAllFindings();
		u2.printAllFindings();
		u3.printAllFindings();
		Assert.assertEquals(result2[5], "var2");
		Assert.assertEquals(result2[1], "var0");
		Assert.assertEquals(result2[3], "var1");
		Assert.assertEquals(result3[5], "var2");
		Assert.assertEquals(result3[1], "var0");
		Assert.assertEquals(result3[3], "var1");
	}
	
	@Test
	public void testExtractFromSingleVariable() {
		String str = "${var0}";
		String regExp1 = "\\$\\{(.??)\\}";
		String regExp2 = "\\$\\{(.*?)\\}";
		String regExp3 = "\\$\\{(.+?)\\}";
		
		RegExpUtils u1 = new RegExpUtils(str,regExp1);
		RegExpUtils u2 = new RegExpUtils(str,regExp2);
		RegExpUtils u3 = new RegExpUtils(str,regExp3);
		String[] result1 = u1.getAllFindings();
		String[] result2 = u2.getAllFindings();
		String[] result3 = u3.getAllFindings();
		u1.printAllFindings();
		u2.printAllFindings();
		u3.printAllFindings();
		Assert.assertEquals("var0", result2[1]);
	}
	@Test
	public void testNoVariable() {
		String str = "asdfas sa ${ a df";
		String regExp1 = "\\$\\{(.??)\\}";
		String regExp2 = "\\$\\{(.*?)\\}";
		String regExp3 = "\\$\\{(.+?)\\}";
		
		RegExpUtils u1 = new RegExpUtils(str,regExp1);
		RegExpUtils u2 = new RegExpUtils(str,regExp2);
		RegExpUtils u3 = new RegExpUtils(str,regExp3);
		String[] result1 = u1.getAllFindings();
		String[] result2 = u2.getAllFindings();
		String[] result3 = u3.getAllFindings();
		u1.printAllFindings();
		u2.printAllFindings();
		u3.printAllFindings();
		//Assert.assertEquals("var0", result2[1]);
	}
	
	@Test
	public void replaceSingleVariable() {
		String str = "${varName}";
		String value = "blablabla";
		String result = str.replace(str, value);
		System.out.println(result);
	}

	@Test
	public void replaceWhiteSpaces() {
		String str =" //* [text()='Sign In'] ";
		String expected = "//*[text()='Sign In']";
		str = str.replaceAll(WHITE_SPECE_NOT_IN_QUOTE,"");
		Assert.assertEquals(expected,str);
	}

	@Test
	public void replaceWithValue() {
		String regExp = "(?i)with value";
		String input = "//* with value ";
		String replacement = "\\[text()='\\$\\{value\\}'\\]";
		//String replacement = "uyuyuuu";
		String result = input.replaceAll(regExp,replacement);
		Assert.assertEquals("//* "+replacement+" ",result);
	}

	@Test
	public void testHasVariables() {
		//String[]  results = find(lineWithVarsAndScripts,EXTRACT_VARIABLES_REGEX);
		//printArray(results);
		RegExpUtils ru = new RegExpUtils(lineWithVarsAndScripts,EXTRACT_VARIABLES_REGEX);
		ru.printAllFindings();
	}









	
}
