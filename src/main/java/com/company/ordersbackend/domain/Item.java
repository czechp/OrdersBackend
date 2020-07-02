package com.company.ordersbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String serialNumber;

    private String description;

    private String url;

    @ManyToOne
    private Producer producer;

    @ManyToOne
    private Provider provider;

    @ManyToOne
    private ItemCategory itemCategory;


    public Item(String name, String serialNumber, String description, String url, Producer producer, Provider provider, ItemCategory itemCategory) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.description = description;
        this.url = url;
        this.producer = producer;
        this.provider = provider;
        this.itemCategory = itemCategory;
    }

    public Item(Item item) {
        this.name = item.name;
        this.serialNumber = item.serialNumber;
        this.description = item.description;
        this.url = item.url;
        this.producer = item.producer;
        this.provider = item.provider;
        this.itemCategory = item.itemCategory;
    }
}
