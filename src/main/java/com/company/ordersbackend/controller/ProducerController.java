package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ProducerDTO;
import com.company.ordersbackend.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/producer")
public class ProducerController {
    private ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProducerDTO> findAll() {
        return producerService.findAll();
    }
}
