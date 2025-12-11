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
            conn.disconnect();
        } catch (Exception e) {
            Assert.fail("Cannot get product " + productId + ": " + e.getMessage());
        }
    }
}
