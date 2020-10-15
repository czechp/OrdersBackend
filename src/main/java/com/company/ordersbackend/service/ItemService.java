package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.ItemAccessory;
import com.company.ordersbackend.domain.ItemInOrder;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.model.ItemAccessoryDTO;
import com.company.ordersbackend.model.ItemDTO;
import com.company.ordersbackend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private ItemInOrderRepository itemInOrderRepository;
    private ItemAccessoryRepository itemAccessoryRepository;


    public ItemService(ItemRepository itemRepository, DTOMapper dtoMapper, ProducerRepository producerRepository, ProviderRepository providerRepository, ItemCategoryRepository itemCategoryRepository, ItemInOrderRepository itemInOrderRepository, ItemAccessoryRepository itemAccessoryRepository) {
        this.itemRepository = itemRepository;
        this.dtoMapper = dtoMapper;
        this.producerRepository = producerRepository;
        this.providerRepository = providerRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.itemInOrderRepository = itemInOrderRepository;
        this.itemAccessoryRepository = itemAccessoryRepository;
    }

    public Optional<Item> findById(long id) {
        return itemRepository.findById(id);
    }

    public List<ItemDTO> findAll() {
        return toDTOList(itemRepository.findAll());
    }

    public Optional<ItemDTO> save(ItemDTO itemDTO, Errors errors) {
        if (!errors.hasErrors()) {
            Item item = dtoMapper.itemPOJO(itemDTO);
            if (producerRepository.existsById(item.getProducer().getId())) {
                if (providerRepository.existsById(item.getProvider().getId())) {
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

    public Optional<ItemInOrder> convertItemIntoItemInOrder(long itemId, int amount) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            ItemInOrder itemInOrder = new ItemInOrder(optionalItem.get());
            itemInOrder.setAmount(amount);
            return Optional.of(itemInOrder);
        }
        return Optional.empty();
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
        if (itemRepository.existsById(id)) {
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


    public List<Item> saveAll(List<Item> items) {
        return itemRepository.saveAll(items);
    }

    public boolean existsByName(String name) {
        return itemRepository.existsByName(name);
    }

    @Transactional()
    public ItemAccessoryDTO createNewAccessory(long itemId, long accessoryId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(
                        () -> new NotFoundException("item id --- " + itemId)
                );
        Item itemToAccessory = itemRepository.findById(accessoryId)
                .orElseThrow(
                        () -> new NotFoundException("item id (accessory) --- " + itemId)
                );
        ItemAccessory itemAccessory = new ItemAccessory(itemToAccessory);
        item.addItemAccessory(itemAccessory);
        return dtoMapper.itemAccessoryDTO(itemAccessoryRepository.save(itemAccessory));
    }

    @Transactional()
    public void deleteAccessory(long accessoryId) {
        ItemAccessory itemAccessory = itemAccessoryRepository.findById(accessoryId)
                .orElseThrow(() -> new NotFoundException("itemAccessory id --- " + accessoryId));
        itemAccessoryRepository.delete(itemAccessory);
    }
}
