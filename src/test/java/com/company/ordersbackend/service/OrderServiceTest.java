package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.exception.AccessDeniedException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.model.OrderDTO;
import com.company.ordersbackend.repository.ItemAccessoryRepository;
import com.company.ordersbackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderServiceTest {
    @Mock
    Errors errors;
    @Mock()
    Principal principal;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ItemService itemService;
    @Mock
    private AppUserService appUserService;
    @Mock
    private EmailSenderService emailSenderService;
    @MockBean()
    private ItemAccessoryRepository itemAccessoryRepository;


    @Autowired
    private DTOMapper dtoMapper;

    private OrderService orderService;

    private Order order;
    private AppUser appUser;
    private ItemAccessory itemAccessory;
    private Producer producer;
    private Provider provider;
    private ItemCategory itemCategory;

    @BeforeEach
    public void init() {
        this.orderService = new OrderService(orderRepository, dtoMapper, itemService, appUserService, emailSenderService, itemAccessoryRepository);
        appUser = new AppUser("user", "user", "superUser", "webcoderc@gmail.com");

        this.order = Order.builder()
                .id(1L)
                .name("Any order")
                .appUser(appUser)
                .itemsInOrder(new ArrayList<>())
                .orderStatus(OrderStatus.NEW)
                .creationDate(LocalDateTime.now())
                .closedDate(LocalDateTime.now())
                .commentary("Any commentary")
                .orderNr("Any order nr")
                .build();

        this.producer = Producer.builder()
                .id(1L)
                .name("Any producer")
                .build();

        this.provider = Provider.builder()
                .id(1L)
                .name("Any provider")
                .build();

        this.itemCategory = ItemCategory.builder()
                .id(1L)
                .name("Any item category")
                .build();

        this.itemAccessory = ItemAccessory.builder()
                .id(1L)
                .name("Any item accessory")
                .serialNumber("123-456")
                .description("Any description")
                .url("www.google.pl")
                .producer(producer)
                .provider(provider)
                .itemCategory(itemCategory)
                .build();
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
    public void findOrderByUsernameTest() {
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
    public void findOrderByUsernameTest_UserNotExist() {
        //given
        String username = "user";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.empty());
        List<OrderDTO> result = orderService.findAllByUsername(username);
        //then
        assertThat(result, hasSize(0));
    }

    @Test
    public void findByUsernameAndIdTest() {
        //given
        String username = "user";
        AppUser appUser = new AppUser();
        appUser.setRole(AppUserRole.USER.toString());
        long id = 1L;
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(appUser));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.of(new Order()));
        Optional<OrderDTO> result = orderService.findByUsernameAndId(username, id);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get(), instanceOf(OrderDTO.class));
    }

    @Test
    public void findByUsernameAndIdTest_userNotExists() {
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
    public void findByUsernameAndIdTest_orderNotExists() {
        //given
        String username = "user";
        long id = 1L;
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(appUser));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.findByUsernameAndId(username, id);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void modifyNameTest() {
        //given
        String username = "user";
        long id = 1L;
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
    public void modifyNameTest_userNotExists() {
        //given
        String username = "user";
        long id = 1L;
        String orderName = "New order name";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.modifyName(username, id, orderName);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void modifyNameTest_orderNotExists() {
        //given
        String username = "user";
        long id = 1L;
        String orderName = "New order name";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.modifyName(username, id, orderName);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void modifyStatusTest() {
        //given
        String username = "user";
        long id = 1L;
        String status = "FINISHED";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.of(new Order()));
        when(orderRepository.save(any())).thenReturn(new Order());
        Optional<OrderDTO> result = orderService.modifyStatus(username, id, status);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get(), instanceOf(OrderDTO.class));
    }

    @Test
    public void modifyStatusTest_userNotExists() {
        //given
        String username = "user";
        long id = 1L;
        String status = "FINISHED";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        Optional<OrderDTO> result = orderService.modifyStatus(username, id, status);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void modifyStatusTest_orderNotExists() {
        //given
        String username = "user";
        long id = 1L;
        String status = "FINISHED";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.modifyStatus(username, id, status);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void modifyStatusTest_statusNotExists() {
        //given
        String username = "user";
        long id = 1L;
        String status = "XXXX";
        //when
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.of(new AppUser()));
        when(orderRepository.findByAppUserAndId(any(), anyLong())).thenReturn(Optional.of(new Order()));
        when(orderRepository.save(any())).thenReturn(new Order());
        Optional<OrderDTO> result = orderService.modifyStatus(username, id, status);
        //then
        assertFalse(result.isPresent());
    }

    @Test()
    public void findOrderByStatusForSuperUserTest() {
        //given
        String status = "NEW";
        //when
        when(appUserService.isSuperUser(any())).thenReturn(true);
        when(orderRepository.findByOrderStatus(any())).thenReturn(
                Arrays.asList(
                        new Order(),
                        new Order()
                )
        );
        List<OrderDTO> result = orderService.findOrderByStatusForSuperUser(status, principal);
        //then
        assertThat(result, hasSize(2));
    }

    @Test()
    public void findOrderByStatusForSuperUserTest_notSuperUser() {
        //given
        String status = "NEW";
        //when
        when(appUserService.isSuperUser(any())).thenReturn(false);
        //then
        assertThrows(AccessDeniedException.class, () -> orderService.findOrderByStatusForSuperUser(status, principal));
    }

    @Test()
    public void findOrderByStatusForSuperUserTest_statusNotExists() {
        //given
        String status = "xxxx";
        //when
        when(appUserService.isSuperUser(any())).thenReturn(true);
        //then
        assertThrows(NotFoundException.class, () -> orderService.findOrderByStatusForSuperUser(status, principal));
    }

    @Test()
    public void deleteTest() {
        //given
        long id = 1L;
        AppUser appUser = new AppUser();
        Order order = new Order();
        appUser.getOrders().add(order);
        order.setAppUser(appUser);
        //when
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        orderService.delete(id);
        //then
        verify(appUserService, times(1)).saveAppUser(appUser);
    }

    @Test()
    public void deleteTest_orderNotExists() {
        //given
        long id = 1L;
        //when
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> orderService.delete(id));
    }

    @Test()
    public void addItemToOrderFromAccessoriesTest() {
        //given
        long orderId = 1L;
        long accessoryId = 1L;
        int amount = 10;
        String username = "user";
        //when
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        when(itemAccessoryRepository.findById(anyLong())).thenReturn(Optional.of(itemAccessory));
        OrderDTO result = orderService.addItemToOrderFromAccessories(orderId, accessoryId, amount, username);
        //then
        assertThat(result, instanceOf(OrderDTO.class));
        assertThat(result.getItemsInOrder(), hasSize(1));
    }

    @Test()
    public void addItemToOrderFromAccessoriesTest_AccesDenied() {
        //given
        long orderId = 1L;
        long accessoryId = 1L;
        int amount = 10;
        String username = "admin";
        //when
        when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        when(itemAccessoryRepository.findById(anyLong())).thenReturn(Optional.of(itemAccessory));
        //then
        assertThrows(AccessDeniedException.class, () -> orderService.addItemToOrderFromAccessories(orderId, accessoryId, amount, username));
    }

    @Test()
    public void addItemToOrderFromAccessoriesTest_NotFound() {
        //given
        long orderId = 1L;
        long accessoryId = 1L;
        int amount = 10;
        String username = "admin";
        //when
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(itemAccessoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> orderService.addItemToOrderFromAccessories(orderId, accessoryId, amount, username));
    }
}