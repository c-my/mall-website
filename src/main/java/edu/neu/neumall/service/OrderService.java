package edu.neu.neumall.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.neu.neumall.entity.Order;
import edu.neu.neumall.repository.OrderRepository;
import edu.neu.neumall.repository.ProductRepository;
import edu.neu.neumall.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    private UserRepository userRepository;
    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        @Qualifier("userRepository") UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Data
    static class OrderForm {
        @JsonAlias("user_id")
        private final long userID;

        @JsonAlias("product_id")
        private final long productID;

        @JsonAlias("order_type")
        private final String orderType;
    }

    public Order toOrder(OrderForm form) {
        Order order = new Order();
        try {
            order.setOrderTYpe(Order.OrderType.valueOf(form.orderType));
        } catch (IllegalArgumentException e) {
            return null;
        }

        var userOption = userRepository.findById(form.userID);
        if (userOption.isEmpty()) {
            return null;
        } else {
            order.setUser_id(userOption.get());
        }

        var productOption = productRepository.findById(form.productID);
        if (productOption.isEmpty()) {
            return null;
        } else {
            order.setProduct_id(productOption.get());
        }

        return order;
    }

}
