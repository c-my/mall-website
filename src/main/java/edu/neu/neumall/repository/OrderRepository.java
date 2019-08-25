package edu.neu.neumall.repository;


import edu.neu.neumall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findByOwner_ID(long userID);

    Set<Order> findByProduct_ID(long productID);
}
