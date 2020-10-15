package com.company.ordersbackend.controller;

import com.company.ordersbackend.domain.ItemAccessory;
import com.company.ordersbackend.domain.Order;
import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import com.company.ordersbackend.repository.ItemAccessoryRepository;
import com.company.ordersbackend.repository.ItemInOrderRepository;
import com.company.ordersbackend.repository.OrderRepository;
import com.company.ordersbackend.service.DTOMapper;
import com.company.ordersbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Profile("development")
@RestController()
@RequestMapping(value = "/api/test")
public class TestController {

    @Autowired()
    private ItemAccessoryRepository itemAccessoryRepository;

    @GetMapping()
    public int test() {
        return itemAccessoryRepository.findAll().size();
    }
}
