package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.Order;
import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import com.company.ordersbackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderServiceTest {
    @Mock
    Errors errors;
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemService itemService;

    @Mock
    private AppUserService appUserService;

    @Autowired
    private DTOMapper dtoMapper;

    private OrderService orderService;

    @BeforeEach
    public void init() {
        this.orderService = new OrderService(orderRepository, dtoMapper, itemService, appUserService);
    }

    @Test
    public void saveTest() {
        //given
        OrderDTO orderDTO = new OrderDTO();
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(errors.hasErrors()).thenReturn(false);
        when(orderRepository.save(any())).thenReturn(new Order());
        Optional<OrderDTO> result = orderService.save(orderDTO, errors, "user");
        //then
        assertTrue(result.isPresent());
    }

    @Test
    public void saveTest_hasErrors() {
        //given
        OrderDTO orderDTO = new OrderDTO();
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(errors.hasErrors()).thenReturn(true);
        Optional<OrderDTO> result = orderService.save(orderDTO, errors, "user");
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void saveTest_UserDoesNotExist() {
        //given
        OrderDTO orderDTO = new OrderDTO();
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.empty());
        when(errors.hasErrors()).thenReturn(false);
        when(orderRepository.save(any())).thenReturn(new Order());
        Optional<OrderDTO> result = orderService.save(orderDTO, errors, "user");
        //then
        assertTrue(result.isEmpty());
    }

}