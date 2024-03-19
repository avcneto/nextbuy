package com.nextbuy.cart.controller;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.dto.CartDTO;
import com.nextbuy.cart.service.CartService;
import com.nextbuy.cart.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static java.lang.String.format;

/*

    MÉTODOS NECESSÁRIOS
    - CRIAR CARRINHO
    - ATUALIZAR CARRINHO - REMOVER PRDUTO
    - REMOVER QNT ITEM
    - ADD QNT ITEM
    - TOTAL
    - EXCLUIR CARRINHO (REMOVER TDS PRODUTOS)

 */

@RestController
@RequestMapping("/cart")
public record CartController(CartService cartService) {
    private static final String CART_ID_PATH = "/cart/%s";
    private static final String ID = "id";
    private static final String ALL = "all";
    private static final String TEN = "10";
    private static final String ZERO = "0";
    @GetMapping(path = ALL)
    public ResponseEntity<Pagination<Cart>> getAllItems(
            @RequestParam(defaultValue = TEN) Integer limit,
            @RequestParam(defaultValue = ZERO) Integer offset) {
        var item = cartService.getAllItems(limit, offset);
        return ResponseEntity.ok(item);
    }

    @GetMapping(params = ID)
    public ResponseEntity<Cart> getCartById(@RequestParam long id){
        var cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody @Valid CartDTO cartDTO){
        var cart = cartService.createCart(cartDTO);
        return ResponseEntity.created(URI.create(format(CART_ID_PATH, cart.getId()))).body(cart);
    }
}