package org.qamation.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtils {
	private String input;
	private String regExp;
	private Pattern p;
	private Matcher m;
	
	public RegExpUtils(String input, String regex) {
		this.input = input;
		this.regExp = regex;
		this.p = Pattern.compile(regex);
		this.m = p.matcher(input);
	}
	
	public Matcher getMatcher() {
		return m;
	}

	public String getFindingInFirstGroup(int groupNumber) {
		m.reset();
		m.find();
		String result = m.group(groupNumber);
		return result;
	}
	
	public String[] getAllFindings() {
		m.reset();
		ArrayList<String> list = new ArrayList<String>();
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				list.add(m.group(i));
			}
		}
		return list.toArray(new String[] {});
	}

	public ArrayList<String[]> getAllMatchesAndGroups() {
		m.reset();
		ArrayList<String[]> list = new ArrayList<String[]>();
		while (m.find()) {
			String[] groups = new String[m.groupCount()+1];
			for (int i = 0; i <= m.groupCount(); i++) {
				groups[i] = m.group(i);
			}
			list.add(groups);
		}
		return list;
	}

	public String getMatchesAndGroups(int matchNumber, int groupIndex) {
		m.reset();
		int i = 1;
		while (m.find()) {
			if (i == matchNumber) {
				if (groupIndex <= m.groupCount()) {
					return m.group(groupIndex);
				}
				else throw new RuntimeException("\nProvided groupIndex is out of range found groups.");
			}
			i++;
		}
		return "";
	}



	public void printAllFindings() {
		System.out.println("Input: "+input+"\nRegExp: "+regExp);
		String[] res = getAllFindings();
		if (res.length>0) {
			for (int i = 0; i < res.length; i++) {
				System.out.println("result["+i+"] = "+res[i]);
			}
		}
		else System.out.println("Nothing is found");
	}

	private void printGroups() {
		for (int i = 0; i <= m.groupCount(); i++) {
			String s = m.group(i);
			System.out.println("Group " + i + " returns: " + s);
		}
	}

	public void printFinds() {
		m.reset();
		System.out.println("printing finds");
		while (m.find()) {
			printGroups();
		}
	}
	
	public boolean isInputMatches() {
		return m.matches();
	}
	
	public static int extarctNumberFromString(String input, String regexp) {
		RegExpUtils regexputils = new RegExpUtils(input, regexp);
		Matcher m = regexputils.getMatcher();
		String str = regexputils.getFindingInFirstGroup(1);
		int intNumber = StringUtils.convertStringToInt(str);
		return intNumber;
	}
	
}
