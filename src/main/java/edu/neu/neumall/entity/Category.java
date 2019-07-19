package edu.neu.neumall.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer categoryID;

    @Column(name = "category_name")
    private String categoryName;

    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private List<Product> goods;

    public List<Product> getGoods() {
        return goods;
    }

    public void setGoods(List<Product> goods) {
        this.goods = goods;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
