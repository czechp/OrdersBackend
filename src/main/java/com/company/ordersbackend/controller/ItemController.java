package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ItemAccessoryDTO;
import com.company.ordersbackend.model.ItemDTO;
import com.company.ordersbackend.service.CsvMapperService;
import com.company.ordersbackend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/item")
@CrossOrigin
@Validated()
public class ItemController {
    private ItemService itemService;
    private CsvMapperService csvMapperServicer;

    @Autowired()
    public ItemController(ItemService itemService, CsvMapperService csvMapperService) {
        this.itemService = itemService;
        this.csvMapperServicer = csvMapperService;
    }

    @GetMapping
    public List<ItemDTO> findALl() {
        return itemService.findAll();
    }

    @PostMapping
    public ResponseEntity<ItemDTO> save(@RequestBody @Valid ItemDTO item, Errors errors) {
        Optional<ItemDTO> result = itemService.save(item, errors);
        return result.isPresent() ? ResponseEntity.ok(result.get()) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{itemId}/accessory/{accessoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemAccessoryDTO createNewAccessory(@PathVariable(name = "itemId") @Min(1) long itemId,
                                               @PathVariable(name="accessoryId") @Min(1) long accessoryId){
        return itemService.createNewAccessory(itemId, accessoryId);
    }

    @DeleteMapping("/accessory/{accessoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccessory(@PathVariable(value = "accessoryId") @Min(1) long accessoryId){
        itemService.deleteAccessory(accessoryId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid ItemDTO itemDTO, Errors errors) {
        return itemService.update(id, itemDTO, errors) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        return itemService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @PostMapping("/csv/category/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void mapCsvToItem(@RequestParam("file") MultipartFile file, @PathVariable("id") long id) {
        csvMapperServicer.mapToItem(file, id);
    }
}
