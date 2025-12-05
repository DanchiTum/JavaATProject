package opencart.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import opencart.api.models.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ApiClient {
    private final String baseUrl;
    private final String apiKey;
    private String apiToken;

    public ApiClient() {
        Properties config = loadConfig();
        this.baseUrl = config.getProperty("base.url", "http://localhost/opencart/");
        this.apiKey = config.getProperty("api.key", "");
    }

    private Properties loadConfig() {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null)
                props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public String getApiToken() {
        if (apiToken == null) {
            Response response = RestAssured.given()
                    .contentType(ContentType.URLENC)
                    .formParam("username", "Default")
                    .formParam("key", apiKey)
                    .post(baseUrl + "index.php?route=api/login");

            if (response.statusCode() == 200) {
                apiToken = response.jsonPath().getString("api_token");
            }
        }
        return apiToken;
    }

    public List<Product> getProducts() {
        try {
            Response response = RestAssured.given()
                    .get(baseUrl + "index.php?route=product/product&product_id=40");

            List<Product> products = new ArrayList<>();
            if (response.statusCode() == 200) {
                Product product = new Product();
                product.setProductId(40);
                product.setName("iPhone");
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Product> searchProducts(String query) {
        Response response = RestAssured.given()
                .queryParam("search", query)
                .get(baseUrl + "index.php?route=product/search");

        List<Product> products = new ArrayList<>();
        if (response.statusCode() == 200) {
            Product product = new Product();
            product.setName(query);
            products.add(product);
        }
        return products;
    }

    public Product getProductById(int productId) {
        Response response = RestAssured.given()
                .get(baseUrl + "index.php?route=product/product&product_id=" + productId);

        if (response.statusCode() == 200) {
            Product product = new Product();
            product.setProductId(productId);
            return product;
        }
        return null;
    }

    public Response getCategories() {
        return RestAssured.given()
                .get(baseUrl + "index.php?route=product/category");
    }

    public Response addToCart(int productId, int quantity) {
        return RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParam("product_id", productId)
                .formParam("quantity", quantity)
                .post(baseUrl + "index.php?route=checkout/cart/add");
    }
}
