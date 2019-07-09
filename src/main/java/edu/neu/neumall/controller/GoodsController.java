package edu.neu.neumall.controller;

import edu.neu.neumall.repository.GoodsRepository;
import edu.neu.neumall.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
@RequestMapping(path = "/goods")
public class GoodsController {
    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping(path = "all")
    public @ResponseBody
    Iterable<Products> getAllGoods() {
        return goodsRepository.findAll();
    }

    @GetMapping(path = "add")
    public @ResponseBody
    String addNewGoods(@RequestParam String name, @RequestParam String description, @RequestParam Integer count) {
        Products g = new Products();
        g.setCount(count);
        g.setDescription(description);
        g.setName(name);
        goodsRepository.save(g);
        return "added";
    }

}
