package edu.neu.neumall.Repository;

import edu.neu.neumall.entity.Goods;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Goods, Integer> {
}
