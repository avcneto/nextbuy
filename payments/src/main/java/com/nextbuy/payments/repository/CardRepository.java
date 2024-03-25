package com.nextbuy.payments.repository;

import com.nextbuy.payments.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByIdUser(Long idUser);

}
