package opencart.pages;

import opencart.core.WebElementWrapper;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(css = "#content h1")
    private WebElementWrapper productTitle;

    @FindBy(css = ".price-new, #content .price")
    private WebElementWrapper productPrice;

    @FindBy(id = "button-cart")
    private WebElementWrapper addToCartButton;

    @FindBy(id = "input-quantity")
    private WebElementWrapper quantityInput;

    @FindBy(css = "button[data-original-title='Add to Wish List']")
    private WebElementWrapper wishlistButton;

    @FindBy(css = "button[data-original-title='Compare this Product']")
    private WebElementWrapper compareButton;

    @FindBy(css = ".alert-success")
    private WebElementWrapper successAlert;

    public String getProductTitle() {
        return productTitle.safeGetText();
    }

    public String getProductPrice() {
        return productPrice.safeGetText();
    }

    public ProductPage setQuantity(int qty) {
        quantityInput.waitAndSendKeys(String.valueOf(qty));
        return this;
    }

    public ProductPage addToCart() {
        addToCartButton.waitAndClick();
        return this;
    }

    public ProductPage addToWishlist() {
        wishlistButton.waitAndClick();
        return this;
    }

    public ProductPage addToCompare() {
        compareButton.waitAndClick();
        return this;
    }

    public boolean isSuccessAlertVisible() {
        return successAlert.isDisplayedSafe();
    }

    public String getSuccessMessage() {
        return successAlert.safeGetText();
    }
}
