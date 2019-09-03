package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.ProductService;
import edu.neu.neumall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private UserService userService;
    private ProductService productService;

    @Autowired
    public AdminController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public String adminPage(Model model) {
        var users = userService.findAll();
        var product = productService.getAllProducts();
        model.addAttribute("userList", users);
        model.addAttribute("productList", product);
        return "admin.html";
    }
}
