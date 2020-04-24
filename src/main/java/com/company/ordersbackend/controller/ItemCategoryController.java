package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.service.ItemCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<ItemCategoryDTO> save(@RequestBody @Valid ItemCategoryDTO itemCategoryDTO, Errors errors) {
        Optional<ItemCategoryDTO> result = itemCategoryService.save(itemCategoryDTO, errors);
        return result.isPresent() ? new ResponseEntity(result.get(), HttpStatus.CREATED) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ItemCategoryDTO> update(@PathVariable long id, @RequestBody @Valid ItemCategoryDTO itemCategoryDTO, Errors errors) {

        return itemCategoryService.update(id, itemCategoryDTO, errors) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable long id){
        return itemCategoryService.deleteById(id) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
