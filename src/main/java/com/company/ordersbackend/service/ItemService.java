package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.model.ItemDTO;
import com.company.ordersbackend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private DTOMapper dtoMapper;

    public ItemService(ItemRepository itemRepository, DTOMapper dtoMapper) {
        this.itemRepository = itemRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<ItemDTO> findAll(){
        return toDTOList(itemRepository.findAll());
    }


    private List<ItemDTO> toDTOList(List<Item> itemList){
        List<ItemDTO> result = new ArrayList<>();
        for (Item item : itemList) {
            result.add(dtoMapper.itemDTO(item));
        }
        return result;
    }


}
