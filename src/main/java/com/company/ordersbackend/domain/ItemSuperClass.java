package com.company.ordersbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@MappedSuperclass()
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@SuperBuilder()
public class ItemSuperClass {
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


    public ItemSuperClass(String name, String serialNumber, String description, String url, Producer producer, Provider provider, ItemCategory itemCategory) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.description = description;
        this.url = url;
        this.producer = producer;
        this.provider = provider;
        this.itemCategory = itemCategory;
    }

    public ItemSuperClass(ItemSuperClass itemSuperClass) {
        this.name = itemSuperClass.name;
        this.serialNumber = itemSuperClass.serialNumber;
        this.description = itemSuperClass.description;
        this.url = itemSuperClass.url;
        this.producer = itemSuperClass.producer;
        this.provider = itemSuperClass.provider;
        this.itemCategory = itemSuperClass.itemCategory;
    }
}
