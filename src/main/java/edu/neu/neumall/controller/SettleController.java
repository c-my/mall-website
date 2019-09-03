package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/settle")
public class SettleController {

    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public SettleController(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @PostMapping
    public String processSettle(@RequestParam("itemList[]") List<Long> shoppingCartItems, Model model, @AuthenticationPrincipal User user) {
        for (var cartID : shoppingCartItems) {
            System.out.println(cartID);
        }

        return "settle.html";
    }

    @GetMapping
    public String testPage() {
        return "testSettle.html";
    }
}
