package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.Order;
import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    public void findOrderByUsernameTest(){
        //given
        String username = "user";
        AppUser appUser = new AppUser();
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(appUser));
        when(orderRepository.findByAppUser(any())).thenReturn(
                Arrays.asList(
                        new Order(),
                        new Order()
                )
        );
        List<OrderDTO> result = orderService.findAllByUsername(username);
        //then
        assertThat(result, hasSize(2));
    }

    @Test
    public void findOrderByUsernameTest_UserNotExist(){
        //given
        String username = "user";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.empty());
        List<OrderDTO> result = orderService.findAllByUsername(username);
        //then
        assertThat(result, hasSize(0));
    }

    @Test
    public void findByUsernameAndIdTest(){
        //given
        String username = "user";
        long id = 1L;
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.of(new Order()));
        Optional<OrderDTO> result = orderService.findByUsernameAndId(username, id);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get(), instanceOf(OrderDTO.class));
    }

    @Test
    public void findByUsernameAndIdTest_userNotExists(){
        //given
        String username = "user";
        long id = 1L;
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.findByUsernameAndId(username, id);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void findByUsernameAndIdTest_orderNotExists(){
        //given
        String username = "user";
        long id = 1L;
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.findByUsernameAndId(username, id);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void modifyNameTest(){
        //given
        String username = "user";
        long id  = 1L;
        String orderName = "New order name";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.of(new Order()));
        when(orderRepository.save(any())).thenReturn(new Order());
        Optional<OrderDTO> result = orderService.modifyName(username, id, orderName);
        //then
        assertTrue(result.isPresent());
    }

    @Test
    public void modifyNameTest_userNotExists(){
        //given
        String username = "user";
        long id  = 1L;
        String orderName = "New order name";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.modifyName(username, id, orderName);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void modifyNameTest_orderNotExists(){
        //given
        String username = "user";
        long id  = 1L;
        String orderName = "New order name";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.modifyName(username, id, orderName);
        //then
        assertFalse(result.isPresent());
    }
}