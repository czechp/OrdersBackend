package com.company.ordersbackend.model;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.Provider;
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
public class ProviderDTO {
        private long id;
        @NotBlank(message = "Name cannot be empty")
        @NotNull(message = "Name cannot be null")
        private String name;

        public ProviderDTO(String name) {
            this.name = name;
        }
}
