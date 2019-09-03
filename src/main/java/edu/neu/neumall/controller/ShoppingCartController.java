package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {
    @GetMapping
    public String shoppingCartPage(@AuthenticationPrincipal User user, Model model) {
        ArrayList<Product> products = new ArrayList<>();
        var shoppingCartItems = user.getShoppingCart();
        if (shoppingCartItems != null) {
            for (var item : shoppingCartItems) {
                products.add(item.getProduct());
            }
        }
        model.addAttribute("products", products);
        return "cart.html";
    }
}
