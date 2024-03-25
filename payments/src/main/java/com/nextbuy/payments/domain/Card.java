package com.nextbuy.payments.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Table(name = "card")
@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUser;

    private String nameOnCard;

    private Long numberCard;

    private Integer cvc;

    private Date dueDate;

    @JoinColumn(name = "payment_id")
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Payment payment;


}
