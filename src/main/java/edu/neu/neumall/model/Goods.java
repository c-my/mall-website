package edu.neu.neumall.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Goods {
    @Id
    private Integer id;

    private String name;

    private Integer count;

    private String description;

    public Integer getCount() {
        return count;
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
