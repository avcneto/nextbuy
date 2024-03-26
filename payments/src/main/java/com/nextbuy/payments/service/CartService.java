package com.nextbuy.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nextbuy.payments.domain.Cart;
import com.nextbuy.payments.gateway.CartGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartGateway cartGateway;

    public Cart getCartById(Long idCart) throws JsonProcessingException {
        return cartGateway.getCart(idCart).orElseThrow(() -> new RuntimeException("Cart Not Found"));
    }

    public void finishCart(Long idCart) throws JsonProcessingException {
        cartGateway.patchCart(getCartById(idCart).getId());
    }
}
