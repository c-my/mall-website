package edu.neu.neumall.repository;

import edu.neu.neumall.entity.ShippingAddr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<ShippingAddr, Integer> {
}
