package com.marvic.factsigner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/")
public class AppHomeController {
    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("FactSigner Application (c) " + LocalDate.now().getYear() + " Marvic.");
    }
}
