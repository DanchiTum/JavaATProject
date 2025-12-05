package opencart.bo;

import opencart.pages.*;

public class ShopBO {
    private final HomePage homePage;

    public ShopBO() {
        this.homePage = new HomePage();
    }

    public ShopBO openShop() {
        homePage.open();
        return this;
    }

    public SearchPage searchProduct(String productName) {
        return homePage.search(productName);
    }

    public ProductPage viewProduct(String productName) {
        SearchPage searchPage = searchProduct(productName);
        return searchPage.clickProduct(0);
    }

    public ShopBO addProductToCart(String productName, int quantity) {
        ProductPage productPage = viewProduct(productName);
        productPage.setQuantity(quantity).addToCart();
        return this;
    }

    public CartPage openCart() {
        return new CartPage().open();
    }

    public boolean verifyProductInCart() {
        CartPage cartPage = openCart();
        return cartPage.hasItems();
    }

    public ShopBO addProductToCompare(String productName) {
        SearchPage searchPage = searchProduct(productName);
        if (searchPage.hasResults()) {
            searchPage.addToCompareByIndex(0);
        }
        return this;
    }

    public String getProductPrice(String productName) {
        ProductPage productPage = viewProduct(productName);
        return productPage.getProductPrice();
    }
}
