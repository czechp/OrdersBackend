package com.company.ordersbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
public class ItemInOrderDTO extends ItemDTO {
    private LocalDateTime orderDate;
    private LocalDateTime deliverDate;
    private boolean isOrdered;
    private boolean isDelivered;
    private int amount;
}
