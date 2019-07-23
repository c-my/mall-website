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
        if (category != null) {
            productList = productService.getProductByPriceRangeAndCategory(price_low, price_high, category);
        } else {
            productList = productService.getProductByPriceBetween(price_low, price_high);
        }
        if (order_by != null) {
            switch (order_by) {
                case "price":
                    productList.sort((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
                    break;
                case "count":
                    productList.sort((p1, p2) -> p1.getCount().compareTo(p2.getCount()));
                    break;
            }
        }
        return productList;
    }


    @PostMapping
    public String addNewGoods(ProductUpdateForm form,
                              @AuthenticationPrincipal User owner) {
        Product g = form.toProduct();
        g.setOwner(owner);

        var categoryName = g.getCategoryName();
        var category = getCategory(categoryName, this.categoryRepository);
        g.setCategory(category);
        long productID = productService.addProduct(g).getID();
        return "{\"productid\":" + productID + "}";
    }

    @PutMapping
    public String updateGoodsByID(ProductService.ProductUpdateForm form
            , @AuthenticationPrincipal User owner) {
        var productExits = productService.getProductByID(form.getProductid());
        if (productExits.isEmpty()) {
            return "{\"success\":\"false\"}";
        }
        var dstProduct = productExits.get();

        if (!dstProduct.getOwner().equals(owner)) {
            return "{\"success\":\"false\"}";
        }

        dstProduct.setName(form.getProductname());
        dstProduct.setDescription(form.getDescription());
        dstProduct.setCount(form.getCount());
        dstProduct.setPrice(form.getPrice());
        dstProduct.setStatusByName(form.getStatus());
        dstProduct.setOwner(owner);

        var category = getCategory(form.getCategoryname(), categoryRepository);
        dstProduct.setCategory(category);

        productService.addProduct(dstProduct);
        return "{\"success\":\"true\"}";
    }

    @DeleteMapping
    public String deleteProduct(long productid
            , @AuthenticationPrincipal User owner) {
        var product = productService.getProductByID(productid);
        if (product.isEmpty() || !product.get().getOwner().equals(owner)) {
            return "{\"success\":\"false\"}";
        }
        productService.deleteProduct(productid);
        return "{\"success\":\"true\"}";
    }

    private Category getCategory(String categoryName, CategoryRepository categoryRepository) {
        var categoryExists = categoryRepository.findByCategoryName(categoryName);
        if (categoryExists.isEmpty()) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
        }
        return categoryRepository.findByCategoryName(categoryName).get();

    }
}
