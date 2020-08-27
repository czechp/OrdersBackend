package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Task;
import com.company.ordersbackend.exception.BadInputDataException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.security.Principal;
import java.util.List;

@Service()
public class TaskService {
    private TaskRepository taskRepository;
    private AppUserService appUserService;

    @Autowired()
    public TaskService(TaskRepository taskRepository, AppUserService appUserService) {
        this.taskRepository = taskRepository;
        this.appUserService = appUserService;
    }


    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public Task save(Task task, Errors errors, Principal principal) {
        if(!errors.hasErrors()){
            task.setAppUser(appUserService.findAppUserByUsername(principal.getName()).orElseThrow(()->new NotFoundException("user --- " + principal.getName())));
            return taskRepository.save(task);
        }
        throw new BadInputDataException("");
    }
}
