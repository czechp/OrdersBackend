package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.repository.ItemCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemCategoryService {
    private ItemCategoryRepository itemCategoryRepository;
    private DTOMapper dtoMapper;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository, DTOMapper dtoMapper) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<ItemCategoryDTO> findAll() {
        return mapToList(itemCategoryRepository.findAll());
    }

    public Optional<ItemCategoryDTO> save(ItemCategoryDTO itemCategoryDTO, Errors errors) {
        if (!errors.hasErrors()) {
            ItemCategory result = itemCategoryRepository.save(dtoMapper.itemCategoryPOJO(itemCategoryDTO));
            return Optional.of(dtoMapper.itemCategoryDTO(result));
        }
        return Optional.empty();
    }

    public boolean update(long id, ItemCategoryDTO itemCategoryDTO, Errors errors) {
        if (itemCategoryRepository.existsById(id) && !errors.hasErrors()) {
            ItemCategory itemCategory = itemCategoryRepository.findById(id).get();
            itemCategory.setName(itemCategoryDTO.getName());
            itemCategoryRepository.save(itemCategory);
            return true;
        }
        return false;
    }

    public boolean deleteById(long id) {
        if (itemCategoryRepository.existsById(id)) {
            if (itemCategoryRepository.findById(id).get().getItemList().isEmpty()) {
                itemCategoryRepository.deleteById(id);
                return true;
            }

        }

        return false;
    }

    private List<ItemCategoryDTO> mapToList(List<ItemCategory> all) {
        List<ItemCategoryDTO> result = new ArrayList<>();
        for (ItemCategory itemCategory : all) {
            result.add(dtoMapper.itemCategoryDTO(itemCategory));
        }
        return result;
    }

    public Optional<ItemCategory> findById(long categoryId) {
        return itemCategoryRepository.findById(categoryId);
    }
}
