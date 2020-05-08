package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.domain.Producer;
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

    public List<ItemDTO> findAll(){
        return toDTOList(itemRepository.findAll());
    }

    public Optional<ItemDTO> save(ItemDTO itemDTO, Errors errors){
        if(!errors.hasErrors()){
            Item item = dtoMapper.itemPOJO(itemDTO);
            if(producerRepository.existsById(item.getProducer().getId())){
                if(providerRepository.existsById(item.getProducer().getId())){
                    if(itemCategoryRepository.existsById(item.getItemCategory().getId())){
                        return Optional.of(dtoMapper.itemDTO(itemRepository.save(item)));
                    }
                }
            }

        }
        return Optional.empty();
    }


    private List<ItemDTO> toDTOList(List<Item> itemList){
        List<ItemDTO> result = new ArrayList<>();
        for (Item item : itemList) {
            result.add(dtoMapper.itemDTO(item));
        }
        return result;
    }

}
