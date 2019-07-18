package edu.neu.neumall.service;

import edu.neu.neumall.entity.Shipping;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    @Data
    public static class ShippingForm {
        private final String receiverName;
        private final String receiverPhone;

        private final String receiverProvince;
        private final String receiverCity;
        private final String receiverAddr;

        private final String receiverZip;
    }

    public static Shipping toShipping(ShippingForm form) {
        Shipping shipping = new Shipping();
        shipping.setReceiverName(form.receiverName);
        shipping.setReceiverPhone(form.receiverPhone);

        shipping.setReceiverProvince(form.receiverProvince);
        shipping.setReceiverCity(form.receiverCity);
        shipping.setReceiverAddress(form.receiverAddr);

        shipping.setReceiverZip(form.receiverZip == null ? "" : form.receiverZip);
        return shipping;
    }
}
