package com.company.ordersbackend.controller;

import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class AppUserController {
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<AppUserDTO> login(@RequestBody AppUserDTO appUserDTO) {
        Optional<AppUserDTO> appUser = appUserService.authorization(appUserDTO);
        return appUser.isPresent() ? ResponseEntity.ok(appUser.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid AppUserDTO appUserDTO, Errors errors, ServletRequest servletRequest) {
        return appUserService.saveAppUser(appUserDTO, errors, servletRequest) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/activate-user")
    public ResponseEntity<String> activateUser(@RequestParam(name = "token") String token) {
        return appUserService.activateAppUser(token) ? ResponseEntity.ok("Użytkownik aktywowany")
                : new ResponseEntity<>("Błąd podczas aktywacji", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/user/role/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppUserDTO changeRole(@PathVariable("id") long id, @RequestParam("role") String role, Principal principal) {
        return appUserService.changeRole(id, role, principal);
    }

    @DeleteMapping("user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id, Principal principal) {
        appUserService.delete(id, principal);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<AppUserDTO> findAll(Principal principal) {
        return appUserService.findAll(principal);
    }
}
