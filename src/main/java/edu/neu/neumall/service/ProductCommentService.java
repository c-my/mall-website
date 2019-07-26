package edu.neu.neumall.service;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.entity.ProductComment;
import edu.neu.neumall.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class ProductCommentService {

    private ProductRepository productRepository;

    @Autowired
    public ProductCommentService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Data
    public static class CommentForm {
        @NotNull(message = "product_id cannot be null")
        private long product_id;

        @NotNull(message = "content cannot be null")
        @NotBlank(message = "content must has a value")
        private String content;

        @NotNull(message = "comment_type cannot be null")
        @NotBlank(message = "comment_type must has a value")
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
