package com.videocartago.renting.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {
    @GetMapping("/hello")
    public String hello() {
        return "¡Hola soy un RestController de Spring Boot 2026  xx!";
    } 
}
