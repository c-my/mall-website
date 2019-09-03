package edu.neu.neumall.service;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("productService")
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductByPriceBetween(String low, String high) {
        if (high.equals("0")) {
            return productRepository.findByPriceGreaterThanEqual(Double.parseDouble(low));
        } else {
            double lowPrice = Double.parseDouble(low);
            double highPrice = Double.parseDouble(high);
            return getProductByPriceBetween(lowPrice, highPrice);
        }
    }

    private List<Product> getProductByPriceBetween(double low, double high) {
        if (low > high) {
            return new ArrayList<>();
        } else if (low != high) {
            return productRepository.findByPriceBetween(low, high);
        } else {
            return productRepository.findByPrice(low);
        }
    }

    public List<Product> getProductByPriceRangeAndCategory(String low, String high, String category) {
        if (high.equals("0")) {
            return productRepository.findByPriceGreaterThanEqualAndCategory_Name(Double.parseDouble(low), category);
        } else {
            double lowPrice = Double.parseDouble(low);
            double highPrice = Double.parseDouble(high);
            return getProductByPriceRangeAndCategory(lowPrice, highPrice, category);
        }

    }

    private List<Product> getProductByPriceRangeAndCategory(double low, double high, String category) {
        if (low > high) {
            return new ArrayList<>();
        } else if (low != high) {
            return productRepository.findByPriceBetweenAndCategory_Name(low, high, category);
        } else {
            return productRepository.findByPriceAndCategory_Name(low, category);
        }
    }

    public List<Product> getProductByCategoryName(String category) {
        return productRepository.findByCategory_Name(category);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductByID(long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    @Data
    public static class ProductUpdateForm {
        private long productid;
        private String productname;
        private String description;
        private int count;
        private double price;
        private String categoryname;
        private String status;
        private String main_img;
    }

    public static Product toProduct(ProductUpdateForm form) {
        Product product = new Product();
        product.setName(form.getProductname());
        product.setDescription(form.getDescription());
        product.setCount(form.getCount());
        product.setPrice(form.getPrice());
        product.setStatusByName(form.getStatus());
        product.setMain_img(form.main_img);
        return product;
    }
}