import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.qamation.mouse.MouseActionPatterns;
import org.qamation.webdriver.utils.LocatorFactory;

import static org.junit.Assert.*;

public class MouseActionPatternsTest {

    @Test
    public void getMouseNavigationString0() {
        String line = "01.02.<@!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found=MouseActionPatterns.extractMouseAction(line);
        assertEquals("!",found[1]);
        found = MouseActionPatterns.extractBYString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getMouseNavigaionString1() {
        String line = "<@!!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found=MouseActionPatterns.extractMouseAction(line);
        assertEquals("!!",found[1]);
        found = MouseActionPatterns.extractBYString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getMouseNavigationString2() {
        String line = "01.02.<@?{xpath=\"/*[@id='login']\"}>";
        String[] found=MouseActionPatterns.extractMouseAction(line);
        assertEquals("?",found[1]);
        found = MouseActionPatterns.extractBYString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getMouseNavigationString3() {
        String line = "<@^{xpath=\"/*[@id='login']\"}>";
        assertTrue(MouseActionPatterns.isMouseNavigation(line));
        String[] found=MouseActionPatterns.extractMouseAction(line);
        assertEquals("^",found[1]);
        assertTrue(MouseActionPatterns.hasMouseBYString(line));
        found = MouseActionPatterns.extractBYString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getMouseNavigationString4() {
        String line = "<@_>";
        assertTrue(MouseActionPatterns.isMouseNavigation(line));
        String[] found=MouseActionPatterns.extractMouseAction(line);
        assertEquals("_",found[1]);
        assertFalse(MouseActionPatterns.hasMouseBYString(line));
    }

    @Test
    public void getMouseMoveToCoordinates() {
        String line = "<@~[10,10]>";
        assertTrue(MouseActionPatterns.isMouseNavigation(line));
        String[] found=MouseActionPatterns.extractMouseAction(line);
        assertEquals("~",found[1]);
        assertFalse(MouseActionPatterns.hasMouseBYString(line));
        assertTrue(MouseActionPatterns.hasMouseCoordinates(line));
        found = MouseActionPatterns.extractCoordinates(line);
        assertEquals("10,10",found[1]);
    }

    @Test
    public void getMouseMoveToPoint() {
        String line = "<@~[5,123]>";
        Point p = MouseActionPatterns.extractPoint(line);
        assertEquals(5,p.x);
        assertEquals(123,p.y);
    }


    @Test
    public void getLocatorFactory() {
        String line = "01.02.<@!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found = MouseActionPatterns.extractBYString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
        By locator = LocatorFactory.getLocator(found[1]);
        assertEquals("By.xpath: \"/*[@id='login']\"",locator.toString());
    }






}
