package com.nextbuy.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nextbuy.payments.controller.dto.RegisterPaymentDTO;
import com.nextbuy.payments.domain.Payment;
import com.nextbuy.payments.domain.Pix;
import com.nextbuy.payments.domain.TypePayment;
import com.nextbuy.payments.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private CardService cardService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentRepository repository;


    public Payment registerPayment(RegisterPaymentDTO dto) throws JsonProcessingException {
        var cart = cartService.getCartById(dto.idCart());
        var payment = new Payment();

        payment.setIdCarrinho(cart.getId());
        payment.setTotalValue(cart.getTotal());

        if (TypePayment.PIX.equals(dto.typePayment())) {
            payment.setPix(new Pix());
        } else {
            payment.setCard(cardService.getCardByIdUser(cart.getUserId()));
        }

        return repository.save(payment);

    }
}
