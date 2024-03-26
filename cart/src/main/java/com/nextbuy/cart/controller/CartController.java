package com.nextbuy.cart.controller;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.domain.Item;
import com.nextbuy.cart.dto.AddItemDTO;
import com.nextbuy.cart.dto.CartDTO;
import com.nextbuy.cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<Cart> createCart(@RequestBody @Valid CartDTO cartDTO) {
    var cart = cartService.createCart(cartDTO);
    return ResponseEntity.created(URI.create(format(CART_ID_PATH, cart.getId()))).body(cart);
  }

  @PostMapping("/add")
  public ResponseEntity<Cart> addItemToCart(@RequestBody AddItemDTO addItemDTO) {
    var cart = cartService.addItemToCart(addItemDTO);
    return ResponseEntity.ok(cart);
  }

  @DeleteMapping
  public ResponseEntity<Void> removeItemFromCart(@RequestParam Long cartId, @RequestParam String itemId) {
    cartService.removeItemFromCart(cartId, itemId);
    return ResponseEntity.ok().build();
  }

    @GetMapping(params = ID)
    public ResponseEntity<Cart> getCartById(@RequestParam Long id){
        var cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }


//
//    @PostMapping
//    public ResponseEntity<Cart> checkout(@RequestParam Long userId){
//        var cart = cartService.checkout(userId);
//        return ResponseEntity.ok(cart);
//    }

}