package edu.neu.neumall.repository;

import edu.neu.neumall.entity.Products;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Products, Integer> {
}
