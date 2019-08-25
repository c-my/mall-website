package edu.neu.neumall.repository;

import edu.neu.neumall.entity.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
    public Set<ProductComment> findByTargetID(long productID);

}
