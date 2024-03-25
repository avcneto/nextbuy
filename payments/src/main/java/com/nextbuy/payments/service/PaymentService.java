package com.nextbuy.payments.service;

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


    public Payment registerPayment(Long idCarrinho, TypePayment typePayment) {
        if (typePayment == null) {
            throw new RuntimeException("Type Payment undefiened");
        }

        var cart = cartService.getCartById(idCarrinho);
        var payment = new Payment();

        payment.setIdCarrinho(cart.id());
        payment.setTotalValue(cart.total());

        if (TypePayment.PIX.equals(typePayment)) {
            payment.setPix(new Pix());
        } else {
            payment.setCard(cardService.getCardByIdUser(cart.userId()));
        }
        return repository.save(payment);

    }
}
