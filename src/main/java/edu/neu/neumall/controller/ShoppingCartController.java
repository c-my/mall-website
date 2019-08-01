package edu.neu.neumall.controller;

import edu.neu.neumall.entity.ShoppingCart;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }


    /**
     * add a shopping-cart item for current user
     *
     * @param form   form-data from front-end
     * @param errors errors when validating
     * @param user   current log-in user
     * @return operation result
     */
    @PostMapping
    public String addShoppingCart(@Valid ShoppingCartService.ShopingCartForm form,
                                  Errors errors,
                                  @AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        if (errors.hasErrors()) {
            System.out.println("errors");
            System.out.println(errors.toString());
            return "{\"success\":false}";
        }
        var shoppingCart = shoppingCartService.toShoppingCart(form, user);
        if (shoppingCart == null) {
            System.out.println("error when to shoppingcart");
            return "{\"success\":false}";
        }
        shoppingCartService.save(shoppingCart);
        return "{\"success\":true}";
    }

    /**
     * get all shopping-cart items of current user
     *
     * @param user current user
     * @return shopping-cart items
     */
    @GetMapping
    public @ResponseBody
    List<ShoppingCart> getShoppingCart(@AuthenticationPrincipal User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return shoppingCartService.getShoppingCart(user);
    }

    @PutMapping
    public String updateShoppingCart(@Valid ShoppingCartService.ShoppingCartUpdateForm form,
                                     Errors errors,
                                     @AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        if (errors.hasErrors()) {
            return "{\"success\":false}";
        }
        long cartID = form.getCart_id();
        var cartExists = shoppingCartService.getShoppingCartByID(cartID);
        if (cartExists.isEmpty()) {// cart not exists
            return "{\"success\":false}";
        }
        if (!cartExists.get().getOwner().equals(user)) {// current user is not owner
            return "{\"success\":false}";
        }
        if (form.getCount() == 0) { //delete the item
            shoppingCartService.removeShoppingCartByID(cartID);
            return "{\"success\":true}";
        }
        var cart = cartExists.get();
        var cartTem = shoppingCartService.toShoppingCart(form, user);
        cart.setCount(cartTem.getCount());
        cart.setProduct(cartTem.getProduct());
        shoppingCartService.save(cart);
        return "{\"success\":true}";
    }

    /**
     * clear current user's all shopping-cart items
     *
     * @param user current user
     * @return operation result
     */
    @DeleteMapping
    public String clearCart(@AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        var cartList = shoppingCartService.removeShoppingCart(user);
        return "{\"success\":true}";
    }
}
