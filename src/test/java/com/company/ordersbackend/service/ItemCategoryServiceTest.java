package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.repository.ItemCategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
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
class ItemCategoryServiceTest {

    @Autowired
    ModelMapper modelMapper;

    @Mock
    ItemCategoryRepository itemCategoryRepository;

    ItemCategoryService itemCategoryService;

    @BeforeEach
    public void init(){
        this.itemCategoryService = new ItemCategoryService(itemCategoryRepository, modelMapper);
    }

    @Test
    public void findAllTest(){
        //given
        List<ItemCategory> list = Arrays.asList(
                new ItemCategory("PLC"),
                new ItemCategory("HMI")
        );

        //when
        when(itemCategoryRepository.findAll()).thenReturn(list);
        List<ItemCategoryDTO> result = itemCategoryService.findAll();

        //then
        assertThat(result, hasSize(list.size()));
        assertThat(result.get(0), instanceOf(ItemCategoryDTO.class));
        assertEquals(list.get(0).getId(), result.get(0).getId());
        assertEquals(list.get(0).getName(), result.get(0).getName());
    }
}
