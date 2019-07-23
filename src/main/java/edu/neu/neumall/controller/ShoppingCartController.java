package edu.neu.neumall.controller;

import edu.neu.neumall.entity.ShoppingCart;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }


    @PostMapping
    public String addShoppingCart(ShoppingCartService.ShopingCartForm form, @AuthenticationPrincipal User user) {
        var shoppingCart = shoppingCartService.toShoppintCart(form);
        if (shoppingCart == null) {
            return "{\"success\":false}";
        }
        shoppingCartService.add(shoppingCart);
        return "{\"success\":false}";
    }

    @GetMapping
    public @ResponseBody
    List<ShoppingCart> getShoppingCart(@AuthenticationPrincipal User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return shoppingCartService.getShoppingCart(user);
    }

    @PutMapping
    public String updateShoppingCart(@AuthenticationPrincipal User user) {
        return "";
    }

    @DeleteMapping("/clear")
    public String clearCart(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        var cartList = shoppingCartService.removeShoppingCart(user);
        return "{\"success\":true}";
    }

}
