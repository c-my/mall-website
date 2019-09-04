package edu.neu.neumall.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ShoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartID;

    @ManyToOne
    @JoinColumn
//    @JsonManagedReference
    private User owner;

    @ManyToOne
    @JoinColumn
//    @JsonManagedReference
    private Product product;

    @Column(name = "count")
    private int count;

    @CreationTimestamp
    private Date create_time;

    @UpdateTimestamp
    private Date update_time;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public long getCartID() {
        return cartID;
    }

    public void setCartID(long cartID) {
        this.cartID = cartID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShoppingCart) {
            return this.product.equals(((ShoppingCart) obj).product)
                    && this.owner.equals(((ShoppingCart) obj).owner);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.product.getName().hashCode();
    }
}
