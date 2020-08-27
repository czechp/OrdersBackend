package com.company.ordersbackend.controller;

import com.company.ordersbackend.domain.Task;
import com.company.ordersbackend.exception.BadInputDataException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc()
@RunWith(SpringRunner.class)
class TaskControllerTest {

    private final String url = "/api/task";

    @Autowired()
    MockMvc mockMvc;

    @MockBean()
    TaskService taskService;

    Task task;
    ObjectMapper objectMapper;

    @BeforeEach()
    public void init() {
        objectMapper = new ObjectMapper();
        this.task = new Task();
        task.setName("Any name");
    }

    @Test()
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void findAll_Test() throws Exception {
        //given
        //when
        when(taskService.findAll()).thenReturn(
                Arrays.asList(
                        new Task(),
                        new Task()
                )
        );
        //then
        mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test()
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void save_Test() throws Exception {
        //given
        //when
        when(taskService.save(any(), any(), any())).thenReturn(new Task());
        //then
        mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test()
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void save_IncorrectDataTest() throws Exception {
        //given
        task.setName("");
        //when
        doThrow(BadInputDataException.class)
                .when(taskService)
                .save(any(), any(), any());
        //then
        mockMvc.perform(
                post(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test()
    @WithMockUser(username = "user", password = "user", roles = "USER")
    public void save_UserNotExistsTest() throws Exception {
        //given
        //when
        doThrow(NotFoundException.class)
                .when(taskService)
                .save(any(), any(), any());
        //then
        mockMvc.perform(
                post(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}