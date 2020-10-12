package com.company.ordersbackend.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "item_accessories")
@Data()
@NoArgsConstructor()
@SuperBuilder()
public class ItemAccessory extends ItemSuperClass {
    @ManyToOne()
    private Item item;

    public ItemAccessory(Item item) {
        super(item);
    }

}
