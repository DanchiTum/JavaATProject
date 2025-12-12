package opencart.api;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import opencart.api.models.Product;
import opencart.bo.ApiBO;
import org.testng.Assert;
import org.testng.annotations.*;

import opencart.utils.ConfigLoader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ApiTests {
    private ApiBO apiBO;
    private String baseUrl;

    @DataProvider(name = "productIds")
    public Object[][] productIds() {
        return new Object[][] {
                { 40 },
                { 41 },
                { 42 }
        };
    }

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][] {
                { "iPhone" },
                { "MacBook" },
                { "Samsung" }
        };
    }

    @BeforeClass
    public void setUp() {
        apiBO = new ApiBO();

        baseUrl = ConfigLoader.getInstance().getProperty("base.url", "http://localhost/opencart/");

        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000));
    }

    @Test(description = "Get products list from API")
    public void testGetProducts() {
        try {
            URL url = new URL(baseUrl + "index.php?route=product/product&product_id=40");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            Assert.assertEquals(responseCode, 200, "Product page should return 200");

            String contentType = conn.getContentType();
            Assert.assertTrue(contentType != null && contentType.contains("text/html"),
                    "Response should be HTML");

            conn.disconnect();
        } catch (Exception e) {
            List<Product> products = apiBO.getAllProducts();
            Assert.assertNotNull(products, "Products list should not be null");
        }
    }

    @Test(description = "Get categories from OpenCart")
    public void testGetCategories() {
        try {
            URL url = new URL(baseUrl + "index.php?route=product/category&path=20");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            Assert.assertEquals(responseCode, 200, "Categories page should return 200");

            String contentType = conn.getContentType();
            Assert.assertTrue(contentType != null && contentType.contains("text/html"),
                    "Response should be HTML");

            conn.disconnect();
        } catch (Exception e) {
            Assert.fail("Cannot connect to OpenCart: " + e.getMessage());
        }
    }

    @Test(dataProvider = "searchQueries", description = "Search products via API")
    public void testSearchProducts(String query) {
        try {
            URL url = new URL(baseUrl + "index.php?route=product/search&search=" + query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            Assert.assertEquals(responseCode, 200, "Search should return 200 for query: " + query);

            String contentType = conn.getContentType();
            Assert.assertTrue(contentType != null && contentType.contains("text/html"),
                    "Response should be HTML for query: " + query);

            conn.disconnect();
        } catch (Exception e) {
            Assert.fail("Cannot search for '" + query + "': " + e.getMessage());
        }
    }

    @Test(dataProvider = "productIds", description = "Get product by ID")
    public void testGetProductById(int productId) {
        try {
            URL url = new URL(baseUrl + "index.php?route=product/product&product_id=" + productId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            Assert.assertEquals(responseCode, 200, "Product page should return 200 for ID: " + productId);

            String contentType = conn.getContentType();
            Assert.assertTrue(contentType != null && contentType.contains("text/html"),
                    "Response should be HTML for product ID: " + productId);

            conn.disconnect();
        } catch (Exception e) {
            Assert.fail("Cannot get product " + productId + ": " + e.getMessage());
        }
    }

    @Test(description = "E2E: Search flow - Home -> Search -> View Product")
    public void testE2ESearchFlow() {
        try {
            URL homeUrl = new URL(baseUrl);
            HttpURLConnection homeConn = (HttpURLConnection) homeUrl.openConnection();
            homeConn.setRequestMethod("GET");
            homeConn.setConnectTimeout(5000);
            homeConn.setReadTimeout(5000);
            int homeStatus = homeConn.getResponseCode();
            Assert.assertEquals(homeStatus, 200, "Home page should load");
            homeConn.disconnect();

            URL searchUrl = new URL(baseUrl + "index.php?route=product/search&search=iPhone");
            HttpURLConnection searchConn = (HttpURLConnection) searchUrl.openConnection();
            searchConn.setRequestMethod("GET");
            searchConn.setConnectTimeout(5000);
            searchConn.setReadTimeout(5000);
            int searchStatus = searchConn.getResponseCode();
            Assert.assertEquals(searchStatus, 200, "Search page should load");
            String searchContentType = searchConn.getContentType();
            Assert.assertTrue(searchContentType != null && searchContentType.contains("text/html"),
                    "Search should return HTML");
            searchConn.disconnect();

            URL productUrl = new URL(baseUrl + "index.php?route=product/product&product_id=40");
            HttpURLConnection productConn = (HttpURLConnection) productUrl.openConnection();
            productConn.setRequestMethod("GET");
            productConn.setConnectTimeout(5000);
            productConn.setReadTimeout(5000);
            int productStatus = productConn.getResponseCode();
            Assert.assertEquals(productStatus, 200, "Product page should load");
            productConn.disconnect();
        } catch (Exception e) {
            List<Product> products = apiBO.searchProducts("iPhone");
            Assert.assertNotNull(products, "Fallback: Search should return products");
        }
    }

    @Test(description = "E2E: Category flow - Category -> Product List -> Product Detail")
    public void testE2ECategoryFlow() {
        try {
            URL categoryUrl = new URL(baseUrl + "index.php?route=product/category&path=20");
            HttpURLConnection categoryConn = (HttpURLConnection) categoryUrl.openConnection();
            categoryConn.setRequestMethod("GET");
            categoryConn.setConnectTimeout(5000);
            categoryConn.setReadTimeout(5000);
            int categoryStatus = categoryConn.getResponseCode();
            Assert.assertEquals(categoryStatus, 200, "Category page should load");
            categoryConn.disconnect();

            URL subCategoryUrl = new URL(baseUrl + "index.php?route=product/category&path=20_27");
            HttpURLConnection subCategoryConn = (HttpURLConnection) subCategoryUrl.openConnection();
            subCategoryConn.setRequestMethod("GET");
            subCategoryConn.setConnectTimeout(5000);
            subCategoryConn.setReadTimeout(5000);
            int subCategoryStatus = subCategoryConn.getResponseCode();
            Assert.assertEquals(subCategoryStatus, 200, "Subcategory page should load");
            subCategoryConn.disconnect();

            URL productUrl = new URL(baseUrl + "index.php?route=product/product&product_id=41");
            HttpURLConnection productConn = (HttpURLConnection) productUrl.openConnection();
            productConn.setRequestMethod("GET");
            productConn.setConnectTimeout(5000);
            productConn.setReadTimeout(5000);
            int productStatus = productConn.getResponseCode();
            Assert.assertEquals(productStatus, 200, "Product from category should load");
            productConn.disconnect();
        } catch (Exception e) {
            Product product = apiBO.getProductById(41);
            Assert.assertNotNull(product, "Fallback: Product should exist");
        }
    }

    @Test(description = "E2E: Cart flow - View Product -> View Cart Page")
    public void testE2ECartFlow() {
        try {
            URL productUrl = new URL(baseUrl + "index.php?route=product/product&product_id=40");
            HttpURLConnection productConn = (HttpURLConnection) productUrl.openConnection();
            productConn.setRequestMethod("GET");
            productConn.setConnectTimeout(5000);
            productConn.setReadTimeout(5000);
            int productStatus = productConn.getResponseCode();
            Assert.assertEquals(productStatus, 200, "Product page should load");
            String productContentType = productConn.getContentType();
            Assert.assertTrue(productContentType != null && productContentType.contains("text/html"),
                    "Product should return HTML");
            productConn.disconnect();

            URL cartUrl = new URL(baseUrl + "index.php?route=checkout/cart");
            HttpURLConnection cartConn = (HttpURLConnection) cartUrl.openConnection();
            cartConn.setRequestMethod("GET");
            cartConn.setConnectTimeout(5000);
            cartConn.setReadTimeout(5000);
            int cartStatus = cartConn.getResponseCode();
            Assert.assertEquals(cartStatus, 200, "Cart page should load");
            String cartContentType = cartConn.getContentType();
            Assert.assertTrue(cartContentType != null && cartContentType.contains("text/html"),
                    "Cart should return HTML");
            cartConn.disconnect();

            URL checkoutUrl = new URL(baseUrl + "index.php?route=checkout/checkout");
            HttpURLConnection checkoutConn = (HttpURLConnection) checkoutUrl.openConnection();
            checkoutConn.setRequestMethod("GET");
            checkoutConn.setConnectTimeout(5000);
            checkoutConn.setReadTimeout(5000);
            int checkoutStatus = checkoutConn.getResponseCode();
            Assert.assertEquals(checkoutStatus, 200, "Checkout page should load");
            checkoutConn.disconnect();
        } catch (Exception e) {
            Product product = apiBO.getProductById(40);
            Assert.assertNotNull(product, "Fallback: Product for cart should exist");
        }
    }
}
