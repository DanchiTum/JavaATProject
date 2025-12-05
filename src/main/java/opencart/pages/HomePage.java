package opencart.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(name = "search")
    private WebElement searchInput;

    @FindBy(css = "button.btn.btn-light.btn-lg")
    private WebElement searchButton;

    @FindBy(css = "#content .product-layout")
    private List<WebElement> featuredProducts;

    @FindBy(css = ".navbar-nav > li > a")
    private List<WebElement> menuItems;

    @FindBy(id = "cart")
    private WebElement cartButton;

    @FindBy(css = "#cart .dropdown-menu")
    private WebElement cartDropdown;

    public HomePage open() {
        navigateTo("");
        return this;
    }

    public SearchPage search(String query) {
        wrap(searchInput).waitAndSendKeys(query);
        wrap(searchButton).waitAndClick();
        return new SearchPage();
    }

    public int getFeaturedProductsCount() {
        return featuredProducts.size();
    }

    public void clickFirstFeaturedProduct() {
        if (!featuredProducts.isEmpty()) {
            featuredProducts.get(0).findElement(
                    org.openqa.selenium.By.cssSelector(".caption a")).click();
        }
    }

    public void openCart() {
        wrap(cartButton).waitAndClick();
    }

    public boolean isCartDropdownVisible() {
        return wrap(cartDropdown).isDisplayedSafe();
    }
}
