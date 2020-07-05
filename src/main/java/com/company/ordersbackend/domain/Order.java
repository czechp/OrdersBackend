package com.company.ordersbackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="item_in_order_id")
    private List<ItemInOrder> itemsInOrder = new ArrayList<>();

    private LocalDateTime creationDate;

    private LocalDateTime closedDate;

    public Order() {
        this.creationDate = LocalDateTime.now();
    }

    public Order(AppUser appUser) {
        this.appUser = appUser;
        this.creationDate = LocalDateTime.now();
    }

    public void addItem(ItemInOrder itemInOrder){
        this.itemsInOrder.add(itemInOrder);
    }

}
