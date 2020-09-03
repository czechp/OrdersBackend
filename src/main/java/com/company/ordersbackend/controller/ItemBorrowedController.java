package com.company.ordersbackend.controller;

import com.company.ordersbackend.domain.ItemBorrowed;
import com.company.ordersbackend.service.ItemBorrowedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController()
@RequestMapping("/api/itemBorrowed")
public class ItemBorrowedController {
    private ItemBorrowedService itemBorrowedService;

    @Autowired()
    public ItemBorrowedController(ItemBorrowedService itemBorrowedService) {
        this.itemBorrowedService = itemBorrowedService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ItemBorrowed> findAll(){
        return itemBorrowedService.findAll();
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemBorrowed save(@PathVariable(value = "id") long itemId,
                             Principal principal,
                             @RequestParam(value = "amount") int amount){
        return itemBorrowedService.save(itemId, amount, principal);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") long id){
        itemBorrowedService.delete(id);
    }
}
