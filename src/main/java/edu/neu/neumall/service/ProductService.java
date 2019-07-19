package edu.neu.neumall.service;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
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
            return productRepository.findByPriceGreaterThanEqualAndCategory_CategoryName(Double.parseDouble(low), category);
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
            return productRepository.findByPriceBetweenAndCategory_CategoryName(low, high, category);
        } else {
            return productRepository.findByPriceAndCategory_CategoryName(low, category);
        }
    }

    public List<Product> getProductByCategoryName(String category) {
        return productRepository.findByCategory_CategoryName(category);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(long id){
        productRepository.deleteByProductID(id);
    }
}
