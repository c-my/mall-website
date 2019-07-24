package edu.neu.neumall.controller;

import edu.neu.neumall.entity.ProductComment;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ProductCommentRepository;
import edu.neu.neumall.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/comment")
public class ProductCommentController {
    private ProductCommentRepository productCommentRepository;
    private ProductCommentService productCommentService;

    @Autowired
    public ProductCommentController(ProductCommentRepository productCommentRepository,
                                    ProductCommentService productCommentService) {
        this.productCommentRepository = productCommentRepository;
        this.productCommentService = productCommentService;
    }

    /**
     * return all comments of a certain product
     *
     * @param productID product's identity
     * @return comments of the product
     */
    @GetMapping
    public @ResponseBody
    Set<ProductComment> getComment(@RequestParam("product_id") long productID) {
        return productCommentRepository.findByTargetID(productID);
    }

    /**
     * add a comment for certain product identified by product's id,
     * set current user as commenter
     *
     * @param form form-data from front-end
     * @param user current log-in user
     * @return operation result
     */
    @PostMapping
    public String appendComment(ProductCommentService.CommentForm form,
                                @AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        ProductComment comment = productCommentService.toComment(form);
        if (comment == null) {
            return "\"success\":false";
        }
        comment.setCommenter(user);
        return "\"success\":true";
    }
}
