package opencart.api.models;

import lombok.Data;

@Data
public class Product {
    private int productId;
    private String name;
    private String description;
    private String model;
    private String sku;
    private double price;
    private int quantity;
    private String image;
    private int categoryId;
    private boolean status;
}
