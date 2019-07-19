package edu.neu.neumall.service;

import edu.neu.neumall.entity.Shipping;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    @Data
    public static class ShippingForm {
        private final String receivername;
        private final String receiverphone;

        private final String receiverprovince;
        private final String receivercity;
        private final String receiveraddr;

        private final String receiverzip;
    }

    public static Shipping toShipping(ShippingForm form) {
        Shipping shipping = new Shipping();
        shipping.setReceiverName(form.receivername);
        shipping.setReceiverPhone(form.receiverphone);

        shipping.setReceiverProvince(form.receiverprovince);
        shipping.setReceiverCity(form.receivercity);
        shipping.setReceiverAddress(form.receiveraddr);

        shipping.setReceiverZip(form.receiverzip == null ? "" : form.receiverzip);
        return shipping;
    }
}
