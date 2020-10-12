package com.company.ordersbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "producers")
@Builder()
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonIgnore()
    @OneToMany(mappedBy = "producer", fetch = FetchType.EAGER)
    private List<Item> itemList = new ArrayList<>();


    public Producer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Producer(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
