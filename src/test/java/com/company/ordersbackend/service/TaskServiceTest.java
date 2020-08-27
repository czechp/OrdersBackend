package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.Task;
import com.company.ordersbackend.exception.BadInputDataException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import java.security.Principal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest()
@RunWith(SpringRunner.class)
class TaskServiceTest {
    @MockBean()
    TaskRepository taskRepository;
    TaskService taskService;
    Task task;
    @MockBean()
    private Errors errors;
    @MockBean()
    private Principal principal;
    @MockBean()
    AppUserService appUserService;

    @BeforeEach()
    public void init() {
        this.taskService = new TaskService(taskRepository, appUserService);
        this.task = new Task();
        task.setName("Any task");
        task.setAppUser(new AppUser());
    }

    @Test()
    public void save_Test() {
        //given
        //when
        when(taskRepository.save(any())).thenReturn(task);
        when(principal.getName()).thenReturn("user");
        when(appUserService.findAppUserByUsername(anyString())).thenReturn(Optional.of(new AppUser()));
        when(errors.hasErrors()).thenReturn(false);
        Task result = taskService.save(task, errors, principal);
        //then
        assertThat(result, instanceOf(Task.class));
        assertThat(result, notNullValue());
    }

    @Test()
    public void save_UserNotExistsTest(){
        //given
        //when
        when(principal.getName()).thenReturn("user");
        when(appUserService.findAppUserByUsername(any())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> taskService.save(task, errors, principal));
    }


    @Test()
    public void save_IncorrectDataTest() {
        //given
        //when
        when(errors.hasErrors()).thenReturn(true);
        //then
        assertThrows(BadInputDataException.class, () -> taskService.save(task, errors, principal));
    }
}