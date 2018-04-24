import org.junit.Test;
import org.openqa.selenium.By;
import org.qamation.utils.RegExpUtils;
import org.qamation.webdriver.utils.LocatorFactory;

import static org.junit.Assert.*;

public class MouseNavigationPatternsTest {
    public static final String MOUSE_NAVIGATION_REGEXP = "<@([!]{1,2}|[\\?_~\\^]).*>";
    public static final String MOUSE_BYSTRING_REGEXP = "<@.*\\{(.*)\\}>";

    @Test
    public void getMouseNavigationString0() {
        String line = "01.02.<@!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found=extractMouseNavigation(line);
        assertEquals("!",found[1]);
    }

    @Test
    public void getMouseNavigaionString1() {
        String line = "<@!!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found=extractMouseNavigation(line);
        assertEquals("!!",found[1]);
    }

    @Test
    public void getMouseNavigationString2() {
        String line = "01.02.<@?{xpath=\"/*[@id='login']\"}>";
        String[] found=extractMouseNavigation(line);
        assertEquals("?",found[1]);
    }

    @Test
    public void getMouseNavigationString3() {
        String line = "<@^{xpath=\"/*[@id='login']\"}>";
        assertTrue(isMouseNavigation(line));
        String[] found=extractMouseNavigation(line);
        assertEquals("^",found[1]);
        assertTrue(isMouseByString(line));
        found = extractByString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
    }

    @Test
    public void getLocatorFactory() {
        String line = "01.02.<@!{xpath=\"/*[@id='login']\"}>.bla{SPACE}bla";
        String[] found = extractByString(line);
        assertEquals("xpath=\"/*[@id='login']\"",found[1]);
        By locator = LocatorFactory.getLocator(found[1]);
        assertEquals("By.xpath: \"/*[@id='login']\"",locator.toString());
    }



    private String[]  extractMouseNavigation(String navigationString) {
        return getAllFindings(navigationString,MOUSE_NAVIGATION_REGEXP);
    }

    private String[] extractByString(String mouseNavigation) {
        return getAllFindings(mouseNavigation, MOUSE_BYSTRING_REGEXP);
    }

    private String[] getAllFindings(String input, String regExp) {
        RegExpUtils utils = new RegExpUtils(input,regExp);
        utils.printAllFindings();
        return utils.getAllFindings();
    }

    private boolean isMouseNavigation(String input) {
        return doesMatch(input, MOUSE_NAVIGATION_REGEXP);
    }

    private boolean isMouseByString(String input) {
        return doesMatch(input,MOUSE_BYSTRING_REGEXP);
    }

    private boolean doesMatch(String input, String regExp) {
        RegExpUtils utils = new RegExpUtils(input,regExp);
        return utils.isInputMatches();
    }


}
