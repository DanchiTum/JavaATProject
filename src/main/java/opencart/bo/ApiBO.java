package opencart.bo;

import opencart.api.ApiClient;
import opencart.api.models.Product;

import java.util.List;

public class ApiBO {
    private final ApiClient apiClient;

    public ApiBO() {
        this.apiClient = new ApiClient();
    }

    public List<Product> getAllProducts() {
        return apiClient.getProducts();
    }

    public List<Product> searchProducts(String query) {
        return apiClient.searchProducts(query);
    }

    public Product getProductById(int productId) {
        return apiClient.getProductById(productId);
    }

    public boolean isProductAvailable(String productName) {
        List<Product> products = searchProducts(productName);
        return !products.isEmpty();
    }

    public String getApiToken() {
        return apiClient.getApiToken();
    }

    public boolean validateApiConnection() {
        try {
            return getApiToken() != null && !getApiToken().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
