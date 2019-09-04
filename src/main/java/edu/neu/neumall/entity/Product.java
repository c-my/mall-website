package edu.neu.neumall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Product")
public class Product {
    /**
     * Product's ID, primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long ID;

    /**
     * Product's name
     */
    @NotNull
    @Column(name = "name")
    private String name;

    /**
     * Product's main image
     */
    @NotNull
    @Column(name = "main_image", columnDefinition = "varchar(50) default '/img/default_product_img.jpg'")
    private String main_img = "varchar(50) default 'img/default_product_img.jpg";
    // TODO: 2019/7/23 add a default image file for product

    /**
     * Number of product
     */
    @NotNull
    @Column(name = "count")
    private Integer count;

    /**
     * Product's price
     */
    @NotNull
    @Column(name = "price", columnDefinition = "DECIMAL(20,2)")
    private Double price;

    /**
     * Category of product
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "category")
//    @JsonBackReference
    private Category category;

    /**
     * Detail description of Product
     */
    @NotNull
    @Column(name = "description")
    private String description;

    /**
     * Seller of the product
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @OneToMany(mappedBy = "target")
    private List<ProductComment> commentList;

    /**
     * Product's Status
     */
    @NotNull
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ProductStatus status;


    /**
     * Order relationship with user
     */
//    @JsonBackReference
    @OneToMany(mappedBy = "product")
    private Set<Order> order;

    /**
     * Represent item in shopping cart of user
     */
    @OneToMany(mappedBy = "product")
//    @JsonBackReference
    private Set<ShoppingCart> shoppingCartSet;

    @CreationTimestamp
    @Column(name = "create_time")
    private Date createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Date updateTime;

    public List<ProductComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ProductComment> commentList) {
        this.commentList = commentList;
    }

    public enum ProductStatus {
        ONSALE, OFFSALE
    }

    public Integer getCount() {
        return count;
    }

    public Long getID() {
        return ID;
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

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public String getCategoryName() {
        return this.category.getName();
    }

    public void setStatusByName(String status) {
        try {
            this.status = ProductStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            this.status = ProductStatus.OFFSALE;
        }
    }

    public String getMain_img() {
        return main_img;
    }

    public void setMain_img(String main_img) {
        this.main_img = main_img;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Product) {
            return this.owner.equals(((Product) o).owner)
                    && this.name.equals(((Product) o).name);
        }
        return false;
    }
}
