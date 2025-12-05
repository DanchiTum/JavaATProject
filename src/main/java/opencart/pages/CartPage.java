package opencart.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "#content form table tbody tr")
    private List<WebElement> cartItems;

    @FindBy(css = "#content form table")
    private WebElement cartTable;

    @FindBy(css = ".buttons .pull-right a")
    private WebElement checkoutButton;

    @FindBy(css = "#content p")
    private WebElement emptyCartMessage;

    @FindBy(css = "a[href*='route=checkout/checkout']")
    private WebElement proceedToCheckout;

    public CartPage open() {
        navigateTo("index.php?route=checkout/cart");
        return this;
    }

    public int getCartItemsCount() {
        try {
            return cartItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isCartEmpty() {
        return wrap(emptyCartMessage).safeGetText().contains("empty");
    }

    public void proceedToCheckout() {
        wrap(checkoutButton).waitAndClick();
    }

    public boolean hasItems() {
        return getCartItemsCount() > 0;
    }
}
