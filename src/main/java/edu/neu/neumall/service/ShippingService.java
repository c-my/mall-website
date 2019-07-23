package edu.neu.neumall.service;

import edu.neu.neumall.entity.ShippingAddr;
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
        private final String receiverdistrict;
        private final String receiveraddr;

        private final String receiverzip;
    }

    public static ShippingAddr toShipping(ShippingForm form) {
        ShippingAddr shippingAddr = new ShippingAddr();
        shippingAddr.setReceiverName(form.receivername);
        shippingAddr.setReceiverPhone(form.receiverphone);

        shippingAddr.setReceiverProvince(form.receiverprovince);
        shippingAddr.setReceiverCity(form.receivercity);
        shippingAddr.setReceiverAddress(form.receiveraddr);
        shippingAddr.setDistrict(form.getReceiverdistrict());

        shippingAddr.setReceiverZip(form.receiverzip == null ? "" : form.receiverzip);
        return shippingAddr;
    }
}
