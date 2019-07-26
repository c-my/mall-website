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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

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

    public void removeShoppingCartByID(long id) {
        shoppingCartRepository.deleteById(id);
    }

    public Optional<ShoppingCart> getShoppingCartByID(long id) {
        return shoppingCartRepository.findById(id);
    }

    public ShoppingCart Save(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    public void save(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }


    public static interface ShoppingCartFormInterface {
        long getCart_id();

        long getProduct_id();

        String getUser_phone();

        int getCount();
    }

    @Data
    public static class ShopingCartForm implements ShoppingCartFormInterface {
        @NotNull(message = "product_id can not be null")
        @NotBlank(message = "_product_id must has a velue")
        private long product_id;

        @Null
        private String user_phone;

        @NotNull(message = "count can not be null")
        @NotBlank(message = "count must has a velue")
        private int count;

        @Override
        public long getCart_id() {
            return 0;
        }
    }

    @Data
    public static class ShoppingCartUpdateForm implements ShoppingCartFormInterface {
        @NotNull(message = "cart_id can not be null")
        @NotBlank(message = "cart_id must has a velue")
        private long cart_id;

        @NotNull(message = "product_id can not be null")
        @NotBlank(message = "product_id must has a velue")
        private long product_id;

        @NotNull(message = "user_phone can not be null")
        @NotBlank(message = "user_phone must has a velue")
        private String user_phone;

        @NotNull(message = "count can not be null")
        @NotBlank(message = "count must has a velue")
        private int count;
    }

    public ShoppingCart toShoppingCart(ShoppingCartFormInterface form, User user) {
        ShoppingCart shoppingCart = new ShoppingCart();

        var productExists = productRepository.findById(form.getCart_id());
        if (productExists.isEmpty()) {
            return null;
        }
        var product = productExists.get();
        if (product.getCount() < form.getCount()) {
            return null;
        }

//        var userExists = userRepository.findByPhone(form.getUser_phone());
//        if (userExists.isEmpty()) {
//            return null;
//        }
//        var user = userExists.get();

        shoppingCart.setOwner(user);
        shoppingCart.setProduct(product);
        shoppingCart.setCount(form.getCount());

        return shoppingCart;
    }
}
