package edu.neu.neumall.service;

import edu.neu.neumall.entity.ShoppingCart;
import edu.neu.neumall.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettleService {
    public void Purchase(User user, List<ShoppingCart> shoppingCart) {
    }

    private boolean canPurchase(List<ShoppingCart> shoppingCarts) {
        return false;
    }
}
