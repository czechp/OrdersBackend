package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.repository.ItemCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ItemCategoryService {
    private ItemCategoryRepository itemCategoryRepository;
    private ModelMapper modelMapper;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository, ModelMapper modelMapper) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<ItemCategoryDTO> findAll() {
        return mapToList(itemCategoryRepository.findAll());
    }

    private List<ItemCategoryDTO> mapToList(List<ItemCategory> all) {
        List<ItemCategoryDTO> result = new ArrayList<>();
        for (ItemCategory itemCategory : all) {
            result.add(modelMapper.map(itemCategory, ItemCategoryDTO.class));
        }
        return result;
    }
}
