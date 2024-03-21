package com.nextbuy.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class Ping {

  private static final String PONG = "pong";

  @GetMapping
  public ResponseEntity<String> ping() {
    return ResponseEntity.ok(PONG);
  }
}
