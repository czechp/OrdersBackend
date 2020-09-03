package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.ItemBorrowed;
import com.company.ordersbackend.exception.BadInputDataException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.repository.ItemBorrowedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service()
public class ItemBorrowedService {
    private ItemBorrowedRepository itemBorrowedRepository;
    private AppUserService appUserService;
    private ItemService itemService;

    @Autowired()
    public ItemBorrowedService(ItemBorrowedRepository itemBorrowedRepository, AppUserService appUserService, ItemService itemService) {
        this.itemBorrowedRepository = itemBorrowedRepository;
        this.appUserService = appUserService;
        this.itemService = itemService;
    }

    public ItemBorrowed save(long itemId, int amount, Principal principal) {
        Item item = itemService.findById(itemId).orElseThrow(() -> new NotFoundException("item --- " + itemId));
        AppUser appUser = appUserService.findAppUserByUsername(principal.getName()).orElseThrow(() -> new NotFoundException("user --- " + principal.getName()));
        ItemBorrowed itemBorrowed = new ItemBorrowed(item);
        itemBorrowed.setId(0L);
        itemBorrowed.setAppUser(appUser);
        itemBorrowed.setAmount(amount);
        try {
            return itemBorrowedRepository.save(itemBorrowed);
        } catch (Exception e) {
            throw new BadInputDataException("amount --- " + amount);
        }
    }

    public void delete(long id) {
        if (itemBorrowedRepository.existsById(id))
            itemBorrowedRepository.deleteById(id);
        else
            throw new NotFoundException("itemBorrowed id --- " + id);
    }

    public List<ItemBorrowed> findAll() {
        return itemBorrowedRepository.findAll();
    }
}
