package edu.neu.neumall.repository;

import edu.neu.neumall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPriceBetween(Double low, Double high);
}
