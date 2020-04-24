package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ProducerDTO;
import com.company.ordersbackend.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<ProducerDTO> save(@RequestBody  @Valid ProducerDTO producerDTO, Errors errors){
        Optional<ProducerDTO> result = producerService.save(producerDTO, errors);
        return result.isPresent() ? ResponseEntity.ok(result.get()) : ResponseEntity.badRequest().build();
    }

    @PatchMapping(path="/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid ProducerDTO producerDTO){
        return null;
    }
}
