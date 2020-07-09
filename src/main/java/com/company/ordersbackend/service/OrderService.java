package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.ItemInOrder;
import com.company.ordersbackend.domain.Order;
import com.company.ordersbackend.domain.OrderStatus;
import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private DTOMapper dtoMapper;
    private ItemService itemService;
    private AppUserService appUserService;


    public OrderService(OrderRepository orderRepository, DTOMapper dtoMapper, ItemService itemService, AppUserService appUserService) {
        this.orderRepository = orderRepository;
        this.dtoMapper = dtoMapper;
        this.itemService = itemService;
        this.appUserService = appUserService;
    }

    public Optional<OrderDTO> save(OrderDTO orderDTO, Errors errors, String username) {
        Optional<AppUser> optionalAppUser = appUserService.findAppUserByUsername(username);
        if (!errors.hasErrors() && optionalAppUser.isPresent()) {
            Order order = dtoMapper.orderPOJO(orderDTO);
            order.setOrderStatus(OrderStatus.NEW);
            order.setAppUser(optionalAppUser.get());
            return Optional.of(dtoMapper.orderDTO(orderRepository.save(order)));
        }
        return Optional.empty();
    }

    public Optional<OrderDTO> addItemToOrder(long orderId, String username, long itemId, int amount) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getAppUser().getUsername().equals(username) && order.getOrderStatus() != OrderStatus.FINISHED) {
                Optional<ItemInOrder> optionalItemInOrder = itemService.convertItemIntoItemInOrder(itemId, amount);
                if (optionalItemInOrder.isPresent()) {
                    order.getItemsInOrder().add(optionalItemInOrder.get());
                    return Optional.of(dtoMapper.orderDTO(orderRepository.save(order)));
                }
            }
        }
        return Optional.empty();
    }

    public List<OrderDTO> findByUsername(String username) {
        List<OrderDTO> result = new ArrayList<>();
        Optional<AppUser> optionalAppUser = appUserService.findAppUserByUsername(username);
        if (optionalAppUser.isPresent()) {
            List<Order> orders = orderRepository.findByAppUser(optionalAppUser.get());
            for (Order order : orders) {
                result.add(dtoMapper.orderDTO(order));
            }
            return result;
        }

        return result;
    }

    public Optional<OrderDTO> findByUsernameAndId(String username, long id){
        Optional<AppUser> optionalAppUser = appUserService.findAppUserByUsername(username);
        if(optionalAppUser.isPresent()){
            Optional<Order> optionalOrder = orderRepository.findByAppUserAndId(optionalAppUser.get(), id);
            if(optionalOrder.isPresent()){
                return Optional.of(dtoMapper.orderDTO(optionalOrder.get()));
            }
        }

        return Optional.empty();
    }
}