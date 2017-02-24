package org.qamation.sikuli;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;

public class SikuliApiTest {
	String oasis_file = "src/main/resources/oasis.PNG";
	String notepad_file = "src/main/resources/notepad.PNG";

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	
	public void startEquity() {
		Screen s = new Screen();
		try {
			// GraphicsEnvironment ge =
			// GraphicsEnvironment.getLocalGraphicsEnvironment();
			// GraphicsDevice[] gdArray = ge.getScreenDevices();

			// Robot r = new Robot();
			// r.getGr
			// s.doubleClick(notepad_file);
			Thread.sleep(1000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void createApp() {
		try {
			App ap = App.open("pcsws C:\\Users\\pavel.gouchtchine\\Desktop\\966.ws");	
			//App ap = App.open("notepad");	
			//Thread.sleep(2000);
			Region ap_reg = ap.waitForWindow(10);	
			//Thread.sleep(2000);
			if (ap_reg != null) System.out.println("App is running with hight: " + ap_reg.h + ", width: " + ap_reg.w + " x: " + ap_reg.x+ " y: " + ap_reg.y);
			//Thread.sleep(2000);
			if (ap.isRunning()) {
				System.out.println("App is running");
				//Thread.sleep(2000);
			}
			ap_reg.type("CTCTEST");
			//Thread.sleep(2000);
			ap_reg.type(Key.TAB);
			//Thread.sleep(2000);
			ap_reg.type("CTCTEST");
			//Thread.sleep(2000);
			ap_reg.type(Key.ENTER);
			//Thread.sleep(2000);
			ap.close();
			
			if (!ap.isRunning())
				System.out.println("App is stopped");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
