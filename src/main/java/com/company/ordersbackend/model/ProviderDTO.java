package com.company.ordersbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProviderDTO {
        private long id;
        @NotBlank(message = "Name cannot be empty")
        @NotNull(message = "Name cannot be null")
        private String name;


        public ProviderDTO(String name) {
            this.name = name;
        }
}
