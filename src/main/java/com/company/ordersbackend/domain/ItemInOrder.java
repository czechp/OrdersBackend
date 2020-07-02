package com.company.ordersbackend.domain;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data()
@Entity(name = "items-in-order")
public class ItemInOrder extends Item{
    private LocalDateTime orderDate;
    private LocalDateTime deliverDate;
    private boolean isOrdered;
    private boolean isDelivered;
    private int amount;

    public ItemInOrder() {
    }

    public ItemInOrder(Item item){
        super(item);
    }
    
}
