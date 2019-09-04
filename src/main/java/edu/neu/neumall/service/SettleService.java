package edu.neu.neumall.service;

import edu.neu.neumall.entity.*;
import edu.neu.neumall.repository.ShippingAddrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettleService {

    private ProductService productService;
    private OrderService orderService;
    private ShoppingCartService shoppingCartService;
    private ShippingAddrRepository shippingAddrRepository;

    @Autowired
    public SettleService(ProductService productService,
                         OrderService orderService,
                         ShoppingCartService shoppingCartService,
                         ShippingAddrRepository shippingAddrRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.shippingAddrRepository = shippingAddrRepository;
    }

    public boolean ProcessPurchase(User user, List<Long> shoppingCartsID, int addressID) {
        var shoppingCarts = shoppingCartService.getShoppingCartsByID(shoppingCartsID);
        return Purchase(user, shoppingCarts, addressID);
    }

    boolean Purchase(User user, List<ShoppingCart> shoppingCart, int addressID) {
        if (!canPurchase(shoppingCart)) {
            return false;
        }
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
            var address = shippingAddrRepository.findById(addressID).get();

            //clear shopping-cart
            shoppingCartService.deleteCartByID(item.getCartID(), user);

            // add a order record
            Order record = new Order();
            record.setCount(item.getCount());
            record.setOwner(item.getOwner());
            record.setProduct(item.getProduct());
            record.setPrice(product.getPrice());
            record.setAddress(address);
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
