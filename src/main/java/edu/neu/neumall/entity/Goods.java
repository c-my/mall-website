package edu.neu.neumall.entity;

import javax.persistence.*;

@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodsID = 0;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private Integer count;

    private String description;

    public Integer getCount() {
        return count;
    }

    public Integer getGoodsID() {
        return goodsID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoodsID(Integer goodsID) {
        this.goodsID = goodsID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
