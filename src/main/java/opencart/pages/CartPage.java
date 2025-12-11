package opencart.pages;

import opencart.core.WebElementWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "#content form table tbody tr")
    private List<WebElement> cartItems;

    @FindBy(css = "#content form table")
    private WebElementWrapper cartTable;

    @FindBy(css = ".buttons .pull-right a")
    private WebElementWrapper checkoutButton;

    @FindBy(css = "#content p")
    private WebElementWrapper emptyCartMessage;

    @FindBy(css = "a[href*='route=checkout/checkout']")
    private WebElementWrapper proceedToCheckout;

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
        return emptyCartMessage.safeGetText().contains("empty");
    }

    public void proceedToCheckout() {
        checkoutButton.waitAndClick();
    }

    public boolean hasItems() {
        return getCartItemsCount() > 0;
    }
}
