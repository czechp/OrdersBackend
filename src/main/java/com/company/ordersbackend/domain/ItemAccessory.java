package com.company.ordersbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity(name = "item_accessories")
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
public class ItemAccessory extends ItemSuperClass {
    public ItemAccessory(ItemSuperClass itemSuperClass) {
        super(itemSuperClass);
    }
}
