package com.company.ordersbackend.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data()
@Entity()
public class ItemInOrder extends ItemSuperClass {
    private LocalDateTime orderDate;
    private LocalDateTime deliverDate;
    private boolean isOrdered;
    private boolean isDelivered;
    private int amount;


    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public ItemInOrder() {
        super();
    }

    public ItemInOrder(ItemSuperClass itemSuperClass) {
        super(itemSuperClass);
    }


}
