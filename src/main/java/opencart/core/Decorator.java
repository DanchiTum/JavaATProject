package opencart.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;

public class Decorator extends DefaultFieldDecorator {
    private WebDriver driver;

    public Decorator(WebDriver driver) {
        super(new DefaultElementLocatorFactory(driver));
        this.driver = driver;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        Class<?> clazz = decoratableClass(field);
        if (clazz != null) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) {
                return null;
            }
            WebElement proxy = proxyForLocator(loader, locator);
            return createInstance(clazz, proxy);
        }
        return super.decorate(loader, field);
    }

    private Class<?> decoratableClass(Field field) {
        Class<?> clazz = field.getType();
        try {
            clazz.getConstructor(WebDriver.class, WebElement.class);
        } catch (Exception e) {
            return null;
        }
        return clazz;
    }

    private <T> T createInstance(Class<T> clazz, WebElement element) {
        try {
            return clazz.getConstructor(WebDriver.class, WebElement.class)
                    .newInstance(driver, element);
        } catch (Exception e) {
            throw new AssertionError("Cannot create instance of " + clazz, e);
        }
    }
}
