package opencart.pages;

import opencart.core.WebElementWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(name = "search")
    private WebElementWrapper searchInput;

    @FindBy(css = "button.btn.btn-light.btn-lg")
    private WebElementWrapper searchButton;

    @FindBy(css = "#content .product-layout")
    private List<WebElement> featuredProducts;

    @FindBy(css = ".navbar-nav > li > a")
    private List<WebElement> menuItems;

    @FindBy(id = "cart")
    private WebElementWrapper cartButton;

    @FindBy(css = "#cart .dropdown-menu")
    private WebElementWrapper cartDropdown;

    public HomePage open() {
        navigateTo("");
        return this;
    }

    public SearchPage search(String query) {
        searchInput.waitAndSendKeys(query);
        searchButton.waitAndClick();
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
        cartButton.waitAndClick();
    }

    public boolean isCartDropdownVisible() {
        return cartDropdown.isDisplayedSafe();
    }
}
