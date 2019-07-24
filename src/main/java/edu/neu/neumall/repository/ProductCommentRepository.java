package edu.neu.neumall.repository;

import edu.neu.neumall.entity.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
    public Set<ProductComment> findByTargetID(long productID);

}
