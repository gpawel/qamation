package org.qamation.keyboard;

import java.awt.Robot;
import static java.awt.event.KeyEvent.*;
import java.util.ArrayList;

public class KeyboardMouseEmulator {

	private Robot r;

	public KeyboardMouseEmulator() {
		try {
			this.r = new Robot();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void sendKeys(int[] keys) {
		for (int k : keys) {
			r.keyPress(k);
			r.keyRelease(k);
		}

	}

	private int[] convert(ArrayList<Integer> input) {
		Integer[] keys = input.toArray(new Integer[input.size()]);
		int[] result = new int[keys.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = keys[i].intValue();
		}
		return result;
	}

	public void writeString(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			typeChar(c);
		}
		r.delay(500);
	}

	private void typeChar(char c) {
		try {
			if (Character.isUpperCase(c)) {
				r.keyPress(VK_SHIFT);
			}
			r.keyPress(Character.toUpperCase(c));
			r.keyRelease(Character.toUpperCase(c));

			if (Character.isUpperCase(c)) {
				r.keyRelease(VK_SHIFT);
			}
		} catch (IllegalArgumentException e) {
			typeUnicodeDigitsFor(c);
		}
	}
	
	private void typeUnicodeDigitsFor(char c) {
		String unicodeDigits = "0"+String.valueOf(getCodePoint(c));
		r.keyPress(VK_ALT);
		type_PLUSS_Sign();
		for (int i = 0; i < unicodeDigits.length(); i++) {
			int key = Integer.parseInt(unicodeDigits.substring(i, i + 1));
			typeNumPad(key);
		}
		r.keyRelease(VK_ALT);
	}

	private void type_PLUSS_Sign() {
		r.keyPress(VK_SHIFT);
		r.keyPress(VK_EQUALS);
		r.keyRelease(VK_EQUALS);
		r.keyRelease(VK_SHIFT);
	}

	private int getCodePoint(char c) {
		return Character.codePointAt(new char[] {c}, 0);
	}

	private void typeNumPad(int digit) {
		System.out.print("to numpad type: "+digit);
		switch (digit) {
		case 0:
			doType(VK_NUMPAD0);
			break;
		case 1:
			doType(VK_NUMPAD1);
			break;
		case 2:
			doType(VK_NUMPAD2);
			break;
		case 3:
			doType(VK_NUMPAD3);
			break;
		case 4:
			doType(VK_NUMPAD4);
			break;
		case 5:
			doType(VK_NUMPAD5);
			break;
		case 6:
			doType(VK_NUMPAD6);
			break;
		case 7:
			doType(VK_NUMPAD7);
			break;
		case 8:
			doType(VK_NUMPAD8);
			break;
		case 9:
			doType(VK_NUMPAD9);
			break;
		}
		System.out.println();
	}

	private void doType(int... keyCodes) {
		doType(keyCodes, 0, keyCodes.length);
	}

	private void doType(int[] keyCodes, int offset, int length) {
		if (length == 0) {
			return;
		}
		r.keyPress(keyCodes[offset]);
		doType(keyCodes, offset + 1, length - 1);
		r.keyRelease(keyCodes[offset]);
	}

}

