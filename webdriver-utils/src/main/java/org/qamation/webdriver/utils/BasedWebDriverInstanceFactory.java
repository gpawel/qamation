package org.qamation.webdriver.utils;

import org.openqa.selenium.WebDriver;
import java.lang.reflect.Constructor;

/**
 * Created by Pavel.Gouchtchine on 02/08/2017.
 */
public class BasedWebDriverInstanceFactory {
    public static Object createInstance(String className, WebDriver driver) {
        try {
            Class<?> pageClass = Class.forName(className);
            Constructor<?> cons = pageClass.getConstructor(WebDriver.class);
            Object obj = cons.newInstance(driver);
            return obj;
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot create page for class "+className,e);
        }
    }
}
