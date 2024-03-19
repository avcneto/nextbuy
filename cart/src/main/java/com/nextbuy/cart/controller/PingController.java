package com.nextbuy.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public record PingController() {

    private static final String PONG = "pong";

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(PONG);
    }
}