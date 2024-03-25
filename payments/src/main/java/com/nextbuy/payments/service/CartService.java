package com.nextbuy.payments.service;

import com.nextbuy.payments.controller.dto.CartDTO;
import com.nextbuy.payments.gateway.CartGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartGateway cartGateway;

    public CartDTO getCartById(Long idCart) {
        return cartGateway.getCart(idCart).orElseThrow(() -> new RuntimeException("Cart Not Found"));
    }

}
