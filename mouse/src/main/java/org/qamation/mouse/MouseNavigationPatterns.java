package org.qamation.mouse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Mouse;
import org.qamation.utils.RegExpUtils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MouseNavigationPatterns {
    public static final String MOUSE_NAVIGATION_REGEXP = "<@(([!\\?_~\\^]){1,})+.*>";//"<@([!]{1,2}|[\\?_~\\^]).*>";
    public static final String MOUSE_NAVIGATION_COORDINATES_REGEXP = "<@.*\\[(\\d{1,},\\d{1,})\\]>";
    public static final String MOUSE_BYSTRING_REGEXP = "<@.*\\{(.*)\\}>";


    public static final String MOUSE_CLICK = "!";
    public static final String MOUSE_DOUBLE_CLICK = "!!";
    public static final String MOUSE_CONTEXT_CLICK = "?";
    public static final String MOUSE_DOWN = "_";




    public static Action get(WebDriver driver, String mouseNavigationToken) {
        return ()->{Actions actions = new Actions(driver); actions.click();};
    }

    public static String[]  extractMouseNavigation(String navigationString) {
        return getAllFindings(navigationString,MOUSE_NAVIGATION_REGEXP);
    }

    public static String[] extractByString(String mouseNavigation) {
        return getAllFindings(mouseNavigation, MOUSE_BYSTRING_REGEXP);
    }

    public static String [] extractCoordinates(String navigationString) {
        return getAllFindings(navigationString,MOUSE_NAVIGATION_COORDINATES_REGEXP);

    }

    private static String[] getAllFindings(String input, String regExp) {
        RegExpUtils utils = new RegExpUtils(input,regExp);
        utils.printAllFindings();
        return utils.getAllFindings();
    }

    public static boolean isMouseNavigation(String input) {
        return doesMatch(input, MOUSE_NAVIGATION_REGEXP);
    }

    public static boolean hasMouseByString(String input) {
        return doesMatch(input,MOUSE_BYSTRING_REGEXP);
    }

    public static boolean hasMouseCoordinates(String input) {return doesMatch(input,MOUSE_NAVIGATION_COORDINATES_REGEXP);}

    private static boolean doesMatch(String input, String regExp) {
        RegExpUtils utils = new RegExpUtils(input,regExp);
        return utils.isInputMatches();
    }
}
