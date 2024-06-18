package com.mealapp.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mealApp/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {

    @Operation(summary = "Test endpoint")
    @GetMapping(path = "/test")
    public String hello() {
        return "Test successful";
    }
}
