package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ItemInOrderDTO;
import com.company.ordersbackend.service.ItemInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api/itemInOrder")
@CrossOrigin(origins = "*")
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

    @PutMapping()
    public ItemInOrderDTO update(@RequestBody() @Valid() ItemInOrderDTO itemInOrderDTO, Errors errors){
            return itemInOrderService.update(itemInOrderDTO, errors).orElseThrow(ItemInOrderNotExists::new);
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ItemInOrderNotExists extends RuntimeException{

}
