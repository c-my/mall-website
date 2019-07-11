package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "all")
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

    @GetMapping(path = "add")
    public @ResponseBody
    String addNewGoods(@RequestParam String name, @RequestParam String description, @RequestParam Integer count) {
        Product g = new Product();
        g.setCount(count);
        g.setDescription(description);
        g.setName(name);
        productService.addProduct(g);
        return "added";
    }

}
