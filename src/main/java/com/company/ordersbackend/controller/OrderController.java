package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.service.OrderService;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderDTO>> getOrdersForUser(Principal principal) {
        return ResponseEntity.ok(orderService.findAllByUsername(principal.getName()));
    }

    @PostMapping("/{orderId}/item/{itemId}")
    public ResponseEntity<OrderDTO> addItemToOrder(@PathVariable(name = "orderId") long orderId,
                                                   @PathVariable(name = "itemId") long itemId,
                                                   @RequestParam(name = "amount") int amount,
                                                   Principal principal) {
        Optional<OrderDTO> result = orderService.addItemToOrder(orderId, principal.getName(), itemId, amount);
        return result.isPresent() ? ResponseEntity.ok(result.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") long id, Principal principal) {
        Optional<OrderDTO> result = orderService.findByUsernameAndId(principal.getName(), id);
        return result.isPresent() ? ResponseEntity.ok(result.get()) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/name/{id}")
    public ResponseEntity<OrderDTO> changeName(@PathVariable("id")long id,
                                               @RequestParam("name")String name,
                                               Principal principal){
        Optional<OrderDTO> optionalOrderDTO = orderService.modifyName(principal.getName(), id, name);
        return optionalOrderDTO.isPresent() ?
                ResponseEntity.ok(optionalOrderDTO.get()) :
                ResponseEntity.notFound().build();
    }
}
