package edu.neu.neumall.repository;

import edu.neu.neumall.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    public List<ShoppingCart> findAllByOwner_ID(long id);

    public List<ShoppingCart> removeByOwner_ID(long id);
}
