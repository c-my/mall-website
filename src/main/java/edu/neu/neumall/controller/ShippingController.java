package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Shipping;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ShippingRepository;
import edu.neu.neumall.service.ShippingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private ShippingRepository shippingRepository;

    public ShippingController(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @GetMapping
    public List<Shipping> getShippings(@AuthenticationPrincipal User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return user.getShippingList();
    }

    @PostMapping
    public String appendShipping(ShippingService.ShippingForm form, @AuthenticationPrincipal User user) throws NoSuchMethodException {
        if (user == null) {
            return "not login";
        }
        Shipping shipping = ShippingService.toShipping(form);
        shipping.setOwner(user);
        shippingRepository.save(shipping);
        return "{\"success\":true}";
    }
}
