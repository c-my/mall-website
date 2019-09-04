package edu.neu.neumall.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "OrderInfo")
public class Order {
    /**
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long ID;

    /**
     * User attached to order
     */
    @ManyToOne
    @JoinColumn
    @NotNull
    private User owner;

    /**
     * Product attached to order
     */
    @ManyToOne
    @JoinColumn
    @NotNull
    private Product product;

    /**
     * Number of product in order
     */
    @Column(name = "count")
    private int count;

    /**
     * Price of product in order
     */
    @Column(name = "price")
    private double price;

    /**
     * Order's type:
     * PURCHASE/REFUND
     */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private OrderType type;

    @NotNull
    @ManyToOne
    private ShippingAddr address;

    @CreationTimestamp
    private Date create_time;

    @UpdateTimestamp
    private Date update_time;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

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

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public ShippingAddr getAddress() {
        return address;
    }

    public void setAddress(ShippingAddr address) {
        this.address = address;
    }

    public enum OrderStatus {
        FINISHED
    }

    public enum OrderType {
        PURCHASE, REF
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Order) {
            return this.owner.equals(((Order) o).owner)
                    && this.product.equals(((Order) o).product);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.ID);
    }
}
