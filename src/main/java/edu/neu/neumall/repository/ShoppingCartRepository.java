package edu.neu.neumall.repository;

import edu.neu.neumall.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    public List<ShoppingCart> findAllByOwner_UserID(long id);

    public List<ShoppingCart> removeByOwner_UserID(long id);
}
