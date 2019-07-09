package edu.neu.neumall.repository;

import edu.neu.neumall.entity.Goods;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Goods, Integer> {
}
