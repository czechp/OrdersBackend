package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.model.*;
import com.company.ordersbackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ItemServiceTest {

    @Mock()
    private ItemAccessoryRepository itemAccessoryRepository;
    @Mock
    private ItemInOrderRepository itemInOrderRepository;
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

    private Item item;
    private ItemAccessory itemAccessory;
    private Producer producer;
    private Provider provider;
    private ItemCategory itemCategory;

    @BeforeEach
    public void init() {
        this.item = Item.builder()
                .id(1L)
                .name("name")
                .serialNumber("123-456-789")
                .description("any description")
                .url("www.google.pl")
                .producer(producer)
                .provider(provider)
                .itemCategory(itemCategory)
                .accessories(new ArrayList<>())
                .build();

        this.producer = Producer.builder().id(1l).name("Producer").build();
        this.provider = Provider.builder().id(1l).name("Provider").build();
        this.itemCategory = ItemCategory.builder().id(1l).name("ItemCategory").build();
        itemService = new ItemService(itemRepository, dtoMapper, producerRepository, providerRepository, itemCategoryRepository, itemInOrderRepository, itemAccessoryRepository);
        itemAccessory = ItemAccessory.builder()
                .id(1L)
                .name("name")
                .serialNumber("123-456-789")
                .description("any description")
                .url("www.google.pl")
                .producer(producer)
                .provider(provider)
                .itemCategory(itemCategory)
                .item(item)
                .build();
    }

    @Test
    public void findAllTest() {
        //given
        List<Item> items = Arrays.asList(
                new Item(1L, "xxxx", "yyyy", "zzzz", "zxczxc", new Producer(1L, "xxx"), new Provider("xxxx"), new ItemCategory(1L, "xxxx")),
                new Item(1L, "xxxx", "yyyy", "zzzz", "zxczxc", new Producer(1L, "xxx"), new Provider("xxxx"), new ItemCategory(1L, "xxxx")),
                new Item(1L, "xxxx", "yyyy", "zzzz", "zxczxc", new Producer(1L, "xxx"), new Provider("xxxx"), new ItemCategory(1L, "xxxx"))

        );
        //when
        when(itemRepository.findAll()).thenReturn(items);
        List<ItemDTO> result = itemService.findAll();
        //then
        assertThat(result, hasSize(items.size()));
        assertThat(result.get(0), instanceOf(ItemDTO.class));
    }

    @Test
    public void saveTest() {
        //given
        long id = 1L;
        ItemDTO itemDTO = new ItemDTO(1L, "xxxx", "yyyy", "zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"), new ArrayList<>());
        //when
        when(producerRepository.existsById(anyLong())).thenReturn(true);
        when(providerRepository.existsById(anyLong())).thenReturn(true);
        when(itemCategoryRepository.existsById(anyLong())).thenReturn(true);
        when(errors.hasErrors()).thenReturn(false);
        when(itemRepository.save(dtoMapper.itemPOJO(itemDTO))).thenReturn(dtoMapper.itemPOJO(itemDTO));
        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get(), notNullValue());
        assertThat(result.get().getId(), not(0));
    }

    @Test
    public void saveTest_hasErrors() {
        //given
        long id = 1L;
        ItemDTO itemDTO = new ItemDTO(1L, "xxxx", "yyyy", "zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"), new ArrayList<>());
        //when
        when(errors.hasErrors()).thenReturn(true);
        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void saveTest_producerNotExists() {
        //given
        long id = 1L;
        ItemDTO itemDTO = new ItemDTO(1L, "xxxx", "yyyy", "zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"), new ArrayList<>());
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(producerRepository.existsById(id)).thenReturn(false);

        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void saveTest_providerNotExists() {
        //given
        long id = 1L;
        ItemDTO itemDTO = new ItemDTO(1L, "xxxx", "yyyy", "zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"), new ArrayList<>());
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(producerRepository.existsById(id)).thenReturn(true);
        when(providerRepository.existsById(id)).thenReturn(false);

        Optional<ItemDTO> result = itemService.save(itemDTO, errors);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void saveTest_itemCategoryNotExists() {
        //given
        long id = 1L;
        ItemDTO itemDTO = new ItemDTO(1L, "xxxx", "yyyy", "zzzz", "zxczxc", new ProducerDTO(1L, "xxx"), new ProviderDTO("xxxx"), new ItemCategoryDTO(1L, "xxxx"), new ArrayList<>());
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
    public void updateTest() {
        //given
        long id = 1L;
        ItemDTO itemDTO = new ItemDTO(id, "xxxx", "yyyy", "zzzz", "xxxxx",
                new ProducerDTO(), new ProviderDTO(), new ItemCategoryDTO(), new ArrayList<>());
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
    public void updateTest_hasErrors() {
        //given
        //when
        when(errors.hasErrors()).thenReturn(true);
        boolean result = itemService.update(1L, new ItemDTO(), errors);
        //then
        assertFalse(result);
    }

    @Test
    public void updateTest_ItemNotExists() {
        //given
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        boolean result = itemService.update(1L, new ItemDTO(), errors);
        //then
        assertFalse(result);
    }

    @Test
    public void updateTest_AnySupplyNotExists() {
        //given
        ItemDTO itemDTO = new ItemDTO(1L, "xxxx", "yyyy", "zzzz", "xxxxx",
                new ProducerDTO(), new ProviderDTO(), new ItemCategoryDTO(), new ArrayList<>());

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

    @Test
    public void deleteTest() {
        //given
        long id = 1L;
        //when
        when(itemRepository.existsById(id)).thenReturn(true);
        doNothing().when(itemRepository).deleteById(anyLong());
        boolean result = itemService.delete(id);
        //then
        assertTrue(result);
        verify(itemRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteTest_ItemNotExists() {
        //given
        long id = 1L;
        //when
        when(itemRepository.existsById(id)).thenReturn(false);
        boolean result = itemService.delete(id);
        //then
        assertFalse(result);
        verify(itemRepository, times(0)).deleteById(id);
    }

    @Test
    public void convertItemIntoItemInOrder_Test() {
        //given
        long itemId = 1L;
        int amount = 100;
        //when
        when(itemRepository.findById(any())).thenReturn(Optional.of(new Item()));
        Optional<ItemInOrder> result = itemService.convertItemIntoItemInOrder(itemId, amount);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get(), instanceOf(ItemInOrder.class));
    }

    @Test
    public void convertItemIntoItemInOrder_itemNotExists() {
        //given
        long itemId = 1L;
        int amount = 100;
        //when
        when(itemRepository.findById(any())).thenReturn(Optional.empty());
        Optional<ItemInOrder> result = itemService.convertItemIntoItemInOrder(itemId, amount);
        //then
        assertFalse(result.isPresent());
    }


    @Test()
    public void createNewAccessoryTest() {
        //given
        long itemId = 1L;
        long accessoryId = 2L;
        //when
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemAccessoryRepository.save(any())).thenReturn(itemAccessory);
        ItemAccessoryDTO result = itemService.createNewAccessory(itemId, accessoryId);
        //then
        assertEquals(item.getSerialNumber(), result.getSerialNumber());
        assertThat(result, instanceOf(ItemAccessoryDTO.class));
    }

    @Test()
    public void createNewAccessoryTest_NotFound(){
        //given
        long itemId = 1L;
        long accessoryId = 2L;
        //when
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, ()->itemService.createNewAccessory(itemId, accessoryId));
    }

    @Test()
    public void deleteAccessoryTest(){
        //given
        long accessoryId = 1L;
        //when
        when(itemAccessoryRepository.existsById(anyLong())).thenReturn(true);
        itemService.deleteAccessory(accessoryId);
        //then
        verify(itemAccessoryRepository, times(1)).deleteById(accessoryId);
    }

    @Test()
    public void deleteAccessoryTest_NotFound(){
        //given
        long accessoryId = 1L;
        //when
        when(itemAccessoryRepository.findById(any())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, ()-> itemService.deleteAccessory(accessoryId));
    }
}