package org.qamation.mouse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MouseActionSupplier {
    private static Map<String,Supplier<Action>> MOUSE_ACTION_SUPPLIER;

    static {
        final Map<String,Supplier<MouseAction>> actions = new HashMap<>();
        actions.put("!",() -> new Actions(driver).click().build());

    }
}
