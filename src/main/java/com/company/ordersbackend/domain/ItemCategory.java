package com.company.ordersbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonIgnore()
    @OneToMany(mappedBy = "itemCategory", fetch = FetchType.EAGER)
    private List<Item> itemList = new ArrayList<>();

    public ItemCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ItemCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
