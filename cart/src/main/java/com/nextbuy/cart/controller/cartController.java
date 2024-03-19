package com.nextbuy.cart.controller;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public record cartController(CartService cartService) {

    @PostMapping
    public ResponseEntity<Cart> createCart()
}

    @GetMapping
    public ResponseEntity<Optional<Cart>> getCartById(@RequestParam long id){
        var cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);;
    }
