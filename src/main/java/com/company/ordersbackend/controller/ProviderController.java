package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.ProviderDTO;
import com.company.ordersbackend.service.ProviderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
