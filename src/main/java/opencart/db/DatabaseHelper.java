package opencart.db;

import opencart.api.models.Product;
import opencart.utils.ConfigLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private final String url;
    private final String user;
    private final String password;
    private final String prefix;

    public DatabaseHelper() {
        String host = ConfigLoader.getInstance().getProperty("db.host", "localhost");
        String port = ConfigLoader.getInstance().getProperty("db.port", "3306");
        String dbName = ConfigLoader.getInstance().getProperty("db.name", "opencart_db");

        this.url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        this.user = ConfigLoader.getInstance().getProperty("db.user", "opencart_user");
        this.password = ConfigLoader.getInstance().getProperty("db.password", "0960");
        this.prefix = ConfigLoader.getInstance().getProperty("db.prefix", "oc_");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, pd.name, p.model, p.price, p.quantity, p.status " +
                "FROM " + prefix + "product p " +
                "JOIN " + prefix + "product_description pd ON p.product_id = pd.product_id " +
                "WHERE pd.language_id = 1";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setModel(rs.getString("model"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStatus(rs.getBoolean("status"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(int productId) {
        String sql = "SELECT p.product_id, pd.name, p.model, p.price, p.quantity, p.status " +
                "FROM " + prefix + "product p " +
                "JOIN " + prefix + "product_description pd ON p.product_id = pd.product_id " +
                "WHERE p.product_id = ? AND pd.language_id = 1";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setModel(rs.getString("model"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStatus(rs.getBoolean("status"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getProductCount() {
        String sql = "SELECT COUNT(*) FROM " + prefix + "product WHERE status = 1";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean validateProductExists(int productId) {
        return getProductById(productId) != null;
    }
}
