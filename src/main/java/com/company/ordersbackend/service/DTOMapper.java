package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.domain.Producer;
import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.model.ItemDTO;
import com.company.ordersbackend.model.ProducerDTO;
import com.company.ordersbackend.model.ProviderDTO;
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
        result.setProducer(item.getProducer());
        result.setProvider(item.getProvider());
        result.setItemCategory(item.getItemCategory());

        return result;
    }

    public Item itemPOJO(ItemDTO itemDTO) {
        Item result = new Item();
        result.setId(itemDTO.getId());
        result.setName(itemDTO.getName());
        result.setSerialNumber(itemDTO.getSerialNumber());
        result.setUrl(itemDTO.getUrl());
        result.setProducer(itemDTO.getProducer());
        result.setProvider(itemDTO.getProvider());
        result.setItemCategory(itemDTO.getItemCategory());
        return result;
    }

}
