package edu.neu.neumall.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "OrderTable")
public class Order {
    /**
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long ID;

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
     *          PURCHASE/REFUND
     */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private OrderType type;

    @CreationTimestamp
    private Date create_time;

    @UpdateTimestamp
    private Date update_time;

    public enum OrderStatus {
        FINISHED
    }

    public enum OrderType {
        PURCHASE, REFUND
    }
}
