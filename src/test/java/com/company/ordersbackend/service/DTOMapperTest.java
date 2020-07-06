package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.model.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class DTOMapperTest {

    @Autowired
    DTOMapper dtoMapper;

    @Test
    public void itemCategoryDTOTest(){
        //given
        ItemCategory itemCategory = new ItemCategory(1L, "Xxxx");
        //when
        ItemCategoryDTO result = dtoMapper.itemCategoryDTO(itemCategory);
        //then
        assertEquals(itemCategory.getId(), result.getId());
        assertEquals(itemCategory.getName(), result.getName());
    }

    @Test
    public void itemCategoryPojoTest(){
        //given
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO(1L, "YYY");
        //when
        ItemCategory result = dtoMapper.itemCategoryPOJO(itemCategoryDTO);
        //then
        assertEquals(itemCategoryDTO.getId(), result.getId());
        assertEquals(itemCategoryDTO.getName(), result.getName());
    }

    @Test
    public void providerPojoTest(){
        //given
        ProviderDTO providerDTO = new ProviderDTO(1L, "YYY");
        //when
        Provider result = dtoMapper.providerPOJO(providerDTO);
        //then
        assertEquals(providerDTO.getId(), result.getId());
        assertEquals(providerDTO.getName(), result.getName());
    }


    @Test
    public void providerDTOTest(){
        //given
        Provider provider = new Provider(1L, "Xxxx");
        //when
        ProviderDTO result = dtoMapper.providerDTO(provider);
        //then
        assertEquals(provider.getId(), result.getId());
        assertEquals(provider.getName(), result.getName());
    }

    @Test
    public void producerPojoTest(){
        //given
        ProducerDTO producerDTO = new ProducerDTO(1L, "YYY");
        //when
        Producer result = dtoMapper.producerPOJO(producerDTO);
        //then
        assertEquals(producerDTO.getId(), result.getId());
        assertEquals(producerDTO.getName(), result.getName());
    }

    @Test
    public void producerDTOTest(){
        //given
        Producer producer = new Producer(1L, "Xxxx");
        //when
        ProducerDTO result = dtoMapper.producerDTO(producer);
        //then
        assertEquals(producer.getId(), result.getId());
        assertEquals(producer.getName(), result.getName());
    }

    @Test
    public void itemDTOTest(){
        //given
        Item item =  new Item(1L, "XXX", "YYYY","ZZZ","WWWWWW", new Producer(), new Provider(), new ItemCategory());
        //when
        ItemDTO result = dtoMapper.itemDTO(item);
        //then
        assertThat(result, instanceOf(ItemDTO.class));
        assertEquals(result.getId(), item.getId());
        assertEquals(result.getName(), item.getName());
        assertEquals(result.getSerialNumber(), item.getSerialNumber());
        assertEquals(result.getUrl(), item.getUrl());
        assertThat(result.getProducer(), instanceOf(ProducerDTO.class));
        assertThat(result.getProvider(), instanceOf(ProviderDTO.class));
        assertThat(result.getItemCategory(), instanceOf(ItemCategoryDTO.class));
    }

    @Test
    public void appUserPojo_Test(){
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user123", "USER", "webdas@gmail.com");
        //when
        AppUser result = dtoMapper.appUserPOJO(appUserDTO);
        //then
        assertThat(result, instanceOf(AppUser.class));
        assertEquals(result.getId(), appUserDTO.getId());
    }

    @Test
    public void appUserDTO_Test(){
        //given
        AppUser appUser = new AppUser(1L, "user", "user123", "USER", "webdas@gmail.com");
        //when
        AppUserDTO result = dtoMapper.appUserDTO(appUser);
        //then
        assertThat(result, instanceOf(AppUserDTO.class));
        assertEquals(result.getId(), appUser.getId());
    }

    @Test
    public void itemInOrderPojo_Test(){
        //given
        ItemInOrderDTO itemInOrderDTO = new ItemInOrderDTO();
        ProducerDTO producerDTO = new ProducerDTO();
        producerDTO.setId(1L);
        ProviderDTO providerDTO = new ProviderDTO();
        providerDTO.setId(1L);
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
        itemCategoryDTO.setId(1L);
        itemInOrderDTO.setProducer(producerDTO);
        itemInOrderDTO.setProvider(providerDTO);
        itemInOrderDTO.setItemCategory(itemCategoryDTO);
        itemInOrderDTO.setId(1L);
        //when
        ItemInOrder result = dtoMapper.itemInOrderPOJO(itemInOrderDTO);
        //then
        assertThat(result, instanceOf(ItemInOrder.class));
        assertEquals(result.getId(), itemInOrderDTO.getId());
    }

    @Test
    public void itemInOrderDTO_Test(){
        //given
        ItemInOrder itemInOrder = new ItemInOrder();
        itemInOrder.setId(1L);
        Producer producer = new Producer();
        producer.setId(1L);
        Provider provider = new Provider();
        provider.setId(1L);
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setId(1L);
        itemInOrder.setProducer(producer);
        itemInOrder.setProvider(provider);
        itemInOrder.setItemCategory(itemCategory);

        //when
        ItemInOrderDTO result = dtoMapper.itemInOrderDTO(itemInOrder);
        //then
        assertThat(result, instanceOf(ItemInOrderDTO.class));
        assertEquals(result.getId(), itemInOrder.getId());
    }

    @Test
    public void orderPOJO_Test(){
        //given
        OrderDTO orderDTO = new OrderDTO();
        //when
        Order result = dtoMapper.orderPOJO(orderDTO);
        //then
        assertThat(result, instanceOf(Order.class));
    }

    @Test
    public void orderDTO_Test(){
        //given
        Order order = new Order();
        //when
        OrderDTO result = dtoMapper.orderDTO(order);
        //then]
        assertThat(result, instanceOf(OrderDTO.class));
    }
}