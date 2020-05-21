package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.model.ItemDTO;
import com.company.ordersbackend.repository.ItemCategoryRepository;
import com.company.ordersbackend.repository.ItemRepository;
import com.company.ordersbackend.repository.ProducerRepository;
import com.company.ordersbackend.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private DTOMapper dtoMapper;
    private ProducerRepository producerRepository;
    private ProviderRepository providerRepository;
    private ItemCategoryRepository itemCategoryRepository;

    public ItemService(ItemRepository itemRepository, DTOMapper dtoMapper, ProducerRepository producerRepository, ProviderRepository providerRepository, ItemCategoryRepository itemCategoryRepository) {
        this.itemRepository = itemRepository;
        this.dtoMapper = dtoMapper;
        this.producerRepository = producerRepository;
        this.providerRepository = providerRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public List<ItemDTO> findAll() {
        return toDTOList(itemRepository.findAll());
    }

    public Optional<ItemDTO> save(ItemDTO itemDTO, Errors errors) {
        if (!errors.hasErrors()) {
            Item item = dtoMapper.itemPOJO(itemDTO);
            if (producerRepository.existsById(item.getProducer().getId())) {
                if (providerRepository.existsById(item.getProducer().getId())) {
                    if (itemCategoryRepository.existsById(item.getItemCategory().getId())) {
                        return Optional.of(dtoMapper.itemDTO(itemRepository.save(item)));
                    }
                }
            }

        }
        return Optional.empty();
    }

    public boolean update(long id, ItemDTO itemDTO, Errors errors) {
        if (!errors.hasErrors()) {
            Optional<Item> optionalItem = itemRepository.findById(id);
            if (optionalItem.isPresent()) {
                if (producerRepository.existsById(itemDTO.getProducer().getId()) &&
                        providerRepository.existsById(itemDTO.getProvider().getId()) &&
                        itemCategoryRepository.existsById(itemDTO.getItemCategory().getId())) {
                    Item itemToUpdate = optionalItem.get();
                    itemRepository.save(assignItemDTOtoPOJO(itemDTO, itemToUpdate));
                    return true;
                }
            }
        }
        return false;
    }

    private Item assignItemDTOtoPOJO(ItemDTO itemDTO, Item itemToUpdate) {
        itemToUpdate.setName(itemDTO.getName());
        itemToUpdate.setDescription(itemDTO.getDescription());
        itemToUpdate.setSerialNumber(itemDTO.getSerialNumber());
        itemToUpdate.setUrl(itemDTO.getUrl());
        itemToUpdate.setProducer(producerRepository.findById(itemDTO.getProducer().getId()).get());
        itemToUpdate.setProvider(providerRepository.findById(itemDTO.getProvider().getId()).get());
        itemToUpdate.setItemCategory(itemCategoryRepository.findById(itemDTO.getItemCategory().getId()).get());
        return itemToUpdate;
    }


    public boolean delete(long id) {
        if(itemRepository.existsById(id)){
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private List<ItemDTO> toDTOList(List<Item> itemList) {
        List<ItemDTO> result = new ArrayList<>();
        for (Item item : itemList) {
            result.add(dtoMapper.itemDTO(item));
        }
        return result;
    }


}
