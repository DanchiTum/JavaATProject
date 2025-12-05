package opencart.pages;

import opencart.core.DriverPool;
import opencart.core.WebElementWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static Properties config;

    static {
        config = new Properties();
        try (InputStream is = BasePage.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null)
                config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BasePage() {
        this.driver = DriverPool.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected WebElementWrapper wrap(WebElement element) {
        return WebElementWrapper.wrap(element);
    }

    public String getBaseUrl() {
        return config.getProperty("base.url", "http://localhost/opencart/");
    }

    public void navigateTo(String path) {
        driver.get(getBaseUrl() + path);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
