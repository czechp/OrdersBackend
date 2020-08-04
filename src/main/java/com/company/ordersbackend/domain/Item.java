package com.company.ordersbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "items")
@Data()
@AllArgsConstructor()
public class Item extends ItemSuperClass{


    public Item(String name, String serialNumber, String description, String url, Producer producer, Provider provider, ItemCategory itemCategory) {
        super(name, serialNumber, description, url, producer, provider, itemCategory);
    }

    public Item(long id,String name, String serialNumber, String description, String url, Producer producer, Provider provider, ItemCategory itemCategory) {
        super(id, name, serialNumber, description, url, producer, provider, itemCategory);
    }
}
