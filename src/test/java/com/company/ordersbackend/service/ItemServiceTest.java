package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.domain.Producer;
import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ItemCategoryDTO;
import com.company.ordersbackend.model.ItemDTO;
import com.company.ordersbackend.model.ProducerDTO;
import com.company.ordersbackend.model.ProviderDTO;
import com.company.ordersbackend.repository.ItemCategoryRepository;
import com.company.ordersbackend.repository.ItemRepository;
import com.company.ordersbackend.repository.ProducerRepository;
import com.company.ordersbackend.repository.ProviderRepository;
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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class ItemServiceTest {
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private Errors errors;

    @Autowired
    private DTOMapper dtoMapper;

    @Mock
    private ProducerRepository producerRepository;

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private ItemCategoryRepository itemCategoryRepository;

    @BeforeEach
    public void init(){
        itemService = new ItemService(itemRepository, dtoMapper, producerRepository, providerRepository, itemCategoryRepository);
    }

    @Test
    public void findAllTest(){
        //given
        List<Item> items = Arrays.asList(
                new Item(1L,"xxxx","yyyy","zzzz", "zxczxc", new Producer(1L, "xxx"), new Provider("xxxx"), new ItemCategory(1L, "xxxx")),
                new Item(1L,"xxxx","yyyy","zzzz", "zxczxc", new Producer(1L, "xxx"), new Provider("xxxx"), new ItemCategory(1L, "xxxx")),
                new Item(1L,"xxxx","yyyy","zzzz", "zxczxc", new Producer(1L, "xxx"), new Provider("xxxx"), new ItemCategory(1L, "xxxx"))

                );
        //when
        when(itemRepository.findAll()).thenReturn(items);
        List<ItemDTO> result = itemService.findAll();
        //then
        assertThat(result, hasSize(items.size()));
        assertThat(result.get(0), instanceOf(ItemDTO.class));
    }

    @Test
    public void saveTest(){
        //given
        long id  = 1L;
        ItemDTO itemDTO = new ItemDTO(1L,"xxxx","yyyy","zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"));
        //when
        when(producerRepository.existsById(id)).thenReturn(true);
        when(providerRepository.existsById(id)).thenReturn(true);
        when(itemCategoryRepository.existsById(id)).thenReturn(true);
        when(errors.hasErrors()).thenReturn(false);
        when(itemRepository.save(dtoMapper.itemPOJO(itemDTO))).thenReturn(dtoMapper.itemPOJO(itemDTO));
        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get(), notNullValue());
        assertThat(result.get().getId(), not(0));
    }

    @Test
    public void saveTest_hasErrors(){
        //given
        long id  = 1L;
        ItemDTO itemDTO = new ItemDTO(1L,"xxxx","yyyy","zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"));
        //when
        when(errors.hasErrors()).thenReturn(true);
        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void saveTest_producerNotExists(){
        //given
        long id  = 1L;
        ItemDTO itemDTO = new ItemDTO(1L,"xxxx","yyyy","zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"));
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(producerRepository.existsById(id)).thenReturn(false);

        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void saveTest_providerNotExists(){
        //given
        long id  = 1L;
        ItemDTO itemDTO = new ItemDTO(1L,"xxxx","yyyy","zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"));
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(producerRepository.existsById(id)).thenReturn(true);
        when(providerRepository.existsById(id)).thenReturn(false);

        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void saveTest_itemCategoryNotExists(){
        //given
        long id  = 1L;
        ItemDTO itemDTO = new ItemDTO(1L,"xxxx","yyyy","zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"));
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(producerRepository.existsById(id)).thenReturn(true);
        when(providerRepository.existsById(id)).thenReturn(true);
        when(itemCategoryRepository.existsById(id)).thenReturn(false);

        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void updateTest(){
        //given
        long id  = 1L;
        ItemDTO itemDTO = new ItemDTO(id, "xxxx", "yyyy", "zzzz", "xxxxx",
        new ProducerDTO(), new ProviderDTO(), new ItemCategoryDTO());
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(producerRepository.findById(anyLong())).thenReturn(Optional.of(new Producer()));
        when(providerRepository.findById(anyLong())).thenReturn(Optional.of(new Provider()));
        when(itemCategoryRepository.findById(anyLong())).thenReturn(Optional.of(new ItemCategory()));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(new Item()));
        when(producerRepository.existsById(anyLong())).thenReturn(true);
        when(providerRepository.existsById(anyLong())).thenReturn(true);
        when(itemCategoryRepository.existsById(anyLong())).thenReturn(true);
        boolean result = itemService.update(id, itemDTO, errors);
        //then
        assertTrue(result);
    }

    @Test
    public void updateTest_hasErrors(){
        //given
        //when
        when(errors.hasErrors()).thenReturn(true);
        boolean result = itemService.update(1L, new ItemDTO(), errors);
        //then
        assertFalse(result);
    }

    @Test
    public void updateTest_ItemNotExists(){
        //given
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        boolean result = itemService.update(1L, new ItemDTO(), errors);
        //then
        assertFalse(result);
    }

    @Test
    public void updateTest_AnySupplyNotExists(){
        //given
        ItemDTO itemDTO = new ItemDTO(1L, "xxxx", "yyyy", "zzzz", "xxxxx",
                new ProducerDTO(), new ProviderDTO(), new ItemCategoryDTO());

        //when
        when(errors.hasErrors()).thenReturn(false);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(new Item()));
        when(producerRepository.existsById(anyLong())).thenReturn(false);
        when(providerRepository.existsById(anyLong())).thenReturn(true);
        when(itemCategoryRepository.existsById(anyLong())).thenReturn(true);
        boolean result = itemService.update(1L, itemDTO, errors);
        //then
        assertFalse(result);
    }
}