package opencart.pages;

import opencart.core.DriverPool;
import opencart.core.WebElementWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    static {
        // Properties loaded via ConfigLoader
    }

    public BasePage() {
        this.driver = DriverPool.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new opencart.core.Decorator(driver), this);
    }

    protected WebElementWrapper wrap(WebElement element) {
        return WebElementWrapper.wrap(element);
    }

    public String getBaseUrl() {
        return opencart.utils.ConfigLoader.getInstance().getProperty("base.url", "http://localhost/opencart/");
    }

    public void navigateTo(String path) {
        driver.get(getBaseUrl() + path);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
