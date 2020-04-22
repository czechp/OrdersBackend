package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ProviderDTO;
import com.company.ordersbackend.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/provider")
public class ProviderController {
    private ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public List<ProviderDTO> findAll() {
        return providerService.findAll();
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> save(@RequestBody @Valid ProviderDTO  providerDTO, Errors errors){
        Optional<ProviderDTO> result = providerService.save(providerDTO, errors);
        return result.isPresent() ? new ResponseEntity(result.get(), HttpStatus.CREATED) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    public ResponseEntity<ProviderDTO> update(@RequestBody @Valid ProviderDTO  providerDTO, Errors errors){
        Optional<ProviderDTO> result = providerService.save(providerDTO, errors);
        return result.isPresent() ? new ResponseEntity(result.get(), HttpStatus.CREATED) : new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

}
