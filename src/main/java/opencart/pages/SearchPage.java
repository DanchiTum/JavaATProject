package opencart.pages;

import org.openqa.selenium.By;
import opencart.core.WebElementWrapper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchPage extends BasePage {

    @FindBy(css = "div.product-thumb")
    private List<WebElement> searchResults;

    @FindBy(css = "#content h1")
    private WebElementWrapper pageTitle;

    @FindBy(css = "#content p")
    private WebElementWrapper noResultsMessage;

    @FindBy(id = "input-search")
    private WebElementWrapper searchInput;

    @FindBy(id = "button-search")
    private WebElementWrapper searchButton;

    public SearchPage open(String query) {
        navigateTo("index.php?route=product/search&search=" + query);
        return this;
    }

    public int getResultsCount() {
        return searchResults.size();
    }

    public boolean hasResults() {
        return getResultsCount() > 0;
    }

    public String getPageTitle() {
        return pageTitle.safeGetText();
    }

    public ProductPage clickProduct(int index) {
        if (index < searchResults.size()) {
            WebElement product = searchResults.get(index);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            product.findElement(By.cssSelector(".image a")).click();
        }
        return new ProductPage();
    }

    public void addToCartByIndex(int index) {
        if (index < searchResults.size()) {
            WebElement product = searchResults.get(index);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
            product.findElement(By.cssSelector("button[title='Add to Cart']")).click();
        }
    }

    public void addToCompareByIndex(int index) {
        if (index < searchResults.size()) {
            WebElement product = searchResults.get(index);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {
            }
            product.findElement(By.cssSelector("button[title='Compare this Product']")).click();
        }
    }

    public List<WebElement> getResults() {
        return searchResults;
    }
}
