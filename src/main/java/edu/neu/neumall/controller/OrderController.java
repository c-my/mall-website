package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Order;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.OrderRepository;
import edu.neu.neumall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository,
                           OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @PostMapping
    public String addOrder(OrderService.OrderForm form,
                           @AuthenticationPrincipal User user) {
        var orderID = orderService.purchase(form);
        return "{\"order_id\":" + orderID + "}";
    }

    @GetMapping
    public @ResponseBody
    Set<Order> getOrder(@AuthenticationPrincipal User user) {
        return user.getUser_order();
    }
}
