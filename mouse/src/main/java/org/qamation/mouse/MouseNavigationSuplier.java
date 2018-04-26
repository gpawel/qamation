package org.qamation.mouse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Mouse;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MouseNavigationSuplier {



    public  Action get(WebDriver driver, String mouseNavigation) {
        return a->{Actions actions = new Actions(driver); actions.click();};
    }
}
