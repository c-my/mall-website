package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping
    @ResponseBody
    String deleteProduct(@RequestParam("id") long productID) {
        var productExist = productService.getProductByID(productID);
        if (productExist.isEmpty()) {
            return "\"success\":false";
        }
        var product = productExist.get();
        product.setStatus(Product.ProductStatus.OFFSALE);
        productService.save(product);
        return "\"success\":true";
    }

    @PatchMapping
    @ResponseBody
    String unSaleProduct(@RequestParam("id") long productID) {
        var productExist = productService.getProductByID(productID);
        if (productExist.isEmpty()) {
            return "\"success\":false";
        }
        var product = productExist.get();
        product.setStatus(Product.ProductStatus.ONSALE);
        productService.save(product);
        return "\"success\":true";
    }
}
