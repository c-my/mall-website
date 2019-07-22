package edu.neu.neumall.entity;

import javax.persistence.*;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartID;

    @OneToOne
    @JoinColumn
    private User owner;

    @ManyToOne
    @JoinColumn
    private Product product;

    @Column(name = "count")
    private int count;
}
