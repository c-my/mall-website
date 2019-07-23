package edu.neu.neumall.form;

import edu.neu.neumall.entity.Category;
import edu.neu.neumall.entity.Product;
import lombok.Data;

@Data
public class ProductUpdateForm {

    private String productname;
    private String description;
    private int count;
    private double price;
    private String categoryname;
    private String status;
    private String main_img;

    public Product toProduct() {
        Product product = new Product();

        Category category = new Category();
        category.setName(categoryname);

        product.setName(productname);
        product.setCount(count);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);
        product.setStatusByName(status);
        product.setMain_img(main_img);

        return product;
    }

}
