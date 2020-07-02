package com.company.ordersbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private ProducerDTO producer;

    @NotNull(message = "Provider cannot be null")
    private ProviderDTO provider;

    @NotNull(message = "Item category cannot be null")
    private ItemCategoryDTO itemCategory;

    public ItemDTO(ItemDTO itemDTO) {
        this.id = itemDTO.getId();
        this.name = itemDTO.getName();
        this.description = itemDTO.getDescription();
        this.serialNumber = itemDTO.getSerialNumber();
        this.url = itemDTO.getUrl();
        this.producer = itemDTO.getProducer();
        this.provider = itemDTO.getProvider();
        this.itemCategory = itemDTO.getItemCategory();

    }

}
