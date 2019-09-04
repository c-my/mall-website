package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ShoppingCartRepository;
import edu.neu.neumall.service.SettleService;
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
    private SettleService settleService;

    @Autowired
    public SettleController(ShoppingCartRepository shoppingCartRepository,
                            SettleService settleService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.settleService = settleService;
    }

    @PostMapping
    public String confirmSettle(@RequestParam(value = "itemList[]", required = false) List<Long> shoppingCartItems, Model model, @AuthenticationPrincipal User user) {
        if (shoppingCartItems != null) {
            for (var cartID : shoppingCartItems) {
                System.out.println(cartID);
            }
        }
        var shoppingCartList = shoppingCartRepository.findAllById(shoppingCartItems);
        model.addAttribute("productList", shoppingCartList);
        model.addAttribute("shippingAddrList", user.getShippingAddrAddrList());
        return "settle.html";
    }

    @PostMapping("/process")
    public String processSettle(@RequestParam("purchaseList[]") List<Long> purchaseItem,
                                @RequestParam("addressID") int addressID,
                                @AuthenticationPrincipal User user) {
        if (purchaseItem != null) {
            for (var cartID : purchaseItem) {
                System.out.println(cartID);
            }
        }
        System.out.println("addr id: "+addressID);
        settleService.ProcessPurchase(user, purchaseItem, addressID);
        return "settleSuccess.html";
    }

    @GetMapping("/test")
    public String testPage() {
        return "testSettle.html";
    }
}
