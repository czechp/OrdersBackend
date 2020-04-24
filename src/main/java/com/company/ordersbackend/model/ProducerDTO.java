package com.company.ordersbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDTO {
    private long id;

    @NotNull(message = "Name cannot be  null")
    @NotBlank(message = "Name cannot be empty")
    private String name;
}
