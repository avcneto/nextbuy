package com.nextbuy.payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbuy.payments.controller.dto.CardDTO;
import com.nextbuy.payments.domain.Card;
import com.nextbuy.payments.domain.Payment;
import com.nextbuy.payments.domain.TypePayment;
import com.nextbuy.payments.service.CardService;
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

    @PostMapping("/card")
    public ResponseEntity<Card> registerBankAccount(@Valid @RequestBody CardDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.registerCard(objectMapper.convertValue(dto, Card.class)));
    }

    @PostMapping("/{idCarrinho}/{typePayment}")
    public ResponseEntity<Payment> registerPayment(@PathVariable Long idCarrinho, @PathVariable TypePayment typePayment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.registerPayment(idCarrinho, typePayment));
    }
}
