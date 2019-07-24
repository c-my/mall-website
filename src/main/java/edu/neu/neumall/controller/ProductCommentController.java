package edu.neu.neumall.controller;

import edu.neu.neumall.entity.ProductComment;
import edu.neu.neumall.entity.User;
import edu.neu.neumall.repository.ProductCommentRepository;
import edu.neu.neumall.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
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
     * @param errors    errors when validating
     * @return comments of the product
     */
    @GetMapping
    public @ResponseBody
    Set<ProductComment> getComment(@Valid @RequestParam("product_id") long productID,
                                   Errors errors) {
        if (errors.hasErrors()) {
            return new HashSet<>();
        }
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
    public String appendComment(@Valid ProductCommentService.CommentForm form,
                                Errors errors,
                                @AuthenticationPrincipal User user) {
        if (user == null) {
            return "not login";
        }
        if (errors.hasErrors()) {
            return "\"success\":false";
        }
        ProductComment comment = productCommentService.toComment(form);
        if (comment == null) {
            return "\"success\":false";
        }
        comment.setCommenter(user);
        return "\"success\":true";
    }
}
