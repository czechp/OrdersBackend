package com.company.ordersbackend.controller;

import com.company.ordersbackend.domain.ItemBorrowed;
import com.company.ordersbackend.exception.BadInputDataException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.service.ItemBorrowedService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc()
class ItemBorrowedControllerTest {

    private final String url = "/api/itemBorrowed/";
    @Autowired()
    private MockMvc mockMvc;
    @MockBean()
    private ItemBorrowedService itemBorrowedService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test()
    @WithMockUser()
    public void save_Test() throws Exception {
        //given
        ItemBorrowed itemBorrowed = new ItemBorrowed();
        itemBorrowed.setAmount(12);
        //when
        when(itemBorrowedService.save(anyLong(), anyInt(), anyString(), any())).thenReturn(itemBorrowed);
        //then
        mockMvc.perform(
                post(url + "{id}", 1)
                        .param("amount", "12")
                        .param("receiver", "anyMan")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(12));
    }

    @Test()
    @WithMockUser()
    public void save_NotFoundTest() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(itemBorrowedService)
                .save(anyLong(), anyInt(), anyString(), any());
        //then
        mockMvc.perform(post(url + "{id}", 1)
                .param("amount", "12")
                .param("receiver", "anyMan")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    @Test()
    @WithMockUser()
    public void save_uncrossableEntityTest() throws Exception {
        //given
        //when
        doThrow(BadInputDataException.class)
                .when(itemBorrowedService)
                .save(anyLong(), anyInt(), anyString(), any());
        //then
        mockMvc.perform(post(url + "{id}", 1)
                .param("amount", "12")
                .param("receiver", "anyMan")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

    }

}