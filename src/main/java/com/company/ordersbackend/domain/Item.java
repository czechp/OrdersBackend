package com.company.ordersbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "items")
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
}
