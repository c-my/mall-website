package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.ShoppingCart;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String shoppingCartPage(@AuthenticationPrincipal User user, Model model) {
        var shoppingCartItems = user.getShoppingCart();
        if (shoppingCartItems == null) {
            shoppingCartItems = new ArrayList<>();
        }
        model.addAttribute("productlist", shoppingCartItems);
        System.out.println("get " + shoppingCartItems.size() + " cart items");
        for (var item : shoppingCartItems) {
            System.out.println("product: " + item.getProduct().getName() + " x " + item.getCount());
        }
        return "cart.html";
    }

    @PostMapping("/add")
    @ResponseBody
    public String appendShoppingCart(@RequestParam long productID, @RequestParam int count,
                                     @AuthenticationPrincipal User user) {
        var result = shoppingCartService.addShoppingCartItem(productID, count, user);
        if (result) {
            return "success";
        }
        return "false";
    }

    @PostMapping("/delete")
    @ResponseBody
    public void removeShoppingCart(@RequestParam("shoppingCartID") long cartID, @AuthenticationPrincipal User user) {
        System.out.println("deleting shopping-cart: " + cartID);
        shoppingCartService.deleteCartByID(cartID, user);
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateShoppingCart(@RequestParam("shoppingCartID") long cartID,
                                     @RequestParam("count") int count,
                                     @AuthenticationPrincipal User user) {
        var shoppingCartOptional = shoppingCartService.getByID(cartID);
        if (shoppingCartOptional.isEmpty() || !shoppingCartOptional.get().getOwner().equals(user)
                || count < 0) {
            return "false";
        }
        var item = shoppingCartOptional.get();
        item.setCount(count);
        user.getShoppingCart().stream().filter(shoppingCart -> shoppingCart.getCartID() == item.getCartID()).findFirst()
                .get().setCount(count);
        shoppingCartService.save(item);
        return "true";
    }
}
