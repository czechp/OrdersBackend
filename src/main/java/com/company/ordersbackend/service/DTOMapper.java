package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.model.*;
import org.springframework.stereotype.Service;

@Service
public class DTOMapper {

    public ItemCategoryDTO itemCategoryDTO(ItemCategory itemCategory) {
        ItemCategoryDTO result = new ItemCategoryDTO();
        result.setId(itemCategory.getId());
        result.setName(itemCategory.getName());
        return result;
    }

    public ItemCategory itemCategoryPOJO(ItemCategoryDTO itemCategoryDTO) {
        ItemCategory result = new ItemCategory();
        result.setId(itemCategoryDTO.getId());
        result.setName(itemCategoryDTO.getName());
        return result;
    }

    public ProviderDTO providerDTO(Provider provider) {
        ProviderDTO result = new ProviderDTO();
        result.setId(provider.getId());
        result.setName(provider.getName());
        return result;
    }

    public Provider providerPOJO(ProviderDTO providerDTO) {
        Provider result = new Provider();
        result.setId(providerDTO.getId());
        result.setName(providerDTO.getName());
        return result;
    }

    public ProducerDTO producerDTO(Producer producer) {
        ProducerDTO result = new ProducerDTO();
        result.setId(producer.getId());
        result.setName(producer.getName());
        return result;
    }

    public Producer producerPOJO(ProducerDTO producerDTO) {
        Producer result = new Producer();
        result.setId(producerDTO.getId());
        result.setName(producerDTO.getName());

        return result;
    }

    public ItemDTO itemDTO(Item item) {
        ItemDTO result = new ItemDTO();
        result.setId(item.getId());
        result.setName(item.getName());
        result.setSerialNumber(item.getSerialNumber());
        result.setUrl(item.getUrl());
        result.setDescription(item.getDescription());
        result.setProducer(producerDTO(item.getProducer()));
        result.setProvider(providerDTO(item.getProvider()));
        result.setItemCategory(itemCategoryDTO(item.getItemCategory()));

        return result;
    }

    public Item itemPOJO(ItemDTO itemDTO) {
        Item result = new Item();
        result.setId(itemDTO.getId());
        result.setName(itemDTO.getName());
        result.setSerialNumber(itemDTO.getSerialNumber());
        result.setUrl(itemDTO.getUrl());
        result.setDescription(itemDTO.getDescription());
        result.setProducer(producerPOJO(itemDTO.getProducer()));
        result.setProvider(providerPOJO(itemDTO.getProvider()));
        result.setItemCategory(itemCategoryPOJO(itemDTO.getItemCategory()));
        return result;
    }

    public AppUser appUserPOJO(AppUserDTO appUserDTO) {
        AppUser result = new AppUser();
        result.setId(appUserDTO.getId());
        result.setUsername(appUserDTO.getUsername());
        result.setPassword(appUserDTO.getPassword());
        result.setEmail(appUserDTO.getEmail());
        result.setRole(appUserDTO.getRole());
        return result;
    }

    public AppUserDTO appUserDTO(AppUser appUser) {
        AppUserDTO result = new AppUserDTO();
        result.setId(appUser.getId());
        result.setUsername(appUser.getUsername());
        result.setPassword(appUser.getPassword());
        result.setEmail(appUser.getEmail());
        result.setRole(appUser.getRole());
        return result;
    }

}
