package edu.neu.neumall.repository;

import edu.neu.neumall.entity.ShippingAddr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ShippingAddrRepository extends JpaRepository<ShippingAddr, Integer> {
    Set<ShippingAddr> findByOwner_IDOrderByID(long userID);
}
