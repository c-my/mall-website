package edu.neu.neumall.repository;

import edu.neu.neumall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetween(double low, double high);

    List<Product> findByPrice(double price);

    List<Product> findByPriceAndCategory_CategoryName(double price, String category);

    List<Product> findByPriceGreaterThanEqual(double price);

    List<Product> findByPriceGreaterThanEqualAndCategory_CategoryName(double price, String category);


    List<Product> findByPriceLessThanEqual(double price);

    List<Product> findByCategory_CategoryName(String name);

    List<Product> findByPriceBetweenAndCategory_CategoryName(double low, double high, String category);

    @Override
    Optional<Product> findById(Long aLong);

    Product findByProductID(long id);

    void deleteByProductID(long id);
}
