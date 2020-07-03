package com.company.ordersbackend.controller;

import com.company.ordersbackend.domain.Order;
import com.company.ordersbackend.repository.AppUserRepository;
import com.company.ordersbackend.repository.ItemInOrderRepository;
import com.company.ordersbackend.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(value = "/api/test")
public class TestController {
    private OrderRepository orderRepository;
    private AppUserRepository appUserRepository;
    private ItemInOrderRepository itemInOrderRepository;

    public TestController(OrderRepository orderRepository, AppUserRepository appUserRepository, ItemInOrderRepository itemInOrderRepository) {
        this.orderRepository = orderRepository;
        this.appUserRepository = appUserRepository;
        this.itemInOrderRepository = itemInOrderRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Order>> test() {
        Order order = new Order();
        order.setAppUser(appUserRepository.findById(1L).get());
        order.addItem(itemInOrderRepository.findById(4L).get());
        orderRepository.save(order);
        List<Order> result = orderRepository.findAll();
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
