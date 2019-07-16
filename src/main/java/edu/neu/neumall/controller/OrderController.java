package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import edu.neu.neumall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void addOrder(OrderService.OrderForm form, @AuthenticationPrincipal User user) {

    }

    @GetMapping
    public void getOrder() {

    }
}
