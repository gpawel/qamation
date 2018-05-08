package org.qamation.mouse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MouseActionSupplier {
    private  Map<String,Supplier<Action>> actions = null;


    public MouseActionSupplier(WebDriver driver) {
        actions = new HashMap<>();
        actions.put("!",()-> new Actions(driver).click().build());
        actions.put("!_",() -> new Actions(driver).clickAndHold().build());
        actions.put("?",()-> new Actions(driver).contextClick().build());
        actions.put("!!",()-> new Actions(driver).doubleClick().build());
        actions.put("^",()-> new Actions(driver).release().build());
    }

    public MouseActionSupplier(WebDriver driver, WebElement el) {
        actions = new HashMap<>();
        actions.put("!",()-> new Actions(driver).click(el).build());
        actions.put("!_",()-> new Actions(driver).clickAndHold(el).build());
        actions.put("?",() -> new Actions(driver).contextClick(el).build());
        actions.put("!!",() -> new Actions(driver).contextClick(el).build());
        actions.put("~",()-> new Actions(driver).moveToElement(el).build());
        actions.put("^",()-> new Actions(driver).release(el).build());
    }

    public MouseActionSupplier(WebDriver driver, WebElement source, WebElement target) {
        this(driver,source);
        actions.put("~!",()-> new Actions(driver).dragAndDrop(source,target).build());
    }

    public MouseActionSupplier (WebDriver driver, WebElement el, int x, int y) {
        this(driver, el);
        actions.put("~!",()-> new Actions(driver).dragAndDropBy(el,x,y).build());
        actions.put("~^",()-> new Actions(driver).moveToElement(el,x,y).build());

    }

    public MouseActionSupplier(WebDriver driver, int x, int y) {
        this(driver);
        actions.put("~",()-> new Actions(driver).moveByOffset(x,y).build());
    }

    public Map<String, Supplier<Action>> getActions() {
        return actions;
    }

    public Action getAction(String action) {
        return actions.get(action).get();
    }


}
