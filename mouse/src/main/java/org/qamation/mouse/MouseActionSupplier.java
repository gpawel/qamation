package org.qamation.mouse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;


public class MouseActionSupplier {
    private  Map<String,Supplier<Action>> actionsStorage = null;
    private Actions driverActions;
    private Supplier supplier;



    public MouseActionSupplier(WebDriver driver) {
        init(driver);
        // CLICK
        supplier = ()-> driverActions.click().build();
        actionsStorage.put(MouseTags.CLICK,supplier);
        actionsStorage.put(MouseKeyWords.CLICK,supplier);

        // CLICK AND HOLD
        supplier = () -> driverActions.clickAndHold().build();
        actionsStorage.put(MouseTags.CLICK_AND_HOLD,supplier);
        actionsStorage.put(MouseKeyWords.CLICK_AND_HOLD,supplier);

        // RIGHT CLICK
        supplier = ()-> driverActions.contextClick().build();
        actionsStorage.put(MouseTags.RIGHT_CLICK,supplier);
        actionsStorage.put(MouseKeyWords.RIGHT_CLICK,supplier);

        // DOUBLE CLICK
        supplier = ()-> driverActions.doubleClick().build();
        actionsStorage.put(MouseTags.DOUBLE_CLICK,supplier);
        actionsStorage.put(MouseKeyWords.DOUBLE_CLICK,supplier);

        //release
        supplier = ()-> driverActions.release().build();
        actionsStorage.put(MouseTags.RELEASE,supplier);
        actionsStorage.put(MouseKeyWords.RELEASE,supplier);
    }

    public MouseActionSupplier(WebDriver driver, WebElement el) {
        init(driver);
        //actionsStorage.put("_!",()-> driverActions.click().build());

        // CLICK AT
        supplier = ()-> driverActions.click(el).build();
        actionsStorage.put(MouseTags.CLICK_AT,supplier);
        actionsStorage.put(MouseKeyWords.CLICK_AT,supplier);

        // CLICK AND HOLD AT
        supplier = ()-> driverActions.clickAndHold(el).build();
        actionsStorage.put(MouseTags.CLICK_AND_HOLD_AT,()-> driverActions.clickAndHold(el).build());
        actionsStorage.put(MouseKeyWords.CLICK_AND_HOLD_AT,supplier);

        // right click at
        supplier = () -> driverActions.contextClick(el).build();
        actionsStorage.put(MouseTags.RIGHT_CLICK_AT,supplier);
        actionsStorage.put(MouseKeyWords.RIGHT_CLICK_AT,supplier);


        // double click at
        supplier = () -> driverActions.contextClick(el).build();
        actionsStorage.put(MouseTags.DOUBLE_CLICK_AT,supplier);
        actionsStorage.put(MouseKeyWords.DOUBLE_CLICK_AT,supplier);

        // move to
        supplier = ()-> driverActions.moveToElement(el).build();
        actionsStorage.put(MouseTags.MOVE_TO,supplier);
        actionsStorage.put(MouseKeyWords.MOVE_TO,supplier);

        //release
        supplier = ()-> driverActions.release(el).build();
        actionsStorage.put(MouseTags.RELEASE,supplier);
        actionsStorage.put(MouseKeyWords.RELEASE,supplier);
    }

    public MouseActionSupplier(WebDriver driver, WebElement source, WebElement target) {
        this(driver,source);
        // drag and drop elements
        supplier = ()-> driverActions.dragAndDrop(source,target).build();
        actionsStorage.put(MouseTags.DRAG_AND_DROP_FROM_TO,supplier);
        actionsStorage.put(MouseKeyWords.DRAG_AND_DROP_FROM_TO,supplier);
    }

    public MouseActionSupplier (WebDriver driver, WebElement el, int x, int y) {
        this(driver, el);
        // drag and drop element to
        supplier = ()-> driverActions.dragAndDropBy(el,x,y).build();
        actionsStorage.put(MouseTags.DRAG_AND_DROP_TO_ELEMENT_OFFSET,supplier);
        actionsStorage.put(MouseKeyWords.DRAG_AND_DROP_TO_ELEMENT_OFFSET,supplier);

        // move element to
        supplier = ()-> driverActions.moveToElement(el,x,y).build();
        actionsStorage.put(MouseTags.MOVE_TO_ELEMENT_OFFSET,supplier);
        actionsStorage.put(MouseKeyWords.MOVE_TO_ELEMENT_OFFSET,supplier);

    }

    public MouseActionSupplier(WebDriver driver, int x, int y) {
        this(driver);
        // move by offset
        supplier = ()-> driverActions.moveByOffset(x,y).build();
        actionsStorage.put(MouseTags.MOVE_BY_OFFSET,supplier);
        actionsStorage.put(MouseKeyWords.MOVE_BY_OFFSET,supplier);
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
    private void init(WebDriver driver) {
        actionsStorage = new TreeMap<String, Supplier<Action>>(String.CASE_INSENSITIVE_ORDER);
        driverActions = getActions(driver);
    }


}
