package edu.neu.neumall.service;

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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
@Transactional
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Order purchase(OrderForm form) {
        if (!canPurchase(form)) {
            System.out.println("cannot pruchase");
            return null;
        }
        return processPurchase(form);

    }

    private boolean canPurchase(OrderForm form) {
        var userID = form.getUser_id();
        var productID = form.getProduct_id();

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
        if (productExist.get().getCount() < form.getOrder_count()) {
            return false;
        }
        return true;
    }

    private Order processPurchase(OrderForm form) {
        var productID = form.getProduct_id();
        var count = form.getOrder_count();

        var product = productRepository.findById(productID).get();
        product.setCount(product.getCount() - count);
        productRepository.save(product);

        Order order;
        try {
            order = toOrder(form);
            var price = product.getPrice();
            order.setPrice(price);

            return orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Data
    public static class OrderForm {
        @NotNull(message = "user_id cannot be null")
        private long user_id;

        @NotNull(message = "product_id cannot be null")
        private long product_id;

        @NotNull(message = "order_type cannot be null")
        @NotBlank(message = "order_type must has value")
        private String order_type;

        @NotNull(message = "order_count cannot be null")
        private int order_count;
    }

    private Order toOrder(OrderForm form) throws Exception {
        Order order = new Order();
        try {
            order.setType(Order.OrderType.valueOf(form.order_type));
        } catch (IllegalArgumentException e) {
            return null;
        }

        var userOption = userRepository.findById(form.user_id);
        if (userOption.isEmpty()) {
            throw new Exception("user not find when cast orderform to order");
        } else {
            order.setOwner(userOption.get());
        }

        var productOption = productRepository.findById(form.product_id);
        if (productOption.isEmpty()) {
            return null;
        } else {
            order.setProduct(productOption.get());
        }
        order.setCount(form.getOrder_count());

        return order;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

}