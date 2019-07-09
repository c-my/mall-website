package edu.neu.neumall.controller;

import edu.neu.neumall.Repository.GoodsRepository;
import edu.neu.neumall.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
@RequestMapping(path = "/goods")
public class GoodsController {
    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping(path = "all")
    public @ResponseBody Iterable<Goods> getAllGoods(){
        return goodsRepository.findAll();
    }

}
