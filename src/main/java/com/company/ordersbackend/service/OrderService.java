package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.exception.AccessDeniedException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.repository.ItemAccessoryRepository;
import com.company.ordersbackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DTOMapper dtoMapper;
    private final ItemService itemService;
    private final AppUserService appUserService;
    private final EmailSenderService emailSenderService;
    private final ItemAccessoryRepository itemAccessoryRepository;

    @Autowired()
    public OrderService(OrderRepository orderRepository, DTOMapper dtoMapper, ItemService itemService, AppUserService appUserService, EmailSenderService emailSenderService, ItemAccessoryRepository itemAccessoryRepository) {
        this.orderRepository = orderRepository;
        this.dtoMapper = dtoMapper;
        this.itemService = itemService;
        this.appUserService = appUserService;
        this.emailSenderService = emailSenderService;
        this.itemAccessoryRepository = itemAccessoryRepository;
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
                    ItemInOrder itemInOrder = optionalItemInOrder.get();
                    order.addItemInOrder(itemInOrder);
                    return Optional.of(dtoMapper.orderDTO(orderRepository.save(order)));
                }
            }
        }
        return Optional.empty();
    }

    public List<OrderDTO> findAllByUsername(String username) {
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

    public Optional<OrderDTO> findById(long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return Optional.of(dtoMapper.orderDTO(optionalOrder.get()));
        }
        return Optional.empty();
    }


    public Optional<OrderDTO> modifyName(String username, long id, String orderName) {
        Optional<AppUser> optionalAppUser = appUserService.findAppUserByUsername(username);
        if (optionalAppUser.isPresent()) {
            Optional<Order> optionalOrder = orderRepository.findByAppUserAndId(optionalAppUser.get(), id);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setName(orderName);
                return Optional.of(dtoMapper.orderDTO(orderRepository.save(order)));
            }

        }
        return Optional.empty();
    }

    public Optional<OrderDTO> modifyStatus(String username, long id, String status) {
        Optional<AppUser> optionalAppUser = appUserService.findAppUserByUsername(username);
        if (optionalAppUser.isPresent()) {
            Optional<Order> optionalOrder = orderRepository.findByAppUserAndId(optionalAppUser.get(), id);
            if (optionalOrder.isPresent() && OrderStatus.getByString(status) != null) {
                Order order = optionalOrder.get();
                order.setOrderStatus(OrderStatus.getByString(status));
                if (OrderStatus.getByString(status) == OrderStatus.REALISE)
                    sendNotificationToSuperUser(order, username);
                return Optional.of(dtoMapper.orderDTO(orderRepository.save(order)));
            }
        }
        return Optional.empty();
    }

    private void sendNotificationToSuperUser(Order order, String username) {

        emailSenderService.sendNotificationAboutNewOrder(username,
                order.getName(),
                appUserService.findByRole(AppUserRole.SUPERUSER.toString()));
    }

    public List<OrderDTO> findOrderByStatusForSuperUser(final String status, Principal principal) {
        if (appUserService.isSuperUser(principal)) {
            if (OrderStatus.getByString(status) != null) {
                return toOrderDTOList(orderRepository.findByOrderStatus(OrderStatus.getByString(status)));
            } else {
                throw new NotFoundException(status);
            }

        } else {
            throw new AccessDeniedException(principal.getName());
        }
    }

    private List<OrderDTO> toOrderDTOList(List<Order> orders) {
        return orders.stream()
                .map(x -> dtoMapper.orderDTO(x))
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
        order.getAppUser().getOrders().remove(order);
        appUserService.saveAppUser(order.getAppUser());
    }

    public OrderDTO modifyCommentary(long id, String commentary, Principal principal) {
        AppUser appUser = appUserService.findAppUserByUsername(principal.getName()).orElseThrow(() -> new NotFoundException("user --- " + principal.getName()));
        Order order = orderRepository.findByAppUserAndId(appUser, id).orElseThrow(() -> new NotFoundException("order id --- " + id));
        order.setCommentary(commentary);
        return dtoMapper.orderDTO(orderRepository.save(order));
    }


    @Transactional()
    public OrderDTO addCommentaryBySuperUser(long id, String commentary, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("order id --- " + id));
        AppUser appUser = appUserService.findAppUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("appuser username --- " + username));
        order.setCommentary(order.getCommentary() + "\n----------------------------------------\n"
                + appUser.getUsername()
                + ": " + "\n" + commentary);
        return dtoMapper.orderDTO(order);
    }

    @Transactional()
    public OrderDTO setOrderNr(long id, String orderNr) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("order id --- " + id));
        order.setOrderNr(orderNr);
        return dtoMapper.orderDTO(order);
    }

    @Transactional()
    public OrderDTO addItemToOrderFromAccessories(long orderId, long accessoryId, int amount, String username) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("order id --- " + orderId));
        ItemAccessory itemAccessory = itemAccessoryRepository.findById(accessoryId).orElseThrow(() -> new NotFoundException("itemAcessory id --- " + accessoryId));
        if (order.getAppUser().getUsername().equals(username)) {
            ItemInOrder itemInOrder = new ItemInOrder(itemAccessory);
            itemInOrder.setAmount(amount);
            order.addItemInOrder(itemInOrder);
            return dtoMapper.orderDTO(order);
        } else {
            throw new AccessDeniedException(username);
        }
    }

    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(x -> dtoMapper.orderDTO(x))
                .collect(Collectors.toList());
    }
}