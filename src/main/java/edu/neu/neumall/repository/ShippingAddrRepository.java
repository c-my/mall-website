package edu.neu.neumall.repository;

import edu.neu.neumall.entity.ShippingAddr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ShippingAddrRepository extends JpaRepository<ShippingAddr, Integer> {
    Set<ShippingAddr> findByOwner_IDOrderByID(long userID);
}
