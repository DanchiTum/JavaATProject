package opencart.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(css = "#content h1")
    private WebElement productTitle;

    @FindBy(css = ".price-new, #content .price")
    private WebElement productPrice;

    @FindBy(id = "button-cart")
    private WebElement addToCartButton;

    @FindBy(id = "input-quantity")
    private WebElement quantityInput;

    @FindBy(css = "button[data-original-title='Add to Wish List']")
    private WebElement wishlistButton;

    @FindBy(css = "button[data-original-title='Compare this Product']")
    private WebElement compareButton;

    @FindBy(css = ".alert-success")
    private WebElement successAlert;

    public String getProductTitle() {
        return wrap(productTitle).safeGetText();
    }

    public String getProductPrice() {
        return wrap(productPrice).safeGetText();
    }

    public ProductPage setQuantity(int qty) {
        wrap(quantityInput).waitAndSendKeys(String.valueOf(qty));
        return this;
    }

    public ProductPage addToCart() {
        wrap(addToCartButton).waitAndClick();
        return this;
    }

    public ProductPage addToWishlist() {
        wrap(wishlistButton).waitAndClick();
        return this;
    }

    public ProductPage addToCompare() {
        wrap(compareButton).waitAndClick();
        return this;
    }

    public boolean isSuccessAlertVisible() {
        return wrap(successAlert).isDisplayedSafe();
    }

    public String getSuccessMessage() {
        return wrap(successAlert).safeGetText();
    }
}
