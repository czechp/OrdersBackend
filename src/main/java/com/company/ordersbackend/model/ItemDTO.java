package com.company.ordersbackend.model;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.domain.Producer;
import com.company.ordersbackend.domain.Provider;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemDTO {
    private long id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Serial Number cannot be null")
    @NotBlank(message = "Serial Number cannot be empty")
    private String serialNumber;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Serial Number cannot be empty")
    private String description;

    @NotNull(message = "URL cannot be null")
    @NotBlank(message = "URL cannot be empty")
    private String url;

    @NotNull(message = "Producer cannot be null")
    private Producer producer;

    @NotNull(message = "Provider cannot be null")
    private Provider provider;

    @NotNull(message = "Item category cannot be null")
    private ItemCategory itemCategory;
}
