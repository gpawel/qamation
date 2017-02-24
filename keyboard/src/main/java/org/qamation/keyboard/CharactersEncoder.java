package org.qamation.keyboard;

import java.awt.event.KeyEvent;

public class CharactersEncoder {
	
	public String hexStringFrom(char ch) {
		return Integer.toHexString(ch);
	}
	
	public int getCodePoint(char ch) {
		return Character.codePointAt(new char[] {ch}, 0);
	}
	
	public String hexStringFrom(int n) {
		return Integer.toHexString(n);
	}
	
	
	public static void main (String [] args) {
		CharactersEncoder e = new CharactersEncoder();
		String s = "é"; // é Ф
		char c = s.charAt(0);
		System.out.println("hex string from char: "+e.hexStringFrom(c));
		System.out.println("Hex string using code point: "+e.hexStringFrom(e.getCodePoint(c)));
		System.out.println("Integber number: "+(int)c);
		int pluss = KeyEvent.VK_PLUS;
		System.out.println("int value of the VK_PLUS is: "+pluss);
		
	
	}
	

}
