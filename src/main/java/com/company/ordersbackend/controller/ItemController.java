package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ItemDTO;
import com.company.ordersbackend.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/item")
@CrossOrigin
public class ItemController {
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDTO> findALl(){
        return itemService.findAll();
    }

    @PostMapping
    public ResponseEntity<ItemDTO> save(@RequestBody @Valid ItemDTO item, Errors errors){
        Optional<ItemDTO> result = itemService.save(item, errors);
        return result.isPresent() ? ResponseEntity.ok(result.get()): ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid ItemDTO itemDTO, Errors errors){
        return itemService.update(id, itemDTO, errors) ? ResponseEntity.ok().build(): ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        return itemService.delete(id)? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
