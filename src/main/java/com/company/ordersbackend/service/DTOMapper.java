package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.model.ProviderDTO;
import org.springframework.stereotype.Service;

@Service
public class DTOMapper {

    public ItemCategoryDTO itemCategoryDTO(ItemCategory itemCategory){
        ItemCategoryDTO result = new ItemCategoryDTO();
        result.setId(itemCategory.getId());
        result.setName(itemCategory.getName());
        return result;
    }

    public ItemCategory itemCategoryPOJO(ItemCategoryDTO itemCategoryDTO){
        ItemCategory result = new ItemCategory();
        result.setId(itemCategoryDTO.getId());
        result.setName(itemCategoryDTO.getName());
        return result;
    }

    public ProviderDTO providerDTO(Provider provider){
        ProviderDTO result = new ProviderDTO();
        result.setId(provider.getId());
        result.setName(provider.getName());
        return result;
    }

    public Provider providerPOJO(ProviderDTO providerDTO){
        Provider result = new Provider();
        result.setId(providerDTO.getId());
        result.setName(providerDTO.getName());
        return result;
    }
}
