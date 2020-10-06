package com.company.ordersbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity(name = "item_accessories")
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
public class ItemAccessory extends ItemSuperClass {
    public ItemAccessory(ItemSuperClass itemSuperClass) {
        super(itemSuperClass);
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Item item;

}
