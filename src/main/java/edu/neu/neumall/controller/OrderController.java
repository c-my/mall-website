package edu.neu.neumall.controller;

import edu.neu.neumall.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping
    public void addOrder(@AuthenticationPrincipal User user) {

    }

    @GetMapping
    public void getOrder() {

    }
}
