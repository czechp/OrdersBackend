package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ItemInOrderDTO;
import com.company.ordersbackend.service.ItemInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/itemInOrder")
@CrossOrigin()

public class ItemInOrderController {
    private ItemInOrderService itemInOrderService;

    @Autowired()
    public ItemInOrderController(ItemInOrderService itemInOrderService) {
        this.itemInOrderService = itemInOrderService;
    }

    @GetMapping()
    public List<ItemInOrderDTO> findAll(){
        return itemInOrderService.findAll();
    }

    @GetMapping("/{id}")
    public ItemInOrderDTO getItemInOrderById(@PathVariable(name = "id") long id){
        return itemInOrderService.findById(id).orElseThrow(ItemInOrderNotExists::new);
    }

    @PutMapping()
    public ItemInOrderDTO update(@RequestBody() @Valid() ItemInOrderDTO itemInOrderDTO, Errors errors){
            return itemInOrderService.update(itemInOrderDTO, errors).orElseThrow(ItemInOrderNotExists::new);
    }

    @DeleteMapping(path="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ItemInOrderDTO> delete(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(
                itemInOrderService.delete(id).orElseThrow(ItemInOrderNotExists::new),
                HttpStatus.OK
        );
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ItemInOrderNotExists extends RuntimeException{
}
