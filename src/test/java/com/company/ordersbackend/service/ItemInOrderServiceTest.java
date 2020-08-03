package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.model.ItemInOrderDTO;
import com.company.ordersbackend.repository.ItemInOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest()
@RunWith(SpringRunner.class)
class ItemInOrderServiceTest {

    @Mock()
    private ItemInOrderRepository itemInOrderRepository;

    @Autowired()
    private  DTOMapper dtoMapper;

    private ItemInOrderService itemInOrderService;

    @BeforeEach()
    public void init(){
        this.itemInOrderService = new ItemInOrderService(itemInOrderRepository, dtoMapper);
    }

    @Test()
    public void deleteTest(){
        //given
        long id = 1L;
        ItemInOrder itemInOrder = new ItemInOrder();
        itemInOrder.setItemCategory(new ItemCategory());
        itemInOrder.setProvider(new Provider());
        itemInOrder.setProducer(new Producer());
        //when
        when(itemInOrderRepository.findById(id)).thenReturn(Optional.of(itemInOrder));
        Optional<ItemInOrderDTO> result = itemInOrderService.delete(id);
        //then
        assertTrue(result.isPresent());
    }

    @Test()
    public void deleteTest_NotExists(){
        //given
        long id = 1L;
        //when
        when(itemInOrderRepository.findById(id)).thenReturn(Optional.empty());
        Optional<ItemInOrderDTO> result = itemInOrderService.delete(id);
        //then
        assertFalse(result.isPresent());
    }

    @Test()
    public void findByIdTest(){
        //given
        long id = 1L;
        ItemInOrder itemInOrder = new ItemInOrder();
        itemInOrder.setItemCategory(new ItemCategory());
        itemInOrder.setProvider(new Provider());
        itemInOrder.setProducer(new Producer());
        //when
        when(itemInOrderRepository.findById(anyLong())).thenReturn(Optional.of(itemInOrder));
        Optional<ItemInOrderDTO> result = itemInOrderService.findById(id);
        //then
        assertTrue(result.isPresent());
    }

    @Test()
    public void findByIdTest_NotExists(){
        //given
        //when
        when(itemInOrderRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<ItemInOrderDTO> result = itemInOrderService.findById(anyLong());
        //then
        assertFalse(result.isPresent());
    }

}