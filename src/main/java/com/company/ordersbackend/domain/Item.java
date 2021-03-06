package com.company.ordersbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@SuperBuilder()
public class Item extends ItemSuperClass {


    public Item(String name, String serialNumber, String description, String url, Producer producer, Provider provider, ItemCategory itemCategory) {
        super(name, serialNumber, description, url, producer, provider, itemCategory);
    }

    public Item(long id, String name, String serialNumber, String description, String url, Producer producer, Provider provider, ItemCategory itemCategory) {
        super(id, name, serialNumber, description, url, producer, provider, itemCategory);
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemAccessory> accessories = new ArrayList<>();

    public void addItemAccessory(ItemAccessory itemAccessory){
        this.accessories.add(itemAccessory);
        itemAccessory.setItem(this);
    }


}
