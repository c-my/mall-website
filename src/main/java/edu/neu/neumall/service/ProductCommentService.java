package edu.neu.neumall.service;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.ProductComment;
import edu.neu.neumall.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCommentService {

    private ProductRepository productRepository;

    @Autowired
    public ProductCommentService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Data
    public static class CommentForm {
        private long product_id;
        private String content;
        private String comment_type;
    }

    public ProductComment toComment(CommentForm form) {
        ProductComment comment = new ProductComment();

        var productExists = this.productRepository.findById(form.product_id);
        if (productExists.isEmpty()) {
            return null;
        }
        comment.setTarget(productExists.get());
        comment.setContent(form.content);
        comment.setTypeByName(form.comment_type);
        return comment;
    }
}
