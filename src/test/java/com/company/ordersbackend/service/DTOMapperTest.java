package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.model.ProviderDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}