package com.company.ordersbackend.model;

import com.company.ordersbackend.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategoryDTO {
    private long id;

    @NotBlank(message = "Category name cannot be blank")
    @NotNull(message = "Category name cannot be null")
    private String name;

}
