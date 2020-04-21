package com.company.ordersbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProviderDTO {
        private long id;
        private String name;

        public ProviderDTO(String name) {
            this.name = name;
        }
}
