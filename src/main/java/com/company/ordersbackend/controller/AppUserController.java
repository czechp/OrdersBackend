package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class AppUserController {
    private AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<AppUserDTO> login(@RequestBody AppUserDTO appUserDTO) {
        Optional<AppUserDTO> appUser = appUserService.authorization(appUserDTO);
        return appUser.isPresent() ? ResponseEntity.ok(appUser.get()) : ResponseEntity.notFound().build();
    }
}
