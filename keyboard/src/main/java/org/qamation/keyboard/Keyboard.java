package org.qamation.keyboard;

public interface Keyboard extends org.openqa.selenium.interactions.Keyboard {
	public void sendKeysAt(String location, CharSequence... keys);
	public void sendSpecialKeys(String keys);
	//public void sendSpecialkeysAt(String location, String keys);
}
