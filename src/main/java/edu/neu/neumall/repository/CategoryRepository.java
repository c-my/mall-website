package edu.neu.neumall.repository;

import edu.neu.neumall.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Optional<Category> findByCategoryName(String category);
}
