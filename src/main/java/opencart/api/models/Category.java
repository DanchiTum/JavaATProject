package opencart.api.models;

import lombok.Data;

@Data
public class Category {
    private int categoryId;
    private String name;
    private String description;
    private int parentId;
    private String image;
    private boolean status;
}
