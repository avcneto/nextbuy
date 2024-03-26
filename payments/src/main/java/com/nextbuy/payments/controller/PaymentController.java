package com.nextbuy.payments.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbuy.payments.controller.dto.CardDTO;
import com.nextbuy.payments.controller.dto.RegisterPaymentDTO;
import com.nextbuy.payments.domain.Card;
import com.nextbuy.payments.domain.Payment;
import com.nextbuy.payments.service.CardService;
import com.nextbuy.payments.service.CartService;
import com.nextbuy.payments.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CartService cartService;

    @PostMapping("/card")
    public ResponseEntity<Card> registerBankAccount(@Valid @RequestBody CardDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.registerCard(objectMapper.convertValue(dto, Card.class)));
    }

    @PostMapping("/register")
    public ResponseEntity<Payment> registerPayment(@Valid @RequestBody RegisterPaymentDTO dto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.registerPayment(dto));
    }

    @PatchMapping("/confirm")
    public ResponseEntity<Void> confirm(@Valid @RequestBody CartIdDTO dto) throws JsonProcessingException {
        cartService.finishCart(dto.idCart());
        return ResponseEntity.ok().build();
    }
}
