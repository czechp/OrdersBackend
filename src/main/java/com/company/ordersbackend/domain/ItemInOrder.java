package com.company.ordersbackend.domain;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data()
@Entity()
public class ItemInOrder extends ItemSuperClass{
    private LocalDateTime orderDate;
    private LocalDateTime deliverDate;
    private boolean isOrdered;
    private boolean isDelivered;
    private int amount;

    public ItemInOrder() {
        super();
    }

    public ItemInOrder(ItemSuperClass itemSuperClass){
        super(itemSuperClass);
    }
    
}
