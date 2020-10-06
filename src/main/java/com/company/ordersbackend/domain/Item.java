package com.company.ordersbackend.domain;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "items")
@Getter()
@Setter()
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor()
@NoArgsConstructor()
public class Item extends ItemSuperClass {


    public Item(String name, String serialNumber, String description, String url, Producer producer, Provider provider, ItemCategory itemCategory) {
        super(name, serialNumber, description, url, producer, provider, itemCategory);
    }

    public Item(long id, String name, String serialNumber, String description, String url, Producer producer, Provider provider, ItemCategory itemCategory) {
        super(id, name, serialNumber, description, url, producer, provider, itemCategory);
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemAccessory> accessories = new ArrayList<>();
}
