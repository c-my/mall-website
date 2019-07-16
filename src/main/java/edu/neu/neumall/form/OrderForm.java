package edu.neu.neumall.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.neu.neumall.entity.Order;
import lombok.Data;

@Data
public class OrderForm {
    @JsonAlias("user_id")
    private final long userID;

    @JsonAlias("product_id")
    private final long productID;

    @JsonAlias("order_type")
    private final Order.OrderType orderType;

//    public Order toOrder() {
//        Order order = new Order();
//        order.setUser_id(userID);
//        order.setProduct_id(productID);
//    }
}
