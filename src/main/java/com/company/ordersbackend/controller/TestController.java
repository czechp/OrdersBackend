package com.company.ordersbackend.controller;

import com.company.ordersbackend.domain.Order;
import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import com.company.ordersbackend.repository.ItemInOrderRepository;
import com.company.ordersbackend.repository.OrderRepository;
import com.company.ordersbackend.service.DTOMapper;
import com.company.ordersbackend.service.OrderService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Profile("development")
@RestController()
@RequestMapping(value = "/api/test")
public class TestController {
    private OrderRepository orderRepository;
    private AppUserRepository appUserRepository;
    private ItemInOrderRepository itemInOrderRepository;
    private OrderService orderService;
    private DTOMapper dtoMapper;

    public TestController(OrderRepository orderRepository, AppUserRepository appUserRepository, ItemInOrderRepository itemInOrderRepository, OrderService orderService, DTOMapper dtoMapper) {
        this.orderRepository = orderRepository;
        this.appUserRepository = appUserRepository;
        this.itemInOrderRepository = itemInOrderRepository;
        this.orderService = orderService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> test() {
        Order order = new Order();
        order.setName("Start");
        order.setAppUser(appUserRepository.findById(1L).get());

        orderRepository.save(order);
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> result = orders.stream()
                .map(x -> dtoMapper.orderDTO(x))
                .collect(Collectors.toList());

        System.out.println(orderService.addItemToOrder(1L, "user", 1L, 10).get());


        return new ResponseEntity(result, HttpStatus.OK);
    }
}
