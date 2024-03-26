package com.nextbuy.payments.service;

import com.nextbuy.payments.domain.Card;
import com.nextbuy.payments.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public Card registerCard(Card card) {
        return repository.save(card);
    }

    public Card getCardByIdUser(Long idUser) {
        return repository.findByIdUser(idUser).orElseThrow(() -> new RuntimeException("Card not found"));
    }
}
