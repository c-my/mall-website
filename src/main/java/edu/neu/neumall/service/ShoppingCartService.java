package edu.neu.neumall.service;

import edu.neu.neumall.entity.Order;
import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.ShoppingCart;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ProductRepository;
import edu.neu.neumall.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;
    private ProductRepository productRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    public boolean addShoppingCartItem(long productID, int count, User user) {
        var productOptional = productRepository.findById(productID);
        if (productOptional.isEmpty()) {
            return false;
        }
        var product = productOptional.get();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCount(count);
        shoppingCart.setProduct(product);
        shoppingCart.setOwner(user);
        shoppingCartRepository.save(shoppingCart);
        return true;
    }

}
