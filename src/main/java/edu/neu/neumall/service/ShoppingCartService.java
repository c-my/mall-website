package edu.neu.neumall.service;

import edu.neu.neumall.entity.ShoppingCart;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ProductRepository;
import edu.neu.neumall.repository.ShoppingCartRepository;
import edu.neu.neumall.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductRepository productRepository,
                               @Qualifier("userRepository") UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ShoppingCart> getShoppingCart(User user) {
        return shoppingCartRepository.findAllByOwner_ID(user.getID());
    }

    public List<ShoppingCart> removeShoppingCart(User user) {
        return shoppingCartRepository.removeByOwner_ID(user.getID());
    }

    public void add(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    @Data
    public static class ShopingCartForm {
        private long productid;
        private String userphone;
        private int count;
    }

    public ShoppingCart toShoppintCart(ShopingCartForm form) {
        ShoppingCart shoppingCart = new ShoppingCart();

        var productExists = productRepository.findById(form.productid);
        if (productExists.isEmpty()) {
            return null;
        }
        var product = productExists.get();
        if (product.getCount() < form.count) {
            return null;
        }

        var userExists = userRepository.findByPhone(form.userphone);
        if (userExists.isEmpty()) {
            return null;
        }
        var user = userExists.get();

        shoppingCart.setOwner(user);
        shoppingCart.setProduct(product);
        shoppingCart.setCount(form.count);

        return shoppingCart;
    }
}
