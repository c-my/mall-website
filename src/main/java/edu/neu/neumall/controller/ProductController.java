package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Category;
import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.form.ProductUpdateForm;
import edu.neu.neumall.repository.CategoryRepository;
import edu.neu.neumall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public @ResponseBody
    Iterable<Product> getAllGoods(@RequestParam(required = false, defaultValue = "0") String price_low,
                                  @RequestParam(required = false, defaultValue = "0") String price_high,
                                  @RequestParam(required = false) String category,
                                  @RequestParam(required = false) String order_by) {
        List<Product> productList;
        productList = productService.getProductByPriceBetween(price_low, price_high);

        if (category != null) {
            productList = productService.getProductByPriceRangeAndCategory(price_low, price_high, category);
        } else {
            productList = productService.getProductByPriceBetween(price_low, price_high);
        }
        if (order_by != null) {
            switch (order_by) {
                case "price":
                    productList.sort((Comparator<Product>) (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
                    break;
                case "count":
                    productList.sort((Comparator<Product>) (p1, p2) -> p1.getCount().compareTo(p2.getCount()));
                    break;
            }
        }
        return productList;
    }


    @PostMapping
    @PutMapping
    public String addNewGoods(ProductUpdateForm form,
                              @AuthenticationPrincipal User owner) {
        Product g = form.toProduct();
        g.setOwner(owner);

        var categoryName = g.getCategoryName();
        var category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            categoryRepository.save(g.getCategory());
        }
        category = categoryRepository.findByCategoryName(categoryName);
        g.setCategory(category);
        productService.addProduct(g);
        return "{\"success\":\"true\"}";
    }

    @DeleteMapping
    public String deleteProduct(long productid) {
        productService.deleteProduct(productid);
        return "{\"success\":\"true\"}";
    }
}
