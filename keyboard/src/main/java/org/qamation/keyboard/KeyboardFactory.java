package org.qamation.keyboard;

import org.qamation.webdriver.utils.BasedWebDriverInstanceFactory;
import org.openqa.selenium.WebDriver;

/**
 * Created by Pavel.Gouchtchine on 02/08/2017.
 */
public class KeyboardFactory extends BasedWebDriverInstanceFactory {
    public static Keyboard createKeyboard(String keyboardImplementationClass, WebDriver driver) {
        return (Keyboard) BasedWebDriverInstanceFactory.createInstance(keyboardImplementationClass,driver);
    }
}
