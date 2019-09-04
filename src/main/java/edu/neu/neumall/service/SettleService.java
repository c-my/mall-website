package edu.neu.neumall.service;

import edu.neu.neumall.entity.Order;
import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.ShoppingCart;
import edu.neu.neumall.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettleService {

    private ProductService productService;
    private OrderService orderService;

    @Autowired
    public SettleService(ProductService productService,
                         OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    public boolean Purchase(User user, List<ShoppingCart> shoppingCart) {
        if (!canPurchase(shoppingCart)) {
            return false;
        }
        ArrayList<Product> products = new ArrayList<>();
        for (var item : shoppingCart) {
            var targetProductID = item.getProduct().getID();
            var productOptional = productService.getProductByID(targetProductID);
            if (productOptional.isEmpty()) {
                return false;
            }
            // reset product's count
            var product = productOptional.get();
            product.setCount(product.getCount() - item.getCount());
            productService.save(product);

            // add a order record
            Order record = new Order();
            record.setCount(item.getCount());
            record.setOwner(item.getOwner());
            record.setProduct(item.getProduct());
            record.setPrice(product.getPrice());
            record.setType(Order.OrderType.PURCHASE);
            orderService.save(record);
        }
        return true;
    }

    private boolean isShoppingCartItemValid(ShoppingCart shoppingCart) {
        var product = shoppingCart.getProduct();
        if (product.getCount() < shoppingCart.getCount()) {
            return false;
        }
        return true;
    }

    private boolean canPurchase(List<ShoppingCart> shoppingCarts) {
        for (var item : shoppingCarts) {
            if (!isShoppingCartItemValid(item)) {
                return false;
            }
        }
        return true;
    }
}
