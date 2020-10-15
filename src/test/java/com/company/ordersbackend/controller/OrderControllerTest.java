package com.company.ordersbackend.controller;

import com.company.ordersbackend.exception.AccessDeniedException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.model.OrderDTO;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test()
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void changeCommentary() throws Exception {
        //given
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCommentary("Any text");
        //when
        when(orderService.modifyCommentary(anyLong(), anyString(), any())).thenReturn(orderDTO);
        //then
        mockMvc.perform(patch("/api/order/commentary/{id}", 1L)
                .param("commentary", "Any text")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentary").value("Any text"));
    }

    @Test()
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void changeCommentary_userNotExist() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(orderService)
                .modifyCommentary(anyLong(), anyString(), any());
        //then
        mockMvc.perform(patch("/api/order/commentary/{id}", 1L)
                .param("commentary", "any text")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test()
    @WithMockUser(roles = "SUPERUSER")
    void changeCommentaryBySuperUser_Test() throws Exception {
        //given
        OrderDTO orderDTO = new OrderDTO();
        //when
        when(orderService.addCommentaryBySuperUser(anyLong(), anyString(), anyString()))
                .thenReturn(orderDTO);
        //then
        mockMvc.perform(patch("/api/order/commentary/superuser/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("commentary", "Any text"))
                .andExpect(status().isOk());
    }

    @Test()
    @WithMockUser(roles = "SUPERUSER")
    void changeCommentaryBySuperUser_orderNotExistTest() throws Exception {
        //given
        OrderDTO orderDTO = new OrderDTO();
        //when
        doThrow(NotFoundException.class)
                .when(orderService)
                .addCommentaryBySuperUser(anyLong(), anyString(), anyString());
        //then
        mockMvc.perform(patch("/api/order/commentary/superuser/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("commentary", "Any text"))
                .andExpect(status().isNotFound());
    }

    @Test()
    @WithMockUser(roles = "SUPERUSER")
    void setOrderNr_Test() throws Exception {
        //given
        //when
        when(orderService.setOrderNr(anyLong(), anyString()))
                .thenReturn(new OrderDTO());
        //then
        mockMvc.perform(patch("/api/order/orderNr/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("orderNr", "1234567"))
                .andExpect(status().isOk());
    }


    @Test()
    @WithMockUser(roles = "SUPERUSER")
    void setOrderNr_badInputDataTest() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(orderService)
                .setOrderNr(anyLong(), anyString());
        //then
        mockMvc.perform(patch("/api/order/orderNr/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("orderNr", "123"))
                .andExpect(status().isBadRequest());
    }

    @Test()
    @WithMockUser(roles = {"USER"})
    void addItemToOrderFromAccessoriesTest() throws Exception {
        //given

        //when
        when(orderService.addItemToOrderFromAccessories(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(new OrderDTO());
        //then
        mockMvc.perform(get("/api/order/{orderId}/accessory/{accessoryId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("amount", "10"))
                .andExpect(status().isOk());
    }


    @Test()
    @WithMockUser(roles = {"USER"})
    void addItemToOrderFromAccessoriesTest_AccessDenied() throws Exception {
        //given

        //when
        doThrow(AccessDeniedException.class)
                .when(orderService)
                .addItemToOrderFromAccessories(anyLong(), anyLong(), anyInt(), anyString());
        //then
        mockMvc.perform(get("/api/order/{orderId}/accessory/{accessoryId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("amount", "10"))
                .andExpect(status().isForbidden());
    }


    @Test()
    @WithMockUser(roles = {"USER"})
    void addItemToOrderFromAccessoriesTest_NotFound() throws Exception {
        //given

        //when
        doThrow(NotFoundException.class)
                .when(orderService)
                .addItemToOrderFromAccessories(anyLong(), anyLong(), anyInt(), anyString());
        //then
        mockMvc.perform(get("/api/order/{orderId}/accessory/{accessoryId}", 1L, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("amount", "10"))
                .andExpect(status().isNotFound());
    }

}