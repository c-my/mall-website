package edu.neu.neumall.controller;

import edu.neu.neumall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    String getProduct(Model model) {
        var product = productService.getAllProducts();
        model.addAttribute("productList", product);
        return "product.html";
    }

    @GetMapping("/{productID}")
    String productDetail(@PathVariable("productID") long productID, Model model) {
        var product = productService.getProductByID(productID);
        if (product.isEmpty()) {
            return "error";
        }
        model.addAttribute("product", product.get());
        return "productDetail.html";
    }
}
