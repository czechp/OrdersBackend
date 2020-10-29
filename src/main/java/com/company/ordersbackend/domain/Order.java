package com.company.ordersbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemInOrder> itemsInOrder = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;


    private LocalDateTime creationDate;

    private LocalDateTime closedDate;

    private String commentary = "";

    private String orderNr = "";

    public Order() {
        this.creationDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.NEW;
    }

    public Order(AppUser appUser) {
        this.appUser = appUser;
        this.creationDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.NEW;
    }

    public void setItemsInOrder(List<ItemInOrder> itemsInOrder) {
        this.itemsInOrder.addAll(itemsInOrder);
    }

    public void addItemInOrder(ItemInOrder itemInOrder) {
        for (ItemInOrder inOrder : this.itemsInOrder) {
            if (itemInOrder.getSerialNumber().equals(inOrder.getSerialNumber())) {
                inOrder.setAmount(itemInOrder.getAmount() + inOrder.getAmount());
                return;
            }
        }
        itemInOrder.setOrder(this);
        this.itemsInOrder.add(itemInOrder);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(name);
        hashCodeBuilder.append(id);
        hashCodeBuilder.append(creationDate);

        return hashCodeBuilder.toHashCode();
    }
}
