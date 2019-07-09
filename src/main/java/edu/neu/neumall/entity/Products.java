package edu.neu.neumall.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goodsID = 0;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private Integer count;

    private String description;

    @CreationTimestamp
    private Date create_time;

    @UpdateTimestamp
    private Date update_time;

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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
