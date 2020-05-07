package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.model.ItemDTO;
import com.company.ordersbackend.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class ItemServiceTest {
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Autowired
    private DTOMapper dtoMapper;

    @BeforeEach
    public void init(){
        itemService = new ItemService(itemRepository, dtoMapper);
    }

    @Test
    public void findAllTest(){
        //given
        List<Item> items = Arrays.asList(new Item(), new Item(), new Item());
        //when
        when(itemRepository.findAll()).thenReturn(items);
        List<ItemDTO> result = itemService.findAll();
        //then
        assertThat(result, hasSize(items.size()));
        assertThat(result.get(0), instanceOf(ItemDTO.class));
    }
}