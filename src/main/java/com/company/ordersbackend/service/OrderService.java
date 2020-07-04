package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.Order;
import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import com.company.ordersbackend.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private DTOMapper dtoMapper;
    private AppUserRepository appUserRepository;

    public OrderService(OrderRepository orderRepository, DTOMapper dtoMapper, AppUserRepository appUserRepository) {
        this.orderRepository = orderRepository;
        this.dtoMapper = dtoMapper;
        this.appUserRepository = appUserRepository;
    }

    public Optional<OrderDTO> save(OrderDTO orderDTO, Errors errors, String username){
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if(!errors.hasErrors() && optionalAppUser.isPresent()) {
            Order order = dtoMapper.orderPOJO(orderDTO);
            order.setAppUser(optionalAppUser.get());
            return Optional.of(dtoMapper.orderDTO(orderRepository.save(order)));
        }
        return Optional.empty();
    }
}