package com.nextbuy.cart.controller;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.dto.CartDTO;
import com.nextbuy.cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.lang.String.format;

@RestController
@RequestMapping("/cart")
public record CartController(
        CartService cartService
) {
    private static final String CART_ID_PATH = "/cart/%s";
    private static final String ID = "id";
    private static final String USER_ID = "userId";
    private static final String ALL = "all";
    private static final String TEN = "10";
    private static final String ZERO = "0";

    @PostMapping
    public ResponseEntity<Cart> createOrUpdateCart(@RequestBody @Valid CartDTO cartDTO){
        var cart = cartService.createOrUpdateCart(cartDTO);
        return ResponseEntity.created(URI.create(format(CART_ID_PATH, cart.getId()))).body(cart);
    }

//    @PostMapping
//    public ResponseEntity<Cart> addItemToCart(@RequestParam Long userId, @RequestParam Long itemId){
//        var cart = cartService.addItemToCart(userId, itemId);
//        return ResponseEntity.ok(cart);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Cart> removeItemFromCart(@RequestParam Long userId, @RequestParam Long itemId){
//        var cart = cartService.removeItemFromCart(userId, itemId);
//        return ResponseEntity.ok(cart);
//    }
//
//    @GetMapping(params = ID)
//    public ResponseEntity<Cart> getCartByUserId(@RequestParam Long userId){
//        var cart = cartService.getCartById(userId);
//        return ResponseEntity.ok(cart);
//    }
//
//    @PostMapping
//    public ResponseEntity<Cart> checkout(@RequestParam Long userId){
//        var cart = cartService.checkout(userId);
//        return ResponseEntity.ok(cart);
//    }

}