package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/order")
@CrossOrigin()
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> save(@RequestBody OrderDTO orderDTO, Errors errors, Principal principal) {
        Optional<OrderDTO> result = orderService.save(orderDTO, errors, principal.getName());
        return result.isPresent() ? ResponseEntity.ok(result.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getOrdersForUser(Principal principal){
        return  null;
    }
}
