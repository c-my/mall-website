package edu.neu.neumall.controller;

import edu.neu.neumall.entity.ShippingAddr;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ShippingAddrRepository;
import edu.neu.neumall.repository.UserRepository;
import edu.neu.neumall.service.ShippingAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/test")
public class ShippingTestController {
    private final ShippingAddrRepository shippingAddrRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShippingTestController(ShippingAddrRepository shippingAddrRepository,
                                  @Qualifier("userRepository") UserRepository userRepository) {
        this.shippingAddrRepository = shippingAddrRepository;
        this.userRepository = userRepository;
    }

    /**
     * //     * @param user current login user
     *
     * @return user's all shipping addresses
     */
    @GetMapping
    public @ResponseBody
    Iterable<ShippingAddr> getShipping() {
        return shippingAddrRepository.findAll();
    }

    /**
     * add a shipping address for current user
     *
     * @param form   form-data from front-end
     * @param errors errors when validating
     *               //     * @param user   current login-user
     * @return operation result
     */
    @PostMapping
    public String appendShipping(@Valid ShippingAddrService.ShippingForm form,
                                 Errors errors) {

        if (errors.hasErrors()) {
            return "{\"success\":false}";
        }
        ShippingAddr shippingAddr = ShippingAddrService.toShipping(form);
        shippingAddr.setOwner(userRepository.findById((long) 1).get());
        shippingAddrRepository.save(shippingAddr);

        return "{\"success\":true}";
    }

    /**
     * update certain shipping address of current user, identified by address id
     *
     * @param form   form-data from front-end
     * @param errors errors when validating
     *               //     * @param user   current log-in user
     * @return operation status
     */
    @PutMapping
    public String updateShipping(@Valid ShippingAddrService.ShippingUpdateForm form,
                                 Errors errors) {
        if (errors.hasErrors()) {
            return "{\"success\":false}";
        }
        ShippingAddr formAddr = ShippingAddrService.toShipping(form);

        var addrExists = shippingAddrRepository.findById(formAddr.getID());
        if (addrExists.isEmpty()) {
            return "{\"success\":false}";
        }
        ShippingAddr targetAddr = addrExists.get();
        targetAddr.setReceiverAddress(formAddr.getReceiverAddress());
        targetAddr.setReceiverProvince(formAddr.getReceiverProvince());
        targetAddr.setReceiverCity(formAddr.getReceiverCity());
        targetAddr.setReceiverDistrict(formAddr.getReceiverDistrict());
        targetAddr.setReceiverName(formAddr.getReceiverName());
        targetAddr.setReceiverPhone(formAddr.getReceiverPhone());

        shippingAddrRepository.save(targetAddr);
        return "{\"success\":true}";
    }

    /**
     * delete a shipping address of current user, identified by id
     *
     * @param shippingID target shipping-address to be deleted
     *                   //     * @param user       current log-in user
     * @return operation status
     */
    @DeleteMapping
    public String removeShipping(@RequestParam("address_id") int shippingID) {
        var shipOpt = shippingAddrRepository.findById(shippingID);
        if (shipOpt.isEmpty()) {
            return "{\"success\":false}";
        }
        shippingAddrRepository.deleteById(shipOpt.get().getID());

        return "{\"success\":true}";
    }
}
