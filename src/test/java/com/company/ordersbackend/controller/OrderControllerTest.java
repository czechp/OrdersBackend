package com.company.ordersbackend.controller;

import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.service.OrderService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc()
class OrderControllerTest {

    @Autowired()
    MockMvc mockMvc;

    @MockBean()
    OrderService orderService;

    @Test()
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void deleteTest() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(
                delete("/api/order/{id}", 1L))
                .andExpect(status().isNoContent()
                );
    }

    @Test()
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void deleteTest_orderNotExists() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(orderService)
                .delete(anyLong());
        //then
        mockMvc.perform(
                delete("/api/order/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}