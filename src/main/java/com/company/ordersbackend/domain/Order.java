package com.company.ordersbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Data()
@AllArgsConstructor()
@Builder()
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

    private String commentary = "";

    private String orderNr = "";

    public void addItemInOrder(ItemInOrder itemInOrder){
        for (ItemInOrder inOrder : this.itemsInOrder) {
            if (itemInOrder.getSerialNumber().equals(inOrder.getSerialNumber())) {
                inOrder.setAmount(itemInOrder.getAmount() + inOrder.getAmount());
                return;
            }
        }
        this.itemsInOrder.add(itemInOrder);
    }

    public Order() {
        this.creationDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.NEW;
    }

    public Order(AppUser appUser) {
        this.appUser = appUser;
        this.creationDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.NEW;
    }


}
