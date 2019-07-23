package edu.neu.neumall.service;

import edu.neu.neumall.entity.ShippingAddr;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddrService {

    @Data
    public static class ShippingForm {
        private final String receiver_name;

        private final String receiver_phone;

        private final String receiver_province;
        private final String receiver_city;
        private final String receiver_district;
        private final String receiver_addr;
        private final String receiver_zip;
    }

    public static ShippingAddr toShipping(ShippingForm form) {
        ShippingAddr shippingAddr = new ShippingAddr();
        shippingAddr.setReceiverName(form.receiver_name);
        shippingAddr.setReceiverPhone(form.receiver_phone);

        shippingAddr.setReceiverProvince(form.receiver_province);
        shippingAddr.setReceiverCity(form.receiver_city);
        shippingAddr.setReceiverAddress(form.receiver_addr);
        shippingAddr.setDistrict(form.getReceiver_district());

        shippingAddr.setReceiverZip(form.receiver_zip == null ? "" : form.receiver_zip);
        return shippingAddr;
    }
}
