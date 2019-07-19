package edu.neu.neumall.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import edu.neu.neumall.entity.Order;
import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.OrderRepository;
import edu.neu.neumall.repository.ProductRepository;
import edu.neu.neumall.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository,
                        @Qualifier("userRepository") UserRepository userRepository,
                        ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public long purchase(OrderForm form) {
        if (!canPurchase(form)) {
            System.out.println("cannot pruchase");
            return -1;
        }
        return processPurchase(form);

    }

    private boolean canPurchase(OrderForm form) {
        var userID = form.getUserID();
        var productID = form.getProductID();

        //if user not exists or user is not a customer
        var userExist = userRepository.findById(userID);
        if (userExist.isEmpty() || !userExist.get().getRole().equals(User.UserRole.CUSTOMER)) {
            return false;
        }

        //if product not exists or not on sell
        var productExist = productRepository.findById(productID);
        if (productExist.isEmpty() || productExist.get().getStatus().equals(Product.ProductStatus.OFFSALE)) {
            return false;
        }

        // if there is not enough products
        if (productExist.get().getCount() < form.getOrderCount()) {
            return false;
        }
        return true;
    }

    private long processPurchase(OrderForm form) {
        var productID = form.getProductID();
        var count = form.getOrderCount();

        var product = productRepository.findById(productID).get();
        product.setCount(product.getCount() - count);
        productRepository.save(product);

        Order order;
        try {
            order = toOrder(form);
            var price = product.getPrice();
            order.setPrice(price);

            var orderID = orderRepository.save(order).getOrder_id();
            return orderID;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Data
    public static class OrderForm {
        @JsonAlias("user_id")
        private long userID;

        @JsonAlias("product_id")
        private long productID;

        @JsonAlias("order_type")
        private String orderType;

        @JsonAlias("order_count")
        private int orderCount;
    }

    private Order toOrder(OrderForm form) throws Exception {
        Order order = new Order();
        try {
            order.setOrderType(Order.OrderType.valueOf(form.orderType));
        } catch (IllegalArgumentException e) {
            return null;
        }

        var userOption = userRepository.findById(form.userID);
        if (userOption.isEmpty()) {
            throw new Exception("user not find when cast orderform to order");
        } else {
            order.setUser_id(userOption.get());
        }

        var productOption = productRepository.findById(form.productID);
        if (productOption.isEmpty()) {
            return null;
        } else {
            order.setProduct_id(productOption.get());
        }
        order.setCount(form.getOrderCount());

        return order;
    }

}
