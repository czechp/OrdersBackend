package com.company.ordersbackend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data()
@Entity()
public class ItemBorrowed extends ItemSuperClass {

    @NotNull()
    @ManyToOne()
    private AppUser appUser;

    @Min(value = 1)
    @Max(value = 100)
    private int amount;

    @NotNull()
    @NotBlank()
    private String receiver;

    private LocalDateTime creationDate;

    public ItemBorrowed() {
        this.creationDate = LocalDateTime.now();
    }

    public ItemBorrowed(ItemSuperClass itemSuperClass) {
        super(itemSuperClass);
        this.creationDate = LocalDateTime.now();
    }
}
