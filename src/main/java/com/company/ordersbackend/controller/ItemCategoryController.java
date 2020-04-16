package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.service.ItemCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping(path = "/api/itemCategory")
public class ItemCategoryController {
    private ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemCategoryDTO> findAll() {
        return itemCategoryService.findAll();
    }
}
