package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.repository.ItemCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ItemCategoryServiceTest {

    @Autowired
    DTOMapper dtoMapper;

    @Mock
    Errors errors;

    @Mock
    ItemCategoryRepository itemCategoryRepository;

    ItemCategoryService itemCategoryService;

    @BeforeEach
    public void init() {
        this.itemCategoryService = new ItemCategoryService(itemCategoryRepository, dtoMapper);
    }

    @Test
    public void findAllTest() {
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

    @Test
    public void saveTest_noErrors() {
        //given
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO(0L, "PLC");
        ItemCategory itemCategory = new ItemCategory("PLC");

        //when
        when(itemCategoryRepository.save(itemCategory)).thenReturn(itemCategory);
        when(errors.hasErrors()).thenReturn(false);
        Optional<ItemCategoryDTO> result = itemCategoryService.save(itemCategoryDTO, errors);

        //then
        assertThat(result.get(), instanceOf(ItemCategoryDTO.class));
        assertEquals(result.get().getName(), itemCategoryDTO.getName());
        assertTrue(result.isPresent());
    }

    @Test
    public void saveTest_hasErrors() {
        //give when
        when(errors.hasErrors()).thenReturn(true);
        Optional<ItemCategoryDTO> result = itemCategoryService.save(new ItemCategoryDTO(), errors);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteById_IdExists(){
        //given
        long id = 1L;
        //when
        when(itemCategoryRepository.existsById(id)).thenReturn(true);
        doNothing().when(itemCategoryRepository).deleteById(id);
        boolean result = itemCategoryService.deleteById(id);
        //then
        assertTrue(result);
        verify(itemCategoryRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteById_IdNotExist(){
        //given
        long id = 1L;
        //when
        when(itemCategoryRepository.existsById(id)).thenReturn(false);
        boolean result = itemCategoryService.deleteById(id);
        //then
        assertFalse(result);
        verify(itemCategoryRepository, times(0)).deleteById(id);
    }

    @Test
    public void updateTest(){
        //given
        long id = 1L;
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO(id, "XXX");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(itemCategoryRepository.existsById(id)).thenReturn(true);
        when(itemCategoryRepository.findById(id)).thenReturn(Optional.of(dtoMapper.itemCategoryPOJO(itemCategoryDTO)));
        boolean result = itemCategoryService.update(id, itemCategoryDTO, errors);
        //then
        assertTrue(result);
        verify(itemCategoryRepository, times(1)).save(any());
    }

    @Test
    public void updateTest_hasErrors(){
        //given
        long id = 1L;
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO(id, "XXX");
        //when
        when(errors.hasErrors()).thenReturn(true);
        when(itemCategoryRepository.existsById(id)).thenReturn(true);
        boolean result = itemCategoryService.update(id, itemCategoryDTO, errors);
        //then
        assertFalse(result);
    }

    @Test
    public void updateTest_NotExists(){
        //given
        long id = 1L;
        ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO(id, "XXX");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(itemCategoryRepository.existsById(id)).thenReturn(false);
        boolean result = itemCategoryService.update(id, itemCategoryDTO, errors);
        //then
        assertFalse(result);
    }
}
