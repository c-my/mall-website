package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Shipping;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ShippingRepository;
import edu.neu.neumall.service.ShippingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingRepository shippingRepository;

    public ShippingController(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @GetMapping
    public List<Shipping> getShipping(@AuthenticationPrincipal User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return user.getShippingList();
    }

    @PostMapping
    public String appendShipping(ShippingService.ShippingForm form, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        Shipping shipping = ShippingService.toShipping(form);
        shipping.setOwner(user);
        shippingRepository.save(shipping);
        return "{\"success\":true}";
    }

    @DeleteMapping
    public String removeShipping(int shippingID, @AuthenticationPrincipal User user) {
        var shipOpt = shippingRepository.findById(shippingID);
        if (shipOpt.isEmpty() || !shipOpt.get().getOwner().equals(user)) {
            return "{\"success\":false}";
        }
        shippingRepository.deleteById(shipOpt.get().getShippingID());

        return "{\"success\":true}";
    }
}
