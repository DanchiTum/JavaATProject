package opencart.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WebElementWrapper {
    private final WebElement element;
    private final WebDriver driver;
    private final WebDriverWait wait;

    public WebElementWrapper(WebElement element) {
        this.element = element;
        this.driver = DriverPool.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static WebElementWrapper wrap(WebElement element) {
        return new WebElementWrapper(element);
    }

    public void waitAndClick() {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void waitAndSendKeys(String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    public String safeGetText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isDisplayedSafe() {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public void scrollIntoView() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public WebElement getElement() {
        return element;
    }

    public static WebElementWrapper find(By locator) {
        WebDriver driver = DriverPool.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return new WebElementWrapper(element);
    }

    public static List<WebElement> findAll(By locator) {
        return DriverPool.getDriver().findElements(locator);
    }
}
