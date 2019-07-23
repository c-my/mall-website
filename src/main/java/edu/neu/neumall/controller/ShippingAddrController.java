package edu.neu.neumall.controller;

import edu.neu.neumall.entity.ShippingAddr;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ShippingAddrRepository;
import edu.neu.neumall.repository.UserRepository;
import edu.neu.neumall.service.ShippingAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/shipping")
public class ShippingAddrController {

    private final ShippingAddrRepository shippingAddrRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShippingAddrController(ShippingAddrRepository shippingAddrRepository,
                                  @Qualifier("userRepository") UserRepository userRepository) {
        this.shippingAddrRepository = shippingAddrRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public @ResponseBody
    Iterable<ShippingAddr> getShipping(@AuthenticationPrincipal User user) {
        if (user == null) {
            Logger.getAnonymousLogger().log(Level.INFO, "not login");
            return new ArrayList<>();
        }
        return user.getShippingAddrAddrList();
    }

    @PostMapping
    public String appendShipping(ShippingAddrService.ShippingForm form, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        ShippingAddr shippingAddr = ShippingAddrService.toShipping(form);
        shippingAddr.setOwner(user);
        shippingAddrRepository.save(shippingAddr);

        user.getShippingAddrAddrList().add(shippingAddr);
        userRepository.save(user);

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
