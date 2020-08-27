package com.company.ordersbackend.controller;

import com.company.ordersbackend.domain.Task;
import com.company.ordersbackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController()
@CrossOrigin("*")
@RequestMapping("/api/task")
public class TaskController {
    private TaskService taskService;

    @Autowired()
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Task> findAll(){
        return taskService.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Task save(@RequestBody Task task, Errors errors, Principal principal){
        return taskService.save(task, errors, principal);
    }
}
