package org.qamation.keyboard;

import java.awt.Robot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.Command;

import org.qamation.utils.StringUtils;

public class RobotKeyboardEmulator {
	private Robot robot;
	private WebDriver driver;
	
	public RobotKeyboardEmulator(WebDriver driver) {
		try {
			this.driver = driver;
			this.robot = new Robot();
		}
		catch (Exception e) {
			String stackTrack = StringUtils.getStackTrace(e);
			throw new RuntimeException("Unable to create RobotKeyboardEmulator\n\n"+stackTrack,e);
		}
	}
	
	private Command createCommand() {
		SessionId sessionId = ((RemoteWebDriver)driver).getSessionId();
		Command command = new Command(sessionId,"" );
		return command;
	}
	
}
