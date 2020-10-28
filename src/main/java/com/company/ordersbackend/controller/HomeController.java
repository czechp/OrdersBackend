package com.company.ordersbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/start")
@CrossOrigin("*")
public class HomeController {

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public void applicationStartingConfirmation() {

    }
}
