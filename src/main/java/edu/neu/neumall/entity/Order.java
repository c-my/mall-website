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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long order_id;

    @ManyToOne
    @JoinColumn
    @NotNull
    private User user_id;

    @ManyToOne
    @JoinColumn
    @NotNull
    private Product product_id;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

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
