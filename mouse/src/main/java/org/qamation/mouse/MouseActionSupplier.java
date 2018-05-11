package org.qamation.mouse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MouseActionSupplier {
    private  Map<String,Supplier<Action>> actionsStorage = null;
    private Actions driverActions;

    public MouseActionSupplier(WebDriver driver) {
        actionsStorage = new HashMap<>();
        driverActions = getActions(driver);
        actionsStorage.put("!",()-> driverActions.click().build());
        actionsStorage.put("!_",() -> driverActions.clickAndHold().build());
        actionsStorage.put("?",()-> driverActions.contextClick().build());
        actionsStorage.put("!!",()-> driverActions.doubleClick().build());
        actionsStorage.put("^",()-> driverActions.release().build());
    }

    public MouseActionSupplier(WebDriver driver, WebElement el) {
        actionsStorage = new HashMap<>();
        driverActions = getActions(driver);
        actionsStorage.put("!",()-> driverActions.click(el).build());
        actionsStorage.put("!_",()-> driverActions.clickAndHold(el).build());
        actionsStorage.put("?",() -> driverActions.contextClick(el).build());
        actionsStorage.put("!!",() -> driverActions.contextClick(el).build());
        actionsStorage.put("~",()-> driverActions.moveToElement(el).build());
        actionsStorage.put("^",()-> driverActions.release(el).build());
    }

    public MouseActionSupplier(WebDriver driver, WebElement source, WebElement target) {
        this(driver,source);
        actionsStorage.put("~!",()-> driverActions.dragAndDrop(source,target).build());
    }

    public MouseActionSupplier (WebDriver driver, WebElement el, int x, int y) {
        this(driver, el);
        actionsStorage.put("~!",()-> driverActions.dragAndDropBy(el,x,y).build());
        actionsStorage.put("~^",()-> driverActions.moveToElement(el,x,y).build());

    }

    public MouseActionSupplier(WebDriver driver, int x, int y) {
        this(driver);
        actionsStorage.put("~",()-> driverActions.moveByOffset(x,y).build());
    }

    public Map<String, Supplier<Action>> getActionsStorage() {
        return actionsStorage;
    }

    public Action getAction(String action) {
        Supplier<Action> supplier = actionsStorage.get(action);
        return supplier.get();
    }

    private Actions getActions(WebDriver driver ) {
        return new Actions(driver);
    }


}
