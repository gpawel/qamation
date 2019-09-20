package org.qamation.mouse;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.qamation.webdriver.utils.LocatorFactory;
import java.util.ArrayList;
import java.util.List;

public class MouseEmulator {
    private WebDriver driver;

    public MouseEmulator(WebDriver driver) {
        this.driver = driver;
    }

    public Action getAction(String mouseActionString) {
        MouseActionSupplier supplier = getMouseActionSupplier(mouseActionString);
        String[] actionStrings = MouseActionPatterns.extractMouseAction(mouseActionString);
        if (actionStrings.length != 2 ) throw new RuntimeException(mouseActionString+" must have one mouse action specification");
        return supplier.getAction(actionStrings[1]);
    }

    public MouseActionSupplier getMouseActionSupplier(String mouseActionString) {
        boolean stop = ! MouseActionPatterns.isMouseAction(mouseActionString);
        if (stop) {
            throw new RuntimeException(mouseActionString+" does not have an Action");
        }

        boolean hasBy = MouseActionPatterns.hasMouseBYString(mouseActionString);
        boolean hasPoint = MouseActionPatterns.hasMouseCoordinates(mouseActionString);
        if (hasBy && hasPoint) {
            WebElement[] els = getWebElements(mouseActionString);
            Point p = getPoint(mouseActionString);
            return new MouseActionSupplier(driver,els[0],p.x,p.y);
        }
        if (hasBy) {
            WebElement[] els = getWebElements(mouseActionString);
            if (els.length==1) return new MouseActionSupplier(driver,els[0]);
            return new MouseActionSupplier(driver,els[0],els[1]);
        }
        if (hasPoint) {
            Point p = getPoint(mouseActionString);
            return new MouseActionSupplier(driver,p.x,p.y);
        }
        throw new RuntimeException(mouseActionString+" does not have enugh information to produce MouseActionSupplier instance");
    }

    private WebElement[] getWebElements(String mouseActionString) {
        String[] locations = MouseActionPatterns.extractBYString(mouseActionString);
        List<WebElement> els = new ArrayList<>();
        for (int i=1; i<locations.length; i+=2) {
            By by = LocatorFactory.getLocator(locations[i]);
            List<WebElement> elements = driver.findElements(by);
                     if (elements.size() == 0) throw new RuntimeException("Nothing is found by location: "+locations[i]);
                     if (elements.size()!=1) throw new RuntimeException(locations[i]+" must point to only one web element");
            els.addAll(elements);
        }
        if (els.size()>2) throw new RuntimeException(mouseActionString+" must points to max two web elements");
        return els.toArray(new WebElement[] {});
    }

    private Point getPoint(String mouseActionString) {
        return MouseActionPatterns.extractPoint(mouseActionString);
    }


}
