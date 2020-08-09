package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

}