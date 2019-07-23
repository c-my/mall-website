package edu.neu.neumall.controller;

import edu.neu.neumall.entity.ShippingAddr;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ShippingAddrRepository;
import edu.neu.neumall.service.ShippingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingAddrRepository shippingAddrRepository;

    public ShippingController(ShippingAddrRepository shippingAddrRepository) {
        this.shippingAddrRepository = shippingAddrRepository;
    }

    @GetMapping
    public @ResponseBody
    Iterable<ShippingAddr> getShipping(@AuthenticationPrincipal User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return user.getShippingAddrAddrList();
    }

    @PostMapping
    public String appendShipping(ShippingService.ShippingForm form, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        ShippingAddr shippingAddr = ShippingService.toShipping(form);
        shippingAddr.setOwner(user);
        shippingAddrRepository.save(shippingAddr);
        return "{\"success\":true}";
    }

    @DeleteMapping
    public String removeShipping(int shippingID, @AuthenticationPrincipal User user) {
        var shipOpt = shippingAddrRepository.findById(shippingID);
        if (shipOpt.isEmpty() || !shipOpt.get().getOwner().equals(user)) {
            return "{\"success\":false}";
        }
        shippingAddrRepository.deleteById(shipOpt.get().getID());

        return "{\"success\":true}";
    }
}
