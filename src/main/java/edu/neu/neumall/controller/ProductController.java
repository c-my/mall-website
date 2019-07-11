package edu.neu.neumall.controller;

import edu.neu.neumall.entity.Product;
import edu.neu.neumall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "all")
    public @ResponseBody
    Iterable<Product> getAllGoods(@RequestParam(required = false, defaultValue = "0") String price_low,
                                  @RequestParam(required = false, defaultValue = "0") String price_high,
                                  @RequestParam(required = false) String category) {
//        List<Product>
        if (price_high != price_low) {

        }
        return productRepository.findAll();
    }

    @GetMapping(path = "add")
    public @ResponseBody
    String addNewGoods(@RequestParam String name, @RequestParam String description, @RequestParam Integer count) {
        Product g = new Product();
        g.setCount(count);
        g.setDescription(description);
        g.setName(name);
        productRepository.save(g);
        return "added";
    }

}
