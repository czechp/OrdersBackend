package com.company.ordersbackend.controller;

import com.company.ordersbackend.exception.AccesDeniedException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.service.AppUserService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc()
class AppUserControllerTest {

    @Autowired()
    private MockMvc mockMvc;

    @MockBean()
    AppUserService appUserService;

    @Test()
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void changeRoleTest() throws Exception {
        //given
        AppUserDTO appUserDTO = new AppUserDTO(
                1L,
                "admin",
                "admin",
                "ADMIN",
                "anyEmail@gmail.com"
        );
        //when
        when(appUserService.changeRole(anyLong(), anyString(), any())).thenReturn(appUserDTO);
        //then
        mockMvc.perform(
                patch("/api/user/role/{id}", 1)
                        .param("role", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(appUserDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test()
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void deleteTest() throws Exception {
        //given
        //when
        doNothing().when(appUserService).delete(anyLong(), any());
        //then
        mockMvc.perform(
                delete("/api/user/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test()
    @WithMockUser(username = "user", password = "user", roles = "ADMIN")
    public void deleteTest_isNotAdmin() throws Exception {
        //given
        //when
        doThrow(AccesDeniedException.class)
                .when(appUserService)
                .delete(anyLong(), any());
        //then
        mockMvc.perform(
                delete("/api/user/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test()
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void deleteTest_userNotExists() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(appUserService)
                .delete(anyLong(), any());
        //then
        mockMvc.perform(
                delete("/api/user/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}