package com.company.ordersbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @JsonIgnore()
    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
    private List<Item> itemList = new ArrayList<>();

    public Provider(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Provider(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
