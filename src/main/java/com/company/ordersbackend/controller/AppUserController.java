package com.company.ordersbackend.controller;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.service.AppUserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
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

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid AppUserDTO appUserDTO, Errors errors, ServletRequest servletRequest){
        return appUserService.saveAppUser(appUserDTO, errors, servletRequest) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/activate-user")
    public ResponseEntity<String> activateUser(@RequestParam(name = "token")String token){
        return appUserService.activateAppUser(token) ? ResponseEntity.ok("Użytkownik aktywowany")
                : new ResponseEntity<>("Błąd podczas aktywacji", HttpStatus.NOT_FOUND);
    }
}
