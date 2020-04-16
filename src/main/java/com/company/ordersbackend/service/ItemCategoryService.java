package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.repository.ItemCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<ItemCategoryDTO> save(ItemCategoryDTO itemCategoryDTO, Errors errors) {
        if (!errors.hasErrors()) {
            ItemCategory result = itemCategoryRepository.save(modelMapper.map(itemCategoryDTO, ItemCategory.class));
            return Optional.of(modelMapper.map(result, ItemCategoryDTO.class));
        }
        return Optional.empty();
    }

    private List<ItemCategoryDTO> mapToList(List<ItemCategory> all) {
        List<ItemCategoryDTO> result = new ArrayList<>();
        for (ItemCategory itemCategory : all) {
            result.add(modelMapper.map(itemCategory, ItemCategoryDTO.class));
        }
        return result;
    }


}
