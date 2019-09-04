package edu.neu.neumall.service;

import edu.neu.neumall.entity.Order;
import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.ShoppingCart;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ProductRepository;
import edu.neu.neumall.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        // cart item already exists
        var cartExist = shoppingCartRepository.findByOwnerIDAndProductID(user.getID(), productID);
        if (cartExist.isPresent()) {
            var cart = cartExist.get();
            cart.setCount(cart.getCount() + 1);
            shoppingCartRepository.save(cart);
            return true;
        }
        // item not exist, create a new one
        var product = productOptional.get();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCount(count);
        shoppingCart.setProduct(product);
        shoppingCart.setOwner(user);
        shoppingCartRepository.save(shoppingCart);
        return true;
    }

    public void deleteCartByID(long cartID) {
        shoppingCartRepository.deleteById(cartID);
    }

    public Optional<ShoppingCart> getByID(long cartID) {
        return shoppingCartRepository.findById(cartID);
    }

    public void save(ShoppingCart cart) {
        shoppingCartRepository.save(cart);
    }
}
