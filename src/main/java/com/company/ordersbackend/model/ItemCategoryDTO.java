package com.company.ordersbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategoryDTO {
    private long id;

    @NotBlank(message = "Category name cannot be blank")
    @NotNull(message = "Category name cannot be null")
    private String name;

}
