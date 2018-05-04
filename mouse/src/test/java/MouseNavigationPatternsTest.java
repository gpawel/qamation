import org.junit.Test;
import org.openqa.selenium.By;
import org.qamation.mouse.MouseNavigationPatterns;
import org.qamation.webdriver.utils.LocatorFactory;

import static org.junit.Assert.*;

public class MouseNavigationPatternsTest {

    @Test
    public void getMouseNavigationString0() {
        String line = "01.02.<@!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found=MouseNavigationPatterns.extractMouseNavigation(line);
        assertEquals("!",found[1]);
        found = MouseNavigationPatterns.extractByString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getMouseNavigaionString1() {
        String line = "<@!!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found=MouseNavigationPatterns.extractMouseNavigation(line);
        assertEquals("!!",found[1]);
        found = MouseNavigationPatterns.extractByString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getMouseNavigationString2() {
        String line = "01.02.<@?{xpath=\"/*[@id='login']\"}>";
        String[] found=MouseNavigationPatterns.extractMouseNavigation(line);
        assertEquals("?",found[1]);
        found = MouseNavigationPatterns.extractByString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getMouseNavigationString3() {
        String line = "<@^{xpath=\"/*[@id='login']\"}>";
        assertTrue(MouseNavigationPatterns.isMouseNavigation(line));
        String[] found=MouseNavigationPatterns.extractMouseNavigation(line);
        assertEquals("^",found[1]);
        assertTrue(MouseNavigationPatterns.hasMouseByString(line));
        found = MouseNavigationPatterns.extractByString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getMouseNavigationString4() {
        String line = "<@_>";
        assertTrue(MouseNavigationPatterns.isMouseNavigation(line));
        String[] found=MouseNavigationPatterns.extractMouseNavigation(line);
        assertEquals("_",found[1]);
        assertFalse(MouseNavigationPatterns.hasMouseByString(line));
    }

    @Test
    public void getMouseMoveToCoordinates() {
        String line = "<@~[10,10]>";
        assertTrue(MouseNavigationPatterns.isMouseNavigation(line));
        String[] found=MouseNavigationPatterns.extractMouseNavigation(line);
        assertEquals("~",found[1]);
        assertFalse(MouseNavigationPatterns.hasMouseByString(line));
        assertTrue(MouseNavigationPatterns.hasMouseCoordinates(line));
        found = MouseNavigationPatterns.extractCoordinates(line);
        assertEquals("10,10",found[1]);
    }


    @Test
    public void getLocatorFactory() {
        String line = "01.02.<@!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found = MouseNavigationPatterns.extractByString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
        By locator = LocatorFactory.getLocator(found[1]);
        assertEquals("By.xpath: \"/*[@id='login']\"",locator.toString());
    }






}
