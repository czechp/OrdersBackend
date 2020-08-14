package com.company.ordersbackend.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Data()
public class Order {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne()
    private AppUser appUser;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_in_order_id")
    private List<ItemInOrder> itemsInOrder = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;


    private LocalDateTime creationDate;

    private LocalDateTime closedDate;

    public Order() {
        this.creationDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.NEW;
    }

    public Order(AppUser appUser) {
        this.appUser = appUser;
        this.creationDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.NEW;
    }

    public void addItem(ItemInOrder itemInOrder) {
        this.itemsInOrder.add(itemInOrder);
    }

}
