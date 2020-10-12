package com.company.ordersbackend.controller;

import com.company.ordersbackend.OrdersBackendApplication;
import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.service.DTOMapper;
import com.company.ordersbackend.service.ItemService;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = OrdersBackendApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc()
class ItemControllerTest {

    private final String url = "/api/item";

    @MockBean()
    ItemService itemService;

    @Autowired()
    MockMvc mockMvc;

    @Autowired()
    DTOMapper dtoMapper;

    private ItemAccessory itemAccessory;
    private Item item;
    private Producer producer;
    private Provider provider;
    private ItemCategory itemCategory;

    @BeforeEach()
    public void init() {
        this.producer = Producer.builder().id(1l).name("Producer").build();
        this.provider = Provider.builder().id(1l).name("Provider").build();
        this.itemCategory = ItemCategory.builder().id(1l).name("ItemCategory").build();
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

    @Test()
    @WithMockUser(roles = {"ADMIN", "USER"})
    void createNewAccessoryTest() throws Exception {
        //given

        //when
        when(itemService.createNewAccessory(anyLong(), anyLong())).thenReturn(dtoMapper.itemAccessoryDTO(itemAccessory));
        //then
        mockMvc.perform(
                get(url + "/{itemId}/accessory/{accessoryId}", 1L, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test()
    @WithMockUser(roles = {"ADMIN", "USER"})
    void createNewAccessoryTest_NotFound() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(itemService)
                .createNewAccessory(anyLong(), anyLong());
        //then
        mockMvc.perform(get(url + "/{itemId}/accessory/{accessoryId}", 1L, 1L)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}