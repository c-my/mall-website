package edu.neu.neumall.service;

import edu.neu.neumall.entity.ShippingAddr;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class ShippingAddrService {

    @Data
    public static class ShippingForm {
        @NotBlank(message = "receiver_name must has a value")
        @NotNull(message = "receiver_name can not be null")
        private final String receiver_name;

        @NotBlank(message = "receiver_phone must has a value")
        @NotNull(message = "receiver_phone can not be null")
        private final String receiver_phone;

        @NotBlank(message = "receiver_province must has a value")
        @NotNull(message = "receiver_province can not be null")
        private final String receiver_province;
        @NotBlank(message = "receiver_city must has a value")
        @NotNull(message = "receiver_city can not be null")
        private final String receiver_city;

        private final String receiver_district;
        @NotBlank(message = "receiver_addr must has a value")
        @NotNull(message = "receiver_addr can not be null")
        private final String receiver_addr;
        private final String receiver_zip;
    }

    @Data
    public static class ShippingUpdateForm {
        @NotNull(message = "receiver_id can not be null")
        private final int address_id;

        @NotBlank(message = "receiver_name must has a value")
        @NotNull(message = "receiver_name can not be null")
        private final String receiver_name;

        @NotBlank(message = "receiver_phone must has a value")
        @NotNull(message = "receiver_phone can not be null")
        private final String receiver_phone;

        @NotBlank(message = "receiver_province must has a value")
        @NotNull(message = "receiver_province can not be null")
        private final String receiver_province;
        @NotBlank(message = "receiver_city must has a value")
        @NotNull(message = "receiver_city can not be null")
        private final String receiver_city;

        private final String receiver_district;
        @NotBlank(message = "receiver_addr must has a value")
        @NotNull(message = "receiver_addr can not be null")
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
        shippingAddr.setReceiverDistrict(form.getReceiver_district());

        shippingAddr.setReceiverZip(form.receiver_zip == null ? "" : form.receiver_zip);
        return shippingAddr;
    }

    public static ShippingAddr toShipping(ShippingUpdateForm form) {
        ShippingAddr shippingAddr = new ShippingAddr();
        shippingAddr.setID(form.address_id);
        shippingAddr.setReceiverName(form.receiver_name);
        shippingAddr.setReceiverPhone(form.receiver_phone);

        shippingAddr.setReceiverProvince(form.receiver_province);
        shippingAddr.setReceiverCity(form.receiver_city);
        shippingAddr.setReceiverAddress(form.receiver_addr);
        shippingAddr.setReceiverDistrict(form.getReceiver_district());

        shippingAddr.setReceiverZip(form.receiver_zip == null ? "" : form.receiver_zip);
        return shippingAddr;
    }
}
