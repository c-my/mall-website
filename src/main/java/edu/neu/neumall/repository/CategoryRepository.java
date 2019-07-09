package edu.neu.neumall.repository;

import edu.neu.neumall.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
